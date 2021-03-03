package elements;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.Rectangle;

import rendering.Texture;
import window.Window;

public class Player extends Entity {

	boolean[] keys = new boolean[512];
	float slowdown = 0.03f;
	float acceleration = 0.5f;

	public Player(Texture t) {

		this.t = t;

		initiate();

	}

	public void initiate() {

		screenRect = new Rectangle(x, y, x + t.getWidth(), y + t.getHeight());

		gameRect = new Rectangle(inGameX, inGameY, inGameX + t.getWidth(), inGameY + t.getHeight());

	}

	public void tick() {
		move();
	}

	public void input(Window window) {
		if (window.isKeyPressed(GLFW_KEY_W)) {
			keys[GLFW_KEY_W] = true;
		} else
			keys[GLFW_KEY_W] = false;

		if (window.isKeyPressed(GLFW_KEY_A)) {
			keys[GLFW_KEY_A] = true;
		} else
			keys[GLFW_KEY_A] = false;

		if (window.isKeyPressed(GLFW_KEY_S)) {
			keys[GLFW_KEY_S] = true;
		} else
			keys[GLFW_KEY_S] = false;

		if (window.isKeyPressed(GLFW_KEY_D)) {
			keys[GLFW_KEY_D] = true;
		} else
			keys[GLFW_KEY_D] = false;
	}

	public void move() {
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
		
		if(keys[GLFW_KEY_W]) {
			if(velY < 6) {
				velY+=acceleration;
			}
		}
		if(keys[GLFW_KEY_S]) {
			if(velY > -6) {
				velY-=acceleration;
			}
		}
		if(keys[GLFW_KEY_A]) {
			if(velX > -6) {
				velX-=acceleration;
			}
		}
		if(keys[GLFW_KEY_D]) {
			if(velX < 6) {
				velX+=acceleration;
			}
		}
		
		inGameX += Math.ceil(velX);
		inGameY += Math.ceil(velY);
		
		
	}
}
