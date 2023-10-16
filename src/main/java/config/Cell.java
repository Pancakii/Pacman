package config;

public record Cell(boolean Wall, Cell.Content initialContent) {
	public enum Content {NOTHING, DOT, ENERGIZER}
	
	public static Cell Cellule(int i) {
		//Mur
		if(i == 0)return new Cell(true, Content.NOTHING);
		//Case vide
		if(i == 1)return new Cell(false, Content.NOTHING);
		//Case avec un Dot
		if(i == 2)return new Cell(false, Content.DOT);
		//Case avec un energizer
		if(i == 3)return new Cell(false, Content.ENERGIZER);  
		
		return null;
	}

	// Méthode pour vérifier si la case est un mur
	public boolean isWall() {
		return Wall;
	}

	// Méthode pour vérifier si la case a un DOT
	public boolean aDot() {
		if(initialContent == Content.DOT)return true;
		return false;
	}

	// Méthode pour vérifier si la case a un Energizer
	public boolean aEnergizer() {
		if(initialContent == Content.ENERGIZER)return true;
		return false;
	}

	// Méthode pour vérifier si la case a rien
	public boolean aNothing() {
		if(initialContent == Content.NOTHING)return true;
		return false;
	}
}
