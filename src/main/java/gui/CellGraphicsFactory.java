package gui;


import geometry.IntCoordinates;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.MazeState;
import config.Cell;

public class CellGraphicsFactory {
    private final double scale;

    public CellGraphicsFactory(double scale) {
        this.scale = scale;
    }
	
    /**
     * Méthode qui crée le plateau de jeu (Graphique) de Pacman
     * @param state	Ensemble de donnée de la partie en cours 
     * @param pos	Position de la case
     * @return	Renvoie tout le plateau de jeu (Graphique)
     */
    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos) {
        var group = new Group();
        group.setTranslateX(pos.x() * scale);
        group.setTranslateY(pos.y() * scale);
        var cell = state.getConfig().getCell(pos);
        var dot = new Circle();
        
        if (cell != null) {
            dot.setRadius(switch (cell.initialContent()) {
                case DOT -> scale / 15;
                case ENERGIZER -> scale / 5;
                case NOTHING -> 0;
            });
        } else {
            dot.setRadius(0); 
        }
        dot.setCenterX(scale/2);
        dot.setCenterY(scale/2);
        dot.setFill(Color.YELLOW);
        group.getChildren().add(dot);
        
        if (cell != null) {
        	//Si la case est un Mur : crée un rectangle violet
        	if (cell.isWall() && !cell.isPassable()) {
        		var Wall = new Rectangle();
        		Wall.setHeight(scale);
        		Wall.setWidth(scale);
        		Wall.setY(0);
        		Wall.setX(0);
        		Wall.setFill(Color.BLUEVIOLET);
            	group.getChildren().add(Wall);
            	
            //Sinon si la case n'est pas un Mur et n'est pas passable : crée un rectangle bleu
        	}else if(!cell.isWall() && !cell.isPassable()) {
        		var neWall = new Rectangle();
        		neWall.setHeight(scale);
        		neWall.setWidth(scale);
        		neWall.setY(0);
        		neWall.setX(0);
        		neWall.setFill(Color.BLUE);
            	group.getChildren().add(neWall);
        	}
        }
        
        return new GraphicsUpdater() {
            @Override
            public void update() {
                dot.setVisible(!state.getGridState(pos));
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }
}


