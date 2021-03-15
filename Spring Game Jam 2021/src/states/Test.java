package states;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;

import java.awt.Point;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import elements.Camera;
import elements.Map;
import elements.Player;
import main.Launcher;
import objects.Jungle;
import rendering.Renderer;
import rendering.Texture;
import window.Window;

public class Test implements State {

	public Camera camera;
	
	private Map map;
	private Player player;
	
	final Point startPos = new Point(7000, 7000);
	
	Point cursor;
	
	Point cursorInGame;
	
	int mouse = 0;
	
	
	@Override
	public void render(Renderer r) {
		map.render(camera, r);

		map.renderObjects(camera, r);
		player.render(r);
		
	}

	
	
	@Override
	public void init() {
		map = new Map();
        map.loadMapFile("DefaultResources/Files/test-map.map", 200, 200);
        player = new Player(Texture.loadTexture("DefaultResources/Images/GPlayer-Sheet.png"));
        camera = new Camera(Math.floorDiv(startPos.x, 1920)*1920, Math.floorDiv(startPos.y, 1080)*1080);
        System.out.println(camera.x + " " + camera.y);
        player.inGameX = startPos.x;
        player.inGameY = startPos.y;
        cursorInGame = new Point();

        map.addObject(new Jungle(0, 0), 0, 0);
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
		cursorInGame.setLocation(cursor.x + camera.x, cursor.y + camera.y);
		
	}

	@Override
	public void tick() {
		player.tick(camera, map);
		
		
		
		if (camera.moving) {
			camera.move();
			return;
		}

		
		
		map.tickTiles(cursorInGame, mouse);
//		map.addObject(new Jungle(player.inGameX, player.inGameY), player.inGameX, player.inGameY);
		
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
