package states;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.Point;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import elements.Camera;
import elements.Map;
import elements.Player;
import main.Launcher;
import objects.MapObjectHandler;
import rendering.Renderer;
import rendering.Texture;
import window.Window;

public class Test implements State {

	public Camera camera;
	
	private Map map;
	private Player player;
	
	private MapObjectHandler mObjHandler;
	
	Point cursor;
	
	int mouse = 0;
	
	
	@Override
	public void render(Renderer r) {
		map.render(camera, r);
		mObjHandler.Render(r, camera);
		player.render(r);
		
	}

	
	
	@Override
	public void init() {
		map = new Map();
        map.loadMapFile("DefaultResources/Files/test-map.map", 30, 480);
        player = new Player(Texture.loadTexture("DefaultResources/Images/GPlayer-Sheet.png"));
        camera = new Camera(0, 0);
        
        player.inGameX = 1920/2;
        player.inGameY = 1080/2;
        
        mObjHandler = new MapObjectHandler();
        
	}

	@Override
	public void exit() {

	}

	@Override
	public void input(Window window) {
		if (window.isKeyPressed(GLFW_KEY_ESCAPE)) {
            Launcher.g.shouldExit = true;
        }
		
		mouse = window.isMouseDown(GLFW_MOUSE_BUTTON_1) + window.isMouseDown(GLFW_MOUSE_BUTTON_2)*2;
		
		
		player.input(window);
		
		cursor = getCursor(window.id);
		
	}

	@Override
	public void tick() {
		player.tick(camera, map);
		
		
		
		if (camera.moving) {
			camera.move();
			return;
		}
		mObjHandler.tick(cursor, camera, mouse);
		
		
		
		
		if (player.x  >= 1920 - player.t.getWidth() +1) {
			camera.moveRight();
			player.velX = 8;
		} else if (player.x  <= 0 - 1) {
			camera.moveLeft();
			player.velX = -8;
		} else if (player.y  >= 1080 - player.t.getHeight() + 1) {
			camera.moveUp();
			player.velY = 10;
		} else if (player.y  <= 0 - 1) {
			camera.moveDown();
			player.velY = -10;
		}
	}

	public Point getCursor(long windowID) {
		Point point = new Point();
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(windowID, xBuffer, yBuffer);
		double x = xBuffer.get(0);
		double y = yBuffer.get(0);

		y = -y + 1080 - 1;

		point.x = (int) x;
		point.y = (int) y;
		return point;

	}
	
}
