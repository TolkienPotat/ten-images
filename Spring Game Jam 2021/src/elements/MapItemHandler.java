package elements;

import java.awt.Point;
import java.util.ArrayList;

import rendering.Renderer;

public class MapItemHandler {

	
	ArrayList<Item> items;
	
	
	public MapItemHandler() {
		
		items = new ArrayList<Item>();
		
	}
	
	public void addItem(Item i) {
		
		items.add(i);
		
	}
	
	public void clearItems() {
		
		items.clear();
		
	}
	
	public Item removeAndReturn(int index) {
		
		Item i = items.get(index);
		items.remove(index);
		return i;
		
	}
	
	public void tick(Player player) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).tick(player);
		}
	}
	
	public void render(Renderer r, Camera c) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).render(r, c);
		}
	}
	
	
	
}
