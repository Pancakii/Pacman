package gui.Windows;

import com.sun.javafx.collections.ElementObservableListDecorator;
import config.MazeConfig;
import gui.App;
import gui.PacmanController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import model.MazeState;

public class Jeu  implements EventHandler<ActionEvent> {

    private static Pane root = new Pane();
    private static PacmanController pacmanController;

    static {
        try {
            pacmanController = new PacmanController(MazeConfig.make());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ;
    private static Scene gameScene = new Scene(root);
    private static MazeState maze;

    static {
        try {
            maze = new MazeState(MazeConfig.make());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ;
    private ElementObservableListDecorator<Object> graphicsUpdaters;

    private static boolean lancer = false;

    private static GameView gameView = new GameView(maze, root, 35.0);;

    public static void Game() throws Exception {
        App.menu.setTitle("Pacman"); // Ajouter un nom a la page

        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        App.menu.setWidth(maze.getWidth() * 35.0);
        App.menu.setHeight(maze.getHeight() * 36.0);
        App.menu.setScene(gameScene);

        if(!lancer){
            gameView.animate();

        }

        App.menu.show();
        lancer = true;

    }

    @Override
    public void handle(ActionEvent event) {
        try {
            Game();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
