package config;

public record Cell(boolean wall, Cell.Content initialContent) {
	public enum Content {NOTHING, DOT, ENERGIZER}
	
	// Méthode pour vérifier si la case est un mur
	public boolean isWall() {
		  return wall;
	}
	
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
	
}
