package elements;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.awt.Color;
import java.awt.Rectangle;

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
	//test
	public Texture heldObject;	
	int heldObjectScale = 3;
	
	Color drawColor = new Color(1,1,1);
	
	int maxVelocity = 6;
	
	boolean hasMovedSinceCamera = true;
	
	public Inventory i;


	public Player(Texture t) {

		this.t = t;

		initiate();

	}

	public void initiate() {

		screenRect = new Rectangle(x, y, x + t.getWidth(), y + t.getHeight());

		
		tWidth = t.getWidth() * scale;
		tHeight = t.getHeight() * scale;
		
		gameRect = new Rectangle(inGameX, inGameY, tWidth, tHeight);
		
		i = new Inventory();
		
		heldObject = Texture.loadTexture("DefaultResources/Images/tool.png");
	}


	public void tick(Camera camera, Map map) {
		gameRect.setBounds(inGameX, inGameY, tWidth, tHeight);
		move(map);
		x = inGameX - camera.x;
		y = inGameY - camera.y;

		i.tick(this);
		
		switch (direction) {
		case 0 :
			tcX = 0.5f;
			tcY = 0;
			break;
		case 1 :
			tcX = 0;
			tcY = 0.5f;
			break;
		case 2 :
			tcX = 0;
			tcY = 0;
			break;
		case 3 :
			tcX = 0.5f;
			tcY = 0.5f;
			break;
		}
		
		
		
	}
	
	@Override
	public void render(Renderer r) {
		r.begin();
		t.bind();
		r.drawTextureRegion(x, y, x+tWidth, y+tHeight, tcX, tcY, tcX+0.5f, tcY+0.5f, drawColor, inGameX, inGameY, 0);
		r.end();
		
		r.begin();
		heldObject.bind();
		switch (direction) {
		case 0 :
			r.drawTextureRegion(x + 13, y + 6, x + 13 + heldObject.getWidth()*heldObjectScale, y + 6 + heldObject.getHeight()*heldObjectScale, 0, 0, 1, 1, drawColor, 0, 0, 0);
			break;
		case 1 :
			r.drawTextureRegion(x - 16, y + 6, x - 16 + heldObject.getWidth()*heldObjectScale, y + 6 + heldObject.getHeight()*heldObjectScale, 1, 0, 0, 1, drawColor, 0, 0, 0);
			break;
		case 2 :
			
			break;
		case 3 :
			
			break;
		}
		r.end();
		
		i.render(r);
	}

	public void input(Window window) {

		if(window.isKeyPressed(GLFW_KEY_W)) {
			if (velY < maxVelocity) velY++;
			direction = 3;
		}
		
		if(window.isKeyPressed(GLFW_KEY_S)) {
			if (velY > -maxVelocity) velY--;
			direction = 1;
		}
		
		if(window.isKeyPressed(GLFW_KEY_A)) {
			if (velX > -maxVelocity)velX--;
			direction = 2;
		} 
		
		
		
		if(window.isKeyPressed(GLFW_KEY_D)) {
			if (velX < maxVelocity) velX++;
			direction = 0;
		} 
		
		i.input(window);
	}
	
	public void printCoords() {
		
		System.out.println("inGameX = " + inGameX + "inGameY = " + inGameY);
		
	}
	

	public void move(Map map) {
		if(velY > slowdown) {
			velY-=slowdown;
		} else if (velY < -slowdown) {
			velY+=slowdown;
		} else velY = 0;
		
		if(velX > slowdown) {
			velX-=slowdown;
		} else if (velX < -slowdown) {
			velX+=slowdown;
		} else velX = 0;
		
		
		
		
		if (velX == 0 && velY == 0) {
			return;
		}
		
		gameRect.y += Math.ceil(velY);
		
		
		int rectX = (int) gameRect.getX();
		int rectY = (int) gameRect.getY();
		
		
		
		
		try {
			
		if (!(map.tiles[Math.floorDiv(rectX + tWidth, map.scaledTileSize)][Math.floorDiv(rectY + tHeight, map.scaledTileSize)].id >= map.wallIDPos) 
		&& !(map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY + tHeight, map.scaledTileSize)].id >= map.wallIDPos) 
		&& !(map.tiles[Math.floorDiv(rectX + tWidth, map.scaledTileSize)][Math.floorDiv(rectY, map.scaledTileSize)].id >= map.wallIDPos)
		&& !(map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY, map.scaledTileSize)].id >= map.wallIDPos)) {
			
			
			if (velY != 0 && map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY, map.scaledTileSize)].getJungle() <= Math.abs(velY))inGameY += Math.ceil(velY)  - map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY, map.scaledTileSize)].getJungle()*velY/Math.abs(velY);

		} else {
			
			gameRect.y -= Math.ceil(velY);
			velY = 0;
		}
		} catch (ArrayIndexOutOfBoundsException e) {gameRect.y -= Math.ceil(velY); velY = 0; }
		gameRect.x += Math.ceil(velX);
		rectX = (int) gameRect.getX();
		rectY = (int) gameRect.getY();
		try {
		if (!(map.tiles[Math.floorDiv( rectX + tWidth, map.scaledTileSize)][Math.floorDiv(rectY + tHeight, map.scaledTileSize)].id >= map.wallIDPos) 
		&& !(map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY + tHeight, map.scaledTileSize)].id >= map.wallIDPos) 
		&& !(map.tiles[Math.floorDiv(rectX + tWidth, map.scaledTileSize)][Math.floorDiv(rectY, map.scaledTileSize)].id >= map.wallIDPos)
		&& !(map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY, map.scaledTileSize)].id >= map.wallIDPos)) {

			if (velX != 0 && map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY, map.scaledTileSize)].getJungle() <= Math.abs(velX))inGameX += Math.ceil(velX)  - map.tiles[Math.floorDiv(rectX, map.scaledTileSize)][Math.floorDiv(rectY, map.scaledTileSize)].getJungle()*velX/Math.abs(velX);

		} else {
			
			gameRect.x -= Math.ceil(velX);
			velX = 0;
		}
		} catch (ArrayIndexOutOfBoundsException e) {gameRect.x -= Math.ceil(velX); velX = 0;}
		
	}
}
