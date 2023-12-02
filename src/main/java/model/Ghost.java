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
    public boolean frightened = false;
    private ArrayList<RealCoordinates> path = new ArrayList<>();
    private RealCoordinates pos;
    private Direction direction = Direction.NONE;

    private final double path_finding_timer_max = 0.3;
    private double path_finding_timer = 0;

    private final double scatter_timer_max = 7;
    private double scatter_timer = scatter_timer_max;

    private final double chase_timer_max = 20;
    private double chase_timer = 0;

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

    public void resetPath()
    {
        path = new ArrayList<>();
    }

    @Override
    public double getSpeed() {
        double res = PacMan.INSTANCE.isEnergized() ? 2.0 : 2.8;
        if(eaten)
        {
            res = 8;
        }
        return res;
    }

    public void scatterChaseTimer(long d)
    {
        // sets one of the values to 0 and the other to the max, or just does time stuff
        double delta = (double) d;
        // Work on either chase or scatter timer, depending on values
        if(chase_timer > 0)
        {
            chase_timer -= delta;
            if (chase_timer <= 0)
            {
                // If enters this if block, it means its scatter's turn
                chase_timer = 0;
                scatter_timer = scatter_timer_max;
            }
        }
        else
        {
            scatter_timer -= delta;
            if (scatter_timer <= 0)
            {
                // If enters this if block, it means its chase's turn
                scatter_timer = 0;
                chase_timer = chase_timer_max;
            }
        }
    }

    public boolean can_it_find_path()
    {
        int pellet_count = PacMan.getCountDotTotal();
        return switch (this) {
            case BLINKY, PINKY -> true;
            case INKY -> pellet_count > 30;
            case CLYDE -> pellet_count > 60;
        };
    }

    private void getPathBLINKY(Cell[][] grid)
    {
        // Direct chase
        RealCoordinates pac_pos = PacMan.INSTANCE.getPos();
        path = Node.getPath(this.pos, pac_pos, grid);
    }

    private void getPathINKYPINKY(Cell[][] grid, MazeConfig mazeConfig)
    {
        RealCoordinates pac_pos = PacMan.INSTANCE.getPos();
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

    private void getPathCLYDE(MazeConfig mazeConfig)
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

    private void normalPath(Cell[][] grid, MazeConfig mazeConfig)
    {
        // Follow pacman using own behavior
        path_finding_timer = path_finding_timer_max;

        // If chase time, then chase pacman. If not, move randomly around the map
        if(chase_timer > 0)
        {
            Debug.out(this + " follows pacman");
            switch (this) {
                case BLINKY:
                    getPathBLINKY(grid);
                    break;
                case INKY, PINKY:
                    getPathINKYPINKY(grid, mazeConfig);
                    break;
                case CLYDE:
                    getPathCLYDE(mazeConfig);
                    break;
            }
        }
        else
        {
            Debug.out(this + " goes randomly");
            getPathCLYDE(mazeConfig);
        }
    }

    private void frightenedPath(MazeConfig mazeConfig)
    {
        Debug.out(this + " runs away from pacman");
        // Run away from pacman
		
        path_finding_timer = path_finding_timer_max;
        RealCoordinates pacpos = PacMan.INSTANCE.getPos();
        if (pacpos.round().x() == pos.round().x())
        {
            if(pacpos.y() < pos.y())
            {
                direction = Direction.SOUTH;
            }
            else
            {
                direction = Direction.NORTH;
            }
        }
        else if (pacpos.round().y() == pos.round().y())
        {
            if(pacpos.x() < pos.x())
            {
                direction = Direction.EAST;
            }
            else
            {
                direction = Direction.WEST;
            }
        }
        RealCoordinates next = pos.plus(DirectionUtils.getVector(direction));
        IntCoordinates nextcell = next.round();
        if(mazeConfig.getCell(nextcell).isWall())
        {
            // If about to go towards a wall, go random instead
            getPathCLYDE(mazeConfig);
        }
    }

    private void eatenPath(Cell[][] grid, RealCoordinates base_coord)
    {
        // Return home because eaten
        Debug.out(this + " returns to the base bc:\n" + "eaten = " + eaten + "\ncan be eaten: " + frightened + "\npath_finding_timer: " + path_finding_timer);
        if (path_finding_timer <= 0)
        {
            // Get path to the base and renew timer
            path_finding_timer = path_finding_timer_max;
            path = Node.getPath(this.pos, base_coord, grid);
        }
        if (path.isEmpty())
        {
			// Either arrived at base or Pacman follow location
            // If arrived to the base, set eaten and can be eaten false
			if(pos.round().same(base_coord.round()))
			{
				if(eaten)
				{
					frightened = false;
				}
				eaten = false;
			}
        }
    }

    public void getPath(Cell[][] grid, MazeConfig mazeConfig, long delta, RealCoordinates base_coord)
    {
        Debug.out(this + "\nfrightened: " + frightened + "\neaten: " + eaten);
        double delta_double = (double) delta;
        path_finding_timer -= delta_double / 1000000000;
        boolean bool = path_finding_timer <= 0 || direction == Direction.NONE || eaten || frightened;
        // If not eaten, cant be eaten, the timer is up or the direction is none, find path to Pacman
        // Also be allowed to get out of base
        if(bool && can_it_find_path())
        {
            if (!eaten && !frightened)
            {
                normalPath(grid, mazeConfig);
            }
            else if(frightened)
            {
                frightenedPath(mazeConfig);
            }
            else
            {
                eatenPath(grid, base_coord);
            }
        }
    }



    public void followPath()
    {
        if(!path.isEmpty())
        {
            RealCoordinates to_follow = path.get(0);
            if (pos.round().same(to_follow.round()))
            {
                // If in desired location, to pass to the other one we remove the current one
                path.remove(0);
                followPath();
            }
            else
            {
                // To find out where to move, we check x and y values.
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