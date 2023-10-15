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
    private Text point;

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
            var curNeighbours = curPos.intNeighbours();
            var nextNeighbours = nextPos.intNeighbours();

            // Vérifie si la nouvelle position est un mur
            for (var n : nextNeighbours) {
                if (config.getCell(n).isWall()) {
                    critter.setDirection(Direction.NONE);
                    break;
                }
            }

            if (!curNeighbours.containsAll(nextNeighbours)) {
                switch (critter.getDirection()) {
                    case NORTH -> {
                        for (var n : curNeighbours) {
                            if (config.getCell(n).isWall()) {
                                nextPos = curPos.floorY();
                                critter.setDirection(Direction.NONE);
                                break;
                            }
                        }
                    }
                    case EAST -> {
                        for (var n : curNeighbours) {
                            if (config.getCell(n).isWall()) {
                                nextPos = curPos.ceilX();
                                critter.setDirection(Direction.NONE);
                                break;
                            }
                        }
                    }
                    case SOUTH -> {
                        for (var n : curNeighbours) {
                            if (config.getCell(n).isWall()) {
                                nextPos = curPos.ceilY();
                                critter.setDirection(Direction.NONE);
                                break;
                            }
                        }
                    }
                    case WEST -> {
                        for (var n : curNeighbours) {
                            if (config.getCell(n).isWall()) {
                                nextPos = curPos.floorX();
                                critter.setDirection(Direction.NONE);
                                break;
                            }
                        }
                    }
                }
            }

            critter.setPos(nextPos.warp(width, height));
        }
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
        //displayScore();
    }
    
    // FIXME: this should be displayed in the JavaFX view, not in the console
    private void displayScore() {
        point.setText(score+"");
        point.setX(50);
        point.setY(50);
        System.out.println("Score: " + score);
    }
    
    // FIXME: this should be displayed in the JavaFX view, not in the console. A game over screen would be nice too.
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
