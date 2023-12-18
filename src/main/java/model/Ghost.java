package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import pathfinding.Ghost_pf;
import pathfinding.Node;

import java.util.ArrayList;
import java.util.Random;

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



    public boolean isEaten() {
        return eaten;
    }
    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    public boolean isFrightened() {
        return frightened;
    }
    public void setFrightened(boolean frightened) {
        this.frightened = frightened;
    }

    public ArrayList<RealCoordinates> getPath() {return path;}
    public void setPath(ArrayList<RealCoordinates> path) {this.path = path;}

    public double getPath_finding_timer_max() {return path_finding_timer_max;}

    public double getPath_finding_timer() {return path_finding_timer;}
    public void setPath_finding_timer(double path_finding_timer) {this.path_finding_timer = path_finding_timer;}

    public double getChase_timer_max() {return chase_timer_max;}

    public void setChase_timer(double chase_timer) {this.chase_timer = chase_timer;}
    public double getChase_timer() {return chase_timer;}

    public double getScatter_timer_max() {return scatter_timer_max;}

    public double getScatter_timer() {return scatter_timer;}
    public void setScatter_timer(double scatter_timer) {this.scatter_timer = scatter_timer;}




    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public void setPos(RealCoordinates newPos) {
        pos = newPos;
        
        // Met à jour la position initiale uniquement pour Clyde
        if (this == CLYDE && Clyde_startPosition == null) {
            Clyde_startPosition = newPos.round();
        }
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
        double res = frightened ? 2.0 * nearWormWall() : 2.8 * nearWormWall();
        if(eaten)
        {
            res = 8;
        }
        return res;
    }

    public double nearWormWall()
    {
        if(pos.round().y() == 9 && (pos.round().x() < 5 || pos.round().x() > 15))
        {
            return 0.5;
        }
        return 1.0;
    }

    public void scatterChaseTimer(long d)
    {
        Ghost_pf.scatterChaseTimer(this, d);
    }

    public void getPath(Cell[][] grid, MazeConfig mazeConfig, long delta, RealCoordinates base_coord)
    {
        Ghost_pf.getPath(this, grid, mazeConfig, delta, base_coord);
    }

    public void followPath()
    {
        Ghost_pf.followPath(this);
    }
}