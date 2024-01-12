package gui.Windows;

import gui.App;
import gui.GraphicsUpdater;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.MazeState;

import java.awt.*;

public class ScoreBoard {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final double screenSizeWidth= screenSize.getWidth();
    private static final double screenSizeHeight = screenSize.getHeight();

    private static VBox parent = new VBox();
    private static Scene scene = new Scene(parent);
    private static boolean lancer = false;

    private static int[] tabScore = {0,0,0,0,0};
    private static String[] tabName = {"Mina","Emirhan","Salim","Sofyane","Alexis"};

    /**
     * Une focntion qui vérifie si le joueur peut être affiché dans le scoreboard
     * @param score le score a la fin du jeu
     * @return boolean
     */
    public static boolean canAdd(int score){
        if(tabScore[4] < score || tabScore[4] == 0) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *  Une fonction qui ajoute le nom et le score dans le scoreboard
     * @param score le score que le joueur a fait
     * @param nom le nom que le joueur a fait
     */
    public static void ajoutTab(int score, String nom){
        int[] newTabScore = new int[5];
        String[] newTabString = new String[5];
        boolean ajoute = false;

        for(int i = 0; i< tabScore.length-1 ;i++){
            if(tabScore[i]<score && ajoute == false){
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

    /**
     *  Une fonction qui ajoute au scoreboard si seulement le score à dépasser le precedent
     * @param score score en fin de jeu
     * @param nom nom du joueur
     */
    public static void newScore(int score, String nom){
        if(canAdd(score)){
            ajoutTab(score,nom);
        }
    }


    static Text nom1 = new Text();
    static Text score1 = new Text();
    static Text nom2 = new Text();
    static Text score2 = new Text();
    static Text nom3 = new Text();
    static Text score3 = new Text();
    static Text nom4 = new Text();
    static Text score4 = new Text();
    static Text nom5 = new Text();
    static Text score5 = new Text();

    /**
     * Une fonction qui affiche le score
     */
    public static void affichageScore(){
        int positionXNom = 140;
        int postionXScore = 240;
        int positionY= 120;

        //affichage du texte HighScore en grand

        Image pacmanGift = new Image("HighScoreImage.png");
        ImageView imagePacmanGift = new ImageView(pacmanGift);
        imagePacmanGift.setTranslateX(220);
        imagePacmanGift.setTranslateY(20);
        imagePacmanGift.setFitHeight(50);
        imagePacmanGift.setFitWidth(50);
        parent.getChildren().add(imagePacmanGift);


        //affichage des noms

        nom1.setText(tabName[0]);
        score1.setText(String.valueOf(tabScore[0]));
        nom1.setTranslateX(positionXNom + 20);
        nom1.setTranslateY(positionY +10);
        nom1.setFont(Font.font(10));
        score1.setTranslateX(postionXScore + 20);
        score1.setTranslateY(positionY);
        score1.setFont(Font.font(10));
        nom1.setFill(Color.YELLOW);
        score1.setFill(Color.YELLOW);
        parent.getChildren().add(nom1);
        parent.getChildren().add(score1);

        nom2.setText(tabName[1]);
        score2.setText(String.valueOf(tabScore[1]));
        nom2.setTranslateX(positionXNom + 20);
        nom2.setTranslateY(positionY +10);
        nom2.setFont(Font.font(10));
        score2.setTranslateX(postionXScore + 20);
        score2.setTranslateY(positionY);
        score2.setFont(Font.font(10));
        nom2.setFill(Color.YELLOW);
        score2.setFill(Color.YELLOW);
        parent.getChildren().add(nom2);
        parent.getChildren().add(score2);

        nom3.setText(tabName[2]);
        score3.setText(String.valueOf(tabScore[2]));
        nom3.setTranslateX(positionXNom + 20);
        nom3.setTranslateY(positionY +10);
        nom3.setFont(Font.font(10));
        score3.setTranslateX(postionXScore + 20);
        score3.setTranslateY(positionY);
        score3.setFont(Font.font(10));
        nom3.setFill(Color.YELLOW);
        score3.setFill(Color.YELLOW);
        parent.getChildren().add(nom3);
        parent.getChildren().add(score3);

        nom4.setText(tabName[3]);
        score4.setText(String.valueOf(tabScore[3]));
        nom4.setTranslateX(positionXNom + 20);
        nom4.setTranslateY(positionY +10);
        nom4.setFont(Font.font(10));
        score4.setTranslateX(postionXScore + 20);
        score4.setTranslateY(positionY);
        score4.setFont(Font.font(10));
        nom4.setFill(Color.YELLOW);
        score4.setFill(Color.YELLOW);
        parent.getChildren().add(nom4);
        parent.getChildren().add(score4);

        nom5.setText(tabName[4]);
        score5.setText(String.valueOf(tabScore[4]));
        nom5.setTranslateX(positionXNom + 20);
        nom5.setTranslateY(positionY +10);
        nom5.setFont(Font.font(10));
        score5.setTranslateX(postionXScore + 20);
        score5.setTranslateY(positionY);
        score5.setFont(Font.font(10));
        nom5.setFill(Color.YELLOW);
        score5.setFill(Color.YELLOW);
        parent.getChildren().add(nom5);
        parent.getChildren().add(score5);

        //Pacman animation
        Image pinkyimation = new Image("Pinky.gif");
        ImageView pinkyimationIV = new ImageView(pinkyimation);
        pinkyimationIV.setFitWidth(50);
        pinkyimationIV.setFitHeight(50);
        pinkyimationIV.setTranslateY(200);
        TranslateTransition pacmanTranslateTransition = new TranslateTransition(Duration.seconds(5), pinkyimationIV);
        pacmanTranslateTransition.setFromX(900);
        pacmanTranslateTransition.setToX(-pinkyimationIV.getImage().getWidth() - 100);
        pacmanTranslateTransition.setCycleCount(pacmanTranslateTransition.INDEFINITE);
        parent.getChildren().add(pinkyimationIV);
        pacmanTranslateTransition.play();

        //Fantome animation
        Image inkyAnimation = new Image("Inky.gif");
        ImageView inkyAnimationIV = new ImageView(inkyAnimation);
        inkyAnimationIV.setFitWidth(50);
        inkyAnimationIV.setFitHeight(50);
        inkyAnimationIV.setTranslateY(150);
        TranslateTransition inkyTranslateTransition = new TranslateTransition(Duration.seconds(5), inkyAnimationIV);
        inkyTranslateTransition.setFromX(800);
        inkyTranslateTransition.setToX(-inkyAnimationIV.getImage().getWidth() - 200);
        inkyTranslateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        inkyTranslateTransition.setCycleCount(inkyTranslateTransition.INDEFINITE);
        parent.getChildren().add(inkyAnimationIV);
        inkyTranslateTransition.play();

        Image blinkyAnimation = new Image("Blinky.gif");
        ImageView blinkyAnimationIV = new ImageView(blinkyAnimation);
        blinkyAnimationIV.setFitWidth(50);
        blinkyAnimationIV.setFitHeight(50);
        blinkyAnimationIV.setTranslateY(100);
        TranslateTransition blinkyTranslateTransition = new TranslateTransition(Duration.seconds(5), blinkyAnimationIV);
        blinkyTranslateTransition.setFromX(700);
        blinkyTranslateTransition.setToX(-blinkyAnimationIV.getImage().getWidth() - 300);
        blinkyTranslateTransition.setCycleCount(blinkyTranslateTransition.INDEFINITE);
        parent.getChildren().add(blinkyAnimationIV);
        blinkyTranslateTransition.play();

        Image clydeAnimation = new Image("Clyde.gif");
        ImageView clydeAnimationIV = new ImageView(clydeAnimation);
        clydeAnimationIV.setFitWidth(50);
        clydeAnimationIV.setFitHeight(50);
        clydeAnimationIV.setTranslateY(50);
        TranslateTransition clydeTranslateTransition = new TranslateTransition(Duration.seconds(5), clydeAnimationIV);
        clydeTranslateTransition.setFromX(600);
        clydeTranslateTransition.setToX(-clydeAnimationIV.getImage().getWidth() - 400);
        clydeTranslateTransition.setCycleCount(clydeTranslateTransition.INDEFINITE);
        parent.getChildren().add(clydeAnimationIV);
        clydeTranslateTransition.play();

        Image pacManAnimation = new Image("PacmanLeft.gif");
        ImageView pacManAnimationIV = new ImageView(pacManAnimation);
        pacManAnimationIV.setFitWidth(50);
        pacManAnimationIV.setFitHeight(50);
        pacManAnimationIV.setTranslateY(0);
        TranslateTransition pacManTranslateTransition = new TranslateTransition(Duration.seconds(5), pacManAnimationIV);
        pacManTranslateTransition.setFromX(500);
        pacManTranslateTransition.setToX(-pacManAnimationIV.getImage().getWidth() - 500);
        pacManTranslateTransition.setCycleCount(pacManTranslateTransition.INDEFINITE);
        parent.getChildren().add(pacManAnimationIV);
        pacManTranslateTransition.play();

        Text leave = new Text();
        leave.setText("Press space");
        leave.setTranslateX(220);
        leave.setTranslateY(0);
        leave.setFill(Color.WHITE);
        parent.getChildren().add(leave);


    }

    public static GraphicsUpdater updateScore(){
        return new GraphicsUpdater() {
            @Override
            public void update() {
                nom1.setText(tabName[0]);
                nom2.setText(tabName[1]);
                nom3.setText(tabName[2]);
                nom4.setText(tabName[3]);
                nom5.setText(tabName[4]);
            }
            @Override
            public Node getNode() {
                return null;
            }
        };
    }

    /**
     * Une fonction qui affiche le scoreboard
     */
    public static void afficheScoreBoard(){

        parent.getChildren().clear();
        newScore(MazeState.score,MazeState.nickname);
        affichageScore();

        App.menu.setHeight(500);
        App.menu.setWidth(500);
        parent.setStyle("-fx-background-color: #000000");
        App.menu.setTitle("ScoreBoard");


        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                GameOver.affichageGameOver();}
        });


        App.menu.setX((screenSizeWidth - App.menu.getWidth())/2);
        App.menu.setY((screenSizeHeight -App.menu.getHeight())/2);
        App.menu.setScene(scene);
        App.menu.show();
        lancer = true;



    }

}
