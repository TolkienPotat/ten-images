package elements;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.Rectangle;

import rendering.Texture;
import window.Window;

public class Player extends Entity {
	
	int maxVelocity = 6;

	public Player(Texture t) {

		this.t = t;

		initiate();

	}

	public void initiate() {

		screenRect = new Rectangle(x, y, x + t.getWidth(), y + t.getHeight());

		gameRect = new Rectangle(inGameX, inGameY, inGameX + t.getWidth(), inGameY + t.getHeight());

	}


	public void tick(Camera camera) {
		move();
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

	public void move() {
		
		
		if (velX > 0) {
			velX -= 0.1;
		} else if (velX < 0) {
			velX += 0.1;
		}

		if (velY > 0) {
			velY -= 0.1;
		} else if (velY < 0) {
			velY += 0.1;
		}
		
		inGameX += velX;
		inGameY += velY;
	}
}
