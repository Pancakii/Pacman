package config;

import javafx.scene.text.Text;
import geometry.RealCoordinates;
import geometry.IntCoordinates;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import static config.Cell.Cellule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import static config.Cell.Cellule;

public class MazeConfig {
    public MazeConfig(Cell[][] grid, IntCoordinates pacManPos, IntCoordinates blinkyPos, IntCoordinates pinkyPos,
                      IntCoordinates inkyPos, IntCoordinates clydePos) {
        this.grid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < getHeight(); i++) {
            if (getWidth() >= 0) System.arraycopy(grid[i], 0, this.grid[i], 0, getHeight());
        }
        this.pacManPos = pacManPos;
        this.blinkyPos = blinkyPos;
        this.inkyPos = inkyPos;
        this.pinkyPos = pinkyPos;
        this.clydePos = clydePos;
    }

    private final Cell[][] grid;
    private final IntCoordinates pacManPos, blinkyPos, pinkyPos, inkyPos, clydePos;

    public IntCoordinates getPacManPos() {
        return pacManPos;
    }

    public IntCoordinates getBlinkyPos() {
        return blinkyPos;
    }

    public IntCoordinates getPinkyPos() {
        return pinkyPos;
    }

    public IntCoordinates getInkyPos() {
        return inkyPos;
    }

    public IntCoordinates getClydePos() {
        return clydePos;
    }

    public int getWidth() {
        return grid[0].length;
    }

    public int getHeight() {
        return grid.length;
    }
	
	public Cell[][] getGrid()
	{
		return grid;
	}
	
    public boolean isWall(RealCoordinates position) {
    	IntCoordinates pos = position.round();
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())].isWall();
    }
    public boolean isWall(IntCoordinates pos) {
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())].isWall();
    }

    public boolean isIntersection(IntCoordinates pos)
    {
        boolean[] res = new boolean[4];
        //NORD
        if(pos.x() >= 1)
        {
            if (!grid[pos.x() - 1][pos.y()].isWall()) //si neighbor n'est pas un mur
            {
                res[0] = true;
            }
        }
        //EST
        if(pos.y() <= grid[0].length-2)
        {
            if (!grid[pos.x()][pos.y() + 1].isWall()) //si neighbor n'est pas un mur
            {
                res[1] = true;
            }
        }
        //SUD
        if(pos.x() <= grid.length-2)
        {
            if (!grid[pos.x() + 1][pos.y()].isWall()) //si neighbor n'est pas un mur
            {
                res[2] = true;
            }
        }
        //OUEST
        if(pos.y() >= 1)
        {
            if (!grid[pos.x()][pos.y() - 1].isWall()) //si neighbor n'est pas un mur
            {
                res[3] = true;
            }
        }

        return (res[2] || res[0]) && (res[1] || res[3]);
    }

    public ArrayList<IntCoordinates> waysPossible(IntCoordinates pos)
    {
        ArrayList<IntCoordinates> res = new ArrayList<>();
        // x = i, y = j
        //NORD
        if(pos.x() >= 1)
        {
            if (!grid[pos.x() - 1][pos.y()].isWall()) //si neighbor n'est pas un mur
            {
                res.add(new IntCoordinates(pos.x() - 1, pos.y()));
            }
        }
        //EST
        if(pos.y() <= grid[0].length-2)
        {
            if (!grid[pos.x()][pos.y() + 1].isWall()) //si neighbor n'est pas un mur
            {
                res.add(new IntCoordinates(pos.x(), pos.y() + 1));
            }
        }
        //SUD
        if(pos.x() <= grid.length-2)
        {
            if (!grid[pos.x() + 1][pos.y()].isWall()) //si neighbor n'est pas un mur
            {
                res.add(new IntCoordinates(pos.x() + 1, pos.y()));
            }
        }
        //OUEST
        if(pos.y() >= 1)
        {
            if (!grid[pos.x()][pos.y() - 1].isWall()) //si neighbor n'est pas un mur
            {
                res.add(new IntCoordinates(pos.x(), pos.y() - 1));
            }
        }

        return res;
    }

    public Cell getCell(IntCoordinates pos) {
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())];
    }
    
    // compte le nombre ligne dans le fichier Maze.txt
    public static int compteligne() throws Exception {
        String path =System.getProperty("user.dir") ;
        File file ;
        try {
            file =new File(path+"/src/main/resources/Maze.txt");
        } catch (Exception e ){
            e.printStackTrace();
            file =new File(path + "\\src\\main\\resources\\Maze.txt");
        }
        FileReader fr = new FileReader(file);
        BufferedReader r = new BufferedReader(fr);
        int ligne = 0;
        while ( r.readLine()!= null) {
            ligne++;
        }fr.close();
        return ligne;
    }

    //compte la longueur d'une ligne dans Maze.txt
    public static int comptelongueur() throws Exception{
        File file ;
        String path =System.getProperty("user.dir") ;
        try {
            file =new File(path+"/src/main/resources/Maze.txt");
        } catch (Exception e ){
            e.printStackTrace();
            file =new File(path +"\\src\\main\\resources\\Maze.txt");
        }
        FileReader fr = new FileReader(file);
        BufferedReader r = new BufferedReader(fr);
        fr.close();
        return  r.readLine().length() ;

    }

    // creation du tableau de tableau des cellules
    public static Cell[][] grid () throws Exception {
        String path = System.getProperty("user.dir") ;
        File file;
        try {
            file =new File(path+"/src/main/resources/Maze.txt");
        } catch (Exception e ){
            e.printStackTrace();
            file =new File(path+"\\src\\main\\resources\\Maze.txt");
        }

        FileReader fr = new FileReader(file);
        BufferedReader r = new BufferedReader(fr);
        String str;
        String firstLine = r.readLine(); 
        int maxCols = firstLine.length(); // Obtiens la longueur de la première ligne
        int numRows = compteligne(); // Utilise la fonction pour obtenir le nombre de lignes

        // Initialise le tableau `maze` avec les dimensions appropriées
        Cell[][] maze = new Cell[numRows][maxCols];

        int j = 0;

        // Utilise la première ligne pour initialiser le tableau, puis on lit le labyrinthe
        for (int i = 0; i < maxCols; i++) {
            char currentChar = firstLine.charAt(i);
            if (currentChar == '0') {
                maze[j][i] = Cellule(0);
            } else if (currentChar == '1') {
                maze[j][i] = Cellule(1);
            } else if (currentChar == '2') {
                maze[j][i] = Cellule(2);
            } else if (currentChar == '3') {
                maze[j][i] = Cellule(3);
            }
        }

        j++; // Avance à la prochaine ligne

        while ((str = r.readLine()) != null) {
            for (int i = 0; i < str.length(); i++) {
                char currentChar = str.charAt(i);
                if (currentChar == '0') {
                    maze[j][i] = Cellule(0);
                } else if (currentChar == '1') {
                    maze[j][i] = Cellule(1);
                } else if (currentChar == '2') {
                    maze[j][i] = Cellule(2);
                } else if (currentChar == '3') {
                    maze[j][i] = Cellule(3);
                }
            }
            j++;
        }
        
        return maze;
    }

    
    // configuration du maze
    // placement de pacman et des ghost a fixer
    public static MazeConfig make() throws Exception {
        return new MazeConfig(grid(),
        						//(x, y)
                new IntCoordinates(10, 15), // pacman
                new IntCoordinates(10, 8), // blinky
                new IntCoordinates(10, 9), // pinky
                new IntCoordinates(9, 9), // inky
                new IntCoordinates(11, 9)  // clyde
        ) ;
    }

}
