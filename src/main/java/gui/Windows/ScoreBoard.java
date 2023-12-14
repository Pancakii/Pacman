package gui.Windows;

import gui.App;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.MazeState;

public class ScoreBoard {

    private static VBox parent = new VBox();
    private static Scene scene = new Scene(parent);
    private static boolean lancer = false;

    private static int[] tabScore = {0,0,0,0,0};
    private static String[] tabName = {"Player","Player","Player","Player","Player"};


    public static boolean canAdd(int score){
        if(tabScore[4] < score || tabScore[4] == 0) {
            return true;
        }
        else{
            return false;
        }
    }

    public static void ajoutTab(int score, String nom){
        int[] newTabScore = new int[5];
        String[] newTabString = new String[5];
        boolean ajoute = false;

        for(int i = 0; i< tabScore.length-1 ;i++){
            if(tabScore[i]<score && ajoute==false){
                newTabScore[i] = score;
                newTabString[i] = nom;
                ajoute = true;
            }
            if(ajoute == true){
                newTabScore[i+1] = tabScore[i];
                newTabString[i+1] = tabName[i];
            }
            else{
                newTabScore[i] = tabScore[i];
                newTabString[i] = tabName[i];

            }

        }

        tabName = newTabString;
        tabScore = newTabScore;

    }

    public static void newScore(int score, String nom){
        if(canAdd(score)){
            ajoutTab(score,nom);
        }
    }

    public static void afficheScore(){
        for(int i = 0;i<tabScore.length;i++){
            System.out.println(tabName[i] + tabScore[i]);
        }
    }

    public static void afficheScoreBoard(){

        int positionXNom = 160;
        int postionXScore = 260;
        int positionYS= 170;


        newScore(MazeState.score,MazeState.nickname);
        afficheScore();
        System.out.println();

        App.menu.setHeight(500);
        App.menu.setWidth(500);
        parent.setStyle(String.valueOf(Color.BLACK));
        App.menu.setTitle("ScoreBoard");



        if(!lancer){

            for(int i =0; i<tabScore.length;i++){

            }

            Button gameover = new Button("GameOver");
            gameover.setTranslateX(210);
            gameover.setTranslateY(170);
            gameover.setFont(Font.font(20));
            gameover.setOnAction(new GameOver());
            parent.getChildren().add(gameover);
        }

        App.menu.setScene(scene);
        App.menu.show();
        lancer =true;
    }

}
