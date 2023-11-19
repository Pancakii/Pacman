package pathfinding;

import config.Cell;
import geometry.RealCoordinates;
import misc.Debug;

import java.util.ArrayList;

public class Node
{
    private Node parent;
    private final RealCoordinates coordinates;

    private ArrayList<Node> neighbors = new ArrayList<>(); // 0: nord, 1: est, 2: sud, 3: ouest

    private double f = Double.MAX_VALUE; // total of move and toGo
    private double g; // the cost already used

    private double h; // the cost to go



    public Node(RealCoordinates c, Node p)
    {
        coordinates = c;
        parent = p;
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
                Node node = new Node(new RealCoordinates(coordinates.x() - 1, coordinates.y()), this);
                node.g = this.g + 1;
                if(!Node.same(this, node))
                {
                    neighbors.add(node);
                }
            }
        }
        //EST
        if(coordinates.round().y() <= tab[0].length-2) {
            if (!tab[coordinates.round().x()][coordinates.round().y() + 1].isWall()) //si neighbor n'est pas un mur
            {
                Node node = new Node(new RealCoordinates(coordinates.x(), coordinates.y() + 1), this);
                node.g = this.g + 1;
                if(!Node.same(this, node))
                {
                    neighbors.add(node);
                }
            }
        }
        //SUD
        if(coordinates.round().x() <= tab.length-2) {
            if (!tab[coordinates.round().x() + 1][coordinates.round().y()].isWall()) //si neighbor n'est pas un mur
            {
                Node node = new Node(new RealCoordinates(coordinates.x() + 1, coordinates.y()), this);
                node.g = this.g + 1;
                if(!Node.same(this, node))
                {
                    neighbors.add(node);
                }
            }
        }
        //OUEST
        if(coordinates.round().y() >= 1) {
            if (!tab[coordinates.round().x()][coordinates.round().y() - 1].isWall()) //si neighbor n'est pas un mur
            {
                Node node = new Node(new RealCoordinates(coordinates.x(), coordinates.y() - 1), this);
                node.g = this.g + 1;
                if(!Node.same(this, node))
                {
                    neighbors.add(node);
                }
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


    public static void printArray(ArrayList<Node> a)
    {
        //System.out.println();
        //System.out.print("{");
        for (Node n : a)
        {
            //System.out.print(" (" + n.coordinates.x() + ", " + n.coordinates.y() + ") ");
        }
        //System.out.println("}");
    }

    public static void printArrayRC(ArrayList<RealCoordinates> a)
    {
        System.out.println();
        System.out.print("{");
        for (RealCoordinates n : a)
        {
            System.out.print(" (" + n.x() + ", " + n.y() + ") ");
        }
        System.out.println("}");
    }

    public static void printNodeAndNeighbors(Node n)
    {
        //System.out.println("-------------------------------------");
        //System.out.print("Node  (" + n.coordinates.x() + ", " + n.coordinates.y() + "), f = " + n.f);
        printArray(n.neighbors);
        //System.out.println("-------------------------------------");
    }



    public static Node aStar(RealCoordinates s, RealCoordinates e, Cell[][] grid)
    {
        e = e.round().toRealCoordinates(1.0);
        // Setting the variables that will be used later
        Node start = new Node(s, null);
        Node end = new Node(e, null);
        ArrayList<Node> closedList = new ArrayList<>();
        ArrayList<Node> openList = new ArrayList<>();

        // Initializing for the loop
        start.calculateTotal(e);
        openList.add(start);


        while(!openList.isEmpty())
        {
            //System.out.println();
            //System.out.println();
            //System.out.println("===================================================");
            //System.out.println("Start of big loop");
            Node current = Node.findLowestCost(openList);
            openList.remove(current);
            if(Node.same(current, end))
            {
                return current;
            }

            current.getNeighbors(grid);
            current.calculateTotal(e);
            addListDistinctive(closedList, current);

            printNodeAndNeighbors(current);
            for(Node neighbor : current.neighbors)
            {
                //System.out.println("In neighbor loop");
                printNodeAndNeighbors(neighbor);
                neighbor.calculateTotal(e);
                boolean flag = false;
                for(Node c : closedList)
                {
                    if(Node.same(neighbor, c))
                    {
                        flag = true;
                    }
                }

                //System.out.println("flag = " + flag);
                if(!flag)
                {
                    neighbor.getNeighbors(grid);
                    //System.out.println("In neighbor flag");
                    printNodeAndNeighbors(neighbor);
                    // if in open and new path shorter
                    Node checker = Node.sameIn(openList, neighbor);
                    if((checker != null && neighbor.f < checker.f))
                    {
                        //System.out.println("First if");
                        checker.f = neighbor.f;
                        checker.parent = current;
                    }
                    if(!exists(openList, neighbor))
                    {
                        //System.out.println("Second if");
                        if(checker != null && neighbor.f < checker.f)
                        {
                            //System.out.println("Second.2 if");
                            checker.f = neighbor.f;
                            checker.parent = current;
                        }
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
        printArrayRC(res);
        return res;
    }
}
