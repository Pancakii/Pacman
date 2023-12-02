package gui.Windows;

import gui.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ScoreBoard implements EventHandler<ActionEvent> {

    private static VBox parent = new VBox();
    private static Scene scene = new Scene(parent);
    private static boolean lancer = false;

    public ScoreBoard(){
        App.menu.setHeight(700);
        App.menu.setWidth(700);
        parent.setStyle(String.valueOf(Color.BLACK));
        // Ajouter un nom a la page
        App.menu.setTitle("ScoreBoard");

    }

    @Override
    public void handle(ActionEvent event) {

    }
}
