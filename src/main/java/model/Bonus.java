package model;

public class Bonus {
    private boolean haveBonus ;
    private double bonusTimer;
    private final double bonusTimeMax = 5 ;

    public boolean canHaveBonus() {
        return haveBonus;
    }

    public void setHaveBonus(boolean haveBonus) {
        this.haveBonus = haveBonus;
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
