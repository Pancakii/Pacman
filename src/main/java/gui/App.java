package gui;


import javafx.application.Application;
import javafx.stage.Stage;

import static gui.MenuDemarage.affichageMenuDemarage;


public class App extends Application {
    public static Stage menu; //la seule fenetre du jeu

    @Override
    public void start(Stage primaryStage) {
        affichageMenuDemarage(primaryStage);

    }


}
