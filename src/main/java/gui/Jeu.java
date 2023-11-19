package gui;

import com.sun.javafx.collections.ElementObservableListDecorator;
import config.MazeConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import model.MazeState;

public class Jeu  implements EventHandler<ActionEvent> {

    private static GameView gameView;
    private static Pane root;
    private static PacmanController pacmanController;
    private static Scene gameScene;
    private static MazeState maze;
    private ElementObservableListDecorator<Object> graphicsUpdaters;

    public static GameView getGameView() {
        return gameView;
    }

    public static void Game() throws Exception {
        root = new Pane();
        gameScene = new Scene(root);
        pacmanController = new PacmanController(MazeConfig.make());
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        maze = new MazeState(MazeConfig.make());
        gameView = new GameView(maze, root, 35.0);
        App.menu.setWidth(maze.getWidth() * 35.0);
        App.menu.setHeight(maze.getHeight() * 36.0);
        App.menu.setScene(gameScene);
        App.menu.show();
        gameView.animate();
        gameView.backGame(App.menu);

    }
    public static  void resetJeu(){
        MazeState.lives = 3;
        MazeState.score = 0;

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
