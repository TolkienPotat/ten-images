package elements;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL30;

import rendering.Renderer;
import window.Window;

public class Inventory {

	Point mousePos;

	ArrayList items = new ArrayList();
	
	private Player p;
	
	private GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
		
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			if (key == GLFW.GLFW_KEY_E && action == GLFW.GLFW_PRESS) {
				toggle = !toggle;
			}
		}
	};
	
	private boolean toggle;
	
	public Inventory(Window w) {
		GLFW.glfwSetKeyCallback(w.getID(), keyCallback);
	}

	public void input(Window w, Point mousePos) {
		this.mousePos = mousePos;
		
		
	}
	
	public void addItem(Item i) {
		
	}

	public void tick(Player parent) {
		p = parent;

	}

	public void render(Renderer r) {
		if (toggle) {
			r.begin();

			GL30.glBindTexture(GL30.GL_TEXTURE_2D, 1);
			r.drawTextureRegion(p.x + 32, p.y + 32, p.x + 128, p.y + 64, 0, 0, 1, 1, new Color(207, 211, 193), p.inGameX,
					p.inGameY, 0);

			r.end();
		}
	}
}
