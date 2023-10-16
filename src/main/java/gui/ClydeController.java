package gui;
import java.util.Random;
import model.Direction;
import model.Ghost;

public class ClydeController {


    
    public void Movement() {
        Random deplacement = new Random();
            switch (deplacement.nextInt(5)) {
                        case 1 -> Ghost.CLYDE.setDirection(Direction.NORTH);
                        case 2 -> Ghost.CLYDE.setDirection(Direction.WEST);
                        case 3 -> Ghost.CLYDE.setDirection(Direction.SOUTH);
                        case 4 -> Ghost.CLYDE.setDirection(Direction.EAST);
                    }
            
        }
    
}
