package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
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

    private final double path_finding_timer_max = 0.3;

    private double path_finding_timer = 0;

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
        return PacMan.INSTANCE.isEnergized() ? 2.9 : 3.8;
    }




    public void getPathCLYDE(MazeConfig mazeConfig)
    {
        // Random
        RealCoordinates nextPos = pos.plus(DirectionUtils.getVector(direction));
        IntCoordinates nextCell = nextPos.round();

        RealCoordinates currPost = pos.plus(DirectionUtils.getVector(Direction.NONE));
        IntCoordinates currCell = currPost.round();

        // Handling cases if there's a wall or if in intersection and if the timer of random direction ended
        boolean bool = mazeConfig.getCell(nextCell).isWall() || mazeConfig.isIntersection(currCell);
        int[] base_out_coordinates = {10, 9};
        if(currCell.x() == base_out_coordinates[0] && currCell.y() == base_out_coordinates[1])
        {
            direction = Direction.NORTH;
        }
        else if (bool || direction == Direction.NONE)// adding also if direction is none, so it doesn't stop moving
        {
            ArrayList<Direction> direcs = new ArrayList<>();
            for (Direction DIR : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST})
            {
                nextPos = pos.plus(DirectionUtils.getVector(DIR));
                nextCell = nextPos.round();
                if(!mazeConfig.getCell(nextCell).isWall())
                {
                    direcs.add(DIR);
                }
            }
            int choice = new Random().nextInt(direcs.size());
            direction = direcs.get(choice);
        }
    }

    public void getPath(Cell[][] grid, MazeConfig mazeConfig, long delta, RealCoordinates base_coord)
    {
        double delta_double = (double) delta;
        path_finding_timer -= delta_double / 1000000000;
        if((!eaten && path_finding_timer <= 0) || direction == Direction.NONE)
        {
            // Debug.out("Start of getPath of " + this);
            // Getting the path to follow.

            path_finding_timer = path_finding_timer_max;

            RealCoordinates pac_pos = PacMan.INSTANCE.getPos();
            // Direct chase, goes in front, goes in front, random
            if (this == BLINKY)
            {
                path = Node.getPath(this.pos, pac_pos, grid);
                Node.printArrayRC(path);
            }
            else if (this == INKY || this == PINKY)
            {
                // goes in front
                for (Direction DIR : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST})
                {
                    // Depending on the direction pacman is going and if there's a wall at that position,
                    // Choose a path
                    if (PacMan.INSTANCE.getDirection() == DIR)
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
                if (PacMan.INSTANCE.getDirection() == Direction.NONE)
                {
                    path = Node.getPath(this.pos, pac_pos, grid);
                }
            }
            else if (this == CLYDE)
            {
                getPathCLYDE(mazeConfig);
            }
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
        }

    }



    public void followPath()
    {

        if(!path.isEmpty())
        {
            Debug.out("Start of followPath of " + this);
            RealCoordinates to_follow = path.get(0);
            Debug.out(pos.round() + " == " + to_follow.round());
            if (pos.round().same(to_follow.round()))
            {
                Debug.out("deleting " + path.get(0));
                path.remove(0);
                followPath();
                return;
            }
            else
            {
                if (to_follow.round().x() == pos.round().x())
                {
                    if(to_follow.y() > pos.y())
                    {
                        direction = Direction.SOUTH;
                    }
                    else
                    {
                        direction = Direction.NORTH;
                    }
                }
                else if (to_follow.round().y() == pos.round().y())
                {
                    if(to_follow.x() > pos.x())
                    {
                        direction = Direction.EAST;
                    }
                    else
                    {
                        direction = Direction.WEST;
                    }
                }
            }
        }
    }
}