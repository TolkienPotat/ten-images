package elements;

import java.awt.Rectangle;

import rendering.Texture;

public class Player extends Entity{

	
	
	
	

	public Player(Texture t) {
		
		this.t = t;
		
		initiate();
		
	}
	
	public void initiate() {
		
		screenRect = new Rectangle(x, y, x + t.getWidth(), y + t.getHeight());
		
		gameRect = new Rectangle(inGameX, inGameY, inGameX + t.getWidth(), inGameY + t.getHeight());
		
	}
	
	public void tick(Camera camera) {
		
		x = inGameX - camera.x;
		y = inGameY - camera.y;
		
	}
	
}
