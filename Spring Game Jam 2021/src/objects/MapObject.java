package objects;

import java.awt.Point;

import elements.Camera;
import rendering.Renderer;

public interface MapObject {

	
	
	int scale = 4;
	
	
	
	
	
	
	public void render(Renderer r, Camera c);






	int tick(Point p, int mouse);
	
	public void setPos(int x, int y);
	
	public int getJungle();
	
	public void setJungle(int value);
	
}
