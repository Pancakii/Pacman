package gui;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MazeState;

public class Score {

    /**
     * Une fonction qui met à jour le score du joueur
     * @return
     */
    public GraphicsUpdater displayScore() {

        Text point = new Text();
        final int x = 600;
        final int y = 720;
        final int f = 18;

        point.setText("Score: " + MazeState.score);
        point.setX(18 * 35);
        point.setY(23.2 * 35); //La ou on place le score
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
