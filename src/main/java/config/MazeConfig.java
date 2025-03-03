package config;

import geometry.IntCoordinates;
import geometry.RealCoordinates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static config.Cell.Cellule;

public class MazeConfig {
    /**
     * Constructor of MazeConfig.
     * @param grid the cell table
     * @param pacManPos starter position of pacman
     * @param blinkyPos starter position of BLINKY
     * @param pinkyPos starter position of PINKY
     * @param inkyPos starter position of INKY
     * @param clydePos starter position of CLYDE
     */
    public MazeConfig(Cell[][] grid, IntCoordinates pacManPos, IntCoordinates blinkyPos, IntCoordinates pinkyPos,
                      IntCoordinates inkyPos, IntCoordinates clydePos) {
        this.grid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < getHeight(); i++) {
            if (getWidth() >= 0) System.arraycopy(grid[i], 0, this.grid[i], 0, getWidth());
        }
        this.pacManPos = pacManPos;
        this.blinkyPos = blinkyPos;
        this.inkyPos = inkyPos;
        this.pinkyPos = pinkyPos;
        this.clydePos = clydePos;
    }

    private final Cell[][] grid;
    private final IntCoordinates pacManPos, blinkyPos, pinkyPos, inkyPos, clydePos;

    /**
     * Gets pacManPos.
     * @return pacManPos
     */
    public IntCoordinates getPacManPos() {
        return pacManPos;
    }

    /**
     * Gets blinkyPos.
     * @return blinkyPos
     */
    public IntCoordinates getBlinkyPos() {
        return blinkyPos;
    }

    /**
     * Gets pinkyPos.
     * @return pinkyPos
     */
    public IntCoordinates getPinkyPos() {
        return pinkyPos;
    }

    /**
     * Gets inkyPos.
     * @return inkyPos
     */
    public IntCoordinates getInkyPos() {
        return inkyPos;
    }

    /**
     * Gets clydePos.
     * @return clydePos
     */
    public IntCoordinates getClydePos() {
        return clydePos;
    }

    /**
     * Gets the width of the cell table(grid[0].length).
     * @return the width of the cell table
     */
    public int getWidth() {
        return grid[0].length;
    }

    /**
     * Gets the height of the cell table(grid.length).
     * @return the height of the cell table
     */
    public int getHeight() {
        return grid.length;
    }

    /**
     * Gets grid.
     * @return grid
     */
    public Cell[][] getGrid()
    {
        return grid;
    }

    /**
     * Méthode qui vérifie si la prochaine case est "passable"
     * @param position	Position du critter
     * @return (true) prochaine case est "passable" (false) sinon non
     */
    public boolean isPassable(RealCoordinates position) {
        IntCoordinates pos = position.round();
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())].isPassable();
    }

    /**
     * Méthode qui vérifie si la prochaine case est un mur en RealCoordinates
     * @param position	Position du critter
     * @return (true) c'est un mur (false) ce n'est pas un mur
     */
    public boolean isWall(RealCoordinates position) {
        IntCoordinates pos = position.round();
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())].isWall();
    }

    /**
     * Méthode qui vérifie si la prochaine case est un mur en IntCoordinates
     * @param pos	Position du critter
     * @return (true) c'est un mur (false) ce n'est pas un mur
     */
    public boolean isWall(IntCoordinates pos) {
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())].isWall();
    }

    /**
     *  Regarde si la case est une intersection
     * @param pos
     * @return true si intersection
     */

    public boolean isIntersection(IntCoordinates pos)
    {
        boolean[] res = new boolean[4];
        //NORD
        if(pos.x() >= 1)
        {
            if (!grid[pos.x() - 2][pos.y()].isWall()) //si neighbor n'est pas un mur
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

    /**
     * Gets the cell using the given position
     * @param pos the said position
     * @return the cell
     */
    public Cell getCell(IntCoordinates pos) {
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())];
    }

    /**
     * Méthode qui compte le nombre de ligne du fichier Maze.txt
     * @return Retourne un entier qui correspond aux nombres de ligne du fichier Maze.txt
     */
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

    /**
     * Méthode qui crée un tableau de cellule
     * @return Retourne un tableau de cellule
     */
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
            } else if (currentChar == '4') {
                maze[j][i] = Cellule(4);
            }
        }

        for(int i = 1; i < numRows; i++)
        {
            str = r.readLine();
            for (j = 0; j < str.length(); j++)
            {
                char currentChar = str.charAt(j);
                if (currentChar == '0') {
                    maze[i][j] = Cellule(0);
                } else if (currentChar == '1') {
                    maze[i][j] = Cellule(1);
                } else if (currentChar == '2') {
                    maze[i][j] = Cellule(2);
                } else if (currentChar == '3') {
                    maze[i][j] = Cellule(3);
                } else if (currentChar == '4') {
                    maze[i][j] = Cellule(4);
                }
            }
        }

        return maze;
    }

    /**
     * @return initialisation d'un MazeConfig
     * @throws Exception
     */
    public static MazeConfig make() throws Exception {
        return new MazeConfig(grid(),
                //(x, y)
                new IntCoordinates(11, 16), // pacman
                new IntCoordinates(11, 9), // blinky
                new IntCoordinates(11, 10), // pinky
                new IntCoordinates(10, 10), // inky
                new IntCoordinates(12, 10)  // clyde
        ) ;
    }

}