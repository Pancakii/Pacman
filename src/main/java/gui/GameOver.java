package model;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameOver  {

    public static void affichage(){
        Stage ecran = new Stage();
        Scene scene = new Scene(new Label("GameOver"));
        ecran.setHeight(500);
        ecran.setWidth(500);
        ecran.setScene(scene);
        ecran.show();

    }
}
