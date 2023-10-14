package gui;

import config.Cell;
import geometry.RealCoordinates;

public class Node
{
    RealCoordinates coordinates;
    int used;
    int to_use;
    int total;
    Node[] neighbors = new Node[4]; // 0: nord, 1: est, 2: sud, 3: ouest
    boolean open;

    public Node(double i, double j, int used, double di, double dj)
    {
        coordinates = new RealCoordinates(i, j);
        this.used = used;
        to_use = calculateDistance(di, dj);// une fonc pour calculer la distance
        total = this.used + to_use;
        // faire une fonc pour trouver les neighbors si besoin
        open = false;
    }

    void discoverNeighbors(Cell[][] tab, double di, double dj)
    {
        //a faire: gerer le cas ou index out of bounds
        //NORD
        if(coordinates.x >= 1) {
            if (!tab[(int) coordinates.x - 1][(int) coordinates.y()].Wall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Node(coordinates.x - 1, coordinates.y, used + 1, di, dj);
            }
        }
        //EST
        if(coordinates.y <= tab[0].length-1) {
            if (!tab[(int) coordinates.x][(int) coordinates.y() + 1].Wall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Node(coordinates.x, coordinates.y + 1, used + 1, di, dj);
            }
        }
        //SUD
        if(coordinates.x <= tab.length-1) {
            if (!tab[(int) coordinates.x + 1][(int) coordinates.y()].Wall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Node(coordinates.x + 1, coordinates.y, used + 1, di, dj);
            }
        }
        //OUEST
        if(coordinates.y >= 1) {
            if (!tab[(int) coordinates.x][(int) coordinates.y() - 1].Wall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Node(coordinates.x, coordinates.y - 1, used + 1, di, dj);
            }
        }
    }

    int calculateDistance(double di, double dj)
    {
        int x = Math.abs(di-coordinates.x);
        int y = Math.abs(dj-coordinates.y);
        return x + y;
    }

}
