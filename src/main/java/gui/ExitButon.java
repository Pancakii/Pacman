package gui;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ExitButon implements EventHandler<ActionEvent> {
    //permet de quitter Pacman
    @Override
    public void handle(ActionEvent arg0) {
        Platform.exit();
    }


}



