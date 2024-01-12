package model;


import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.PacmanController;
import gui.Windows.ScoreBoard;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static model.Ghost.*;

public final class MazeState {
    private final MazeConfig config;
    private final int height;
    private final int width;
    private final boolean[][] gridState;
    private final List<Critter> critters;
    public static int score;
    public static int addLiveScore ;
    public static int lives = 3;
    private final Map<Critter, RealCoordinates> initialPos;
    public static String nickname;

    /**
     * Constructor of MazeState
     * @param config mazeconfig
     */
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

    /**
     * Gets critter list, ghosts and pacman.
     * @return the critter list
     */
    public List<Critter> getCritters() {
        return critters;
    }

    /**
     * Gets width.
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets height.
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Méthode servant à mettre à jour le jeu
     * @param deltaTns	Donnée utiliser pour le calcul de la prochaine position des critters
     * @param pause	Vérification si l'utilisateur a mis le jeu en pause
     */
    public void update(long deltaTns, boolean pause) {
    	if(!pause && lives > 0) {
    		moveCritters(deltaTns);
            boolean ate_energizer = updatePacman(deltaTns);
            updateGhosts(deltaTns, ate_energizer);
            bonusUpdate(deltaTns);
            updateMap();
            addLive();
    	}
    }



    //PACMAN FUNCTIONS

    /**
     * Uses Pacman functions to update its state. Returns whether pacman ate an energizer or not.
     * @param deltaTns delta time
     * @return whether pacman ate an energizer or not.
     */
    private boolean updatePacman(long deltaTns)
    {
        boolean ate_energizer = PacMan.checknEatCell(getConfig(), this.gridState);
        eatGhosts();
        PacMan.INSTANCE.energizedTimerCount(deltaTns);
        PacmanController.checknWalk(this.config);
        return ate_energizer;
    }

    /**
     *  Affiche si le bonus peut apparaitre
     * @param deltaTns le temps du bonus
     */
    private void bonusUpdate(long deltaTns){
        if ( Bonus.canHaveBonus()) { // vérifie si le bonus peut être manger
            PacMan.eatBonus(); // vérifie s'il peut manger un fruit
            Bonus.INSTANCE.bonusTimer(deltaTns); // lance le timer du bonus
        }
    }

    /**
     *  Met à jour la map quand tout les dots sont mangées
     */
    public void updateMap(){
        if ( PacMan.getCountDotTotal()==PacMan.getDotTotal()){ // regarde si les dots sont tous mangées
            resetGridState(); // reset la map
            PacMan.setCountDotTotal(0); // reset le compteur de dot
            PacMan.setLevel(PacMan.getLevel()+1); // augmente le niveau
			resetCritters(); // reset les critters ( Pacman et Ghosts )
        }
    }

    /**
     * Si pacman entre dans une case où il y a un fantôme
     * on vérifie s'il est énergisé:
     * -Oui : Gagne 200 pts et mange le fantôme
     * -Non : Perd une vie ou Game Over si 0 vie
     */
    private void eatGhosts() {
        List<Critter> close_ghosts = PacMan.INSTANCE.closeGhosts(critters);
        if(!close_ghosts.isEmpty())
        {
            for (Critter g : close_ghosts)
            {
                Ghost ghost = (Ghost)g;
                if(!ghost.isEaten() && ghost.isFrightened() && PacMan.INSTANCE.isEnergized())
                {
                    addScore(200);
                    ghost.setEaten(true);
                    ghost.setFrightened(false);
                    PacMan.INSTANCE.playEatGhostSound();
                }
                if(!ghost.isFrightened() && !ghost.isEaten())
                {
                    playerLost();
                    PacMan.INSTANCE.playGameOverSound();
                    break;
                }
            }
        }
    }

    //GHOST FUNCTIONS
    /**
     * Updates all ghosts.
     * @param deltaTns delta time
     * @param ate_energizer whether pacman ate an energizer or not
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


    /**
     * Updates the given ghost.
     * @param critter the said ghost
     * @param deltaTns delta time
     * @param ate_energizer whether pacman ate an energizer or not
     */
    public void updateGhost(Critter critter, long deltaTns, boolean ate_energizer)
    {
        Ghost ghost = (Ghost) critter;
        if(ate_energizer)
        {
            ghost.setFrightened(true);
            ghost.resetPath();
        }
        if(!PacMan.INSTANCE.isEnergized())
        {
            ghost.setFrightened(false);
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

    /**
     * Méthode servant à mettre à jour la position des critters
     * @param deltaTns	Donnée utiliser pour le calcul de la prochaine position des critters
     */
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
    
    /**
     * Méthode qui vérifié si le prochain position pris par le critter est valide
     * @param pos	Coordonnées de la prochaine position du critter
     * @param critter	Terme qui regroupe les fantômes et pacman(= le joueur)
     * @return (true) si le mouvement du critter est valide ou (false) à l'inverse
     */
    private boolean isValidPosition(RealCoordinates pos, Critter critter) {

        // Vérifie si la prochaine position est un mur ou est passable
        if 	(critter == PacMan.INSTANCE && config.isWall(pos) || 
            (critter != PacMan.INSTANCE && config.isWall(pos) && !config.isPassable(pos))) {
            return false;
        }

        // Vérifie si les prochaines positions basées sur la prochaine direction sont des murs
        Set<IntCoordinates> curNeighbours = pos.intNeighbours();
        for (IntCoordinates neighbour : curNeighbours) {
            if (config.isWall(neighbour) && !config.isPassable(neighbour.toRealCoordinates(1.0))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Adds the given value to score and addLiveScore.
     * @param increment the said value
     */
    public static void addScore(int increment) {
        score += increment;
        addLiveScore +=increment ;
    }

    /**
     * Ajoute une vie quand le joueur atteint le score de 10000 points
     */
    public void addLive(){
        if (addLiveScore >= 10000) {    // vérifie que le score du joueur a bien atteint 10000 points
            PacMan.INSTANCE.playExtraPacSound();
            setAddLiveScore(0); // remet le compteur qui rajout de la vie à 0
            lives++; // ajoute une vie
        }
    }

    /**
     * Removes 1 life and resets critters. If life is 0, then shows the scoreboard.
     */
    private void playerLost() {
        MazeState.lives--;
        PacMan.INSTANCE.setEnergized(false);
        if(MazeState.lives == 0){
            ScoreBoard.afficheScoreBoard();
        }
        resetCritters();
    }

    /**
     * Resets the game values concerning critters, level, score, bonus and level.
     */
    public void resetGame(){
        MazeState.lives = 3;
        MazeState.score = 0;
        PacMan.setLevel(1);
        PacMan.setCountDot(0);
        PacMan.setCountDotTotal(0);
        setAddLiveScore(0);
        Bonus.setHaveBonus(false);
        Bonus.setBonusTimer(0);
        resetGridState();
        resetCritters();
    }

    /**
     * Resets single critter's position and direction. If it is a ghost, then also resets eaten, frightened and get_out_timer.
     * @param critter the said critter
     */
    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));
        if(critter instanceof Ghost)
        {
            ((Ghost) critter).setFrightened(false);
            ((Ghost) critter).setEaten(false);
            ((Ghost) critter).setGet_out_timer(0);
        }
    }

    /**
     * Resets all critters.
     */
    private void resetCritters() {
        for (var critter: critters) resetCritter(critter);
    }

    /**
     * Resets gridState.
     */
    private void resetGridState(){
        for(int i = 0; i< gridState.length;i++){
            for(int j = 0; j< gridState[i].length;j++){
                gridState[i][j] = false;
            }
        }
    }

    /**
     * Gets config
     * @return config
     */
    public MazeConfig getConfig() {
        return config;
    }

    /**
     * Gets gridState element using IntCoordinates position.
     * @param pos said position
     * @return the value of the element
     */
    public boolean getGridState(IntCoordinates pos) {
        return gridState[pos.y()][pos.x()];
    }

    /**
     * Sets the addLiveScore the given value.
     * @param addLiveScore the said value
     */
    public static void setAddLiveScore(int addLiveScore) {
        MazeState.addLiveScore = addLiveScore;
    }
}