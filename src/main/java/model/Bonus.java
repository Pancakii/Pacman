package model;

public class Bonus {
    private static boolean haveBonus =true;
    private double bonusTimer;
    private final double bonusTimeMax = 5 ;

    public static boolean canHaveBonus() {
        return haveBonus;
    }

    public static void setHaveBonus(boolean havebonus) {
        haveBonus = havebonus;
    }

    public void bonusTimer (long delta){
        double delta_double = (double) delta ;
        if(haveBonus) {
            bonusTimer -= delta_double/1000000000;
            if(bonusTimer <= 0) {
                setHaveBonus(false);
                bonusTimer= 0;
            }
        }
    }



}
