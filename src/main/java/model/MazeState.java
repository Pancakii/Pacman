package model;

import gui.PacmanController;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.GameOver;


import java.util.Set;
import java.util.List;
import java.util.Map;

import static model.Ghost.*;

public final class MazeState {
    private final MazeConfig config;
    private final int height;
    private final int width;
    private final boolean[][] gridState;
    private final List<Critter> critters;
    public static int score;
    public static int lives = 3;

    private final Map<Critter, RealCoordinates> initialPos;


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


    public void update(long deltaTns) {
    	moveCritters(deltaTns);
        boolean ate_energizer = updatePacman(deltaTns);
        updateGhosts(deltaTns, ate_energizer);
        bonusUpdate(deltaTns);
        updateMap();
    }


    /*
    PACMAN FUNCTIONS


    Uses Pacman functions to update its state
     */
    private boolean updatePacman(long deltaTns)
    {
        boolean ate_energizer = PacMan.checknEatCell(getConfig(), this.gridState);
        eatGhosts();
        PacMan.INSTANCE.energizedTimerCount(deltaTns);
        PacmanController.checknWalk(this.config);
        return ate_energizer;
    }
    
    private void bonusUpdate(long deltaTns){
        PacMan.eatBonus();
        if ( Bonus.canHaveBonus()) {
            Bonus.INSTANCE.bonusTimer(deltaTns);
        }
    }

    public void updateMap(){
        if ( PacMan.getCountDotTotal()==PacMan.getDotTotal()){
            resetGridState();
            PacMan.setCountDotTotal(0);
            PacMan.setLevel(PacMan.getLevel()+1);
			resetCritters();
			
        }
    }

    /*
     * Si pacman entre dans une case où il y a un fantôme
     * on vérifie s'il est énergiser
     * Oui : Gagne 10pts et tue le fantôme
     * Non : Perd une vie ou Game Over si 0 vie
     */
    private void eatGhosts() {
        List<Critter> close_ghosts = PacMan.INSTANCE.closeGhosts(critters);
        if(!close_ghosts.isEmpty())
        {
            for (Critter g : close_ghosts)
            {
                Ghost ghost = (Ghost)g;
                if(!ghost.eaten && ghost.frightened && PacMan.INSTANCE.isEnergized())
                {
                    addScore(200);
                    ghost.eaten = true;
                    ghost.frightened = false;
                }
                if(!ghost.frightened && !ghost.eaten)
                {
                    playerLost();
                    break;
                }
            }
        }
    }

    /*
    GHOST FUNCTIONS

    Updates all ghosts
     */
    public void updateGhosts(long deltaTns, boolean ate_energizer)
    {
        for(Critter critter : critters)
        {
            if(critter instanceof Ghost)
            {
                updateGhost(critter, deltaTns, ate_energizer);
            }
        }
    }

    // Updates one ghost
    public void updateGhost(Critter critter, long deltaTns, boolean ate_energizer)
    {
        Ghost ghost = (Ghost) critter;
        if(ate_energizer)
        {
            ghost.frightened = true;
            ghost.resetPath();
        }
        if(!PacMan.INSTANCE.isEnergized())
        {
            ghost.frightened = false;
        }

        ghost.scatterChaseTimer(deltaTns);

        switch (ghost)
        {
            case BLINKY : ghost.getPath(config.getGrid(), config, deltaTns, config.getInkyPos().toRealCoordinates(1.0));
            case INKY : ghost.getPath(config.getGrid(), config, deltaTns, config.getInkyPos().toRealCoordinates(1.0));
            case PINKY : ghost.getPath(config.getGrid(), config, deltaTns, config.getPinkyPos().toRealCoordinates(1.0));
            case CLYDE : ghost.getPath(config.getGrid(), config, deltaTns, config.getClydePos().toRealCoordinates(1.0));
        }
        ghost.followPath();
    }


    private void moveCritters(long deltaTns) {
        for (var critter : critters) {
            var nextPos = critter.nextPos(deltaTns, PacMan.getLevel());

            // Check if the next position is valid
            if (isValidPosition(nextPos, critter)) {
                critter.setPos(nextPos.warp(width, height));
            } else {
                critter.setPos(nextPos.warp(width, height).round().toRealCoordinates(1.0));
            }
        }
    }

    private boolean isValidPosition(RealCoordinates pos, Critter critter) {

        // Verifie si la prochaine position est un mur ou est passable
        if 	(critter == PacMan.INSTANCE && !config.isPassable(pos) ||
            (critter != PacMan.INSTANCE && config.isWall(pos))) {
            return false;
        }

        // Verifie si les prochains basees sur la prochaine direction sont des murs
        Set<IntCoordinates> curNeighbours = pos.intNeighbours();
        for (IntCoordinates neighbour : curNeighbours) {
            if (config.isWall(neighbour) && !config.isPassable(neighbour.toRealCoordinates(1.0))) {
                return false;
            }
        }

        return true;
    }

    public static void addScore(int increment) {
        score += increment;
    }

    private void playerLost() {
        MazeState.lives--;
        if(MazeState.lives == 0){
            GameOver.affichageGameOver();
            resetGame();
        }
        resetCritters();
    }
    
    public void resetGame(){
        MazeState.lives = 3;
        MazeState.score = 0;
        resetGridState();
        resetCritters();
        PacMan.setLevel(1);
        PacMan.setCountDot(0);
    }

    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));
        if(critter instanceof Ghost)
        {
            ((Ghost) critter).frightened = false;
            ((Ghost) critter).eaten = false;

        }
    }

    private void resetCritters() {
        for (var critter: critters) resetCritter(critter);
    }

    private void resetGridState(){
        for(int i = 0; i< gridState.length;i++){
            for(int j = 0; j< gridState.length;j++){
                gridState[i][j] = false;
            }
        }
    }

    public MazeConfig getConfig() {
        return config;
    }

    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }
}
