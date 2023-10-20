package gui;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MazeState;

import static model.MazeState.score;

public class Score {




    public GraphicsUpdater displayScore(Pane root) {

        Text point = new Text();

        point.setText("Score: " + MazeState.score);
        point.setX(50);
        point.setY(50); //La ou on place le score
        point.setFont(Font.font(20));
        point.setFill(Color.YELLOW);
        return new GraphicsUpdater() {
            @Override
            public void update() {

                point.setText("Score: " + MazeState.score);


            }

            @Override
            public Node getNode() {
                return point;
            }
        };
    }
}
