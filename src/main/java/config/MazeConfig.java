package config;

import geometry.IntCoordinates;
import java.io.* ;
import static config.Cell.*;

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

    public boolean isWall(IntCoordinates pos) {
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())].isWall();
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
        File file ;
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
            j++ ;
        }
        return maze;
    }
    // configuration du maze
    // placement de pacman et des ghost a fixer
    public static MazeConfig make() throws Exception {
        return new MazeConfig(grid(),
        						//(x, y)
                new IntCoordinates(2, 1), // pacman
                new IntCoordinates(9, 9), // blinky
                new IntCoordinates(10, 9), // pinke
                new IntCoordinates(11, 9), // inky
                new IntCoordinates(10, 10)  // clyde
        ) ;
    }

}
