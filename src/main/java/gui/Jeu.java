package gui;

import javafx.scene.input.KeyCode;
import config.MazeConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import model.MazeState;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Jeu implements EventHandler<ActionEvent> {
    private static Pane root = new Pane();
    private static MazeConfig mazeConfig;

    static {
        try {
            mazeConfig = MazeConfig.make();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PacmanController pacmanController = new PacmanController(mazeConfig);
    private static Scene gameScene = new Scene(root);
    private static MazeState maze = new MazeState(mazeConfig);
    private static boolean lancer = false;
    private static GameView gameView = new GameView(maze, root, 35.0, maze.getWidth() - 75, (maze.getHeight() * 18.0)/2);

    public static void Game() throws Exception {
        App.menu.setTitle("Pacman"); // Ajouter un nom a la page

        //Sert à détecter si le joueur appuye sur le bouton espace (= Pause du jeu)
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                gameView.togglePause();
            } else {
                pacmanController.keyPressedHandler(event);
            }
        });

        maze.resetGame();
        double gameWidth = maze.getWidth() * 35.0 ;
        double gameHeight = maze.getHeight() * 36.0 ;
        gameScene.setOnKeyReleased(pacmanController::keyReleasedHandler);
        App.menu.setWidth(gameWidth);
        App.menu.setHeight(gameHeight);
        App.menu.setScene(gameScene);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenSizeWidthidth = screenSize.getWidth();
        double screenSizeHeight = screenSize.getHeight();
        App.menu.setX((screenSizeWidthidth - gameWidth)/2);
        App.menu.setY((screenSizeHeight -gameHeight)/8);



        if (!lancer) {
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
