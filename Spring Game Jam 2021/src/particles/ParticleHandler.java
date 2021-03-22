package particles;

import java.util.ArrayList;
import java.util.Random;

import elements.Camera;
import rendering.Renderer;
import rendering.Texture;

public class ParticleHandler {

	ArrayList<Particle> particles;
	
	Random r;

	public ParticleHandler() {

		particles = new ArrayList<Particle>();
		
		r = new Random();

	}

	public void add(Texture t, int x, int y, int jungle, int count) {
		for (int i = 0; i < count; i++) {
			particles.add(new Particle(t, x, y, jungle));
		}
	}
	
	public void add(Texture t, int x, int y, int width, int height, int jungle, int count) {
		for (int i = 0; i < count; i++) {
			
			
			particles.add(new Particle(t, x + r.nextInt(width), y + r.nextInt(height), jungle));
		}
	}

	public void tick() {
		for (int i = 0; i < particles.size(); i++) {
			
			if (particles.get(i).tick() == -1) particles.remove(i);
			
		}
	}
	
	public void render(Renderer r, Camera c) {

		for (int i = 0; i < particles.size(); i++) {
			
			particles.get(i).render(r, c);
			
		}
	}
	
	public int getSize() {
		return particles.size();
	}

}
