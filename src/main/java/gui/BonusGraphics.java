package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ImageView instance ;


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
        this.instance = null ;
    }

    public ImageView updateImageBonus (){
        if ( PacMan.INSTANCE.getLevel()<=13) {
            switch (PacMan.INSTANCE.getLevel()) {
                case 1 -> {
                    this.instance = this.cerise;
                    return this.cerise;
                }
                case 2 -> {
                    this.instance = this.fraise;
                    return this.fraise;
                }
                case 3, 4 -> {
                    this.instance = this.orange;
                    return this.orange;
                }
                case 5, 6 -> {
                    this.instance = this.pomme;
                    return this.pomme;
                }
                case 7, 8 -> {
                    this.instance = this.melon;
                    return this.melon;
                }
                case 9, 10 -> {
                    this.instance = this.galaxian;
                    return this.galaxian;
                }
                case 11, 12 -> {
                    this.instance = this.cloche;
                    return this.cloche;
                }
                case 13 -> {
                    this.instance = this.cle;
                    return this.cle;
                }
            }
        }
        return this.instance ;
    }

    public ImageView getInstance (){
        return this.instance ;
    }

    public GraphicsUpdater afficheBonus (){
        var image = this.instance ;
        return new GraphicsUpdater() {
            @Override
            public void update() {
                image.setTranslateX((12+(1 - size) / 2) * scale) ;
                image.setTranslateY((12+(1 - size) / 2) * scale );
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }




}
