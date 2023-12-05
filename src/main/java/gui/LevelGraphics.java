package gui;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MazeState;
import model.PacMan;

public class LevelGraphics {

    public GraphicsUpdater displayScore(Pane root) {

        Text point = new Text();

        point.setText("Level: " + PacMan.getLevel());
        point.setX(12 * 35);
        point.setY(23.2 * 35); //La ou on place le score
        point.setFont(Font.font(18)); //La taille de l'affichage
        point.setFill(Color.WHITE); //La couleur
        return new GraphicsUpdater() {
            @Override
            public void update() {
                point.setText("Level: " + PacMan.getLevel()); //Update le score
            }

            @Override
            public Node getNode() {
                return point;
            }
        };
    }
}
