package gui;
import model.MazeState;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Vie {

    public GraphicsUpdater remainingLife(Pane root){

        //Initialissation des images de la vie
        Image imageNLF = new Image("CoeurVide.png");
        Image imageOneLF = new Image("Coeur_1.png");
        Image imageTwo = new Image("Coeur_2.png");
        Image imageThree = new Image("Coeur_3.png");


        ImageView vie = new ImageView(imageNLF);

        //Emplacement de l'affichage de la vie
        vie.setFitHeight(20);
        vie.setFitWidth(60);
        vie.setX(40);
        vie.setY(40);

        return new GraphicsUpdater() {
            @Override
            public void update() {

                    switch (MazeState.lives){
                        case 1 : vie.setImage(imageOneLF);break;
                        case 2 : vie.setImage(imageTwo);break;
                        case 3 : vie.setImage(imageThree);break;
                        default: vie.setImage(imageNLF);break;

                    }

            }

            @Override
            public Node getNode() {
                return vie;
            }
        };
    }





}
