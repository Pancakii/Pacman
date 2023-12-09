package model;

import geometry.RealCoordinates;

public sealed interface Critter permits Ghost, PacMan {
    RealCoordinates getPos();

    Direction getDirection();

    double getSpeed();

    /**
     * @param deltaTNanoSeconds time since the last update in nanoseconds
     * @return the next position if there is no wall
     */
    default RealCoordinates nextPos(long deltaTNanoSeconds, int level) {
        return getPos().plus((switch (getDirection()) {
            case NONE -> RealCoordinates.ZERO;
            case NORTH -> RealCoordinates.NORTH_UNIT;
            case EAST -> RealCoordinates.EAST_UNIT;
            case SOUTH -> RealCoordinates.SOUTH_UNIT;
            case WEST -> RealCoordinates.WEST_UNIT;
        }).times(getSpeed() * deltaTNanoSeconds * 1E-9 * (1 + level * 0.1)));
    }

    void setPos(RealCoordinates realCoordinates);
    void setDirection(Direction direction);
}
