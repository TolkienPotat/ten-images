package elements;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL30;

import rendering.Renderer;
import rendering.Texture;
import window.Window;

public class Inventory {

	Point mousePos;

	ArrayList<Item> items = new ArrayList<Item>();

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

		init();
	}

	public void init() {

	}

	public void input(Window w, Point mousePos) {
		this.mousePos = mousePos;

	}

	public void addItem(Item i) {
		if (!items.isEmpty()) {
			
			for (Item item : items) {

				if (item.getClass() == i.getClass()) {
					item.stack++;
				} else {
					items.add(i);
					break;
				}
			}
		} else items.add(i);
	}

	public void tick(Player parent) {
		p = parent;

	}

	public void render(Renderer r) {
		if (toggle) {
			r.begin();

			GL30.glBindTexture(GL30.GL_TEXTURE_2D, 1);
			r.drawTextureRegion(p.x, p.y + 32, p.x + (16 * items.size()), p.y + 48, 0, 0, 1, 1,
					new Color(209, 179, 255), p.inGameX, p.inGameY, 0);

			r.end();

			for (int i = 0; i < items.size(); i++) {
				Item item = items.get(i);

				r.begin();
				item.t.bind();
				r.drawTexture(item.t, p.x + (16 * i), p.y + 32, p.inGameX, p.inGameY);
				r.end();
			}
		}
	}
}
