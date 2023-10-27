package config;

public record Cell(boolean wall, Content initialContent) {
    public enum Content {NOTHING, DOT, ENERGIZER}

    // Méthode pour vérifier si la case est un mur
    public boolean isWall() {
        return wall;
    }

    // Méthode pour vérifier si la case a un DOT
    public boolean aDot() {
        return initialContent == Content.DOT;
    }

    // Méthode pour vérifier si la case a un Energizer
    public boolean aEnergizer() {
        return initialContent == Content.ENERGIZER;
    }

    // Méthode pour vérifier si la case a rien
    public boolean aNothing() {
        return initialContent == Content.NOTHING;
    }

    public static Cell Cellule(int i) {
        // Mur
        if (i == 0) return new Cell(true, Content.NOTHING);
        // Case vide
        if (i == 1) return new Cell(false, Content.NOTHING);
        // Case avec un Dot
        if (i == 2) return new Cell(false, Content.DOT);
        // Case avec un energizer
        if (i == 3) return new Cell(false, Content.ENERGIZER);

        return null;
    }
}
