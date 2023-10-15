package gui;

import config.MazeConfig;
import javafx.scene.input.KeyCode;
import model.Direction;
import model.PacMan;

import javafx.scene.input.KeyEvent;

public class PacmanController {
    MazeConfig m ;
    PacmanController (MazeConfig m ){
        this.m = m ;
    }

    // le but est de le faire deplacer sans qu'il s'arrete face un mur quand
// le joueur appuye sur les touches et de le faire tourner a la bonne direction
// quand le joueur a appuyer sur la bonne touche
    public void keyPressedHandler(KeyEvent event) {
        // regarde s'il la touche appuye est la fleche gauche
        if (event.getCode()== KeyCode.LEFT ){
            // prend la position d'à côté pour voir s'il y a un mur
            for (var n : PacMan.INSTANCE.getPos().intNeighbourWest()) {
                if (!(m.getCell(n).isWall())) {
                    PacMan.INSTANCE.setDirection(Direction.WEST);
                }
            }
            // regarde s'il la touche appuye est la fleche droite
        } else if (event.getCode()== KeyCode.RIGHT ){
            // prend la position d'à côté pour voir s'il y a un mur
            for (var n : PacMan.INSTANCE.getPos().intNeighbourEast() ) {
                if (!(m.getCell(n).isWall())) {
                    PacMan.INSTANCE.setDirection(Direction.EAST);
                }
            }
            // regarde s'il la touche appuye est la fleche haute
        } else if (event.getCode()== KeyCode.UP ){
            // prend la position d'à côté pour voir s'il y a un mur
            for (var n : PacMan.INSTANCE.getPos().intNeighbourNorth()) {
                if (!(m.getCell(n).isWall())) {
                    PacMan.INSTANCE.setDirection(Direction.NORTH);
                }
            }
            // regarde s'il la touche appuye est la fleche basse
        } else if (event.getCode()== KeyCode.DOWN ){
            // prend la position d'à côté pour voir s'il y a un mur
            for (var n :PacMan.INSTANCE.getPos().intNeighbourSouth() ) {
                if (!(m.getCell(n).isWall())) {
                    PacMan.INSTANCE.setDirection(Direction.SOUTH);
                }
            }
        }
        else {
            PacMan.INSTANCE.getDirection(); // do nothing
        }
    }

/// le but est de le faire deplacer automatiquement
// dans la direction de la derniere touche lacher
// alors le but est de chercher si a cette instance
// il y a un mur sinon il tourne
    public void keyReleasedHandler(KeyEvent event) {
        // regarde s'il la derniere touche appuye est la fleche gauche
        if (event.getCode()== KeyCode.LEFT ){
            // prend la position d'à côté pour voir s'il y a un mur
            for (var n : PacMan.INSTANCE.getPos().intNeighbourWest()) {
                if (!(m.getCell(n).isWall())) {
                    PacMan.INSTANCE.setDirection(Direction.WEST);
                }
            }
            // regarde s'il la derniere touche appuye est la fleche droite
        } else if (event.getCode()== KeyCode.RIGHT ){
            // prend la position d'à côté pour voir s'il y a un mur
            for (var n : PacMan.INSTANCE.getPos().intNeighbourEast() ) {
                if (!(m.getCell(n).isWall())) {
                    PacMan.INSTANCE.setDirection(Direction.EAST);
                }
            }
            // regarde s'il la derniere touche appuye est la fleche haute
        } else if (event.getCode()== KeyCode.UP ){
            // prend la position d'à côté pour voir s'il y a un mur
            for (var n : PacMan.INSTANCE.getPos().intNeighbourNorth()) {
                if (!(m.getCell(n).isWall())) {
                    PacMan.INSTANCE.setDirection(Direction.NORTH);
                }
            }
            // regarde s'il la derniere touche appuye est la fleche basse
        } else if (event.getCode()== KeyCode.DOWN ){
            // prend la position d'à côté pour voir s'il y a un mur
            for (var n :PacMan.INSTANCE.getPos().intNeighbourSouth() ) {
                if (!(m.getCell(n).isWall())) {
                    PacMan.INSTANCE.setDirection(Direction.SOUTH);
                }
            }
        } else {
            PacMan.INSTANCE.getDirection(); // do nothing
        }
    }
}
