
package model;
import geometry.RealCoordinates;

/**
 * Cette classe sert à traduire la direction du critter en coordonnées
 * @author Salim
 * @version final
 */
public class DirectionUtils {
	
	/**
     * Méthode qui traduis la direction prise par le critter
     * @param direction	Représente le mouvement entrepris par le critter
     * @return Renvoie un RealCoordinates qui correspond à la direction où se dirige le critter
     */
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