package geometry;

import java.util.HashSet;
import java.util.Set;

public record RealCoordinates(double x, double y) {

    public static final RealCoordinates ZERO = new RealCoordinates(0, 0);
    public static final RealCoordinates NORTH_UNIT = new RealCoordinates(0, -1);
    public static final RealCoordinates EAST_UNIT = new RealCoordinates(1, 0);
    public static final RealCoordinates SOUTH_UNIT = new RealCoordinates(0, 1);
    public static final RealCoordinates WEST_UNIT = new RealCoordinates(-1, 0);


    public RealCoordinates plus(final RealCoordinates other) {
        return new RealCoordinates(x + other.x, y + other.y);
    }

    public RealCoordinates times(final double multiplier) {
        return new RealCoordinates(x * multiplier, y * multiplier);
    }

    /**
     * Méthode qui renvoie un ensemble IntCoordinates
     * @return Renvoie un ensemble IntCoordinates correspondant aux voisins de notre position
     */
    public Set<IntCoordinates> intNeighbours() {
        Set<IntCoordinates> neighbours = new HashSet<>();

        int floorX = (int) Math.floor(x);
        int floorY = (int) Math.floor(y);
        int ceilX = (int) Math.ceil(x);
        int ceilY = (int) Math.ceil(y);

        neighbours.add(new IntCoordinates(floorX, floorY));
        neighbours.add(new IntCoordinates(floorX, ceilY));
        neighbours.add(new IntCoordinates(ceilX, floorY));
        neighbours.add(new IntCoordinates(ceilX, ceilY));

        return neighbours;
    }


    public IntCoordinates round() {
        return new IntCoordinates((int) Math.round(x), (int) Math.round(y));
    }

    public boolean smallDiff(RealCoordinates other)
    {
        return Math.abs(x() - other.x()) < 0.05 && Math.abs(y() - other.y()) < 0.05;
    }
    
    
    /**
     * Méthode qui délimite les zones "hors limites"
     * @param width   largeur du plateau de jeu
     * @param height	longeur du plateau de jeu
     * @return Renvoie des nouvelles coordonnées (RealCoordinates) "appropriées" 
     */
    public RealCoordinates warp(int width, int height) {
        var rx = x;
        var ry = y;
        
        while (Math.round(rx) < 0) {
            rx += width;
        }
        while (Math.round(ry) < 0) {
            ry += height;
        }
        while (Math.round(rx) >= width) {
            rx -= width;
        }

        while (Math.round(ry) >= height) {
            ry -= height;
        }

        return new RealCoordinates(rx, ry);
    }
}
