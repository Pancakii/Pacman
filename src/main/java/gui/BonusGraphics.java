package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Bonus;
import model.PacMan;

public final class BonusGraphics {
    private final double scale;
    private final double  size = 0.7;
    private final ImageView fraise ;
    private final ImageView cerise ;
    private final ImageView orange ;
    private final ImageView melon ;
    private final ImageView pomme ;
    private final ImageView galaxian ;
    private final ImageView cloche ;
    private final ImageView cle ;
    private ImageView instance  ;

    private final double x  ;
    private final double y ;


    public BonusGraphics(double scale ){
        this.scale = scale;
        this.fraise = new ImageView(new Image("FruitFraise.png" , scale*size , scale*size , true , true )) ;
        this.cerise = new ImageView(new Image("FruitCerise.png" , scale*size , scale*size , true , true )) ;
        this.orange = new ImageView(new Image("FruitOrange.png" , scale*size , scale*size , true , true )) ;
        this.melon = new ImageView(new Image("FruitMelon.png" , scale*size , scale*size , true , true )) ;
        this.pomme = new ImageView(new Image("FruitPomme.png" , scale*size , scale*size , true , true )) ;
        this.galaxian = new ImageView(new Image("BonusGalaxian.png" , scale*size , scale*size , true , true )) ;
        this.cloche = new ImageView(new Image("BonusCloche.png" , scale*size , scale*size , true , true )) ;
        this.cle = new ImageView(new Image("BonusCle.png" , scale*size , scale*size , true , true )) ;
        this.instance = cerise ;
        this.x = (10 + (1 - size) / 2) * scale ;
        this.y = (11 + (1 - size) / 2) * scale ;

    }

    // retourne une image en fonction du niveau du jeu
    public ImageView updateImageBonus (){
            switch (PacMan.getLevel()) {
                case 1 -> this.instance = this.cerise;
                case 2 -> this.instance = this.fraise;
                case 3, 4 -> this.instance = this.orange;
                case 5, 6 -> this.instance = this.pomme;
                case 7, 8 -> this.instance = this.melon;
                case 9, 10 -> this.instance = this.galaxian;
                case 11, 12 -> this.instance = this.cloche;
                case 13 -> this.instance = this.cle;
            }
        return this.instance ;
    }


    public GraphicsUpdater afficheBonus (){
        ImageView imageView = new ImageView( updateImageBonus().getImage());
        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        return new GraphicsUpdater() {
            @Override
            public void update() {
                imageView.setImage(updateImageBonus().getImage());
                imageView.setVisible(Bonus.appartionFruit());
            }

            @Override
            public Node getNode() {
                return imageView;
            }
        };
    }
}
