package states;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import elements.Camera;
import elements.Map;
import elements.Player;
import main.Launcher;
import rendering.Renderer;
import rendering.Texture;
import window.Window;

public class Test implements State {

	public Camera camera;
	
	@Override
	public void render(Renderer r) {
		map.render(camera, r);
		player.render(r);
	}

	private Map map;
	private Player player;
	
	
	
	@Override
	public void init() {
		map = new Map();
        map.loadMapFile("DefaultResources/Files/test-map.map", 30, 30);
        player = new Player(Texture.loadTexture("DefaultResources/Images/backflip.png"));
        camera = new Camera(0, 0);
        
        player.inGameX = 1920/2;
        player.inGameY = 1080/2;
	}

	@Override
	public void exit() {

	}

	@Override
	public void input(Window window) {
		if (window.isKeyPressed(GLFW_KEY_ESCAPE)) {
            Launcher.g.shouldExit = true;
        }
		
		player.input(window);
	}

	@Override
	public void tick() {
		player.tick(camera);
		if (camera.moving) {
			camera.move();
			return;
		}
		
		
		if (player.x  >= 1920 - player.t.getWidth()) {
			camera.moveRight();
			player.velX = 8;
		} else if (player.x  <= 0) {
			camera.moveLeft();
			player.velX = -8;
		} else if (player.y  >= 1080 - player.t.getHeight()) {
			camera.moveUp();
			player.velY = 10;
		} else if (player.y  <= 0) {
			camera.moveDown();
			player.velY = -10;
		}
	}

}
