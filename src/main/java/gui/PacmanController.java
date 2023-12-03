package gui;

import config.MazeConfig;
import model.Direction;
import model.DirectionUtils;
import model.PacMan;
import geometry.RealCoordinates;
import geometry.IntCoordinates;
import javafx.scene.input.KeyEvent;

public class PacmanController {
    private final MazeConfig mazeConfig;
    private static Direction lastDirection =  Direction.EAST ;
    private static Direction newDirection = null;



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
            nextPos = nextPos.round().toRealCoordinates(1.0);
            IntCoordinates nextCell = nextPos.round();

            if ( mazeConfig.getCell(nextCell).isPassable() )
            {
                PacMan.INSTANCE.setDirection(newDirection);
                lastDirection = null; // Reset pending direction when setting a new direction
            } else {
            	lastDirection = newDirection;
                newDirection = null;

            }
        }
        else
        {
            newDirection = lastDirection;
        }

    }

    public static void checknWalk ( MazeConfig mazeConfig)
    {
        if ( newDirection == null)
        {
            RealCoordinates nextPos = PacMan.INSTANCE.getPos().plus(DirectionUtils.getVector(lastDirection));
            nextPos = nextPos.round().toRealCoordinates(1.0);
            IntCoordinates nextCell = nextPos.round();


            if (mazeConfig.getCell(nextCell).isPassable())
            {
                PacMan.INSTANCE.setDirection(lastDirection);
                newDirection = lastDirection;
                if(PacMan.INSTANCE.getPos().smallDiff(nextPos))
                {
                    PacMan.INSTANCE.setPos(nextPos);
                }
                else
                {
                    PacMan.INSTANCE.setPos(PacMan.INSTANCE.getPos().round().toRealCoordinates(1.0));
                }
            }
        }
    }

    public void keyReleasedHandler(KeyEvent event) {
        // Dans le gestionnaire de relâchement, vous pouvez laisser le code existant.
    }
}
