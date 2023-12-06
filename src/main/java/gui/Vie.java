package gui;
import model.MazeState;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Vie {

    public GraphicsUpdater remainingLife(){

        //Initialissation des images de la vie
        Image imageNLF = new Image("CoeurVide.png");
        Image imageOneLF = new Image("Coeur_1.png");
        Image imageTwo = new Image("Coeur_2.png");
        Image imageThree = new Image("Coeur_3.png");


        ImageView vie = new ImageView(imageNLF);

        //Emplacement de l'affichage de la vie
        vie.setFitHeight(40);
        vie.setFitWidth(100);
        vie.setX(14.5 * 35);
        vie.setY(22.5 * 35);

        return new GraphicsUpdater() {
            @Override
            public void update() {
                vie.setImage(imageThree);
                switch (MazeState.lives){
                    case 1 : vie.setImage(imageOneLF);break;
                    case 2 : vie.setImage(imageTwo);break;
                    case 3 : vie.setImage(imageThree);break;
                }
            }

            @Override
            public Node getNode() {
                return vie;
            }
        };
    }





}
