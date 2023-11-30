package gui;


import javafx.application.Application;
import javafx.stage.Stage;

import static gui.MenuDemarage.affichageMenuDemarage;


public class App extends Application {
    public static Stage menu = new Stage(); //la seule fenetre du jeu

    @Override
    public void start(Stage primaryStage) {
        menu.setResizable(false); // Ne plus pouvoir aggrandir/retrecir la page
        menu.setFullScreenExitHint ("Press esc for exit");

        affichageMenuDemarage();

    }


}
