package elements;

import java.awt.Rectangle;
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
	
	public Item TakeClosestItem(Rectangle r) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).rect.intersects(r)) {
				
				Item item = items.get(i);
				items.remove(i);
				return item;
				
			}
		}
		return null;
	}
	
}
