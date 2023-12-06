package gui;


import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static gui.Windows.MenuDemarage.affichageMenuDemarage;


public class App extends Application {
    public static Stage menu = new Stage(); //la seule fenetre du jeu


    @Override
    public void start(final Stage primaryStage) {
        menu.setResizable(false); // Ne plus pouvoir aggrandir/retrecir la page
        // Ajouter une icone a la page`
        Image icon = new Image("pacman.png");
        menu.getIcons().add(icon);
        //GameOver.affichageGameOver();
            affichageMenuDemarage();

    }
}
