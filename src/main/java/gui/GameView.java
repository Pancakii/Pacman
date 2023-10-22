package gui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import geometry.IntCoordinates;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.MazeState;
import java.util.ArrayList;
import java.util.List;

public class GameView {
    // class parameters
    private final MazeState maze;
    private final Pane gameRoot; // main node of the game
    private Text scoreText;
    private final List<GraphicsUpdater> graphicsUpdaters;


    public void backGame(Stage stage){
        stage.setTitle("Pacman2.0"); // Ajouter un nom a la page
        Image icon = new Image("pacman.png");
        stage.getIcons().add(icon); // Ajouter une icone a la page
        stage.setResizable(false); // Ne plus pouvoir aggrandir/retrecir la page
        stage.setFullScreen(true); // Mettre en plein ecran
        stage.setFullScreenExitHint ("Press esc for exit");

    }
    private void addGraphics(GraphicsUpdater updater) {
        gameRoot.getChildren().add(updater.getNode());
        graphicsUpdaters.add(updater);

    }

    /**
     * @param maze  le "modèle" de cette vue (le labyrinthe et tout ce qui s'y trouve)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le nombre de pixels représentant une unité du labyrinthe
     */
    public GameView(MazeState maze, Pane root, double scale) {
        this.maze = maze;
        this.gameRoot = root;
        // pixels per cell
        root.setMinWidth(maze.getWidth() * scale - 35);
        root.setMinHeight(maze.getHeight() * scale - 35);
        root.setStyle("-fx-background-color: #FFFFFF");
        
        var critterFactory = new CritterGraphicsFactory(scale);
        var cellFactory = new CellGraphicsFactory(scale);
        graphicsUpdaters = new ArrayList<>();
        
        scoreText = new Text("Score: 0");
        scoreText.setX(50);
        scoreText.setY(50);
        scoreText.setFont(Font.font(20));
        scoreText.setFill(Color.YELLOW);
        gameRoot.getChildren().add(scoreText);
        maze.initializeScoreText(scoreText);     
        for (var critter : maze.getCritters()) addGraphics(critterFactory.makeGraphics(critter));
        for (int x = 0; x < maze.getWidth(); x++)
            for (int y = 0; y < maze.getHeight(); y++)
                addGraphics(cellFactory.makeGraphics(maze, new IntCoordinates(x, y)));
    }

    public void animate() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                var deltaT = now - last;
                maze.update(deltaT);
                for (var updater : graphicsUpdaters) {
                    updater.update();
                }
                last = now;
            }
        }.start();
    }
    
    public void updateScore(int score) {
        scoreText.setText("Score: " + score);
    }
}
