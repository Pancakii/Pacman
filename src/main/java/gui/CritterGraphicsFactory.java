package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Ghost;
import model.PacMan;


public final class CritterGraphicsFactory {
    private final double scale = 35.0;
    private final double  size = 0.7;
    private final ImageView pacmanUp = new ImageView( new Image("PacmanUp.gif",size*scale , size*scale , true ,true ) );
    ;
    private final ImageView pacmanDown = new ImageView( new Image("PacmanDown.gif",size*scale ,size*scale ,true,true ) ) ;
    ;
    private final ImageView pacmanRight = new ImageView( new Image("PacmanRight.gif",size*scale ,size*scale ,true,true ) ) ;
    ;
    private final ImageView pacmanLeft = new ImageView( new Image("PacmanLeft.gif",size*scale ,size*scale ,true,true ) ) ;
    ;
    private final ImageView blinky ;
    private final ImageView clyde ;
    private final ImageView inky ;
    private final ImageView pinky ;
    private final ImageView ghostWhenPacmanEnergized ;
    private ImageView affiche = new ImageView();

    public CritterGraphicsFactory() {
        this.blinky = new ImageView( new Image ("Blinky.gif",size*scale,size*scale,true,true));
        this.clyde = new ImageView( new Image ("Clyde.gif",size*scale,size*scale,true,true));
        this.inky = new ImageView( new Image ("Inky.gif",size*scale,size*scale,true,true));
        this.pinky =new ImageView( new Image ("Pinky.gif",size*scale,size*scale,true,true));
        this.ghostWhenPacmanEnergized = new ImageView( new Image("GhostEnergized.gif",size*scale , size*scale , true ,true ) );
    }

    // change les images de pacman en fonction de la direction de pacman
    public ImageView updateImagePacman (){
         switch ( PacMan.INSTANCE.getDirection()) {
             case NORTH -> {
                 this.affiche = pacmanUp;
                 return affiche ;
             }
             case EAST -> {
                 this.affiche = pacmanRight;
                 return affiche ;
             }
             case WEST -> {
                 this.affiche = pacmanLeft;
                 return affiche ;
             }
             case SOUTH -> {
                 this.affiche = pacmanDown;
                 return affiche ;
             }
         }
        return pacmanRight ;
    }

    public ImageView updateImageGhost(Ghost ghost){
        switch (ghost) {
            case BLINKY -> {
                if (PacMan.INSTANCE.getEnergized_timer() != 0) {
                    return ghostWhenPacmanEnergized;
                } else {
                    return blinky;
                }
            }
            case CLYDE -> {
                if (PacMan.INSTANCE.getEnergized_timer() != 0) {
                    return ghostWhenPacmanEnergized;
                } else {
                    return clyde;
                }
            }
            case INKY -> {
                if (PacMan.INSTANCE.getEnergized_timer() != 0) {
                    return ghostWhenPacmanEnergized;
                } else {
                    return inky ;
                }
            }
            case PINKY -> {
                if (PacMan.INSTANCE.getEnergized_timer() != 0) {
                    return ghostWhenPacmanEnergized;
                } else {
                    return pinky;
                }
            }
        }
        return ghostWhenPacmanEnergized ;
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
                if ( critter instanceof PacMan) {
                    image.setImage(updateImagePacman().getImage());
                } else {
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