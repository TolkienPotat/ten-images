package elements;

import java.awt.Point;
import java.awt.Rectangle;

import objects.MapObject;
import rendering.Renderer;

public class Tile {

	public int id;

	public int x, y;

	public Rectangle r;

	public MapObject object;

	public int xInGame, yInGame;

	public Tile() {

		id = 0;

		r = new Rectangle();

		xInGame = 0;
		yInGame = 0;

	}

	public void tick(Point p, int mouse) {

		if (object == null) {
			return;
		}

		if (object.tick(p, mouse) == -1) {
			object = null;
		}

	}

	public void render(Renderer r, Camera c) {

		if (object == null) {
			return;
		}

		object.render(r, c);

	}

}
