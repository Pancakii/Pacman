package gui;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static model.MazeState.score;

public class Score {
    private Text point = new Text();

    public GraphicsUpdater displayScore(Pane root) {
        point.setText("Score: " + score);
        point.setX(50);
        point.setY(50); //La ou on place le score
        point.setFont(Font.font(20));
        point.setFill(Color.YELLOW);
        root.getChildren().add(point);
        return new GraphicsUpdater() {
            @Override
            public void update() {
                point.setText("Score: " + score);

            }

            @Override
            public Node getNode() {
                return point;
            }
        };
    }
}
