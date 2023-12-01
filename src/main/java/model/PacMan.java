package model;

//TESTING

import geometry.RealCoordinates;
import config.*;

import java.util.ArrayList;
import java.util.List;



public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private boolean energized;
	private final double energized_timer_max = 10;
	private double energized_timer;



	// We should be able to change the position 
	// when we change the level.
    private PacMan()
	{
		this(0.0, 0.0, false);
    }

    public static final PacMan INSTANCE = new PacMan();


    public static void checknEatCell(MazeConfig grid, boolean[][] grid_state)
	{
		/*
		Check if pacman is in a new cell, if so eat the content
		 */
    	var pacPos = PacMan.INSTANCE.getPos().round();// get pacman position
    	int x = pacPos.x(); // get x axis
		int y = pacPos.y(); // get y axis
		if(!grid_state[y][x]) // verify if the cell pacman in is entered before(false = not entered yet)
		{
			if (grid.getCell(pacPos).aDot())// if the unentered cell contains a dot
			{
				MazeState.addScore(1);// add 1 to the score
				grid_state[y][x] = true;// set the cell state "entered"
			}
			else if (grid.getCell(pacPos).aEnergizer())// if the unentered cell contains an energizer
			{
				MazeState.addScore(10);// add 10 to the score
				INSTANCE.energized_timer = INSTANCE.energized_timer_max;// set the energizer timer
				INSTANCE.setEnergized(true);// set energized true
				grid_state[y][x] = true;// set the cell state "entered"
			}
		}
    }

	public List<Critter> closeGhosts(List<Critter> critters)
	{
		/*
		return critters that are close enough. Gonna check if pacman is energized later
		 */
		List<Critter> res = new ArrayList<>();
		var pacPos = PacMan.INSTANCE.getPos().round();

		for (var critter : critters) {
			if (critter instanceof Ghost && critter.getPos().round().equals(pacPos))
			{
				if (PacMan.INSTANCE.isEnergized())
				{
					MazeState.addScore(10);
				}
				res.add(critter);
			}
		}
		return res;
	}

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
	
	
	// Eating energizer or pellets
	public double distance(double[] p)
	{
		// Distance calculator. Can be changed or not,
		// depending on the cell position system.
		return Math.sqrt(Math.pow( ((p[0] - pos.x()) + (p[1] - pos.y())), 2));
	}


	// Eating energizer or pellets
	public double distance(double[] p)
	{
		// Distance calculator. Can be changed or not, 
		// depending on the cell position system.
		return Math.sqrt(Math.pow( ((p[0] - pos.x()) + (p[1] - pos.y())), 2));
	}


	public void energizedTimerCount(long delta)
	{
		// This function decreases the timer of the energy buff.
		// If the timer is done, sets energized false.

		// if energized
		// timer -= delta
		// if timer < 0 then setEnergized(false)

		double delta_double = (double)delta;
		if(energized)
		{
			energized_timer -= delta_double/1000000000;
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

	public int getLevel() {
		return 1;
	}
}
