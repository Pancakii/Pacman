package pathfinding;


import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import model.Direction;
import model.DirectionUtils;
import model.Ghost;
import model.PacMan;

import java.util.ArrayList;
import java.util.Random;

public class Ghost_pf
{

    public void scatterChaseTimer(Ghost ghost, long d)
    {
        // sets one of the values to 0 and the other to the max, or just does time stuff
        double delta = (double) d;
        // Work on either chase or scatter timer, depending on values
        if(ghost.chase_timer > 0)
        {
            ghost.chase_timer -= delta;
            if (ghost.chase_timer <= 0)
            {
                // If enters this if block, it means its scatter's turn
                ghost.chase_timer = 0;
                ghost.scatter_timer = ghost.scatter_timer_max;
            }
        }
        else
        {
            ghost.scatter_timer -= delta;
            if (ghost.scatter_timer <= 0)
            {
                // If enters this if block, it means its chase's turn
                ghost.scatter_timer = 0;
                ghost.chase_timer = ghost.chase_timer_max;
            }
        }
    }

    public boolean can_it_find_path(Ghost ghost)
    {
        int pellet_count = PacMan.getCountDotTotal();
        return switch (ghost) {
            case BLINKY, PINKY -> true;
            case INKY -> pellet_count > 30;
            case CLYDE -> pellet_count > 60;
        };
    }

    private void getPathBLINKY(Ghost ghost, Cell[][] grid)
    {
        // Direct chase
        RealCoordinates pac_pos = PacMan.INSTANCE.getPos();
        ghost.path = Node.getPath(ghost.pos, pac_pos, grid);
    }

    private void getPathINKYPINKY(Ghost ghost, Cell[][] grid, MazeConfig mazeConfig)
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
                    ghost.path = Node.getPath(ghost.pos, nextPos, grid);
                }
                else
                {
                    ghost.path = Node.getPath(ghost.pos, pac_pos, grid);
                }
                break;
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.NONE)
        {
            ghost.path = Node.getPath(ghost.pos, pac_pos, grid);
        }
    }

    private void getPathCLYDE(Ghost ghost, MazeConfig mazeConfig)
    {
        // Random
        RealCoordinates nextPos = ghost.pos.plus(DirectionUtils.getVector(ghost.direction));
        IntCoordinates nextCell = nextPos.round();

        RealCoordinates currPost = ghost.pos.plus(DirectionUtils.getVector(Direction.NONE));
        IntCoordinates currCell = currPost.round();

        // Handling cases if there's a wall or if in intersection and if the timer of random direction ended
        boolean bool = mazeConfig.getCell(nextCell).isWall() || mazeConfig.isIntersection(currCell) && ghost.path_finding_timer <= 0;
        int[] base_out_coordinates = {10, 9};
        if (currCell.x() == base_out_coordinates[0] && currCell.y() == base_out_coordinates[1])
        {
            ghost.direction = Direction.NORTH;
        }
        else if (bool || ghost.direction == Direction.NONE)// adding also if direction is none, so it doesn't stop moving
        {
            ArrayList<Direction> direcs = new ArrayList<>();
            for (Direction DIR : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST})
            {
                nextPos = ghost.pos.plus(DirectionUtils.getVector(DIR));
                nextCell = nextPos.round();
                if (!mazeConfig.getCell(nextCell).isWall())
                {
                    direcs.add(DIR);
                }
            }
            int choice = new Random().nextInt(direcs.size());
            ghost.direction = direcs.get(choice);
        }
    }

    private void normalPath(Ghost ghost, Cell[][] grid, MazeConfig mazeConfig)
    {
        // Follow pacman using own behavior
        ghost.path_finding_timer = ghost.path_finding_timer_max;

        // If chase time, then chase pacman. If not, move randomly around the map
        if(ghost.chase_timer > 0)
        {
            switch (ghost) {
                case BLINKY:
                    getPathBLINKY(ghost, grid);
                    break;
                case INKY, PINKY:
                    getPathINKYPINKY(ghost, grid, mazeConfig);
                    break;
                case CLYDE:
                    getPathCLYDE(ghost, mazeConfig);
                    break;
            }
        }
        else
        {
            ghost.resetPath();
            getPathCLYDE(ghost, mazeConfig);
        }
    }

    private void frightenedPath(Ghost ghost, MazeConfig mazeConfig)
    {
        // Run away from pacman
        ghost.path_finding_timer = ghost.path_finding_timer_max;
        ghost.resetPath();
        getPathCLYDE(ghost, mazeConfig);
    }

    private void eatenPath(Ghost ghost, Cell[][] grid, RealCoordinates base_coord)
    {
        // Return home because eaten
        if (ghost.path_finding_timer <= 0)
        {
            // Get path to the base and renew timer
            ghost.path_finding_timer = ghost.path_finding_timer_max;
            ghost.path = Node.getPath(ghost.pos, base_coord, grid);
        }
        if (ghost.path.isEmpty())
        {
            // Either arrived at base or Pacman follow location
            // If arrived to the base, set eaten and can be eaten false
            if(ghost.pos.round().same(base_coord.round()))
            {
                if(ghost.eaten)
                {
                    ghost.frightened = false;
                }
                ghost.eaten = false;
            }
        }
    }

    public void getPath(Ghost ghost, Cell[][] grid, MazeConfig mazeConfig, long delta, RealCoordinates base_coord)
    {
        double delta_double = (double) delta;
        ghost.path_finding_timer -= delta_double / 1000000000;
        boolean bool = ghost.path_finding_timer <= 0 || ghost.direction == Direction.NONE || ghost.eaten || ghost.frightened;
        // If not eaten, cant be eaten, the timer is up or the direction is none, find path to Pacman
        // Also be allowed to get out of base
        if(bool && can_it_find_path(ghost))
        {
            if (!ghost.eaten && !ghost.frightened)
            {
                normalPath(ghost, grid, mazeConfig);
            }
            else if(ghost.frightened)
            {
                frightenedPath(ghost, mazeConfig);
            }
            else
            {
                eatenPath(ghost, grid, base_coord);
            }
        }
    }



    public void followPath(Ghost ghost)
    {
        if(!ghost.path.isEmpty())
        {
            RealCoordinates to_follow = ghost.path.get(0);
            if (ghost.pos.round().same(to_follow.round()))
            {
                // If in desired location, to pass to the other one we remove the current one
                ghost.path.remove(0);
                followPath(ghost);
            }
            else
            {
                // To find out where to move, we check x and y values.
                if (to_follow.round().x() == ghost.pos.round().x())
                {
                    if(to_follow.y() > ghost.pos.y())
                    {
                        ghost.direction = Direction.SOUTH;
                    }
                    else
                    {
                        ghost.direction = Direction.NORTH;
                    }
                }
                else if (to_follow.round().y() == ghost.pos.round().y())
                {
                    if(to_follow.x() > ghost.pos.x())
                    {
                        ghost.direction = Direction.EAST;
                    }
                    else
                    {
                        ghost.direction = Direction.WEST;
                    }
                }
            }
        }
    }
}