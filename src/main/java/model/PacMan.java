package model;


import config.MazeConfig;
import geometry.RealCoordinates;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private  boolean energized;
    private final double energized_timer_max = 10;
    private double energized_timer;
    private static int level;
    private static int countDot;
    private static int countDotTotal;
    private static final int dotTotal = 151;

    // Ajout des fichiers audio
    private static final String path =System.getProperty("user.dir") ;
    private static final String s = findSlash(path) ;
    private static final String dotSound = path +s + "src" + s + "main"+ s + "resources" + s + "pacman_chomp.wav";
    private static final String energizerSound = path + s + "src" + s + "main"+ s + "resources" + s + "pacmaneatingenergizer.wav";
    private static final String gameOverSound = path + s +"src" + s + "main"+ s + "resources" + s + "pacmandeath.wav";
    private static final String bonusSound = path + s +"src" + s + "main"+ s + "resources" + s + "pacman_eatfruit.wav";
    private static final String benginningSound = path + s +"src" + s + "main"+ s + "resources" + s + "pacman_beginning.wav";
    private static final String extraPacSound = path + s +"src" + s + "main"+ s + "resources" + s + "pacman_eatghost.wav";
    private static final String intermissionSound = path + s +"src" + s + "main"+ s + "resources" + s + "pacman_intermission.wav";
    private static final String eatGhostSound = path + s +"src" + s + "main"+ s + "resources" + s + "pacman_eatghost.wav";



    // We should be able to change the position
    // when we change the level.

    /**
     * Constructor of PacMan.
     */
    private PacMan() {
        this(0.0, 0.0, false);
    }

    public static final PacMan INSTANCE = new PacMan();

    /**
     * Une fonction qui fait pacman manger les dots ou energizers. Returne true s'il en a mangé.
     * Il augmente aussi le score, countDot, CountDotTotal dans les bons cas.
     * @param grid le tableau contenant les cellules.
     * @param grid_state le tableau qui sait si le cellule est entré/découvert ou pas
     * @return si Pacman a mangé un dot ou energiser
     */
    public static boolean checknEatCell(MazeConfig grid, boolean[][] grid_state)
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
                MazeState.addScore(10);// add 1 to the score
                countDot++;
                countDotTotal++;
                grid_state[y][x] = true;// set the cell state "entered"
               PacMan.INSTANCE.playEatDot();
            }
            else if (grid.getCell(pacPos).aEnergizer())// if the unentered cell contains an energizer
            {
                MazeState.addScore(50);// add 10 to the score
                countDot++;
                countDotTotal++ ;
                INSTANCE.energized_timer = INSTANCE.energized_timer_max;// set the energizer timer
                INSTANCE.setEnergized(true);// set energized true
                grid_state[y][x] = true;// set the cell state "entered"
                PacMan.INSTANCE.playEatAnEnergizer();
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne les fantomes qui sont dans la meme case que pacman.
     * @param critters la liste des fantomes
     * @return la liste des fantomes qui sont dans la meme case.
     */
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
				res.add(critter);
			}
		}
		return res;
	}

    /**
     * Constructor of PacMan.
     * @param x coordinate
     * @param y coordinate
     * @param e energized
     */
	private PacMan(double x, double y, boolean e)
	{
		pos = new RealCoordinates(x, y);
		energized = e;
		energized_timer = 0;
		level = 1 ;
		countDot = 0 ;
		countDotTotal = 0 ;

	}

    /**
     * Gets position.
     * @return position
     */
    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    /**
     * Gets speed.
     * @return speed
     */
    @Override
    public double getSpeed() {
        return isEnergized() ? 4 : 3;
    }

    /**
     * Gets direction.
     * @return direction
     */
    @Override
    public Direction getDirection() {return direction;}

    /**
     * Sets direction
     * @param direction to set
     */
    @Override
    public void setDirection(Direction direction) {this.direction = direction;}

    /**
     * Sets position.
     * @param pos to set
     */
    @Override
    public void setPos(RealCoordinates pos) {this.pos = pos;}


    /**
     * Decreases energized_timer, sets energized false when below 0.
     * @param delta delta time
     */
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

    /**
     *  Regarde si le bonus peut être mangées par pacman
     */
    public static void eatBonus(){
		var pacPos = PacMan.INSTANCE.getPos().round();// get pacman position
		int x = pacPos.x(); // get x axis
		int y = pacPos.y(); // get y axis
        if ( x == 11 && y == 12 && Bonus.canHaveBonus()){
            PacMan.INSTANCE.playEatFruitSound();
			MazeState.addScore(Bonus.pointBonus());
			Bonus.setHaveBonus(false);
			Bonus.setBonusTimer(0);
		}
	}

    /**
     * Une fonction qui donne le son quand pacman a perdu de la vie
     */
    public void playGameOverSound() {
        Clip clip ;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(gameOverSound)));
            clip.start();
            if ( clip.isActive()) clip.close();
        } catch (Exception e){
            e.fillInStackTrace() ;
        }
    }

    /**
     * Une fonction qui donne le son quand pacman mange un ghost
     */
    public void playEatGhostSound() {
        Clip clip ;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(eatGhostSound)));
            clip.start();
            if ( clip.isActive()) clip.close();
        } catch (Exception e){
            e.fillInStackTrace() ;
        }
    }

    /**
     * Une fonction qui donne le son quand pacman mange un fruit
     */
    public void playEatFruitSound() {
        Clip clip ;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(bonusSound)));
            clip.start();
            if ( clip.isActive()) clip.close();
        } catch (Exception e){
            e.fillInStackTrace() ;
        }
    }

    /**
     * Une fonction qui donne le son quand on commence le jeu
     */
    public void playBeginningSound() {
        Clip clip ;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(benginningSound)));
            clip.start();
            if ( clip.isActive()) clip.close();
        } catch (Exception e){
            e.fillInStackTrace() ;
        }
    }
    /**
     * Une fonction qui donne le son quand on commence le jeu
     */
    public void playExtraPacSound() {
        Clip clip ;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(extraPacSound)));
            clip.start();
            if ( clip.isActive()) clip.close();
        } catch (Exception e){
            e.fillInStackTrace() ;
        }
    }

    /**
     * Une fonction qui donne le son quand pacman mange un energizer
     */
    public void playEatAnEnergizer(){
        Clip clip ;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(energizerSound)));
            clip.start();
            if ( clip.isActive()) clip.close();
        } catch (Exception e ){
            e.fillInStackTrace() ;
        }
    }

    public void playEatDot (){
        Clip clip ;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(dotSound)));
            clip.start();
            if ( clip.isActive()) clip.close();
        } catch (Exception e ){
            e.fillInStackTrace() ;
        }
    }

    /**
     * Une fonction qui donne le son quand pacman est energized
     */
    public void playIntermission(){
        Clip clip ;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(intermissionSound)));
            clip.start();
            if ( clip.isActive()) clip.close();
        } catch (Exception e ){
            e.fillInStackTrace() ;
        }
    }

    /**
     * Une fonction qui donne le bon slash
     * @param p
     * @return String
     */
    private static String findSlash(String p) {
        for (int i = 0; i < p.length(); i++) {
            switch (p.charAt(i)) {
                case '/':
                    return "/";
                case '\\':
                    return "\\";
            }
        }
        return "/";
    }



    /**
     * Gets energized_timer
     * @return energized_timer
     */
	public double getEnergized_timer() {
		return energized_timer;
	}

    /**
     * Gets energized
     * @return energized
     */
	public boolean isEnergized() {return energized;}

    /**
     * Sets energized
     * @param energized to set
     */
    public void setEnergized(boolean energized)
	{
        this.energized = energized;
    }

    /**
     * retourne le level
     * @return level
     */
    public static int getLevel() {
		return level ;
	}

    /**
     * Change le level
     * @param level to set
     */
    public static void setLevel(int level) {
        PacMan.level = level;

    }

    /**
     * Retourne le countDot
     * @return countDot
     */
    public static int getCountDot() {
		return countDot;
	}

    /**
     *  Change le countDot
     * @param countDot to set
     */
    public static void setCountDot(int countDot) {
		PacMan.countDot = countDot;
	}

    /**
     * Retourne le nombre total de dots mangé
     * @return countDotTotal
     */
    public static int getCountDotTotal() {
		return countDotTotal;
	}

    /**
     * Retourne le nombre de dots au total
     * @return dotTotal
     */
    public static int getDotTotal() {
		return dotTotal;
	}

    /**
     * Change le nombre total de dots mangé
     * @param countDotTotal to set
     */
    public static void setCountDotTotal(int countDotTotal) {
		PacMan.countDotTotal = countDotTotal;
	}
}