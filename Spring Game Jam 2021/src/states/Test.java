package states;

import elements.Map;
import elements.Player;
import main.Launcher;
import rendering.Renderer;
import rendering.Texture;
import window.Window;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class Test implements State {

	@Override
	public void render(Renderer r) {
		// TODO Auto-generated method stub
		map.render(player, r);
		player.render(r);
	}

	private Map map;
	private Player player;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		map = new Map();
        map.loadMapFile("DefaultResources/Files/test-map.map", 30, 30);
        player = new Player(Texture.loadTexture("DefaultResources/Images/Pllayer.png"));
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void input(Window window) {
		// TODO Auto-generated method stub
		if (window.isKeyPressed(GLFW_KEY_ESCAPE)) {
            Launcher.g.shouldExit = true;
        }
		
		player.input(window);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		player.tick();
	}

}
