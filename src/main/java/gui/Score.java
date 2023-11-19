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
        point.setX(600);
        point.setY(720); //La ou on place le score
        point.setFont(Font.font(18)); //La taille de l'affichage
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
