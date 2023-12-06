package model;

import geometry.RealCoordinates;
import config.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private boolean energized;
    private final double energized_timer_max = 10;
    private double energized_timer;
    private static int level;
    private static int countDot;
    private static int countDotTotal;
    private static final int dotTotal = 151;

    // Ajout des fichiers audio
    private static final String dotSound = "Users/sofya/OneDrive/Documents/PACM/pacman/src/main/resources/eatdot.wav";
    private static final String energizerSound = "Users/sofya/OneDrive/Documents/PACM/pacman/src/main/resources/pacmaneatingfruit.wav";
    private static final String gameOverSound = "Users/sofya/OneDrive/Documents/PACM/pacman/src/main/resources/pacmandeath.wav";

    // We should be able to change the position
    // when we change the level.
    private PacMan() {
        this(0.0, 0.0, false);
    }

    public static final PacMan INSTANCE = new PacMan();

    public static boolean checknEatCell(MazeConfig grid, boolean[][] grid_state) {
        var pacPos = PacMan.INSTANCE.getPos().round();
        int x = pacPos.x();
        int y = pacPos.y();
        if (!grid_state[y][x]) {
            if (grid.getCell(pacPos).aDot()) {
                MazeState.addScore(1);
                countDot++;
                countDotTotal++;
                grid_state[y][x] = true;

                // Jouer le son pour manger un dot
                Media sound = new Media(new File(dotSound).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            } else if (grid.getCell(pacPos).aEnergizer()) {
                MazeState.addScore(10);
                countDot++;
                countDotTotal++;
                INSTANCE.energized_timer = INSTANCE.energized_timer_max;
                INSTANCE.setEnergized(true);
                grid_state[y][x] = true;

                // Jouer le son pour manger un energizer
                Media sound = new Media(new File(energizerSound).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                return true;
            }
        }
        return false;
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
		level = 1 ;
		countDot = 0 ;
		countDotTotal = 0 ;
	}

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public double getSpeed() {
        return isEnergized() ? 4 : 3;
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

	public static void eatBonus(){
		var pacPos = PacMan.INSTANCE.getPos().round();// get pacman position
		int x = pacPos.x(); // get x axis
		int y = pacPos.y(); // get y axis
        if ( x == 10 && y == 11 && Bonus.canHaveBonus()){
			MazeState.addScore(Bonus.pointBonus());
			Bonus.setHaveBonus(false);
			Bonus.setBonusTimer(0);
		}
	}


	public double getEnergized_timer() {
		return energized_timer;
	}

	public boolean isEnergized() {return energized;}

    public void setEnergized(boolean energized)
	{
        this.energized = energized;
    }

	public static int getLevel() {
		return level ;
	}

	public static void setLevel(int levels) {
		level = levels;
	}

	public static int getCountDot() {
		return countDot;
	}

	public static void setCountDot(int countDot) {
		PacMan.countDot = countDot;
	}
	public static int getCountDotTotal() {
		return countDotTotal;
	}

	public static int getDotTotal() {
		return dotTotal;
	}

	public static void setCountDotTotal(int countDotTotal) {
		PacMan.countDotTotal = countDotTotal;
	}


    //  méthode pour jouer le son de gameover ps : à ajouter dans la classe gameover
    public void playGameOverSound() {
        Media sound = new Media(new File(gameOverSound).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}