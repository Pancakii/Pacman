package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Direction;
import model.Ghost;
import model.PacMan;


public final class CritterGraphicsFactory {
    private final double scale;
    // FIXME: 20/10/2023  a changer en fonction de la taille du labyrinthe 
    private final double  size = 0.7;
    private final ImageView pacmanUp ;
    private final ImageView pacmanDown  ;
    private final ImageView pacmanRight ;
    private final ImageView pacmanLeft ;
    private final ImageView blinky ;
    private final ImageView clyde ;
    private final ImageView inky ;
    private final ImageView pinky ;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
        this.pacmanUp = new ImageView( new Image("PacmanUp.png",size*scale , size*scale , true ,true ) );
        this.pacmanDown = new ImageView( new Image("PacmanDown.png",size*scale ,size*scale ,true,true ) ) ;
        this.pacmanRight = new ImageView( new Image("PacmanRight.png",size*scale ,size*scale ,true,true ) ) ;
        this.pacmanLeft = new ImageView( new Image("PacmanLeft.png",size*scale ,size*scale ,true,true ) ) ;
        this.blinky = new ImageView( new Image ("ghost_blinky.png",size*scale,size*scale,true,true));
        this.clyde = new ImageView( new Image ("ghost_clyde.png",size*scale,size*scale,true,true));
        this.inky = new ImageView( new Image ("ghost_inky.png",size*scale,size*scale,true,true));
        this.pinky =new ImageView( new Image ("ghost_pinky.png",size*scale,size*scale,true,true));
    }

    // change les images de pacman en fonction des direction de pacman
    public ImageView updateImagePacman (){
        if ( PacMan.INSTANCE.getDirection()== Direction.NORTH ){
            return pacmanUp ;
        } else if (PacMan.INSTANCE.getDirection()== Direction.SOUTH) {
             return pacmanDown ;
        } else if (PacMan.INSTANCE.getDirection()== Direction.WEST){
             return pacmanLeft ;
        } else {
             return pacmanRight ;
        }
    }


    public GraphicsUpdater makeGraphics(Critter critter) {
        var size = 0.7;
        var image = (critter instanceof PacMan) ? updateImagePacman() :
                switch ((Ghost) critter) {
                    case BLINKY -> blinky ;
                    case CLYDE -> clyde ;
                    case INKY -> inky ;
                    case PINKY -> pinky ;
                };
        return new GraphicsUpdater() {
            @Override
            public void update() {
                image.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                image.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);
                // Debug.out("sprite updated");
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }
}
