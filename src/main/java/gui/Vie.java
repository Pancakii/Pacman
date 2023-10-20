package gui;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Vie {

    public GraphicsUpdater remainingLife(Pane root){
        Image image = new Image("pacman.png");
        ImageView vie1 = new ImageView(image);
        ImageView vie2 = new ImageView(image);
        ImageView vie3 = new ImageView(image);

        vie1.setScaleX(0.7);
        vie1.setX(20);
        vie1.setY(20);

        return new GraphicsUpdater() {
            @Override
            public void update() {
                
            }

            @Override
            public Node getNode() {
                return null;
            }
        };
    }





}
