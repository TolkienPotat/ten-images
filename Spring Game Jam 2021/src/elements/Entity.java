package elements;

import java.awt.Rectangle;

import rendering.Renderer;
import rendering.Texture;
import window.Window;

public class Entity {

	Texture t;
	
	public int x, y;
	
	public int inGameX, inGameY;
	
	public int velX, velY;
	
	Rectangle screenRect;
	Rectangle gameRect;
	
	public Entity() {
		
	}
	
	public void render(Renderer r) {
		
		r.begin();
		t.bind();
		r.drawTexture(t, x, y, inGameX, inGameY);
		r.end();
		
	}
}
