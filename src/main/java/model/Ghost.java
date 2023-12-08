package model;

import config.Cell;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import pathfinding.Node;

import java.util.ArrayList;
import java.util.Random;

public enum Ghost implements Critter {

    BLINKY, INKY, PINKY, CLYDE;
    // Direct chase, goes in front, goes in front, random

    public boolean eaten = false;
    public boolean frightened = false;
    private RealCoordinates pos;
    private Direction direction = Direction.NONE;

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

}