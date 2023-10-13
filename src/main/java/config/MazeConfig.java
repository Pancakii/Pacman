package config;

import geometry.IntCoordinates;
import java.io.* ;

import static config.Cell.Content.DOT;
import static config.Cell.*;
import static config.Cell.Content.NOTHING;

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

    public Cell getCell(IntCoordinates pos) {
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())];
    }
    // compte le nombre ligne dans le fichier Maze.txt
    public static int compteligne() throws Exception {

        File file = new File("Maze.txt");
        FileReader fr = new FileReader(file);
        BufferedReader r = new BufferedReader(fr);
        int ligne = 0;
        String lire ;
        while ((lire = r.readLine()) != null) {
            ligne++;
        }fr.close();
        return ligne;
    }

    // creation du tableau de tableau des cellules
    public static Cell[][] grid () throws Exception {
        String path =System.getProperty("user.dir") ;
        File file ;
        try {
            file =new File(path + "/src/main/java/resources/Maze.txt");
        } catch (Exception e ){
            e.printStackTrace();
            file =new File(path + "\\src\\main\\java\\resources\\Maze.txt");
        }

        FileReader fr = new FileReader(file);
        BufferedReader r = new BufferedReader(fr);
        String str ;
        Cell[][] maze = new Cell[compteligne()-1][r.readLine().length()] ;
        int j = 0 ;
        while ((str = r.readLine() )!= null ){
            for ( int i = 0 ; i< str.length();i++){
                if (str.charAt(i)=='0') maze[j][i]= Cellule(0) ;
                if (str.charAt(i)=='1') maze[j][i]= Cellule(1) ;
                if (str.charAt(i)=='2') maze[j][i]= Cellule(2) ;
                if (str.charAt(i)=='3') maze[j][i]= Cellule(3) ;
            }
            j++ ;
        }
        return maze;
    }

    // configuration du mawe
// placement de pacman et des ghost a fixer
    public static MazeConfig make() throws Exception {
        return new MazeConfig(grid(),
                new IntCoordinates(3, 0), // pacamn
                new IntCoordinates(0, 3), // blinky
                new IntCoordinates(3, 5), // pinke
                new IntCoordinates(5, 5), // inky
                new IntCoordinates(5, 1)  // clyde
        ) ;
    }

}
