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
		
		
		inGameX += Math.ceil(velX);
		inGameY += Math.ceil(velY);
		
		
	}
}
