package elements;

import java.awt.Rectangle;

import rendering.Renderer;
import rendering.Texture;

public class Item {
	
	boolean canBePlaced, contained;
	
	int inGameX, inGameY;
	
	int x, y;
	
	int xVel, yVel;
	
	Texture t;
	
	public Rectangle rect;
	
	public Item(Texture texture) {
		t = texture;
		contained = true;
	}
	
	public Item(Texture texture, int inGameX, int inGameY) {
		t = texture;
		contained = false;
		this.inGameY = inGameY;
		this.inGameX = inGameX;
		xVel = 0;
		yVel = 0;
		
		rect = new Rectangle(inGameX, inGameY, texture.getWidth(), texture.getHeight());
	}
	
	public void tick(Player player) {
		
		if (!contained) {
			int xDist = player.inGameX - inGameX;
			int yDist = player.inGameY - inGameY;
			
			if (Math.abs(xDist) + Math.abs(yDist) < 240 && Math.abs(xDist) + Math.abs(yDist) > 20) {
				if (xDist > 0) xVel++; else xVel--; 
				if (yDist > 0) yVel++; else yVel--; 
			}else {
				if (xVel > 0) xVel--; else if (xVel < 0)xVel++;
				if (yVel > 0) yVel--; else if (yVel < 0)yVel++;
			} 
			

			
			
			inGameX += xVel;
			inGameY += yVel;
			rect.setLocation(inGameX, inGameY);
		}
		
	}
	
	public void render(Renderer r, Camera c) {
		
		
		
		r.begin();
		
		if (contained) {
			//drawsomewhere
		} else {
			x = inGameX - c.x;
			y = inGameY - c.y;
			t.bind();
			r.drawTexture(t, x, y, inGameX, inGameY);
		}
		r.end();
	}
}
