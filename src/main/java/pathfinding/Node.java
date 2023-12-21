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
    private double g; // the cost already used

    private double h; // the cost to go


    /**
     * Constructor of Node
     * @param c coordinates
     * @param p Parent node
     */
    private Node(RealCoordinates c, Node p)
    {
        coordinates = c;
        parent = p;
    }

    /**
     * Calculates distance of 2 points.
     * @param p1 point 1
     * @param p2 point 2
     * @return double
     */
    private double calculateDistance(RealCoordinates p1, RealCoordinates p2)
    {
        double x = Math.abs(p1.x() - p2.x());
        double y = Math.abs(p1.y() - p2.y());
        return x + y;
    }

    /**
     * Calculates total cost.
     * @param end the target coordinate
     */
    private void calculateTotal(RealCoordinates end)
    {
        // F(Total cost function), total of move and toGo
        toGo(end);
        f = g + h;
    }

    /**
     * Calculates the cost to go.
     * @param end the target coordinate
     */
    private void toGo(RealCoordinates end)
    {
        // Heuristic function, the cost to go
        h = calculateDistance(coordinates, end);
    }


    /**
     * Determines whether the coordinates of 2 nodes are the same.
     * @param a node 1
     * @param b node 2
     * @return result
     */
    private static boolean same(Node a, Node b)
    {
        // Verifie si les deux noeuds ont la meme coordonnées
        return a.coordinates.x() == b.coordinates.x() && a.coordinates.y() == b.coordinates.y();
    }

    /**
     * Gets possible neighbor nodes.
     * @param tab cell table
     */
    private void getNeighbors(Cell[][] tab)
    {
        neighbors = new ArrayList<>();
        // The function that adds neighbors.
        //NORD
        if(coordinates.round().y() >= 1)
        {
            if (!tab[coordinates.round().y() - 1][coordinates.round().x()].isWall()) //si neighbor n'est pas un mur
            {
                Node node = new Node(new RealCoordinates(coordinates.x(), coordinates.y() - 1), this);
                node.g = this.g + 1;
                if(!Node.same(this, node))
                {
                    neighbors.add(node);
                }
            }
        }
        //EST
        if(coordinates.round().x() <= tab[0].length-2)
        {
            if (!tab[coordinates.round().y()][coordinates.round().x() + 1].isWall()) //si neighbor n'est pas un mur
            {
                Node node = new Node(new RealCoordinates(coordinates.x() + 1, coordinates.y()), this);
                node.g = this.g + 1;
                if(!Node.same(this, node))
                {
                    neighbors.add(node);
                }
            }
        }
        //SUD
        if(coordinates.round().y() <= tab.length-2)
        {
            if (!tab[coordinates.round().y() + 1][coordinates.round().x()].isWall()) //si neighbor n'est pas un mur
            {
                Node node = new Node(new RealCoordinates(coordinates.x(), coordinates.y() + 1), this);
                node.g = this.g + 1;
                if(!Node.same(this, node))
                {
                    neighbors.add(node);
                }
            }
        }
        //OUEST
        if(coordinates.round().x() >= 1)
        {
            if (!tab[coordinates.round().y()][coordinates.round().x() - 1].isWall()) //si neighbor n'est pas un mur
            {
                Node node = new Node(new RealCoordinates(coordinates.x() - 1, coordinates.y()), this);
                node.g = this.g + 1;
                if(!Node.same(this, node))
                {
                    neighbors.add(node);
                }
            }
        }
    }

    /**
     * Finds lowest cost node of the list.
     * @param list the said list
     * @return the lowest cost node
     */
    private static Node findLowestCost(ArrayList<Node> list)
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

    /**
     * Adds the given node to the list but if a node with same coordinates exist, it swaps itself with it.
     * @param list the said list
     * @param to_add the said node
     */
    private static void addListDistinctive(ArrayList<Node> list, Node to_add)
    {
        // Adds element to the list but swaps if coordinates are the same
        Node n = sameIn(list, to_add);
        if(n != null)
        {
            if (n.f > to_add.f)
            {
                list.remove(n);
                list.add(to_add);
            }
            return;
        }
        list.add(to_add);
    }

    /**
     * Determines if a node in the list and the given node has same coordinates.
     * @param list the said list
     * @param element the said node
     * @return node if exists, null otherwise.
     */
    private static Node sameIn(ArrayList<Node> list, Node element)
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

    /**
     * Determines if the given node is "same" with any of the nodes in the given list.
     * @param list the said list
     * @param element the said node
     * @return true if same, false otherwise
     */
    private static boolean exists(ArrayList<Node> list, Node element)
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

    /**
     * Puts almost every other functions together to make the A* algorithm.
     * @param s the start coordinates
     * @param e the target coordinates
     * @param grid the cell table
     * @return the node containing a path from end to start with its one way chained parents
     */
    private static Node aStar(RealCoordinates s, RealCoordinates e, Cell[][] grid)
    {
        // Setting the variables that will be used later
        s = s.round().toRealCoordinates(1.0);
        e = e.round().toRealCoordinates(1.0);
        Node start = new Node(s, null);
        Node end = new Node(e, null);
        ArrayList<Node> closedList = new ArrayList<>();
        ArrayList<Node> openList = new ArrayList<>();

        // Initializing for the loop
        start.calculateTotal(e);
        openList.add(start);


        while(!openList.isEmpty())
        {
            Node current = Node.findLowestCost(openList);
            openList.remove(current);
            if(Node.same(current, end))
            {
                return current;
            }

            current.getNeighbors(grid);
            current.calculateTotal(e);
            addListDistinctive(closedList, current);

            for(Node neighbor : current.neighbors)
            {
                neighbor.calculateTotal(e);
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
                        //System.out.println("Second if");
                        if(checker != null && neighbor.f < checker.f)
                        {
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

    /**
     * Integrates the aStar function to ghosts.
     * @param s the start coordinates
     * @param e the target coordinates
     * @param grid the cell table
     * @return the path coordinates in a list in the correct way, from start to target
     */
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
