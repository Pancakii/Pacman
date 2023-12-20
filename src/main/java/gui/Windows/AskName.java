package gui.Windows;

import gui.App;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.MazeState;

public class AskName implements EventHandler{

    private static VBox parent = new VBox();
    private static Scene scene = new Scene(parent);
    private static boolean lancer = false;


    public static void AskName(){

        App.menu.setHeight(200);
        App.menu.setWidth(500);
        App.menu.setTitle("Name");
        parent.setStyle("-fx-background-color: #000000");
        if(!lancer){
            //La barre qui permet d'ajouter un nom

            GridPane gridPane = new GridPane();
            TextField txtName = new TextField("Random");
            txtName.setPromptText("Nickname");
            txtName.setFocusTraversable(true);
            gridPane.add(txtName,1,0,1,1);

            gridPane.setTranslateX(175);
            gridPane.setTranslateY(100);
            gridPane.setVgap(8);
            gridPane.setHgap(10);
            txtName.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    MazeState.nickname = txtName.getText();
                    Jeu.Game();
                }
            });
            parent.getChildren().add(gridPane);



            //Pacman animation
            Image pacManAnimation = new Image("PacmanRight.gif");
            ImageView pacManAnimationIV = new ImageView(pacManAnimation);
            pacManAnimationIV.setFitWidth(50);
            pacManAnimationIV.setFitHeight(50);
            pacManAnimationIV.setTranslateX(-500);
            pacManAnimationIV.setTranslateY(10);
            TranslateTransition pacmanTranslateTransition = new TranslateTransition(Duration.seconds(5), pacManAnimationIV);
            pacmanTranslateTransition.setFromX(-pacManAnimationIV.getImage().getWidth());
            pacmanTranslateTransition.setToX(500);
            pacmanTranslateTransition.setCycleCount(pacmanTranslateTransition.INDEFINITE);
            parent.getChildren().add(pacManAnimationIV);
            pacmanTranslateTransition.play();

            //Fantome animation
            Image blinkyAnimation = new Image("eyeGhost.jpg");
            ImageView blinkyAnimationIV = new ImageView(blinkyAnimation);
            blinkyAnimationIV.setFitWidth(50);
            blinkyAnimationIV.setFitHeight(50);
            blinkyAnimationIV.setTranslateX(-400);
            blinkyAnimationIV.setTranslateY(-40);
            TranslateTransition blinkyTranslateTransition = new TranslateTransition(Duration.seconds(5), blinkyAnimationIV);
            blinkyTranslateTransition.setFromX(-blinkyAnimationIV.getImage().getWidth());
            blinkyTranslateTransition.setToX(700);
            blinkyTranslateTransition.setCycleCount(blinkyTranslateTransition.INDEFINITE);
            parent.getChildren().add(blinkyAnimationIV);
            blinkyTranslateTransition.play();

            Image inkyAnimation = new Image("eyeGhost.jpg");
            ImageView inkyAnimationIV = new ImageView(inkyAnimation);
            inkyAnimationIV.setFitWidth(50);
            inkyAnimationIV.setFitHeight(50);
            inkyAnimationIV.setTranslateX(-300);
            inkyAnimationIV.setTranslateY(-90);
            TranslateTransition inkyTranslateTransition = new TranslateTransition(Duration.seconds(5), inkyAnimationIV);
            inkyTranslateTransition.setFromX(-inkyAnimationIV.getImage().getWidth());
            inkyTranslateTransition.setToX(800);
            inkyTranslateTransition.setCycleCount(inkyTranslateTransition.INDEFINITE);
            parent.getChildren().add(inkyAnimationIV);
            inkyTranslateTransition.play();

            Image clydeAnimation = new Image("eyeGhost.jpg");
            ImageView clydeAnimationIV = new ImageView(clydeAnimation);
            clydeAnimationIV.setFitWidth(50);
            clydeAnimationIV.setFitHeight(50);
            clydeAnimationIV.setTranslateX(-200);
            clydeAnimationIV.setTranslateY(-140);
            TranslateTransition clydeTranslateTransition = new TranslateTransition(Duration.seconds(5), clydeAnimationIV);
            clydeTranslateTransition.setFromX(-clydeAnimationIV.getImage().getWidth());
            clydeTranslateTransition.setToX(900);
            clydeTranslateTransition.setCycleCount(clydeTranslateTransition.INDEFINITE);
            parent.getChildren().add(clydeAnimationIV);
            clydeTranslateTransition.play();

            Image pinkyAnimation = new Image("eyeGhost.jpg");
            ImageView pinkyAnimationIV = new ImageView(pinkyAnimation);
            pinkyAnimationIV.setFitWidth(50);
            pinkyAnimationIV.setFitHeight(50);
            pinkyAnimationIV.setTranslateX(-100);
            pinkyAnimationIV.setTranslateY(-190);
            TranslateTransition pinkyTranslateTransition = new TranslateTransition(Duration.seconds(5), pinkyAnimationIV);
            pinkyTranslateTransition.setFromX(-pinkyAnimationIV.getImage().getWidth());
            pinkyTranslateTransition.setToX(1000);
            pinkyTranslateTransition.setCycleCount(pinkyTranslateTransition.INDEFINITE);
            parent.getChildren().add(pinkyAnimationIV);
            pinkyTranslateTransition.play();

        }



        App.menu.setScene(scene);
        App.menu.show();
        lancer = true;
    }


    @Override
    public void handle(Event event) {
        AskName();
    }
}
