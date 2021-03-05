package elements;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.Rectangle;

import rendering.Texture;
import window.Window;

public class Player extends Entity {


	
	float slowdown = 0.3f;
	float acceleration = 0.5f;

	
	int maxVelocity = 6;
	
	boolean hasMovedSinceCamera = true;


	public Player(Texture t) {

		this.t = t;

		initiate();

	}

	public void initiate() {

		screenRect = new Rectangle(x, y, x + t.getWidth(), y + t.getHeight());

		gameRect = new Rectangle(inGameX, inGameY, t.getWidth(), t.getHeight());

	}


	public void tick(Camera camera, Map map) {
		gameRect.setBounds(inGameX, inGameY, t.getWidth(), t.getHeight());
		move(map);
		x = inGameX - camera.x;
		y = inGameY - camera.y;

	
		

	}

	public void input(Window window) {

		if(window.isKeyPressed(GLFW_KEY_W)) {
			if (velY < maxVelocity) velY++;
		} 
		
		if(window.isKeyPressed(GLFW_KEY_A)) {
			if (velX > -maxVelocity)velX--;
		} 
		
		if(window.isKeyPressed(GLFW_KEY_S)) {
			if (velY > -maxVelocity) velY--;
		} 
		
		if(window.isKeyPressed(GLFW_KEY_D)) {
			if (velX < maxVelocity) velX++;
		} 
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
		
		try {
			
		if (!(map.tiles[Math.floorDiv((int) (gameRect.getX() + gameRect.getWidth()),map.scaledTileSize)][Math.floorDiv((int) (gameRect.getY() + gameRect.getHeight()), map.scaledTileSize)].id == 3) 
		&& !(map.tiles[Math.floorDiv((int) (gameRect.getX()),map.scaledTileSize)][Math.floorDiv((int) (gameRect.getY() + gameRect.getHeight()), map.scaledTileSize)].id == 3) 
		&& !(map.tiles[Math.floorDiv((int) (gameRect.getX() + gameRect.getWidth()),map.scaledTileSize)][Math.floorDiv((int) (gameRect.getY()), map.scaledTileSize)].id == 3)
		&& !(map.tiles[Math.floorDiv((int) (gameRect.getX()),map.scaledTileSize)][Math.floorDiv((int) (gameRect.getY()), map.scaledTileSize)].id == 3) ) {
			inGameY += Math.ceil(velY);

		} else {
			velY = 0;
			gameRect.y -= Math.ceil(velY);
		}
		} catch (ArrayIndexOutOfBoundsException e) {gameRect.y -= Math.ceil(velY); velY = 0; e.printStackTrace();;}
		gameRect.x += Math.ceil(velX);
		
		try {
		if (!(map.tiles[Math.floorDiv((int) (gameRect.getX() + gameRect.getWidth()),map.scaledTileSize)][Math.floorDiv((int) (gameRect.getY() + gameRect.getHeight()), map.scaledTileSize)].id == 3) 
		&& !(map.tiles[Math.floorDiv((int) (gameRect.getX()),map.scaledTileSize)][Math.floorDiv((int) (gameRect.getY() + gameRect.getHeight()), map.scaledTileSize)].id == 3) 
		&& !(map.tiles[Math.floorDiv((int) (gameRect.getX() + gameRect.getWidth()),map.scaledTileSize)][Math.floorDiv((int) (gameRect.getY()), map.scaledTileSize)].id == 3)
		&& !(map.tiles[Math.floorDiv((int) (gameRect.getX()),map.scaledTileSize)][Math.floorDiv((int) (gameRect.getY()), map.scaledTileSize)].id == 3) ) {

			inGameX += Math.ceil(velX);
		} else {
			velX = 0;
			gameRect.x -= Math.ceil(velX);
		}
		} catch (ArrayIndexOutOfBoundsException e) {gameRect.x -= Math.ceil(velX); velX = 0;}
		
		
	}
}
