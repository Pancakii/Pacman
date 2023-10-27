package model;

//TESTING

import geometry.RealCoordinates;
import config.*;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether singleton is really a good idea.
 * Yes it is, as there will be only one pacman in the whole game.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private boolean energized;
	private final int energized_timer_max = 10000;// in milliseconds
	private int energized_timer;
	
	
	
	// We should be able to change the position 
	// when we change the level.
    private PacMan()
	{
		this(0.0, 0.0, false);
    }
    
    public static final PacMan INSTANCE = new PacMan();
    
    /*
     * Si pacman entre dans une case une nouvelle case
     * il gagne 1pts
     */
    public static void checknEatCell(MazeConfig grid, boolean[][] grid_state) {
    	var pacPos = PacMan.INSTANCE.getPos().round();// get pacman position
    	int x = pacPos.x(); // get x axis
		int y = pacPos.y(); // get y axis
		if(!grid_state[y][x]) // verify if the cell pacman in is entered before(false = not entered yet)
		{
			if (!grid.getCell(pacPos).aDot())// if the unentered cell contains a dot
			{
				MazeState.addScore(1);// add 1 to the score
				grid_state[y][x] = true;// set the cell state "entered"
			}
			else if (grid.getCell(pacPos).aEnergizer())// if the unentered cell contains an energizer
			{
				MazeState.addScore(10);// add 10 to the score
				INSTANCE.setEnergized(true);// set energized true
				grid_state[y][x] = true;// set the cell state "entered"
			}
		}
    }

	/*
	TO DO: finish
	private void statusPacman() {
		var pacPos = PacMan.INSTANCE.getPos().round();

		for (var critter : critters) {
			if (critter instanceof Ghost && critter.getPos().round().equals(pacPos)) {
				if (PacMan.INSTANCE.isEnergized()) {
					addScore(10);
					resetCritter(critter);
				} else {
					playerLost();
					return;
				}
			}
		}
	}
	*/

	private PacMan(double x, double y, boolean e)
	{
		pos = new RealCoordinates(x, y);
		energized = e;
		energized_timer = 0;
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

	
	public void energizedTimerCount(int delta)
	{
		// This function decreases the timer of the energy buff.
		// If the timer is done, sets energized false.
		
		// if energized
		// timer -= delta
		// if timer < 0 then setEnergized(false)
		if(energized)
		{
			energized_timer -= delta;
			if(energized_timer <= 0)
			{
				setEnergized(false);
				energized_timer = 0;
			}
		}
	}
	
	
    public boolean isEnergized() {return energized;}

    public void setEnergized(boolean energized) 
	{
        this.energized = energized;
    }
}
