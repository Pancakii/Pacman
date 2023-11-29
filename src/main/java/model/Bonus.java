package model;

import geometry.RealCoordinates;
import misc.Debug;

public class Bonus {
    private static boolean haveBonus ;
    private static double bonusTimer;
    private static final double bonusTimeMax = 10 ;

    private static int point ;
    public static final Bonus INSTANCE = new Bonus();

    private Bonus()
    {
        haveBonus =false ;
        bonusTimer = 0 ;
    }


    public static boolean canHaveBonus() {
        return haveBonus;
    }

    public static void setHaveBonus(boolean havebonus) {
        haveBonus = havebonus;
    }


    // regarde si le fruit peut apparaitre dans la map
    public static boolean appartionFruit(){
        if ( PacMan.getCountDot()>=70 ){
            haveBonus = true ;
        }
        return haveBonus ;
    }

    public void bonusTimer(long delta){
        double delta_double = (double) delta ;
        if(haveBonus) {
            bonusTimer -= delta_double/1000000000;
            if(bonusTimer <= 0) {
                setHaveBonus(false);
                bonusTimer= 0;
            }
        }
    }

    //retourne le point de chaque
    public static int pointBonus(){
        switch (PacMan.getLevel()){
            case 1 -> {
                point = 100 ;
                return point ;
            }
            case 2 -> {
                point = 300 ;
                return point ;
            }
            case 3, 4 -> {
                point = 500 ;
                return point ;
            }
            case 5, 6 -> {
                point = 700 ;
                return point ;
            }
            case 7, 8 -> {
                point = 1000 ;
                return point ;
            }
            case 9, 10 -> {
                point = 2000 ;
                return point ;
            }
            case 11, 12 -> {
                point = 3000 ;
                return point ;
            }
            case 13 -> {
                point = 5000 ;
                return point ;
            }
        }
        return point ;
    }

}
