package gui;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MazeState;

public class Score {

    public GraphicsUpdater displayScore(final Pane root) {

        Text point = new Text();
        final int x = 600;
        final int y = 720;
        final int f = 18;

        point.setText("Score: " + MazeState.score);
        point.setX(x);
        point.setY(y); //La ou on place le score
        point.setFont(Font.font(f)); //La taille de l'affichage
        point.setFill(Color.WHITE); //La couleur
        return new GraphicsUpdater() {
            @Override
            public void update() {

                point.setText("Score: " + MazeState.score); //Update le score


            }

            @Override
            public Node getNode() {
                return point;
            }
        };
    }
}
