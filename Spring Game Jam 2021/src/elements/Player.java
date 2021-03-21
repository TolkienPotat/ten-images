package elements;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import matrixes.Vector2f;
import rendering.Renderer;
import rendering.Texture;
import window.Window;

public class Player extends Entity {

	float slowdown = 0.3f;
	float acceleration = 0.5f;

	int tWidth, tHeight;

	int scale = 1;

	int direction = 1;
	float tcX, tcY;
	// test
	public Texture heldObject;
	int heldObjectScale = 3;

	public int objectRotation = 0;

	public boolean isSwingingObject = false;

	Color drawColor = new Color(1, 1, 1);

	int maxVelocity = 6;

	boolean hasMovedSinceCamera = true;

	public Inventory i;

	public Player(Texture t, Window w) {

		this.t = t;

		initiate(w);

	}

	public void initiate(Window w) {

		screenRect = new Rectangle(x, y, x + t.getWidth(), y + t.getHeight());

		tWidth = t.getWidth() * scale;
		tHeight = t.getHeight() * scale;

		gameRect = new Rectangle(inGameX, inGameY, tWidth, tHeight);

		i = new Inventory(w);

		heldObject = Texture.loadTexture("DefaultResources/Images/tool.png");
	}

	public void tick(Camera camera, Map map) {
		gameRect.setBounds(inGameX, inGameY, tWidth, tHeight);
		move(map);
		x = inGameX - camera.x;
		y = inGameY - camera.y;

		i.tick(this);

		switch (direction) {
		case 0:
			tcX = 0.5f;
			tcY = 0;
			break;
		case 1:
			tcX = 0;
			tcY = 0.5f;
			break;
		case 2:
			tcX = 0;
			tcY = 0;
			break;
		case 3:
			tcX = 0.5f;
			tcY = 0.5f;
			break;
		}

	}

	@Override
	public void render(Renderer r) {
		r.begin();
		t.bind();
		r.drawTextureRegion(x, y, x + tWidth, y + tHeight, tcX, tcY, tcX + 0.5f, tcY + 0.5f, drawColor, inGameX,
				inGameY, 0);
		r.end();

		r.begin();
		heldObject.bind();

		
		
		if (isSwingingObject) {
			switch (direction) {
			case 0:
				r.drawRotatedTexture(x + 13, y + 6, x + 13 + heldObject.getWidth() * heldObjectScale,
						y + 6 + heldObject.getHeight() * heldObjectScale, 0, 0, 1, 1, drawColor, 0, 0, 0,
						-objectRotation, new Vector2f(x + 13, y + 6));
				break;
			case 1:
				r.drawRotatedTexture(x + 30, y + 6, x + 30 + heldObject.getWidth() * heldObjectScale,
						y + 6 + heldObject.getHeight() * heldObjectScale, 0, 0, 1, 1, drawColor, 0, 0, 0,
						-objectRotation, new Vector2f(x + 30, y + 6));
				break;
			case 2:
				r.drawRotatedTexture(x - 16, y + 10, x - 16 + heldObject.getWidth() * heldObjectScale,
						y + 10 + heldObject.getHeight() * heldObjectScale, 0, 0, 1, 1, drawColor, 0, 0, 0,
						objectRotation, new Vector2f(x - 16 + heldObject.getWidth() * heldObjectScale, y + 10));

				break;
			case 3:
				r.drawRotatedTexture(x - 16, y + 6, x - 16 + heldObject.getWidth() * heldObjectScale,
						y + 6 + heldObject.getHeight() * heldObjectScale, 0, 0, 1, 1, drawColor, 0, 0, 0,
						objectRotation, new Vector2f(x - 16 + heldObject.getWidth() * heldObjectScale, y + 6));
				break;
			}
		}
		r.end();

		i.render(r);
	}

	public void input(Window window, Point mousePos) {

		if (window.isKeyPressed(GLFW_KEY_W)) {
			if (velY < maxVelocity)
				velY++;
			direction = 3;
		}

		if (window.isKeyPressed(GLFW_KEY_S)) {
			if (velY > -maxVelocity)
				velY--;
			direction = 1;
		}

		if (window.isKeyPressed(GLFW_KEY_A)) {
			if (velX > -maxVelocity)
				velX--;
			direction = 2;
		}

		if (window.isKeyPressed(GLFW_KEY_D)) {
			if (velX < maxVelocity)
				velX++;
			direction = 0;
		}

		i.input(window, mousePos);
	}

	public void printCoords() {

		System.out.println("inGameX = " + inGameX + "inGameY = " + inGameY);

	}

	public void move(Map map) {
		if (velY > slowdown) {
			velY -= slowdown;
		} else if (velY < -slowdown) {
			velY += slowdown;
		} else
			velY = 0;

		if (velX > slowdown) {
			velX -= slowdown;
		} else if (velX < -slowdown) {
			velX += slowdown;
		} else
			velX = 0;

		if (velX == 0 && velY == 0) {
			return;
		}

		gameRect.y += Math.ceil(velY);

		int rectX = (int) gameRect.getX();
		int rectY = (int) gameRect.getY();

		try {

			if (!(map.tiles[Math.floorDiv(rectX + tWidth, map.scaledTileSize)][Math.floorDiv(rectY + tHeight,
					map.scaledTileSize)].id >= map.wallIDPos)
					&& !(map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY + tHeight,
							map.scaledTileSize)].id >= map.wallIDPos)
					&& !(map.tiles[Math.floorDiv(rectX + tWidth, map.scaledTileSize)][Math.floorDiv(rectY,
							map.scaledTileSize)].id >= map.wallIDPos)
					&& !(map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY,
							map.scaledTileSize)].id >= map.wallIDPos)) {

				if (velY != 0
						&& map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY, map.scaledTileSize)]
								.getJungle() <= Math.abs(velY))
					inGameY += Math.ceil(velY) - map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math
							.floorDiv(rectY, map.scaledTileSize)].getJungle() * velY / Math.abs(velY);

			} else {

				gameRect.y -= Math.ceil(velY);
				velY = 0;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			gameRect.y -= Math.ceil(velY);
			velY = 0;
		}
		gameRect.x += Math.ceil(velX);
		rectX = (int) gameRect.getX();
		rectY = (int) gameRect.getY();
		try {
			if (!(map.tiles[Math.floorDiv(rectX + tWidth, map.scaledTileSize)][Math.floorDiv(rectY + tHeight,
					map.scaledTileSize)].id >= map.wallIDPos)
					&& !(map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY + tHeight,
							map.scaledTileSize)].id >= map.wallIDPos)
					&& !(map.tiles[Math.floorDiv(rectX + tWidth, map.scaledTileSize)][Math.floorDiv(rectY,
							map.scaledTileSize)].id >= map.wallIDPos)
					&& !(map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY,
							map.scaledTileSize)].id >= map.wallIDPos)) {

				if (velX != 0
						&& map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY, map.scaledTileSize)]
								.getJungle() <= Math.abs(velX))
					inGameX += Math.ceil(velX) - map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math
							.floorDiv(rectY, map.scaledTileSize)].getJungle() * velX / Math.abs(velX);

			} else {

				gameRect.x -= Math.ceil(velX);
				velX = 0;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			gameRect.x -= Math.ceil(velX);
			velX = 0;
		}

	}

	public void swingObject() {

		objectRotation += 9;
		if (objectRotation > 90)
			objectRotation = 0;

	}

}
