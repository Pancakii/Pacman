package model;

import java.util.Math;

import geometry.RealCoordinates;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether singleton is really a good idea.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private boolean energized;
	private final int energized_timer_max = 7000;// in milliseconds
	private int energized_timer;
	

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
    /**
	*
	* @return whether Pac-Man just ate an energizer
	*/
	
	public double distance(double[] p)
	{
		return sqrt(power( ((p[0] - pos.x) + (p[1] - pos.y)), 2)); 
	}
	
	public boolean eatBall(Cell cell)
	{
		if(INSTANCE.distance(new {cell.getCoordinates.x, cell.getCoordinates.y}) < 1)
		{
			if(cell.Content == ENERGIZER)
			{
				setEnergized(true);
				return true;
			}
			else if(cell.Content == ENERGIZER)
			{
				//set score++;
				return true;
			}
		}
		return false;
		//check if the cell has anything
		//If so, if close enough to the middle, eat and return true, else return false
	}
	
	public void energizedTimerCount(double delta)
	{
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
	
	
    public boolean isEnergized() {
        // TODO handle timeout!(func energizedTimerCount)
		
        return energized;
    }

    public void setEnergized(boolean energized) {
        this.energized = energized;
    }
}
