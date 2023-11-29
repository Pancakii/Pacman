package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Direction;
import model.Ghost;
import model.PacMan;
import misc.Debug;


public final class CritterGraphicsFactory {
    private final double scale;
    private final double  size = 0.7;
    private final ImageView pacmanUp ;
    private final ImageView pacmanDown  ;
    private final ImageView pacmanRight ;
    private final ImageView pacmanLeft ;
    private final ImageView blinky ;
    private final ImageView clyde ;
    private final ImageView inky ;
    private final ImageView pinky ;
    private final ImageView ghostWhenPacmanEnergized ;
    private ImageView instance ;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
        this.pacmanUp = new ImageView( new Image("PacmanUp.gif",size*scale , size*scale , true ,true ) );
        this.pacmanDown = new ImageView( new Image("PacmanDown.gif",size*scale ,size*scale ,true,true ) ) ;
        this.pacmanRight = new ImageView( new Image("PacmanRight.gif",size*scale ,size*scale ,true,true ) ) ;
        this.pacmanLeft = new ImageView( new Image("PacmanLeft.gif",size*scale ,size*scale ,true,true ) ) ;
        this.blinky = new ImageView( new Image ("Blinky.gif",size*scale,size*scale,true,true));
        this.clyde = new ImageView( new Image ("Clyde.gif",size*scale,size*scale,true,true));
        this.inky = new ImageView( new Image ("Inky.gif",size*scale,size*scale,true,true));
        this.pinky =new ImageView( new Image ("Pinky.gif",size*scale,size*scale,true,true));
        this.ghostWhenPacmanEnergized = new ImageView( new Image("GhostEnergized.gif",size*scale , size*scale , true ,true ) );
        this.instance = pacmanRight ;
    }

    // change les images de pacman en fonction de la direction de pacman
    public ImageView updateImagePacman (){
         switch ( PacMan.INSTANCE.getDirection()) {
             case NORTH -> {
                 this.instance = pacmanUp;
                 return pacmanUp ;
             }
             case EAST -> {
                 this.instance = pacmanRight;
                 return pacmanRight ;
             }
             case WEST -> {
                 this.instance = pacmanLeft;
                 return pacmanLeft ;
             }
             case SOUTH -> {
                 this.instance = pacmanDown;
                 return pacmanDown ;
             }
         }
        return this.instance ;
    }

    public ImageView updateImageGhost(Ghost ghost){
        switch (ghost) {
            case BLINKY -> {
                if (!PacMan.INSTANCE.isEnergized()) {
                    return blinky;
                } else {
                    return ghostWhenPacmanEnergized;
                }
            }
            case CLYDE -> {
                if (!PacMan.INSTANCE.isEnergized()) {
                    return clyde;
                } else {
                    return ghostWhenPacmanEnergized;
                }
            }
            case INKY -> {
                if (!PacMan.INSTANCE.isEnergized()) {
                    return inky;
                } else {
                    return ghostWhenPacmanEnergized;
                }
            }
            case PINKY -> {
                if (!PacMan.INSTANCE.isEnergized()) {
                    return pinky;
                } else {
                    return ghostWhenPacmanEnergized;
                }
            }
        }
        return ghostWhenPacmanEnergized ;
    }


    public GraphicsUpdater makeGraphics(Critter critter) {
        var size = 0.7;
        var image = (critter instanceof PacMan) ? updateImagePacman() : updateImageGhost((Ghost) critter)
;
        return new GraphicsUpdater() {
            @Override
            public void update() {
                if ( critter instanceof PacMan) {
                    image.setImage(updateImagePacman().getImage());
                }
                if (critter instanceof Ghost){
                    image.setImage(updateImageGhost((Ghost) critter).getImage());
                }
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

/*
                switch ((Ghost) critter) {
                    case BLINKY -> blinky ;
                    case CLYDE -> clyde ;
                    case INKY -> inky ;
                    case PINKY -> pinky ;
                }
 */