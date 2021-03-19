package elements;

import rendering.Texture;

public class Item {
	
	boolean canBePlaced, contained;
	
	Texture t;
	
	public Item(Texture texture) {
		t = texture;
	}
}
