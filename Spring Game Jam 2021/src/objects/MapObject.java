package objects;

import java.awt.Point;

import elements.Camera;
import rendering.Renderer;
import rendering.Texture;

public interface MapObject {

	
	
	int scale = 4;
	
	
	
	
	
	
	public void render(Renderer r, Camera c);


	public Texture getTexture();


	public void renderSized(Renderer r, int sizeModifier, Camera c);
	

	int tick(Point p, int mouse);
	
	public void setPos(int x, int y);
	
	public int getJungle();
	
	public void setJungle(int value);
	
}
