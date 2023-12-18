package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Ghost;
import model.PacMan;

public final class CritterGraphicsFactory {
    private final double scale;
    private final double size = 0.7;
    private final ImageView pacmanUp ;
    private final ImageView pacmanDown  ;
    private final ImageView pacmanRight ;
    private final ImageView pacmanLeft ;
    private final ImageView blinky ;
    private final ImageView clyde ;
    private final ImageView inky ;
    private final ImageView pinky ;
    private final ImageView ghostWhenPacmanEnergized ;
    private final ImageView ghostWhenPacmanEnergizedEnd ;
    private final ImageView eyeGhost ;
    private ImageView instance ;

    /**
     * Initialisation du scale, des images des critters ( avec leur taille )
     * @param scale
     */

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
        this.ghostWhenPacmanEnergizedEnd = new ImageView( new Image("GhostEnergizedEnd.gif",size*scale , size*scale , true ,true ) );
        this.eyeGhost = new ImageView(new Image("eyeGhost.jpg",scale*size , scale*size , true , true ));
        this.instance = pacmanRight ;
    }


    /**
     * Retourne et change les images de pacman en fonction de la direction de pacman
     * @return instance
     */
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

    /**
     *  les ghosts reste bleu entre 10 et 3 sec puis scintille entre 3 et 0 sec
     *  les ghosts se transforme en yeux quand il est mangées
     * @param ghost
     * @return ImageView  (change les images des fatomes en fonction de pacman s'il est energized ou pas et si il est mangées ou pas)
     */
    public ImageView updateImageGhost(Ghost ghost){
        boolean energized = PacMan.INSTANCE.isEnergized();
        double energizedTimer = PacMan.INSTANCE.getEnergized_timer();
        switch (ghost) {
            case BLINKY -> {
                if(ghost.frightened)
                {
                    if (energized && energizedTimer > 3)
                    {
                        return ghostWhenPacmanEnergized;
                    }
                    else if (energized && energizedTimer > 0)
                    {
                        return ghostWhenPacmanEnergizedEnd;
                    }
                }
                else if (ghost.eaten){
                    return eyeGhost ;
                }
                else
                {
                    return blinky;
                }
            }
            case CLYDE -> {
                if(ghost.frightened)
                {
                    if (energized && energizedTimer > 3)
                    {
                        return ghostWhenPacmanEnergized;
                    }
                    else if (energized && energizedTimer > 0)
                    {
                        return ghostWhenPacmanEnergizedEnd;
                    }
                }
                else if (ghost.eaten){
                    return eyeGhost ;
                }
                else
                {
                    return clyde;
                }
            }
            case INKY -> {
                if(ghost.frightened)
                {
                    if (energized && energizedTimer > 3)
                    {
                        return ghostWhenPacmanEnergized;
                    }
                    else if (energized && energizedTimer > 0)
                    {
                        return ghostWhenPacmanEnergizedEnd;
                    }
                }
                else if (ghost.eaten){
                    return eyeGhost ;
                }
                else
                {
                    return inky;
                }
            }
            case PINKY -> {
                if(ghost.frightened)
                {
                    if (energized && energizedTimer > 3)
                    {
                        return ghostWhenPacmanEnergized;
                    }
                    else if (energized && energizedTimer > 0)
                    {
                        return ghostWhenPacmanEnergizedEnd;
                    }
                }
                else if (ghost.eaten){
                    return eyeGhost ;
                }
                else
                {
                    return pinky;
                }
            }
        }
        return ghostWhenPacmanEnergized;
    }

    /**
     * Affiche le graphisme du criiter en temps réel
     * @param critter
     * @return imageView
     */
    public GraphicsUpdater makeGraphics(Critter critter) {
        var size = 0.7;
        var image = (critter instanceof PacMan) ? updateImagePacman() : updateImageGhost((Ghost) critter);
        ImageView imageView = new ImageView(image.getImage());
        return new GraphicsUpdater() {
            @Override
            public void update() {
                if (critter instanceof PacMan) {
                    imageView.setImage(updateImagePacman().getImage());
                } else {
                    imageView.setImage(updateImageGhost((Ghost) critter).getImage());
                }
                imageView.setTranslateX((critter.getPos().x() + (1 - size) / 2) * scale);
                imageView.setTranslateY((critter.getPos().y() + (1 - size) / 2) * scale);

            }

            @Override
            public Node getNode() {
                return imageView;
            }
        };
    }

}