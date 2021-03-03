package elements;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.Rectangle;

import rendering.Texture;
import window.Window;

public class Player extends Entity {
	
	boolean[] keys = new boolean[512];

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
			keys[GLFW_KEY_W] = true;
		} else keys[GLFW_KEY_W] = false;
		
		if(window.isKeyPressed(GLFW_KEY_A)) {
			keys[GLFW_KEY_A] = true;
		} else keys[GLFW_KEY_A] = false;
		
		if(window.isKeyPressed(GLFW_KEY_S)) {
			keys[GLFW_KEY_S] = true;
		} else keys[GLFW_KEY_S] = false;
		
		if(window.isKeyPressed(GLFW_KEY_D)) {
			keys[GLFW_KEY_D] = true;
		} else keys[GLFW_KEY_D] = false;
	}

	public void move() {
		if(keys[GLFW_KEY_W]) {
			velY++;
		}
		if(keys[GLFW_KEY_S]) {
			velY--;
		}
		if(keys[GLFW_KEY_A]) {
			velX--;
		}
		if(keys[GLFW_KEY_D]) {
			velX++;
		}
		
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
