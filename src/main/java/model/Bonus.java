package model;

public class Bonus {
    private static boolean haveBonus ;
    private static double bonusTimer;
    private static int point ;
    public static final Bonus INSTANCE = new Bonus();

    /**
     * Initialisation du bonus
     */
    private Bonus()
    {
        haveBonus =false ;
        bonusTimer = 0 ;
    }


    /**
     * Retourne si pacman peut manger un bonus ou pas
     */
    public static boolean canHaveBonus() {
        return haveBonus;
    }

    /**
     *  Change haveBonus
     */
    public static void setHaveBonus(boolean havebonus) {
        haveBonus = havebonus;
    }

    /**
     * Change le temps du timer
     */
    public static void setBonusTimer (double bonusTime){
         bonusTimer = bonusTime;
    }

    /**
     * Regarde si le fruit peut apparaitre dans la map et retoune haveBonus
     * @return haveBonus
     */
    public static boolean appartionFruit(){
        if ( PacMan.getCountDot()%70==0 && bonusTimer <= 10 && PacMan.getCountDot() != 0){
            haveBonus = true ;
        }
        return haveBonus ;
    }

    /**
     *  timer du bonus
     * @param delta
     */
    public void bonusTimer(long delta){
        double delta_double = (double) delta ;
        if(haveBonus && bonusTimer <= 10) {
            bonusTimer += delta_double*1E-9;
            if(bonusTimer>=10){
                setHaveBonus(false);
                bonusTimer =0 ;
            }
        }
    }

    /**
     * Retourne le point de chaque bonus
     * @return point
     */
    public static int pointBonus(){
        switch (PacMan.getLevel()){
            case 1 -> point = 100 ;
            case 2 -> point = 300 ;
            case 3, 4 -> point = 500 ;
            case 5, 6 -> point = 700 ;
            case 7, 8 -> point = 1000 ;
            case 9, 10 -> point = 2000 ;
            case 11, 12 -> point = 3000 ;
            case 13 -> point = 5000 ;
        }
        return point ;
    }

}
