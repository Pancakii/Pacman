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

    /**
     * Handles scatter/chase timers for the given ghost.
     * @param ghost the said ghost
     * @param d delta time
     */
    public static void scatterChaseTimer(Ghost ghost, long d)
    {
        // sets one of the values to 0 and the other to the max, or just does time stuff
        double delta = (double) d;
        delta = delta/1000000000;
        // Work on either chase or scatter timer, depending on values
        if(ghost.getChase_timer() > 0)
        {
            ghost.setChase_timer(ghost.getChase_timer() - delta);
            if (ghost.getChase_timer() <= 0)
            {
                // If enters this if block, it means its scatter's turn
                ghost.setChase_timer(0);
                ghost.setScatter_timer(ghost.getScatter_timer_max());
            }
        }
        else
        {
            ghost.setScatter_timer(ghost.getScatter_timer() - delta);
            if (ghost.getScatter_timer() <= 0)
            {
                // If enters this if block, it means its chase's turn
                ghost.setScatter_timer(0);
                ghost.setChase_timer(ghost.getChase_timer_max());
            }
        }
    }

    /**
     * Decides whether the given ghost can find path or not. Used for getting out of the ghost base one by one.
     * @param ghost the said ghost
     * @return boolean
     */
    private static boolean can_it_find_path(Ghost ghost, long d)
    {
        double delta = (double) d;
        delta = delta/1000000000;
        if(ghost.getGet_out_timer() <= 15)
        {
            ghost.setGet_out_timer(ghost.getGet_out_timer() + delta);
        }
        int pellet_count = PacMan.getCountDotTotal();
        return switch (ghost) {
            case BLINKY -> true;
            case PINKY -> ghost.getGet_out_timer() > 5;
            case INKY -> pellet_count > 30 && ghost.getGet_out_timer() > 10;
            case CLYDE -> pellet_count > 60 && ghost.getGet_out_timer() > 15;
        };
    }

    /**
     * Gives the given ghost BLINKY type path, direct chase to PacMan.
     * @param ghost the said ghost
     * @param grid the cell table
     */
    private static void getPathBLINKY(Ghost ghost, Cell[][] grid)
    {
        // Direct chase
        RealCoordinates pac_pos = PacMan.INSTANCE.getPos();
        ghost.setPath(Node.getPath(ghost.getPos(), pac_pos, grid));
    }

    /**
     * Gets the given ghost INKY/PINKY type path, cutting 1 cell in front of PacMan.
     * If not possible, direct chase.
     * @param ghost the said ghost
     * @param grid the cell table
     * @param mazeConfig mazeconfig
     */
    private static void getPathINKYPINKY(Ghost ghost, Cell[][] grid, MazeConfig mazeConfig)
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
                    ghost.setPath(Node.getPath(ghost.getPos(), nextPos, grid));
                }
                else
                {
                    ghost.setPath(Node.getPath(ghost.getPos(), pac_pos, grid));
                }
                break;
            }
        }
        if (PacMan.INSTANCE.getDirection() == Direction.NONE)
        {
            ghost.setPath(Node.getPath(ghost.getPos(), pac_pos, grid));
        }
    }

    /**
     * Gets the given ghost a CLYDE type path, random.
     * @param ghost the said ghost
     * @param mazeConfig mazeconfig
     */
    private static void getPathCLYDE(Ghost ghost, MazeConfig mazeConfig)
    {
        // Random
        RealCoordinates nextPos = ghost.getPos().plus(DirectionUtils.getVector(ghost.getDirection()));
        IntCoordinates nextCell = nextPos.round();

        RealCoordinates currPost = ghost.getPos().plus(DirectionUtils.getVector(Direction.NONE));
        IntCoordinates currCell = currPost.round();

        // Handling cases if there's a wall or if in intersection and if the timer of random direction ended
        boolean bool = mazeConfig.getCell(nextCell).isWall() || mazeConfig.isIntersection(currCell) && ghost.getPath_finding_timer() <= 0;
        int[] base_out_coordinates = {Ghost.getClyde_startPosition().x() - 1, Ghost.getClyde_startPosition().y()};
        if (currCell.x() == base_out_coordinates[0] && currCell.y() == base_out_coordinates[1])
        {
            ghost.setDirection(Direction.NORTH);
        }
        else if (bool || ghost.getDirection() == Direction.NONE)// adding also if direction is none, so it doesn't stop moving
        {
            ArrayList<Direction> direcs = new ArrayList<>();
            for (Direction DIR : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST})
            {
                nextPos = ghost.getPos().plus(DirectionUtils.getVector(DIR));
                nextCell = nextPos.round();
                if (!mazeConfig.getCell(nextCell).isWall())
                {
                    direcs.add(DIR);
                }
            }
            int choice = new Random().nextInt(direcs.size());
            ghost.setDirection(direcs.get(choice));
        }
    }

    /**
     * Gets the given ghost when it is not eaten and not frightened (Special behavior for each, almost).
     * @param ghost the said ghost
     * @param grid the cell table
     * @param mazeConfig mazeconfig
     */
    private static void normalPath(Ghost ghost, Cell[][] grid, MazeConfig mazeConfig)
    {
        // Follow pacman using own behavior
        ghost.setPath_finding_timer(ghost.getPath_finding_timer_max());

        // If chase time, then chase pacman. If not, move randomly around the map
        if(ghost.getChase_timer() > 0)
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

    /**
     * Gets the given ghost the path it should be following when frightened (Random).
     * @param ghost the said ghost
     * @param mazeConfig mazeconfig
     */
    private static void frightenedPath(Ghost ghost, MazeConfig mazeConfig)
    {
        // Run away from pacman
        ghost.setPath_finding_timer(ghost.getPath_finding_timer_max());
        ghost.resetPath();
        getPathCLYDE(ghost, mazeConfig);
    }

    /**
     * Gets the given ghost the path it should be following when eaten (Goes to the base).
     * @param ghost the said ghost
     * @param grid the cell table
     * @param base_coord base coordinates
     */
    private static void eatenPath(Ghost ghost, Cell[][] grid, RealCoordinates base_coord)
    {
        // Return home because eaten
        if (ghost.getPath_finding_timer() <= 0)
        {
            // Get path to the base and renew timer
            ghost.setPath_finding_timer(ghost.getPath_finding_timer_max());
            ghost.setPath(Node.getPath(ghost.getPos(), base_coord, grid));
        }
        if (ghost.getPath().isEmpty())
        {
            // Either arrived at base or Pacman follow location
            // If arrived to the base, set eaten and can be eaten false
            if(ghost.getPos().round().same(base_coord.round()))
            {
                if(ghost.isEaten())
                {
                    ghost.setFrightened(false);
                }
                ghost.setEaten(false);
            }
        }
    }

    /**
     * Gets the given ghost the path it needs if:
     * (path_finding_timer <= 0 or direction == NONE or eaten or frightened) and Ghost_pf.can_it_find_path.
     * @param ghost the said ghost
     * @param grid the cell table
     * @param mazeConfig mazeconfig
     * @param delta delta time
     * @param base_coord base coordinates
     */
    public static void getPath(Ghost ghost, Cell[][] grid, MazeConfig mazeConfig, long delta, RealCoordinates base_coord)
    {
        double delta_double = (double) delta;
        ghost.setPath_finding_timer(ghost.getPath_finding_timer() - delta_double / 1000000000);
        boolean bool = ghost.getPath_finding_timer() <= 0 || ghost.getDirection() == Direction.NONE || ghost.isEaten() || ghost.isFrightened();
        // If not eaten, cant be eaten, the timer is up or the direction is none, find path to Pacman
        // Also be allowed to get out of base
        if(bool && can_it_find_path(ghost, delta))
        {
            if (!ghost.isEaten() && !ghost.isFrightened())
            {
                normalPath(ghost, grid, mazeConfig);
            }
            else if(ghost.isFrightened())
            {
                frightenedPath(ghost, mazeConfig);
            }
            else
            {
                eatenPath(ghost, grid, base_coord);
            }
        }
    }


    /**
     * Makes the given ghost follow the path by changing its direction depending on the path.
     * @param ghost the said ghost
     */
    public static void followPath(Ghost ghost)
    {
        if(!ghost.getPath().isEmpty())
        {
            RealCoordinates to_follow = ghost.getPath().get(0);
            if (ghost.getPos().round().same(to_follow.round()))
            {
                // If in desired location, to pass to the other one we remove the current one
                ghost.getPath().remove(0);
                Ghost_pf.followPath(ghost);
            }
            else
            {
                // To find out where to move, we check x and y values.
                if (to_follow.round().x() == ghost.getPos().round().x())
                {
                    if(to_follow.y() > ghost.getPos().y())
                    {
                        ghost.setDirection(Direction.SOUTH);
                    }
                    else
                    {
                        ghost.setDirection(Direction.NORTH);
                    }
                }
                else if (to_follow.round().y() == ghost.getPos().round().y())
                {
                    if(to_follow.x() > ghost.getPos().x())
                    {
                        ghost.setDirection(Direction.EAST);
                    }
                    else
                    {
                        ghost.setDirection(Direction.WEST);
                    }
                }
            }
        }
    }
}