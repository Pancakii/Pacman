package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.PacmanController;
import misc.Debug;
import pathfinding.Node;

import java.util.ArrayList;
import java.util.Random;

public enum Ghost implements Critter {

    BLINKY, INKY, PINKY, CLYDE;
    // Direct chase, goes in front, goes in front, random

    public boolean eaten = false;
    private ArrayList<RealCoordinates> path = new ArrayList<>();
    private RealCoordinates pos;
    private Direction direction = Direction.NONE;
    private final double direction_timer_max = 10;
    private double direction_timer = 0;

    private final double path_finding_timer_max = 2;

    private double path_finding_timer = path_finding_timer_max;

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public void setPos(RealCoordinates newPos) {
        pos = newPos;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    @Override
    public double getSpeed() {
        return PacMan.INSTANCE.isEnergized() ? 3.8 : 1.9;
    }

    public void getPath(Cell[][] grid, MazeConfig mazeConfig, long delta, RealCoordinates base_coord)
    {

        double delta_double = (double) delta;
        path_finding_timer -= delta_double / 1000000000;
        if(!eaten && path_finding_timer <= 0)
        {
            //Debug.out("Start of getPath of " + this);
            // Getting the path to follow.

            path_finding_timer = path_finding_timer_max;

            RealCoordinates pac_pos = PacMan.INSTANCE.getPos();
            if (this == BLINKY)
            {
                // Direct chase, goes in front, goes in front, random
                path = Node.getPath(this.pos, pac_pos, grid);
            }
            else if (this == INKY || this == PINKY)
            {
                // goes in front
                for (Direction DIR : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST})
                {
                    // Depending on the direction pacman is going and if there's a wall at that position,
                    // Choose a path
                    if (PacmanController.getNewDirection() == DIR)
                    {
                        RealCoordinates nextPos = PacMan.INSTANCE.getPos().plus(DirectionUtils.getVector(DIR));
                        IntCoordinates nextCell = nextPos.round();
                        if (!mazeConfig.getCell(nextCell).isWall())
                        {
                            path = Node.getPath(this.pos, nextPos, grid);
                        }
                        else
                        {
                            path = Node.getPath(this.pos, pac_pos, grid);
                        }
                        break;
                    }
                }
                if (PacmanController.getNewDirection() == Direction.NONE)
                {
                    path = Node.getPath(this.pos, pac_pos, grid);
                }
            }
            else if (this == CLYDE)
            {
                // Random
                RealCoordinates nextPos = pos.plus(DirectionUtils.getVector(direction));
                IntCoordinates nextCell = nextPos.round();

                direction_timer -= delta_double / 1000000000;

                // Handling cases if there's a wall or if the timer of random direction ended
                boolean bool = (new Random().nextInt() * 10) <= direction_timer || direction_timer <= 0;
                if (mazeConfig.getCell(nextCell).isWall() || bool)
                {
                    ArrayList<Direction> direcs = new ArrayList<>();
                    for (Direction DIR : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}) {
                        if (direction != DIR)
                        {
                            direcs.add(DIR);
                        }
                    }
                    int choice = new Random().nextInt(direcs.size());
                    direction = direcs.get(choice);
                    direction_timer = direction_timer_max;
                }
            }
            else
            {
                // If eaten, go to the base
                path = Node.getPath(this.pos, pac_pos, grid);
            }
            //Debug.out("End of getPath of " + this);
        }
        else
        {
            if(path_finding_timer <= 0)
            {
                path_finding_timer = path_finding_timer_max;
                path = Node.getPath(this.pos, base_coord, grid);
            }
            if(path.size() <= 1)
            {
                eaten = false;
            }
            //Debug.out("End of getPath of " + this);
        }

    }



    public void followPath()
    {

        if(!path.isEmpty())
        {
            //Debug.out("Start of followPath of " + this);
            RealCoordinates to_follow = path.get(0);
            if (pos.round().same(to_follow.round()))
            {
                path.remove(0);
                // may add here followPath but im scared of infinite recursion
            }
            else
            {
                if (to_follow.x() == pos.x())
                {
                    if(to_follow.y() > pos.y())
                    {
                        direction = Direction.NORTH;
                    }
                    else
                    {
                        direction = Direction.SOUTH;
                    }
                }
                else if (to_follow.y() == pos.y())
                {
                    if(to_follow.x() > pos.x())
                    {
                        direction = Direction.WEST;
                    }
                    else
                    {
                        direction = Direction.EAST;
                    }
                }
                else
                {

                }
            }
            //Debug.out("End of followPath of " + this);
        }

    }



}