package objects;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import elements.Camera;
import rendering.Renderer;
import rendering.Texture;

public class Jungle implements MapObject{

	int inGameX, inGameY;

	int x, y;

	int health = 120;
	
	Random random;
	
	int stage = 1;
	
	int maxStage = 3;
	
	public Texture[] texture;

	public Rectangle bounds;

	public Jungle(int x, int y) {
		texture = new Texture[8];
		inGameX = x;
		inGameY = y;
		setTextures();
		bounds = new Rectangle(x, y, texture[0].getWidth()*scale, texture[0].getHeight()*scale);
		
		random = new Random();

	}
	
	
	@Override
	public void render(Renderer r, Camera c) {
		
		x = inGameX - c.x;
		y = inGameY - c.y;

		if (x > 3840 || x < -1080 || y > 2160 || y < -1080) {
			return;
		}

		r.begin();
		texture[stage - 1].bind();
		r.drawCustomTextureRegion(texture[stage - 1], x, y, 0, 0, texture[stage - 1].getWidth() * scale, texture[stage - 1].getHeight() * scale,
				new Color(1, 1, 1), inGameX, inGameY, stage);
		r.end();
		
	}

	@Override
	public int tick(Point p, int mouse) {
		
		if (random.nextInt(600) == 4 && stage < maxStage) {
			stage++;
		}
		
		return 0;
	}

	@Override
	public void setPos(int x, int y) {
		
		inGameX = x;
		inGameY = y;
		
		bounds.setBounds(x, y, texture[0].getWidth()*scale, texture[0].getHeight()*scale);
		
	}
	
	public void setTextures() {
		
		texture[0] = Texture.loadTexture("DefaultResources/Images/jungle-0.png");
		texture[1] = Texture.loadTexture("DefaultResources/Images/jungle-1.png");
		texture[2] = Texture.loadTexture("DefaultResources/Images/jungle-2.png");
		
	}


	@Override
	public int getJungle() {
		return stage;
	}


	@Override
	public void setJungle(int value) {
		stage = value;
	}

}