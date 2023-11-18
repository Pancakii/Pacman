package pathfinding;

import geometry.RealCoordinates;
import model.Critter;

import java.util.ArrayList;

public class Node
{
    private Node parent;
    private RealCoordinates coordinates;

    private final Node[] neighbors = new Node[4]; // 0: nord, 1: est, 2: sud, 3: ouest

    private double f = Double.MAX_VALUE;
    private double g = Double.MAX_VALUE;

    private double h;



    public Node(RealCoordinates c)
    {
        coordinates = c;
    }

    double calculateDistance(RealCoordinates p1, RealCoordinates p2)
    {
        double x = Math.abs(p1.x() - p2.x());
        double y = Math.abs(p1.y() - p2.y());
        return x + y;
    }





    void calculateTotal()
    {
        // F(Total cost function), total of move and toGo
        f = g + h;
    }

    void move()
    {
        // G(move) function, the cost already used

    }

    void toGo()
    {
        // Heuristic function, the cost to go

    }

    /*
    private static boolean same(Node a, Node b)
    {
        // Verifie si les deux noeuds ont la meme coordonnées
        return a.coordinates.x() == b.coordinates.x() && a.coordinates.y() == b.coordinates.y();
    }

    private ArrayList<Node> getNeighbors(Cell[][] tab, int di, int dj)
    {
        //NORD
        if(coordinates.round().x() >= 1) {
            if (!tab[coordinates.round().x() - 1][coordinates.round().y()].isWall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Pathfinding(coordinates.x() - 1, coordinates.y(), used + 1, di, dj);
            }
        }
        //EST
        if(coordinates.round().y() <= tab[0].length-1) {
            if (!tab[coordinates.round().x()][coordinates.round().y() + 1].isWall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Pathfinding(coordinates.x, coordinates.y + 1, used + 1, di, dj);
            }
        }
        //SUD
        if(coordinates.round().x() <= tab.length-1) {
            if (!tab[coordinates.round().x() + 1][coordinates.round().y()].isWall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Pathfinding(coordinates.x + 1, coordinates.y, used + 1, di, dj);
            }
        }
        //OUEST
        if(coordinates.round().y() >= 1) {
            if (!tab[coordinates.round().x()][coordinates.round().y() - 1].isWall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Pathfinding(coordinates.x, coordinates.y - 1, used + 1, di, dj);
            }
        }
    }

    public static Node aStar(RealCoordinates s, RealCoordinates e)
    {
        Node start = new Node(s);
        Node end = new Node(e);
        ArrayList<Node> closedList = new ArrayList<>();
        ArrayList<Node> openList = new ArrayList<>();

        start.calculateTotal();
        openList.add(start);
        while(!openList.isEmpty())
        {
            Node n = openList.get(-1);
            if(Node.same(n, end))
            {
                return n;
            }


        }

        return null;
    }
    */
}
