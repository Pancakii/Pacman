package gui;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ExitForGO implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent arg0) {
        Platform.exit();
    }
}


