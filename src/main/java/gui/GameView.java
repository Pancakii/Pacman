package gui;

import geometry.IntCoordinates;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import model.MazeState;
import java.util.ArrayList;
import java.util.List;

public class GameView {
    // class parameters
    public final MazeState maze;
    private final Pane gameRoot; // main node of the game
    private final List<GraphicsUpdater> graphicsUpdaters;

    public List<GraphicsUpdater> getGraphicsUpdaters() {
        return graphicsUpdaters;
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
        root.setStyle("-fx-background-color: #000000");
        var critterFactory = new CritterGraphicsFactory(scale);
        var cellFactory = new CellGraphicsFactory(scale);
        var BonusFactory = new BonusGraphics(scale) ;
        var affichageScore = new Score();
        var afficheVie = new Vie();
        graphicsUpdaters = new ArrayList<>();



            for (var critter : maze.getCritters()) addGraphics(critterFactory.makeGraphics(critter));

            for (int x = 0; x < maze.getWidth(); x++)
                for (int y = 0; y < maze.getHeight(); y++)
                    addGraphics(cellFactory.makeGraphics(maze, new IntCoordinates(x, y)));

            addGraphics(affichageScore.displayScore(root));
            addGraphics(afficheVie.remainingLife(root));
            addGraphics(BonusFactory.afficheBonus());


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
}
