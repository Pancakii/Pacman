package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import pathfinding.Ghost_pf;

import java.util.ArrayList;

public enum Ghost implements Critter {

    BLINKY, INKY, PINKY, CLYDE;
    // Direct chase, goes in front, goes in front, random

    private boolean eaten = false;
    private boolean frightened = false;
    private ArrayList<RealCoordinates> path = new ArrayList<>();
    private RealCoordinates pos;
    private Direction direction = Direction.NONE;

    private final double path_finding_timer_max = 0.3;
    private double path_finding_timer = 0;

    private final double scatter_timer_max = 7;
    private double scatter_timer = scatter_timer_max;

    private final double chase_timer_max = 20;
    private double chase_timer = 0;
    private static IntCoordinates Clyde_startPosition;

    private double get_out_timer = 0.0;
    // BLINKY, INKY, PINKY, CLYDE
    // 0     , 10  , 5    , 15


    /**
     * Gets eaten.
     * @return eaten
     */
    public boolean isEaten() {
        return eaten;
    }

    /**
     * Sets eaten.
     * @param eaten to set.
     */
    public void setEaten(boolean eaten) {this.eaten = eaten;}

    /**
     * Gets frightened.
     * @return frightened
     */
    public boolean isFrightened() {return frightened;}

    /**
     * Sets frightened.
     * @param frightened to set.
     */
    public void setFrightened(boolean frightened) {this.frightened = frightened;}

    /**
     * Gets path.
     * @return path
     */
    public ArrayList<RealCoordinates> getPath() {return path;}

    /**
     * Sets path.
     * @param path to set.
     */
    public void setPath(ArrayList<RealCoordinates> path) {this.path = path;}

    /**
     * Gets path_finding_timer_max.
     * @return path_finding_timer_max
     */
    public double getPath_finding_timer_max() {return path_finding_timer_max;}

    /**
     * Gets path_finding_timer.
     * @return path_finding_timer
     */
    public double getPath_finding_timer() {return path_finding_timer;}

    /**
     * Sets path_finding_timer.
     * @param path_finding_timer to set.
     */
    public void setPath_finding_timer(double path_finding_timer) {this.path_finding_timer = path_finding_timer;}

    /**
     * Gets chase_timer_max.
     * @return chase_timer_max
     */
    public double getChase_timer_max() {return chase_timer_max;}

    /**
     * Sets chase_timer.
     * @param chase_timer to set.
     */
    public void setChase_timer(double chase_timer) {this.chase_timer = chase_timer;}

    /**
     * Gets chase_timer.
     * @return chase_timer
     */
    public double getChase_timer() {return chase_timer;}

    /**
     * Gets scatter_timer_max
     * @return scatter_timer_max
     */
    public double getScatter_timer_max() {return scatter_timer_max;}

    /**
     * Gets scatter_timer.
     * @return scatter_timer
     */
    public double getScatter_timer() {return scatter_timer;}

    /**
     * Sets scatter_timer.
     * @param scatter_timer to set.
     */
    public void setScatter_timer(double scatter_timer) {this.scatter_timer = scatter_timer;}

    /**
     * Gets Clyde_startPosition.
     * @return Clyde_startPosition
     */
    public static IntCoordinates getClyde_startPosition() {return Clyde_startPosition;}


    /**
     * Gets pos.
     * @return pos
     */
    @Override
    public RealCoordinates getPos() {return pos;}

    /**
     * Sets pos. Also sets Clyde_startPosition if it hasn't been set yet.
     * @param newPos to set.
     */
    @Override
    public void setPos(RealCoordinates newPos) {
        pos = newPos;

        // Met à jour la position initiale uniquement pour Clyde
        if (this == CLYDE && Clyde_startPosition == null) {
            Clyde_startPosition = newPos.round();
        }
    }

    /**
     * Sets direction.
     * @param direction to set.
     */
    @Override
    public void setDirection(Direction direction) {this.direction = direction;}

    /**
     * Gets direction.
     * @return direction
     */
    @Override
    public Direction getDirection() {return direction;}

    /**
     * Sets path a new ArrayList.
     */
    public void resetPath() {path = new ArrayList<>();}

    /**
     * Gets speed depending on if near wormwall, frightened or eaten.
     * @return double for speed
     */
    @Override
    public double getSpeed() {
        double res = frightened ? 2.0 * nearWormWall() : 2.8 * nearWormWall();
        if(eaten)
        {
            res = 8;
        }
        return res;
    }

    /**
     * Gets get_out_timer.
     * @return get_out_timer
     */
    public double getGet_out_timer() {return get_out_timer;}

    /**
     * Sets get_out_timer.
     * @param get_out_timer to set.
     */
    public void setGet_out_timer(double get_out_timer) {this.get_out_timer = get_out_timer;}

    /**
     * Checks if the ghost is near the wormwall. Returns 1.0 or 0.5 to be multiplied by speed later.
     * @return double for speed multiplier
     */
    public double nearWormWall()
    {
        if(pos.round().y() == 10 && (pos.round().x() < 5 || pos.round().x() > 15))
        {
            return 0.5;
        }
        return 1.0;
    }

    /**
     * Calls Ghost_pf.scatterChaseTimer, which handles the timers of scatter/chase mode.
     * @param d delta time
     */
    public void scatterChaseTimer(long d)
    {
        Ghost_pf.scatterChaseTimer(this, d);
    }

    /**
     * Calls Ghost_pf.getPath, which sets a new path for the ghost.
     * @param grid Cell table
     * @param mazeConfig mazeConfig
     * @param delta delta time
     * @param base_coord base coordinates
     */
    public void getPath(Cell[][] grid, MazeConfig mazeConfig, long delta, RealCoordinates base_coord)
    {
        Ghost_pf.getPath(this, grid, mazeConfig, delta, base_coord);
    }

    /**
     * Calls Ghost_pf.followPath, which makes the ghost follow the path.
     */
    public void followPath()
    {
        Ghost_pf.followPath(this);
    }
}