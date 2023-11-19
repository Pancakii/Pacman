package gui;

import config.MazeConfig;
import model.Direction;
import model.DirectionUtils;
import model.PacMan;
import geometry.RealCoordinates;
import geometry.IntCoordinates;
import javafx.scene.input.KeyEvent;
import misc.Debug;

public class PacmanController {
    private MazeConfig mazeConfig;
    private static Direction newDirection = null;
    private static Direction lastDirection = null;

    public PacmanController(MazeConfig mazeConfig) {
        this.mazeConfig = mazeConfig;
    }

    public void keyPressedHandler(KeyEvent event) {
        newDirection = null;

        switch (event.getCode()) {
            case LEFT:
                newDirection = Direction.WEST;
                break;
            case RIGHT:
                newDirection = Direction.EAST;
                break;
            case UP:
                newDirection = Direction.NORTH;
                break;
            case DOWN:
                newDirection = Direction.SOUTH;
                break;
        }

        if (newDirection != null) {
            RealCoordinates nextPos = PacMan.INSTANCE.getPos().plus(DirectionUtils.getVector(newDirection));
            IntCoordinates nextCell = nextPos.round();

            if (!mazeConfig.getCell(nextCell).isWall()) {
                PacMan.INSTANCE.setDirection(newDirection);
                lastDirection = null; // Reset pending direction when setting a new direction
            } else {
            	lastDirection = newDirection;
                Debug.out(lastDirection.toString());
                newDirection = null;
            }
        }
    }

    public static void checknWalk(MazeConfig mazeConfig) {
        if (newDirection == null && lastDirection != null) {
            RealCoordinates nextPos = PacMan.INSTANCE.getPos().plus(DirectionUtils.getVector(lastDirection));
            IntCoordinates nextCell = nextPos.round();
            if (!mazeConfig.getCell(nextCell).isWall()) {
                PacMan.INSTANCE.setDirection(lastDirection);
                lastDirection = null;
            }
        }
    }

    public void keyReleasedHandler(KeyEvent event) {
        // Dans le gestionnaire de relâchement, vous pouvez laisser le code existant.
    }
}
