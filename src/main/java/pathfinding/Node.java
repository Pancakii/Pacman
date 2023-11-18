package pathfinding;

import config.Cell;
import geometry.RealCoordinates;

import java.util.ArrayList;

public class Node
{
    private Node parent;
    private final RealCoordinates coordinates;

    private ArrayList<Node> neighbors = new ArrayList<>(); // 0: nord, 1: est, 2: sud, 3: ouest

    private double f = Double.MAX_VALUE; // total of move and toGo
    private double g = Double.MAX_VALUE; // the cost already used

    private double h; // the cost to go



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

    void calculateTotal(RealCoordinates end)
    {
        // F(Total cost function), total of move and toGo
        toGo(end);
        f = g + h;
    }

    void toGo(RealCoordinates end)
    {
        // Heuristic function, the cost to go
        h = calculateDistance(coordinates, end);
    }


    private static boolean same(Node a, Node b)
    {
        // Verifie si les deux noeuds ont la meme coordonnées
        return a.coordinates.x() == b.coordinates.x() && a.coordinates.y() == b.coordinates.y();
    }

    private void getNeighbors(Cell[][] tab)
    {
        neighbors = new ArrayList<>();
        // The function that adds neighbors.
        //NORD
        if(coordinates.round().x() >= 1) {
            if (!tab[coordinates.round().x() - 1][coordinates.round().y()].isWall()) //si neighbor n'est pas un mur
            {
                Node node = new Node(new RealCoordinates(coordinates.x() - 1, coordinates.y()));
                node.g = this.g + 1;
                node.parent = this;
                neighbors.add(node);
            }
        }
        //EST
        if(coordinates.round().y() <= tab[0].length-1) {
            if (!tab[coordinates.round().x()][coordinates.round().y() + 1].isWall()) //si neighbor n'est pas un mur
            {
                Node node = new Node(new RealCoordinates(coordinates.x(), coordinates.y() + 1));
                node.g = this.g + 1;
                node.parent = this;
                neighbors.add(node);
            }
        }
        //SUD
        if(coordinates.round().x() <= tab.length-1) {
            if (!tab[coordinates.round().x() + 1][coordinates.round().y()].isWall()) //si neighbor n'est pas un mur
            {
                Node node = new Node(new RealCoordinates(coordinates.x() + 1, coordinates.y()));
                node.g = this.g + 1;
                node.parent = this;
                neighbors.add(node);
            }
        }
        //OUEST
        if(coordinates.round().y() >= 1) {
            if (!tab[coordinates.round().x()][coordinates.round().y() - 1].isWall()) //si neighbor n'est pas un mur
            {
                Node node = new Node(new RealCoordinates(coordinates.x(), coordinates.y() - 1));
                node.g = this.g + 1;
                node.parent = this;
                neighbors.add(node);
            }
        }
    }

    public static Node findLowestCost(ArrayList<Node> list)
    {
        // Self-explanatory name
        // We are sure that the list is not empty
        Node res = list.get(0);
        for (Node n : list)
        {
            if(res.g > n.g)
            {
                res = n;
            }
        }
        return res;
    }

    public static void addListDistinctive(ArrayList<Node> list, Node to_add)
    {
        // Adds element to the list but swaps if coordinates are the same
        Node n = sameIn(list, to_add);
        if(n != null)
        {
            if (n.f > to_add.f)
            {
                list.remove(n);
                list.add(to_add);
                return;
            }
            else
            {
                return;
            }
        }
        list.add(to_add);
    }

    public static Node sameIn(ArrayList<Node> list, Node element)
    {
        // Returns it if a node with same coordinates exist. If not, returns null.
        for (Node n : list)
        {
            if(Node.same(n, element))
            {
                return n;
            }
        }
        return null;
    }

    public static boolean exists(ArrayList<Node> list, Node element)
    {
        for (Node n : list)
        {
            if(Node.same(n, element))
            {
                return true;
            }
        }
        return false;
    }



    public static Node aStar(RealCoordinates s, RealCoordinates e, Cell[][] grid)
    {
        // Setting the variables that will be used later
        Node start = new Node(s);
        Node end = new Node(e);
        ArrayList<Node> closedList = new ArrayList<>();
        ArrayList<Node> openList = new ArrayList<>();

        // Initializing for the loop
        start.calculateTotal(e);
        openList.add(start);


        while(!openList.isEmpty())
        {
            Node current = Node.findLowestCost(openList);
            if(Node.same(current, end))
            {
                return current;
            }

            current.getNeighbors(grid);
            current.calculateTotal(e);
            addListDistinctive(closedList, current);

            for(Node neighbor : current.neighbors)
            {
                boolean flag = false;
                for(Node c : closedList)
                {
                    if(Node.same(neighbor, c))
                    {
                        flag = true;
                    }
                }

                if(!flag)
                {
                    neighbor.getNeighbors(grid);
                    // if in open and new path shorter
                    Node checker = Node.sameIn(openList, neighbor);
                    if((checker != null && neighbor.f < checker.f))
                    {
                        checker.f = neighbor.f;
                        checker.parent = current;
                    }
                    if(!exists(openList, neighbor))
                    {
                        neighbor.calculateTotal(e);
                        openList.add(neighbor);
                    }
                }
            }
        }

        return null;
    }


    public static ArrayList<RealCoordinates> getPath(RealCoordinates s, RealCoordinates e, Cell[][] grid)
    {
        ArrayList<RealCoordinates> res = new ArrayList<>();
        Node node = aStar(s, e, grid);
        while(node != null)
        {
            res.add(0, node.coordinates);
            node = node.parent;
        }
        return res;
    }
}
