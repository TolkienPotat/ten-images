package elements;

import java.awt.Point;
import java.awt.Rectangle;

import objects.MapObject;
import rendering.Renderer;
import rendering.Texture;

public class Tile {

	public int id;

	public int x, y;

	public Rectangle r;

	public MapObject object;

	public int xInGame, yInGame;
	int jungle = 0;
	
	
	boolean shouldKillObject = false;
	
	public Tile() {

		id = 0;

		r = new Rectangle();

		xInGame = 0;
		yInGame = 0;
		
		

	}

	public int tick(Point p, int mouse) {

		if (shouldKillObject) {
			shouldKillObject = false;
			object = null;
		}
		
		if (object == null) {
			return 0;
		}

		

		int i = object.tick(p, mouse);
		if (i == -1) {
			shouldKillObject = true;
		}
		
		return i;

	}

	public void render(Renderer r, Camera c) {

		if (object == null) {
			return;
		}

		object.render(r, c);

	}
	
	public int getJungle() {
		if (object == null) {
			return 0;
		}
		return object.getJungle();
	}
	
	public void setJungle(int value) {
		if (object == null) {
			return;
		}
		object.setJungle(value);
	}
	
	public Texture getObjectTexture() {
		return object.getTexture();
	}

}
