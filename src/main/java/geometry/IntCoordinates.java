package geometry;

public record IntCoordinates(int x, int y) {
    /**
     * Converts IntCoordinates to RealCoordinates with scale
     * @param scale the said scale
     * @return the result
     */
    public RealCoordinates toRealCoordinates(final double scale) {
        return new RealCoordinates(x * scale, y * scale);
    }

    /**
     * Determines whether the given coordinates is the same as this.
     * @param other the said coordinates
     * @return true if same, false otherwise
     */
    public boolean same(IntCoordinates other)
    {
        return x == other.x() && y == other.y();
    }
}
