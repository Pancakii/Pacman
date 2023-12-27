package gui.Windows;

import gui.App;
import gui.ExitButon;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.MazeState;

import java.awt.*;

public class GameOver implements EventHandler<ActionEvent> {
        private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        private static final double screenSizeWidth = screenSize.getWidth();
        private static final double screenSizeHeight = screenSize.getHeight();
        private static VBox parent = new VBox();
        private static Scene scene = new Scene(parent);
        private static Text score =  new Text();
        private static Image pacmanGift = new Image("GameOverText.png");
        private static ImageView imagePacmanGift = new ImageView(pacmanGift);
        private static boolean lancer = false;


        public static void affichageGameOver() {
                final int tw = 500;
                App.menu.setHeight(tw);
                App.menu.setWidth(tw);
                //Il y a du CSS dans le setStyke, lien de l'image, est ce que ca se repete, la taille et la position
                parent.setStyle("-fx-background-image: url('GOB.png'); -fx-background-repeat: no-repeat;-fx-background-size: 500 500;-fx-background-position: left top;");
                // Ajouter un nom a la page
                App.menu.setTitle("GameOver");
                // Ne plus pouvoir aggrandir/retrecir la page

                score.setText("Score: " + MazeState.score);
                if(!lancer) {
                        //affiche le score apres le game over
                        final int txScore = 150;
                        final int tyScore = 150;
                        final int fsScore = 20;


                        //La ou on place le score
                        score.setTranslateX(txScore);
                        score.setTranslateY(tyScore);
                        //La taille de l'affichage du score
                        score.setFont(Font.font(fsScore));
                        //La couleur
                        score.setFill(Color.WHITE);
                        parent.getChildren().add(score);

                        //affichage du mesage GameOver
                        imagePacmanGift.setTranslateX(150);
                        imagePacmanGift.setTranslateY(20);
                        imagePacmanGift.setFitHeight(100);
                        imagePacmanGift.setFitWidth(200);
                        parent.getChildren().add(imagePacmanGift);


                        //bouton pour rejouer
                        Button boutonReplay = new Button(" Retry"); //Le nom du bouton
                        boutonReplay.setTranslateX(210);//Les coordonnées du bouton
                        boutonReplay.setTranslateY(120);
                        boutonReplay.setFont(Font.font(20));
                        boutonReplay.setBackground(null);
                        boutonReplay.setTextFill(Color.WHITE);
                        boutonReplay.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");

                        boutonReplay.setOnMousePressed(e -> boutonReplay.setScaleX(1.2));
                        boutonReplay.setOnMouseReleased(e -> boutonReplay.setScaleX(1.0));

                        boutonReplay.setOnAction(new Jeu());
                        parent.getChildren().add(boutonReplay);

                        //bouton pour retourner au menu
                        Button menubouton = new Button("Menu");
                        menubouton.setTranslateX(210);
                        menubouton.setTranslateY(140);
                        menubouton.setFont(Font.font(20));
                        menubouton.setBackground(null);
                        menubouton.setTextFill(Color.WHITE);
                        menubouton.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");

                        menubouton.setOnMousePressed(e -> menubouton.setScaleX(1.2));
                        menubouton.setOnMouseReleased(e -> menubouton.setScaleX(1.0));

                        menubouton.setOnAction(new MenuDemarage());
                        parent.getChildren().add(menubouton);

                        //bouton pour quitter tout lle programme
                        Button exitbouton = new Button("  Exit  ");
                        exitbouton.setTranslateX(210);
                        exitbouton.setTranslateY(160);
                        exitbouton.setFont(Font.font(20));
                        exitbouton.setBackground(null);
                        exitbouton.setTextFill(Color.WHITE);
                        exitbouton.setStyle("-fx-border-color: yellow; -fx-border-width: 2px;");

                        exitbouton.setOnMousePressed(e -> exitbouton.setScaleX(1.2));
                        exitbouton.setOnMouseReleased(e -> exitbouton.setScaleX(1.0));

                        exitbouton.setOnAction(new ExitButon());
                        parent.getChildren().add(exitbouton);

                        //Pacman animation
                        Image pacManAnimation = new Image("PacmanDeadAnimation.gif");
                        ImageView pacManAnimationIV = new ImageView(pacManAnimation);
                        pacManAnimationIV.setFitWidth(50);
                        pacManAnimationIV.setFitHeight(50);
                        pacManAnimationIV.setTranslateX(225);
                        pacManAnimationIV.setTranslateY(-70);
                        parent.getChildren().add(pacManAnimationIV);

                        Image inkyAnimation = new Image("Inky.gif");
                        ImageView inkyAnimationIV = new ImageView(inkyAnimation);
                        inkyAnimationIV.setFitWidth(50);
                        inkyAnimationIV.setFitHeight(50);
                        inkyAnimationIV.setTranslateX(50);
                        TranslateTransition inkyTranslateTransition = new TranslateTransition(Duration.seconds(5), inkyAnimationIV);
                        inkyTranslateTransition.setFromY(300); // Démarrez en bas de la scène
                        inkyTranslateTransition.setToY(-inkyAnimationIV.getImage().getHeight() - 300); // Déplacez-vous vers le haut de la scène
                        inkyTranslateTransition.setCycleCount(TranslateTransition.INDEFINITE);
                        parent.getChildren().add(inkyAnimationIV);
                        inkyTranslateTransition.play();

                        Image clydeAnimation = new Image("Clyde.gif");
                        ImageView clydeAnimationIV = new ImageView(clydeAnimation);
                        clydeAnimationIV.setFitWidth(50);
                        clydeAnimationIV.setFitHeight(50);
                        clydeAnimationIV.setTranslateX(100);
                        TranslateTransition clydeTranslateTransition = new TranslateTransition(Duration.seconds(5), clydeAnimationIV);
                        clydeTranslateTransition.setFromY(-clydeAnimationIV.getImage().getHeight() -300);
                        clydeTranslateTransition.setToY(300);
                        clydeTranslateTransition.setCycleCount(TranslateTransition.INDEFINITE);
                        parent.getChildren().add(clydeAnimationIV);
                        clydeTranslateTransition.play();

                        Image pinkyAnimation = new Image("Pinky.gif");
                        ImageView pinkyAnimationIV = new ImageView(pinkyAnimation);
                        pinkyAnimationIV.setFitWidth(50);
                        pinkyAnimationIV.setFitHeight(50);
                        pinkyAnimationIV.setTranslateX(350);
                        TranslateTransition pinkyTranslateTransition = new TranslateTransition(Duration.seconds(5), pinkyAnimationIV);
                        pinkyTranslateTransition.setFromY(-pinkyAnimationIV.getImage().getHeight() -300);
                        pinkyTranslateTransition.setToY(350);
                        pinkyTranslateTransition.setCycleCount(TranslateTransition.INDEFINITE);

                        Image blinkyAnimation = new Image("Blinky.gif");
                        ImageView blinkyAnimationIV = new ImageView(blinkyAnimation);
                        blinkyAnimationIV.setFitWidth(50);
                        blinkyAnimationIV.setFitHeight(50);
                        blinkyAnimationIV.setTranslateX(400);
                        TranslateTransition blinkyTranslateTransition = new TranslateTransition(Duration.seconds(5), blinkyAnimationIV);
                        blinkyTranslateTransition.setFromY(300); // Démarrez en bas de la scène
                        blinkyTranslateTransition.setToY(-blinkyAnimationIV.getImage().getHeight() - 275); // Déplacez-vous vers le haut de la scène
                        blinkyTranslateTransition.setCycleCount(TranslateTransition.INDEFINITE);
                        parent.getChildren().add(blinkyAnimationIV);
                        blinkyTranslateTransition.play();


                        parent.getChildren().add(pinkyAnimationIV);
                        pinkyTranslateTransition.play();


                }

                App.menu.setX((screenSizeWidth- App.menu.getWidth())/2);
                App.menu.setY((screenSizeHeight -App.menu.getHeight())/2);
                App.menu.setScene(scene);
                App.menu.show();
                lancer = true;

    }

        @Override
        public void handle(ActionEvent event) {
                affichageGameOver();
        }
}
