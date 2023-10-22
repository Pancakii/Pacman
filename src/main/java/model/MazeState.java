package model;

import javafx.scene.text.*;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import java.util.List;
import java.util.Map;
import static model.Ghost.*;

public final class MazeState {
    private final MazeConfig config;
    private final int height;
    private final int width;
    private final boolean[][] gridState;
    private final List<Critter> critters;
    private static int score;

    private final Map<Critter, RealCoordinates> initialPos;
    private int lives = 3;
    private static Text point;

    public MazeState(MazeConfig config) {
        this.config = config;
        height = config.getHeight();
        width = config.getWidth();
        critters = List.of(PacMan.INSTANCE, Ghost.CLYDE, BLINKY, INKY, PINKY);
        gridState = new boolean[height][width];
        initialPos = Map.of(
                PacMan.INSTANCE, config.getPacManPos().toRealCoordinates(1.0),
                BLINKY, config.getBlinkyPos().toRealCoordinates(1.0),
                INKY, config.getInkyPos().toRealCoordinates(1.0),
                CLYDE, config.getClydePos().toRealCoordinates(1.0),
                PINKY, config.getPinkyPos().toRealCoordinates(1.0)
        );
        resetCritters();
    }

    public List<Critter> getCritters() {
        return critters;
    }

    public double getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public void update(long deltaTns) {
    	this.Neighbours(deltaTns);
        PacMan.pacEnterNewCell(this.gridState);
        this.statusPacman();
    }
    
    private void Neighbours(long deltaTns) {
        for (var critter : critters) {
            var curPos = critter.getPos();
            var nextPos = critter.nextPos(deltaTns);

            // Vérifie si la nouvelle position est un mur
            if (isWall(nextPos)) {
                critter.setDirection(Direction.NONE);
                nextPos = curPos;  // Reste à la position actuelle
            }

            critter.setPos(nextPos.warp(width, height));
        }
    }
    
    //Vérifie si la nouvelle position est un mur
    private boolean isWall(RealCoordinates position) {
        IntCoordinates cell = position.round();
        return config.getCell(cell).isWall();
    }

    
    /*
     * Si pacman entre dans une case où il y a un fantôme
     * on vérifie s'il est énergiser
     * Oui : Gagne 10pts et tue le fantôme
     * Non : Perd une vie ou Game Over si 0 vie
     */
    private void statusPacman() {
    	var pacPos = PacMan.INSTANCE.getPos().round();
    	
    	for (var critter : critters) {
    		if (critter instanceof Ghost && critter.getPos().round().equals(pacPos)) {
    			if(PacMan.INSTANCE.isEnergized()) {
                     addScore(10);
                     resetCritter(critter);
                }else {
                    playerLost();
                    return;
                }
            }
        }
    }

    public static void addScore(int increment) {
        score += increment;
        //gameView.updateScore(score);
    }
    
    public void displayScore() {
    	point.setText("Score: " + score);
    }
    
    public void initializeScoreText(Text scoreText) {
        point = scoreText;
    }
    
    private void playerLost() {
        lives--;
        if (lives == 0) {
            System.out.println("Game over!");
            System.exit(0);
        }
        System.out.println("Lives: " + lives);
        resetCritters();
    }

    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));
    }

    private void resetCritters() {
        for (var critter: critters) resetCritter(critter);
    }

    public MazeConfig getConfig() {
        return config;
    }

    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }

}
