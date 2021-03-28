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

	ArrayList<Item> items = new ArrayList<Item>();

	private Player p;
	
	int selectedItemPosition = -1;

	private GLFWKeyCallback keyCallback = new GLFWKeyCallback() {

		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			if (key == GLFW.GLFW_KEY_E && action == GLFW.GLFW_PRESS) {
				toggle = !toggle;
			}
		}
	};

	private boolean toggle;

	private int mouseButtons;

	public Inventory(Window w) {
		GLFW.glfwSetKeyCallback(w.getID(), keyCallback);

		init();
	}

	public void init() {

	}

	public void input(Window w, Point mousePos, int mouseButtons) {
		this.mousePos = mousePos;
		this.mouseButtons = mouseButtons;

	}

	public void addItem(Item i) {
		i.contained = true;
		i.xVel = 0;
		i.yVel = 0;
		items.add(i);
		items.get(items.size() - 1).rect.setBounds(p.x + 16 * (items.size() - 1), p.y + 32, 16, 16);

	}

	public void tick(Player parent) {
		p = parent;

		if (toggle) {
			for (int i = 0; i < items.size(); i++) {
				items.get(i).rect.setBounds(p.x + 16 * i, p.y + 32, 16, 16);
				if (items.get(i).tick(parent, mousePos, mouseButtons) == 1) {
					selectedItemPosition = i;
				}

			}
		}
	}

	public void render(Renderer r) {
		if (toggle) {
//			r.begin();
//
//			GL30.glBindTexture(GL30.GL_TEXTURE_2D, 1);
//			r.drawTextureRegion(p.x, p.y + 32, p.x + (16 * items.size()), p.y + 48, 0, 0, 1, 1,
//					new Color(209, 179, 255), p.inGameX, p.inGameY, 0);
//
//			r.end();

			for (int i = 0; i < items.size(); i++) {
				Item item = items.get(i);

				r.begin();

				GL30.glBindTexture(GL30.GL_TEXTURE_2D, 1);

				if (i == selectedItemPosition) {
					r.drawTextureRegion(p.x + 16 * i, p.y + 32, p.x + (16 * (i + 1)), p.y + 48, 0, 0, 1, 1,
							new Color(29, 19, 255), p.inGameX, p.inGameY, 0);
				} else {
					r.drawTextureRegion(p.x + 16 * i, p.y + 32, p.x + (16 * (i + 1)), p.y + 48, 0, 0, 1, 1,
							new Color(217, 100, 1), p.inGameX, p.inGameY, 0);
				}
				r.end();

				r.begin();
				item.t.bind();
				r.drawTexture(item.t, p.x + (16 * i), p.y + 32, p.inGameX, p.inGameY, 0);
				r.end();
			}
		}
	}
}
