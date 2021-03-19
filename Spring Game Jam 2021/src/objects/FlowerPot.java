package objects;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import elements.Camera;
import rendering.Renderer;
import rendering.Texture;

public class FlowerPot implements MapObject {

	int inGameX, inGameY;

	int x, y;

	int health = 40;

	public Texture texture;

	public Rectangle bounds;

	MapObject heldObject;

	int jungle = 0;
	
	int age = 0;

	public FlowerPot() {
		inGameX = 0;
		inGameY = 0;
		texture = Texture.loadTexture("DefaultResources/Images/flowerPot.png");
		bounds = new Rectangle(inGameX, inGameY, texture.getWidth() * scale, texture.getHeight() * scale);
	}

	@Override
	public void render(Renderer r, Camera c) {

		x = inGameX - c.x;
		y = inGameY - c.y;

		if (x > 3840 || x < -1080 || y > 2160 || y < -1080) {
			return;
		}

		if (heldObject != null) {
			renderHeldObject(r, c);
		}

		r.begin();
		texture.bind();
		r.drawCustomTextureRegion(texture, x, y, 0, 0, texture.getWidth() * scale, texture.getHeight() * scale,
				new Color(1, 1, 1), inGameX, inGameY, jungle);
		r.end();

	}

	private void renderHeldObject(Renderer r, Camera c) {
		heldObject.renderSized(r, 1, c);
	}

	@Override
	public int tick(Point p, int mouse) {
		age++;
		if (bounds.contains(p) && mouse == 1) {

			health--;
			if (health <= 0) {
				return -1;
			}
			return 1;
		}

		if (bounds.contains(p) && mouse == 2 && age > 10) {

			heldObject = new Tree(inGameX + 25, inGameY + 30);
			
		}
		
		if (heldObject != null) heldObject.setJungle(getJungle());

		return 0;
	}

	@Override
	public void setPos(int x, int y) {
		inGameX = x;
		inGameY = y;
		bounds.setBounds(x, y, texture.getWidth() * scale, texture.getHeight() * scale);
	}

	@Override
	public int getJungle() {
		return jungle;
	}

	@Override
	public void setJungle(int value) {
		jungle = value;
	}

	@Override
	public Texture getTexture() {
		return texture;
	}

	@Override
	public void renderSized(Renderer r, int sizeModifier, Camera c) {
	}

}
