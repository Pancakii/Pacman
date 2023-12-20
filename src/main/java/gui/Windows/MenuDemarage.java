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
import javafx.util.Duration;

public class MenuDemarage implements EventHandler<ActionEvent> {

        private static Jeu jeu = new Jeu();

        public static VBox parent = new VBox();
        public static Scene scene = new Scene(parent);
        private static boolean lancer = false;

        public static void affichageMenuDemarage()  {


                App.menu.setHeight(500);
                App.menu.setWidth(500);

                //Il y a du CSS dans le setStyke, lien de l'image, est ce que ca se repete, la taille et la position
                parent.setStyle("-fx-background-image: url('background.jpeg'); -fx-background-repeat: no-repeat;-fx-background-size: 500 500;-fx-background-position: left top;");
                // Ajouter un nom a la page
                App.menu.setTitle("Menu");



                if(!lancer){
                        //affichage du texte PacMan en grand

                        Image pacmanGift = new Image("MenuTextPacMan.gif");
                        ImageView imagePacmanGift = new ImageView(pacmanGift);
                        imagePacmanGift.setTranslateX(190);
                        imagePacmanGift.setTranslateY(40);
                        imagePacmanGift.setFitHeight(200);
                        imagePacmanGift.setFitWidth(200);
                        parent.getChildren().add(imagePacmanGift);


                        //bouton pour jouer
                        Button jouerbouton = new Button("  Play "); //Le nom du bouton
                        jouerbouton.setTranslateX(230);//Les coordonnées du bouton
                        jouerbouton.setTranslateY(20);
                        jouerbouton.setFont(Font.font(20));
                        jouerbouton.setBackground(null);
                        jouerbouton.setTextFill(Color.WHITE);
                        jouerbouton.setStyle("-fx-border-color: yellow; -fx-border-width: 2px; -fx-font-family: 'Arial'");

                        jouerbouton.scaleShapeProperty();
                        jouerbouton.setOnAction(new AskName());
                        parent.getChildren().add(jouerbouton);

                        //bouton pour les modes
                        Button modebouton = new Button("Mode");
                        modebouton.setTranslateX(230);
                        modebouton.setTranslateY(40);
                        modebouton.setBackground(null);
                        modebouton.setTextFill(Color.WHITE);
                        modebouton.setStyle("-fx-border-color: yellow; -fx-border-width: 2px; -fx-font-family: 'Arial'");
                        modebouton.setFont(Font.font(20));


                        //App.mebouton.setOnAction();
                        parent.getChildren().add(modebouton);

                        //bouton pour quitter tout lle programme
                        Button exitbouton = new Button("  Exit  ");
                        exitbouton.setTextFill(Color.WHITE);
                        exitbouton.setTranslateX(230);
                        exitbouton.setTranslateY(60);
                        exitbouton.setFont(Font.font(20));
                        exitbouton.setStyle("-fx-border-color: yellow; -fx-border-width: 2px; -fx-font-family: 'Arial'");
                        exitbouton.setBackground(null);
                        exitbouton.setOnAction(new ExitButon());
                        parent.getChildren().add(exitbouton);

                        //Pacman animation
                        Image pacManAnimation = new Image("PacmanRight.gif");
                        ImageView pacManAnimationIV = new ImageView(pacManAnimation);
                        pacManAnimationIV.setFitWidth(50);
                        pacManAnimationIV.setFitHeight(50);
                        pacManAnimationIV.setTranslateX(-500);
                        pacManAnimationIV.setTranslateY(80);
                        TranslateTransition pacmanTranslateTransition = new TranslateTransition(Duration.seconds(5), pacManAnimationIV);
                        pacmanTranslateTransition.setFromX(-pacManAnimationIV.getImage().getWidth());
                        pacmanTranslateTransition.setToX(500);
                        pacmanTranslateTransition.setCycleCount(pacmanTranslateTransition.INDEFINITE);
                        parent.getChildren().add(pacManAnimationIV);
                        pacmanTranslateTransition.play();

                        //Fantome animation
                        Image blinkyAnimation = new Image("Blinky.gif");
                        ImageView blinkyAnimationIV = new ImageView(blinkyAnimation);
                        blinkyAnimationIV.setFitWidth(50);
                        blinkyAnimationIV.setFitHeight(50);
                        blinkyAnimationIV.setTranslateX(-400);
                        blinkyAnimationIV.setTranslateY(30);
                        TranslateTransition blinkyTranslateTransition = new TranslateTransition(Duration.seconds(5), blinkyAnimationIV);
                        blinkyTranslateTransition.setFromX(-blinkyAnimationIV.getImage().getWidth());
                        blinkyTranslateTransition.setToX(600);
                        blinkyTranslateTransition.setCycleCount(blinkyTranslateTransition.INDEFINITE);
                        parent.getChildren().add(blinkyAnimationIV);
                        blinkyTranslateTransition.play();

                        Image inkyAnimation = new Image("Inky.gif");
                        ImageView inkyAnimationIV = new ImageView(inkyAnimation);
                        inkyAnimationIV.setFitWidth(50);
                        inkyAnimationIV.setFitHeight(50);
                        inkyAnimationIV.setTranslateX(-300);
                        inkyAnimationIV.setTranslateY(-20);
                        TranslateTransition inkyTranslateTransition = new TranslateTransition(Duration.seconds(5), inkyAnimationIV);
                        inkyTranslateTransition.setFromX(-inkyAnimationIV.getImage().getWidth());
                        inkyTranslateTransition.setToX(700);
                        inkyTranslateTransition.setCycleCount(inkyTranslateTransition.INDEFINITE);
                        parent.getChildren().add(inkyAnimationIV);
                        inkyTranslateTransition.play();

                        Image clydeAnimation = new Image("Clyde.gif");
                        ImageView clydeAnimationIV = new ImageView(clydeAnimation);
                        clydeAnimationIV.setFitWidth(50);
                        clydeAnimationIV.setFitHeight(50);
                        clydeAnimationIV.setTranslateX(-200);
                        clydeAnimationIV.setTranslateY(-70);
                        TranslateTransition clydeTranslateTransition = new TranslateTransition(Duration.seconds(5), clydeAnimationIV);
                        clydeTranslateTransition.setFromX(-clydeAnimationIV.getImage().getWidth());
                        clydeTranslateTransition.setToX(800);
                        clydeTranslateTransition.setCycleCount(clydeTranslateTransition.INDEFINITE);
                        parent.getChildren().add(clydeAnimationIV);
                        clydeTranslateTransition.play();

                        Image pinkyAnimation = new Image("Pinky.gif");
                        ImageView pinkyAnimationIV = new ImageView(pinkyAnimation);
                        pinkyAnimationIV.setFitWidth(50);
                        pinkyAnimationIV.setFitHeight(50);
                        pinkyAnimationIV.setTranslateX(-100);
                        pinkyAnimationIV.setTranslateY(-120);
                        TranslateTransition pinkyTranslateTransition = new TranslateTransition(Duration.seconds(5), pinkyAnimationIV);
                        pinkyTranslateTransition.setFromX(-pinkyAnimationIV.getImage().getWidth());
                        pinkyTranslateTransition.setToX(900);
                        pinkyTranslateTransition.setCycleCount(pinkyTranslateTransition.INDEFINITE);
                        parent.getChildren().add(pinkyAnimationIV);
                        pinkyTranslateTransition.play();


                }


                App.menu.setScene(scene);
                App.menu.show();
                lancer = true;
        }

        @Override
        public void handle(ActionEvent event) {
                affichageMenuDemarage();
        }
}
