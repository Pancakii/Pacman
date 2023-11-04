package gui;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import static gui.Menu.affichageMenu;


public class App extends Application implements EventHandler<ActionEvent> {
    @Override
    public void start(Stage primaryStage) {
        affichageMenu();

    }

    @Override
    public void handle(ActionEvent event) {

    }
}
