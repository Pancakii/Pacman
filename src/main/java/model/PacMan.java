package model;

//TESTING

import java.util.Math;

import geometry.RealCoordinates;

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
	
	private Pacman(double x, double y, boolean e)
	{
		pos = new RealCoordinates(x, y);
		energized = e;
		energized_timer = 0;
	}
	

    public static final PacMan INSTANCE = new PacMan(0, 0, false);

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
	
	
	// Eating energizer or pellets
	public double distance(double[] p)
	{
		// Distance calculator. Can be changed or not, 
		// depending on the cell position system.
		return sqrt(power( ((p[0] - pos.x) + (p[1] - pos.y)), 2)); 
	}
	
	public boolean eatBall(Cell cell)
	{
		// Call this function for a cell that contains a pellet(energizer or normal)
		
		// Check if the cell has anything.
		// If so, if close enough to the middle, eat and return true, else return false.
		if(INSTANCE.distance(new {cell.getCoordinates.x, cell.getCoordinates.y}) < 0.4)
		{
			// Supposing pacman radius is 0.8 cell size, if the distance between pellet and pacman
			// is half pacman long, then it means pacman is close enough to eat it.
			if(cell.Content == ENERGIZER)// to change
			{
				// If it's an energizer, set energized true and 
				// return true toindicate that the pellet is eaten.
				setEnergized(true);
				return true;
			}
			else if(cell.Content == PELLET)// to change
			{
				// If it's a pellet, increment the score and 
				// return true to indicate that the pellet is eaten.
				//set score++;
				return true;
			}
		}
		return false;
	}
	
	public void energizedTimerCount(double delta)
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
	
	
    public boolean isEnergized() 
	{
        // TODO handle timeout!(func energizedTimerCount)
		// Already done, can be used as a getter if needed.
		
        return energized;
    }

    public void setEnergized(boolean energized) 
	{
        this.energized = energized;
    }
}
