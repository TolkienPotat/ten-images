package objects;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import elements.Camera;
import rendering.Renderer;
import rendering.Texture;

public class Tree implements MapObject {

	int inGameX, inGameY;

	int x, y;

	int health = 120;
	
	public Texture texture;

	public Rectangle bounds;

	public Tree(int x, int y) {
		texture = Texture.loadTexture("DefaultResources/Images/tree.png");
		inGameX = x;
		inGameY = y;
		
		bounds = new Rectangle(x, y, texture.getWidth()*scale, texture.getHeight()*scale);

	}

	public void click() {

	}

	@Override
	
	public int tick(Point p, int mouse) {


		
		if (bounds.contains(p) && mouse == 1) {
			
			health--;
			
			
		}
		
		if (health <= 0) {
			return -1;
		}
		return 0;
	}
	
	
	
	@Override
	public void render(Renderer r, Camera c) {

		x = inGameX - c.x;
		y = inGameY - c.y;

		if (x > 3840 || x < -1080 || y > 2160 || y < -1080) {
			return;
		}

		r.begin();
		texture.bind();
		r.drawCustomTextureRegion(texture, x, y, 0, 0, texture.getWidth() * scale, texture.getHeight() * scale,
				new Color(1, 1, 1), inGameX, inGameY);
		r.end();
	}

	@Override
	public void setPos(int x, int y) {
		
		inGameX = x;
		inGameY = y;
		
		bounds.setBounds(x, y, texture.getWidth()*scale, texture.getHeight()*scale);
		
	}

}
