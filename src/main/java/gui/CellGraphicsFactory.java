package gui;


import geometry.IntCoordinates;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.MazeState;
import config.Cell;

public class CellGraphicsFactory {
    private final double scale;

    public CellGraphicsFactory(double scale) {
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos) {
        var group = new Group();
        group.setTranslateX(pos.x() * scale);
        group.setTranslateY(pos.y() * scale);
        var cell = state.getConfig().getCell(pos);
        var dot = new Circle();
        group.getChildren().add(dot);
        dot.setRadius(switch (cell.initialContent()) {
            case DOT -> scale / 15;
            case ENERGIZER -> scale / 5;
            case NOTHING -> 0;
            default -> throw new IllegalArgumentException("Unexpected value: " + cell.initialContent());
        });
        dot.setCenterX(scale / 2);
        dot.setCenterY(scale / 2);
        dot.setFill(Color.YELLOW);
        if (cell.isWall()) {    // J'ai remplacé les appels aux méthodes northWall(), eastWall(), southWall(), et westWall() par l'appel à la méthode isWall() de la classe Cell.
            var nWall = new Rectangle();
            nWall.setHeight(scale / 10);
            nWall.setWidth(scale);
            nWall.setY(0);
            nWall.setX(0);
            nWall.setFill(Color.BLUEVIOLET);
            group.getChildren().add(nWall);
        }
        return new GraphicsUpdater() {
            @Override
            public void update() {
                dot.setVisible(!state.getGridState(pos));
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}


