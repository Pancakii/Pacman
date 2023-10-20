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
}
