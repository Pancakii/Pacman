
package model;
import geometry.RealCoordinates;

public class DirectionUtils {
    public static RealCoordinates getVector(Direction direction) {
        switch (direction) {
            case NORTH:
                return RealCoordinates.NORTH_UNIT;
            case EAST:
                return RealCoordinates.EAST_UNIT;
            case SOUTH:
                return RealCoordinates.SOUTH_UNIT;
            case WEST:
                return RealCoordinates.WEST_UNIT;
        }
        return RealCoordinates.ZERO;
    }
}