package gui;

import config.MazeConfig;
import model.Direction;
import model.DirectionUtils;
import model.PacMan;
import geometry.RealCoordinates;
import geometry.IntCoordinates;
import javafx.scene.input.KeyEvent;
import misc.Debug ;

public class PacmanController {
    private MazeConfig mazeConfig;
    private static Direction lastDirection =  Direction.EAST ;
    private static Direction newDirection = null;



    public PacmanController(MazeConfig mazeConfig) {
        this.mazeConfig = mazeConfig;
    }

    public static Direction getNewDirection() {
        return newDirection;
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

            if ( !mazeConfig.getCell(nextCell).isWall() )
            {
                PacMan.INSTANCE.setDirection(newDirection);
                if(PacMan.INSTANCE.getPos().smallDiff(nextPos))
                {
                    PacMan.INSTANCE.setPos(nextPos);
                }
                else
                {
                    PacMan.INSTANCE.setPos(PacMan.INSTANCE.getPos().round().toRealCoordinates(1.0));
                }
            }
            else
            {
                lastDirection = newDirection;
                newDirection = null;
                //Debug.out(lastDirection.toString());

            }
        }
        else
        {
            newDirection = lastDirection;
        }

    }

    public static void checknWalk ( MazeConfig mazeConfig)
    {
        Debug.out(newDirection + ", " + lastDirection);
        if ( newDirection == null)
        {
            RealCoordinates nextPos = PacMan.INSTANCE.getPos().plus(DirectionUtils.getVector(lastDirection));
            nextPos = nextPos.round().toRealCoordinates(1.0);
            IntCoordinates nextCell = nextPos.round();

            Debug.out(mazeConfig.getCell(nextCell).isWall() + ", " + nextCell);
            if (!mazeConfig.getCell(nextCell).isWall())
            {
                PacMan.INSTANCE.setDirection(lastDirection);
                if(PacMan.INSTANCE.getPos().smallDiff(nextPos))
                {
                    PacMan.INSTANCE.setPos(nextPos);
                }
                else
                {
                    PacMan.INSTANCE.setPos(PacMan.INSTANCE.getPos().round().toRealCoordinates(1.0));
                }

                Debug.out("setDirectionlastDirection " + lastDirection);
            }
        }
    }

    public void keyReleasedHandler(KeyEvent event) {
        // Dans le gestionnaire de relâchement, vous pouvez laisser le code existant.
    }
}
