package particles;

import java.awt.Color;
import java.util.Random;

import elements.Camera;
import matrixes.Vector2f;
import rendering.Renderer;
import rendering.Texture;

public class Particle {

	int xVel, yVel;

	int inGameX, inGameY;
	int x, y;

	int rotation;

	int deltaRotation;

	int spread = 15;

	int particleSize = 8;

	int life;

	int jungle;

	float drawX, drawY;
	float drawX2, drawY2;

	Texture t;

	Random r;

	Color color = new Color(1, 1, 1);

	public Particle(Texture t, int x, int y, int jungle) {

		r = new Random();

		xVel = spread - r.nextInt(spread * 2);
		yVel = spread - r.nextInt(spread * 2);
		rotation = r.nextInt(360);
		deltaRotation = 30 - r.nextInt(60);
		life = r.nextInt(300) + 30;

		drawX = (float) r.nextInt(t.getWidth()) / t.getWidth();
		drawY = (float) r.nextInt(t.getHeight()) / t.getHeight();

		drawX2 = (float) drawX + 1 / t.getWidth();
		drawY2 = (float) drawY + 1 / t.getHeight();

		inGameX = x;
		inGameY = y;

		this.t = t;

		this.jungle = jungle;

	}

	public void render(Renderer r, Camera c) {

		x = inGameX - c.x;
		y = inGameY - c.y;

		r.begin();
		t.bind();
		r.drawCentrallyRotatedTexture(x, y, x + particleSize, y + particleSize, drawX, drawY, drawX2, drawY2, color, inGameX,
				inGameY, jungle, rotation);
		r.end();
	}

	public int tick() {
		inGameX += xVel;
		inGameY += yVel;

		rotation += deltaRotation;

		if (xVel > 0)
			xVel--;
		if (xVel < 0)
			xVel++;
		if (yVel > 0)
			yVel--;
		if (yVel < 0)
			yVel++;

		if (deltaRotation > 0)
			deltaRotation--;
		if (deltaRotation < 0)
			deltaRotation++;

		if (life-- == 0) {
			return -1;
		}

		return 0;
	}
}
