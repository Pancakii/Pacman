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

    public Node(int i, int j, int used, int di, int dj)
    {
        coordinates = new RealCoordinates(i, j);
        this.used = used;
        to_use = calculateDistance(di, dj);// une fonc pour calculer la distance
        total = this.used + to_use;
        // faire une fonc pour trouver les neighbors si besoin
        open = false;
    }

    void discoverNeighbors(Cell[][] tab, int di, int dj)
    {
        //a faire: gerer le cas ou index out of bounds
        //NORD
        if(coordinates.round().x() >= 1) {
            if (!tab[coordinates.round().x() - 1][coordinates.round().y()].Wall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Node(coordinates.x() - 1, coordinates.y(), used + 1, di, dj);
            }
        }
        //EST
        if(coordinates.round().y() <= tab[0].length-1) {
            if (!tab[coordinates.round().x()][coordinates.round().y() + 1].Wall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Node(coordinates.x, coordinates.y + 1, used + 1, di, dj);
            }
        }
        //SUD
        if(coordinates.round().x() <= tab.length-1) {
            if (!tab[coordinates.round().x() + 1][coordinates.round().y()].Wall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Node(coordinates.x + 1, coordinates.y, used + 1, di, dj);
            }
        }
        //OUEST
        if(coordinates.round().y() >= 1) {
            if (!tab[coordinates.round().x()][coordinates.round().y() - 1].Wall()) //si neighbor n'est pas un mur
            {
                neighbors[0] = new Node(coordinates.x, coordinates.y - 1, used + 1, di, dj);
            }
        }
    }

    int calculateDistance(int di, int dj)
    {
        int x = Math.abs(di-coordinates.round().x());
        int y = Math.abs(dj-coordinates.round().y());
        return x + y;
    }

}
