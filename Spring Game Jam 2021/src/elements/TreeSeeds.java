package elements;

import rendering.Texture;

public class TreeSeeds extends Item{

	public TreeSeeds() {
		super(Texture.loadTexture("DefaultResources/Images/TreeSeeds.png"));
	}
	
	public TreeSeeds(int x, int y) {
		super(Texture.loadTexture("DefaultResources/Images/TreeSeeds.png"), x, y);
	}

}
