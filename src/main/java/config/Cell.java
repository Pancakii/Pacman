package config;

/**
 * Classe qui crée des cellules
 * @param passable	Cellule passable
 * @param wall	Cellule qui est un mur
 * @param initialContent	Cellule qui contient quelque chose (Energizer/Dot/Rien) 
 */
public record Cell(boolean passable, boolean wall, Content initialContent) {
    public enum Content {NOTHING, DOT, ENERGIZER}
    
    
    /**
     * Méthode qui vérifie si la cellule est Passable
     * @return (true) la cellule est Passable (false) ce n'est pas Passable
     */
    public boolean isPassable() {
        return passable;
    }
    
    
    /**
     * Méthode qui vérifie si la cellule est Mur
     * @return (true) la cellule est Mur (false) ce n'est pas un Mur
     */
    public boolean isWall() {
        return wall;
    }

    
    /**
     * Méthode qui vérifie si la cellule contient un dot
     * @return (true) la cellule contient un Dot (false) la cellule ne contient pas de Dot
     */
    public boolean aDot() {
        return initialContent == Content.DOT;
    }

    
    /**
     * Méthode qui vérifie si la cellule contient un energizer
     * @return (true) la cellule contient un Energizer (false) la cellule ne contient pas d'Energizer
     */
    public boolean aEnergizer() {
        return initialContent == Content.ENERGIZER;
    }

    
    /**
     * Méthode qui vérifie si la cellule ne contient rien
     * @return (true) la cellule ne contient rien (false) la cellule contient un objet
     */
    public boolean aNothing() {
        return initialContent == Content.NOTHING;
    }
    
    
    /**
     * Méthode qui construit des cellules
     * @param i	Valeur du type des cellules
     * @return Retourne un certain type de cellule en fonction de la valeur de l'entier i
     */
    public static Cell Cellule(int i) {
        // Mur
        if (i == 0) return new Cell(false, true, Content.NOTHING);
        // Mur passable uniquement par fantome
        if (i == 4) return new Cell(false, false, Content.NOTHING);
        // Case vide
        if (i == 1) return new Cell(true, false, Content.NOTHING);
        // Case avec un Dot
        if (i == 2) return new Cell(true, false, Content.DOT);
        // Case avec un energizer
        if (i == 3) return new Cell(true, false, Content.ENERGIZER);

        return null;
    }
}
