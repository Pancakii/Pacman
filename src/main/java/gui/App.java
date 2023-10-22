package gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import config.MazeConfig;
import model.MazeState;
import javafx.scene.image.Image;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var root = new Pane();
        var gameScene = new Scene(root);
        var pacmanController = new PacmanController(MazeConfig.make());
        gameScene.setOnKeyPressed(pacmanController::keyPressedHandler);
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        var maze = new MazeState(MazeConfig.make());
        var gameView = new GameView(maze, root, 35.0);
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
        gameView.backGame(primaryStage);
    }
}
