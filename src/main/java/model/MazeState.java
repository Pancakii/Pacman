package model;


import gui.PacmanController;
import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.Windows.GameOver;
import gui.PacmanController;


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
    public static String nickname = "Nobody";
    private String[] tabNickname = new String[5];
    private int[] tabScore = new int[5];

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

    /**
     * Méthode servant à mettre à jour le jeu
     * @param deltaTns	Donnée utiliser pour le calcul de la prochaine position des critters
     * @param pause	Vérification si l'utilisateur a mis le jeu en pause
     */
    public void update(long deltaTns, boolean pause) {
    	if(!pause) {
    		moveCritters(deltaTns);
            boolean ate_energizer = updatePacman(deltaTns);
            updateGhosts(deltaTns, ate_energizer);
            bonusUpdate(deltaTns);
            updateMap();
    	}
    }


    /**
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

    /**
     *  Affiche si le bonus peut apparaitre
     * @param deltaTns
     */
    private void bonusUpdate(long deltaTns){
        PacMan.eatBonus(); // vérifie s'il peut manger un fruit
        if ( Bonus.canHaveBonus()) { // vérifie si le bonus peut être manger
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

    /**
    *GHOST FUNCTIONS
    *Updates all ghosts
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

    public static void addScore(int increment) {
        score += increment;
    }

    private void playerLost() {
        MazeState.lives--;
        if(MazeState.lives == 0){
            ajoutScore(score);
            for(int i = 0;i< tabNickname.length;i++){
                System.out.println(tabNickname[i]);
                System.out.println(tabScore[i]);
            }

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
        PacMan.setCountDotTotal(0);
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
            for(int j = 0; j< gridState[i].length;j++){
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

    public boolean tryAddScore(int score){
        for(int i = 0; i<tabScore.length;i++){
            if(score>tabScore[i]){
                return true;
            }
        }
        return false;
    }

    public void ajoutScoreDansLaListe(int position){
        String[] tabNomF = new String[5];
        int[] tabScoreF = new  int[5];

        for(int p = 0;p<position;p++){
            tabNomF[p] = this.tabNickname[p];
            tabScoreF[p] = this.tabScore[p];
        }
        tabNomF[position] = this.tabNickname[position];
        tabScoreF[position] = this.tabScore[position];

        for(int i = position+1; i<tabScore.length;i++){
            tabNomF[i] = this.tabNickname[i-1];
            tabScoreF[i] = this.tabScore[i-1];
        }
        this.tabNickname = tabNomF;
        this.tabScore = tabScoreF;

    }
    public void ajoutScore(int score){
        if(tryAddScore(score)){
           int position = 0;
           while(tabScore[position] > score && tabScore[position] != 0){
                 position = position + 1;
           }

           ajoutScoreDansLaListe(position);
        }

    }


}
