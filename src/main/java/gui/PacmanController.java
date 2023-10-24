package gui;

import model.Direction;
import model.PacMan;

import javafx.scene.input.KeyEvent;

public class PacmanController {
    public void keyPressedHandler(KeyEvent event) {
        PacMan.INSTANCE.setDirection(
                switch (event.getCode()) {
                    case LEFT -> Direction.WEST;
                    case RIGHT -> Direction.EAST;
                    case UP -> Direction.NORTH;
                    case DOWN -> Direction.SOUTH;
                    default -> PacMan.INSTANCE.getDirection(); // do nothing
                }
        );
    }
    public void keyReleasedHandler(KeyEvent event) {
        // Nothing to do?
    }
}
