package objects;

import java.awt.Point;
import java.util.ArrayList;

import elements.Camera;
import rendering.Renderer;

public class MapObjectChunk {

	public ArrayList<MapObject> mapObjects;
	
	public int x, y;
	
	public MapObjectChunk(int x, int y) {
		
		mapObjects = new ArrayList<MapObject>();
		mapObjects.add(new Tree(100, 100));
		
		this.x = x;
		this.y = y;
	}
	
	
	public void tick(Point p, int mouse) {
		
		for (int i = 0; i < mapObjects.size(); i++) {
			
			
			
			
			if (mapObjects.get(i).tick(p, mouse) == -1) {
				mapObjects.remove(i);
			}
		}
		
	}
	
	public void render(Renderer r, Camera c) {
		for (int i = 0; i < mapObjects.size(); i++) {
			
			
			
			mapObjects.get(i).render(r, c);
		}
	}
	
	
	public void add(MapObject m) {
		
		mapObjects.add(m);
		
	}
	
}
