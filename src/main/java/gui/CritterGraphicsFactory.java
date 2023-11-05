package gui;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Critter;
import model.Ghost;
import model.PacMan;

public final class CritterGraphicsFactory {
    private final double scale;

    public CritterGraphicsFactory(double scale) {
        this.scale = scale;
    }

    public GraphicsUpdater makeGraphics(Critter critter) {
        var size = 0.7;
        var url = (critter instanceof PacMan) ? "pacman.png" :
                switch ((Ghost) critter) {
                    case BLINKY -> "ghost_blinky.png";
                    case CLYDE -> "ghost_clyde.png";
                    case INKY -> "ghost_inky.png";
                    case PINKY -> "ghost_pinky.png";
                    default -> throw new IllegalArgumentException("Unexpected value: " + (Ghost) critter);
                };
        System.out.println("URL de l'image : " + url); // pour afficher le message de debogage (une image ou qq chose)
        var image = new ImageView(new Image(url, scale * size, scale * size, true, true));
        return new GraphicsUpdater() {
            @Override
            public void update() {
                double translateX = (critter.getPos().x() + (1 - size) / 2) * scale;
                double translateY = (critter.getPos().y() + (1 - size) / 2) * scale;
                System.out.println("translateX : " + translateX + ", translateY: " + translateY); // on deboge la traduction
                image.setTranslateX(translateX);
                image.setTranslateY(translateY);
                System.out.println("Sprite mis à jour"); // on deboge la mise à jour du sprite
            }

            @Override
            public Node getNode() {
                return image;
            }
        };
    }
}

