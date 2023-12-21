package model;

import geometry.RealCoordinates;

public sealed interface Critter permits Ghost, PacMan {
    /**
     * Abstract function that gets the position of the critter.
     * @return position of the said critter
     */
    RealCoordinates getPos();
    /**
     * Abstract function that gets the direction of the critter.
     * @return direction of the said critter
     */
    Direction getDirection();

    /**
     * Abstract function that gets the speed of the critter.
     * @return speed of the said critter
     */
    double getSpeed();

    /**
     * Returns the next position if there is no wall.
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

    /**
     * Abstract function that sets the position of the critter to the given coordinates.
     * @param realCoordinates the said coordinates
     */
    void setPos(RealCoordinates realCoordinates);

    /**
     * Abstract function that sets the direction of the critter to the given direction.
     * @param direction the said direction
     */
    void setDirection(Direction direction);
}
