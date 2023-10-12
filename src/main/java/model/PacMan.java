package model;

import geometry.RealCoordinates;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether singleton is really a good idea.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private boolean energized;

    private PacMan() {
    }
    
    public static final PacMan INSTANCE = new PacMan();
    
    /*
     * Si pacman entre dans une case une nouvelle case
     * il gagne 1pts
     */
    public static void pacEnterNewCell(boolean[][] gridState) {
    	var pacPos = PacMan.INSTANCE.getPos().round();
    	
        if (!gridState[pacPos.y()][pacPos.x()]) {
            MazeState.addScore(1);
            gridState[pacPos.y()][pacPos.x()] = true;
        }
    }

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public double getSpeed() {
        return isEnergized() ? 6 : 4;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void setPos(RealCoordinates pos) {
        this.pos = pos;
    }

    /**
     *
     * @return whether Pac-Man just ate an energizer
     */
    public boolean isEnergized() {
        // TODO handle timeout!
        return energized;
    }

    public void setEnergized(boolean energized) {
        this.energized = energized;
    }
}
