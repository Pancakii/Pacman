package gui;
import model.MazeState;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Vie {

    public GraphicsUpdater remainingLife(Pane root){
        Image image = new Image("NoLF");

        ImageView vie = new ImageView(image);


        vie.setFitHeight(10);
        vie.setFitWidth(10);
        vie.setX(30);
        vie.setY(20);

        return new GraphicsUpdater() {
            @Override
            public void update() {

                switch (MazeState.lives){
                    case 1 : vie.setImage(image);break;
                    case 2 : vie.setImage(image);break;
                    case 3 : vie.setImage(image);break;
                    default: vie.setImage(image);break;


                }
            }

            @Override
            public Node getNode() {
                return vie;
            }
        };
    }





}
