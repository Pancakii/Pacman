package gui;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MazeState;

public class Score {

    public GraphicsUpdater displayScore(Pane root) {

        Text point = new Text();

        point.setText("Score: " + MazeState.score);
        point.setX(50);
        point.setY(50); //La ou on place le score
        point.setFont(Font.font(10)); //La taille de l'affichage
        point.setFill(Color.YELLOW); //La couleur
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
