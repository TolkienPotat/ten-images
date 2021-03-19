package elements;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

import java.awt.Color;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;

import rendering.Renderer;
import window.Window;

public class Inventory {

	private double mouseX, mouseY;

	private Item[] items = new Item[10];

	private boolean toggle;

	private Player p;
	
	

	public void input(Window w) {
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(w.getID(), posX, posY);
		mouseX = posX.get(0);
		mouseY = posY.get(0);

		if (w.isKeyPressed(GLFW.GLFW_KEY_E)) {
			if (!toggle) {
				toggle = true;
			} else {
				toggle = false;
			}
		}
	}

	public void tick(Player parent) {
		p = parent;
		
		
	}

	public void render(Renderer r) {
		r.begin();
		
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, 1);
//		GL30.glDisable(GL30.GL_TEXTURE_2D);
		//test code
		r.drawTextureRegion(p.x+32, p.y+32, p.x+128, p.y+64, 0, 0, 1, 1, new Color(255, 0, 255), p.inGameX, p.inGameY, 0);
		
		r.end();
	}
}
