package model;

import config.MazeConfig;
import geometry.IntCoordinates;
import geometry.RealCoordinates;
import gui.Windows.GameOver;
import gui.PacmanController;

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

    public static void setNickname(String name) {
        nickname = name;
    }

    public void update(long deltaTns) {
    	this.Neighbours(deltaTns);
        pacmanUpdate(deltaTns);
        bonusUpdate(deltaTns);
    }


    /*
    Uses Pacman functions to update its state
     */
    private void pacmanUpdate(long deltaTns)
    {
        PacMan.checknEatCell(getConfig(), this.gridState);
        eatGhosts();
        PacMan.INSTANCE.energizedTimerCount(deltaTns);
        PacmanController.checknWalk(this.config);
    }
    private void bonusUpdate(long deltaTns){
        PacMan.eatBonus();
        if ( Bonus.canHaveBonus()) {
            Bonus.INSTANCE.bonusTimer(deltaTns);
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
            if(PacMan.INSTANCE.isEnergized())
            {
                for (Critter ghost : close_ghosts) {
                    addScore(10);
                    resetCritter(ghost);
                }
            }
            else
            {
                playerLost();
            }
        }
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


    public static void addScore(int increment) {
        score += increment;
    }



    private void playerLost() {
        lives--;
        if(MazeState.lives == 0){
            ajoutScore(nickname,score);
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
        PacMan.setCountDot(0);

    }

    private void resetCritter(Critter critter) {
        critter.setDirection(Direction.NONE);
        critter.setPos(initialPos.get(critter));
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

    public boolean tryAddScore(int score){
        for(int i = 0; i<tabScore.length;i++){
            if(score>tabScore[i]){
                return true;
            }
        }
        return false;
    }

    public void ajoutScoreDansLaListe(String name, int score, int position){
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
    public void ajoutScore(String name, int score){
        if(tryAddScore(score)){
           int position = 0;
           while(tabScore[position] > score && tabScore[position] != 0){
                 position = position + 1;
           }

           ajoutScoreDansLaListe(name,score,position);
        }

    }


}
