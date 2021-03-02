package elements;

import java.awt.Rectangle;

public class Tile {

	public int id;
	
	public int x,y;
	
	public Rectangle r;
	
	
	
	public int xInGame, yInGame;
	
	public Tile() {
		
		id = 0;
		
		r = new Rectangle();
		
		xInGame = 0;
		yInGame = 0;
		
	}

}
