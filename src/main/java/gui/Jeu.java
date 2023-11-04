package gui;

import config.MazeConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.MazeState;

public class Jeu  implements EventHandler<ActionEvent> {

    public void Game(){
        Stage primaryStage = new Stage();
        var root = new Pane();
        var gameScene = new Scene(root);
        var pacmanController = new PacmanController();
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.makeExample1());
        var gameView = new GameView(maze, root, 100.0);

        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
        gameView.backGame(primaryStage);

    }

    @Override
    public void handle(ActionEvent event) {
        Game();
    }
}
