package rendering;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
//import static org.lwjgl.opengl.GL20.GL_DEPTH_TEST;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import fonts.Font;
import matrixes.Matrix4f;
import matrixes.Vector2f;

public class Renderer {

	private static VertexArray vao;
	private static VertexBufferObject vbo;
	private static ShaderProgram program;

	private static FloatBuffer vertices;
	private static int numVertices;
	private static boolean drawing;

	public void init(String fragLocation, String vertexLocation) {
		setupShaderProgram(fragLocation, vertexLocation);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		glEnable(GL_DEPTH_TEST);

		System.out.println("Renderer Successfully Initiated");
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void begin() {
		if (drawing) {
			throw new IllegalStateException("Renderer is already drawing!");
		}
		drawing = true;
		numVertices = 0;
	}

	public void end() {
		if (!drawing) {
			throw new IllegalStateException("Renderer isn't drawing!");
		}
		drawing = false;
		flush();
	}

	public static void flush() {
		if (numVertices > 0) {
			vertices.flip();

			if (vao != null) {
				vao.bind();
			} else {
				vbo.bind(GL_ARRAY_BUFFER);
				specifyVertexAttributes();
			}
			program.use();

			/* Upload the new vertex data */
			vbo.bind(GL_ARRAY_BUFFER);
			vbo.uploadSubData(GL_ARRAY_BUFFER, 0, vertices);

			/* Draw batch */
			glDrawArrays(GL_TRIANGLES, 0, numVertices);

			/* Clear vertex data for next batch */
			vertices.clear();
			numVertices = 0;
		}
	}

	public int getTextWidth(CharSequence text, Font font) {
		return font.getWidth(text);
	}

	public int getTextHeight(CharSequence text, Font font) {
		return font.getHeight(text);
	}

	public void drawText(Font font, CharSequence text, float x, float y) {
		font.drawText(this, text, x, y);
	}

	public void drawText(Font font, CharSequence text, float x, float y, Color c) {
		font.drawText(this, text, x, y, c);
	}

	public void drawTexture(Texture texture, float x, float y, float xIG, float yIG) {

		drawTexture(texture, x, y, new Color(1, 1, 1), xIG, yIG, 0);

	}
	
	public void drawTexture(Texture texture, float x, float y, float xIG, float yIG, int jungle) {

		drawTexture(texture, x, y, new Color(1, 1, 1), xIG, yIG, jungle);

	}

	public void drawColouredTexture(Texture texture, float x, float y, Color c, float xIG, float yIG, int jungle) {
		drawTexture(texture, x, y, c, xIG, yIG, jungle);
	}

	public void drawTexture(Texture texture, float x, float y, Color c, float xIG, float yIG, int jungle) {
		/* Vertex positions */
		float x1 = x;
		float y1 = y;
		float x2 = x1 + texture.getWidth();
		float y2 = y1 + texture.getHeight();

		/* Texture coordinates */
		float s1 = 0f;
		float t1 = 0f;
		float s2 = 1f;
		float t2 = 1f;

		drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, c, xIG, yIG, jungle); /* imp */
	}

	public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth,
			float regHeight, float xIG, float yIG, int jungle) {
		drawTextureRegion(texture, x, y, regX, regY, regWidth, regHeight, new Color(1, 1, 1), xIG, yIG, jungle);
	}

	public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth,
			float regHeight, Color c, float xIG, float yIG, int jungle) {
		/* Vertex positions */
		float x1 = x;
		float y1 = y;
		float x2 = x + regWidth;
		float y2 = y + regHeight;

		
		
		/* Texture coordinates */
		float s1 = regX / texture.getWidth();
		float t1 = regY / texture.getHeight();
		float s2 = (regX + regWidth) / texture.getWidth();
		float t2 = (regY + regHeight) / texture.getHeight();
		

		drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, c, xIG, yIG, jungle);
	}
	
	public void drawCustomTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth,
			float regHeight, Color c, float xIG, float yIG, int jungle) {
		/* Vertex positions */
		float x1 = x;
		float y1 = y;
		float x2 = x + regWidth;
		float y2 = y + regHeight;

		
		
		/* Texture coordinates */

		float s1 = 0f;
		float s2 = 1f;
		float t1 = 0f;
		float t2 = 1f;

		drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, c, xIG, yIG, jungle);
	}
	
	

	public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2, float xIG, float yIG, int jungle) {
		drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, new Color(1, 1, 1), xIG, yIG, jungle);
	}

	public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2, Color c, float xIG, float yIG, int jungle) {
		if (vertices.remaining() < 7 * 8) {
			/* We need more space in the buffer, so flush it */
			flush();
		}

		float r = c.getRed();
		float g = c.getGreen();
		float b = c.getBlue();
		float a = c.getAlpha();

		vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1).put(xIG + s1 * 40).put(yIG + t1 * 40).put(jungle);
		vertices.put(x1).put(y2).put(r).put(g).put(b).put(a).put(s1).put(t2).put(xIG + s1 * 40).put(yIG + t2 * 40).put(jungle);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2).put(xIG + s2 * 40).put(yIG + t2 * 40).put(jungle);

		vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1).put(xIG + s1 * 40).put(yIG + t1 * 40).put(jungle);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2).put(xIG + s2 * 40).put(yIG + t2 * 40).put(jungle);
		vertices.put(x2).put(y1).put(r).put(g).put(b).put(a).put(s2).put(t1).put(xIG + s2 * 40).put(yIG + t1 * 40).put(jungle);

		numVertices += 6;
	}

	public void drawRotatedTexture(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2, Color c, float xIG, float yIG, int jungle, int degrees, Vector2f center) {
		
		
		
		Vector2f x1y1 = new Vector2f(x1, y1);
		Vector2f x1y2 = new Vector2f(x1, y2);
		Vector2f x2y1 = new Vector2f(x2, y1);
		Vector2f x2y2 = new Vector2f(x2, y2);
//		Vector2f center = new Vector2f(x1 + (x2 - x1)/2, y1 + (y2 - y1)/2);
		
		float radius = (float) Math.abs(Math.sqrt((Math.pow((center.x - x1y1.x), 2) + (Math.pow((center.y - x1y1.y), 2)))));
		
		float sideX = (float) (radius * Math.cos(Math.toRadians(degrees - 135)));
		float sideY = (float) (radius * Math.sin(Math.toRadians(degrees - 135)));
		
		x1y1 = new Vector2f(center.x + sideX, center.y + sideY);
		
		
		radius = (float) Math.abs(Math.sqrt((Math.pow((center.x - x1y2.x), 2) + (Math.pow((x1y2.y - center.y), 2)))));
		
		sideX = (float) (radius * Math.cos(Math.toRadians(degrees - 225)));
		sideY = (float) (radius * Math.sin(Math.toRadians(degrees - 225)));
		
		x1y2 = new Vector2f(center.x + sideX, center.y + sideY);
		
		
		radius = (float) Math.abs(Math.sqrt((Math.pow((x2y1.x - center.x), 2) + (Math.pow((center.y - x2y1.y), 2)))));
		
		sideX = (float) (radius * Math.cos(Math.toRadians(degrees - 45)));
		sideY = (float) (radius * Math.sin(Math.toRadians(degrees - 45)));
		
		x2y1 = new Vector2f(center.x + sideX, center.y + sideY);
		
		
		radius = (float) Math.abs(Math.sqrt((Math.pow((x2y2.x - center.x), 2) + (Math.pow((x2y2.y - center.y), 2)))));
		
		sideX = (float) (radius * Math.cos(Math.toRadians(degrees - 315)));
		sideY = (float) (radius * Math.sin(Math.toRadians(degrees - 315)));
		
		x2y2 = new Vector2f(center.x + sideX, center.y + sideY);
		
		if (vertices.remaining() < 7 * 8) {
			/* We need more space in the buffer, so flush it */
			flush();
		}

		float r = c.getRed();
		float g = c.getGreen();
		float b = c.getBlue();
		float a = c.getAlpha();
		
		vertices.put(x1y1.x).put(x1y1.y).put(r).put(g).put(b).put(a).put(s1).put(t1).put(xIG + s1 * 40).put(yIG + t1 * 40).put(jungle);
		vertices.put(x1y2.x).put(x1y2.y).put(r).put(g).put(b).put(a).put(s1).put(t2).put(xIG + s1 * 40).put(yIG + t2 * 40).put(jungle);
		vertices.put(x2y2.x).put(x2y2.y).put(r).put(g).put(b).put(a).put(s2).put(t2).put(xIG + s2 * 40).put(yIG + t2 * 40).put(jungle);

		vertices.put(x1y1.x).put(x1y1.y).put(r).put(g).put(b).put(a).put(s1).put(t1).put(xIG + s1 * 40).put(yIG + t1 * 40).put(jungle);
		vertices.put(x2y2.x).put(x2y2.y).put(r).put(g).put(b).put(a).put(s2).put(t2).put(xIG + s2 * 40).put(yIG + t2 * 40).put(jungle);
		vertices.put(x2y1.x).put(x2y1.y).put(r).put(g).put(b).put(a).put(s2).put(t1).put(xIG + s2 * 40).put(yIG + t1 * 40).put(jungle);

		numVertices += 6;
		
		
	}
	
	
	public static void dispose() {
		MemoryUtil.memFree(vertices);

		if (vao != null) {
			vao.delete();
		}
		vbo.delete();
		program.delete();

	}

	private static void setupShaderProgram(String fragLocation, String vertexLocation) {

		vao = new VertexArray();
		vao.bind();

		/* Generate Vertex Buffer Object */
		vbo = new VertexBufferObject();
		vbo.bind(GL_ARRAY_BUFFER);

		/* Create FloatBuffer */
		vertices = MemoryUtil.memAllocFloat(4096);

		/* Upload null data to allocate storage for the VBO */
		long size = vertices.capacity() * Float.BYTES;
		vbo.uploadData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);

		/* Initialize variables */
		numVertices = 0;
		drawing = false;

		/* Load shaders */
		Shader vertexShader, fragmentShader;

		vertexShader = Shader.loadShader(GL_VERTEX_SHADER, vertexLocation);
		fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, fragLocation);

		/* Create shader program */
		program = new ShaderProgram();
		program.attachShader(vertexShader);
		program.attachShader(fragmentShader);

		program.bindFragmentDataLocation(0, "fragColor");

		program.link();
		program.use();

		/* Delete linked shaders */
		vertexShader.delete();
		fragmentShader.delete();

		/* Get width and height of framebuffer */
		long window = GLFW.glfwGetCurrentContext();
		int width, height;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer widthBuffer = stack.mallocInt(1);
			IntBuffer heightBuffer = stack.mallocInt(1);
			GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
			width = widthBuffer.get();
			height = heightBuffer.get();
		}

		/* Specify Vertex Pointers */
		specifyVertexAttributes();

		

		/* Set texture uniform */
		int uniTex = program.getUniformLocation("texImage");
		program.setUniform(uniTex, 0);

		/* Set model matrix to identity matrix */
		Matrix4f model = new Matrix4f();
		int uniModel = program.getUniformLocation("model");
		program.setUniform(uniModel, model);

		/* Set view matrix to identity matrix */
		Matrix4f view = new Matrix4f();
		int uniView = program.getUniformLocation("view");
		program.setUniform(uniView, view);

		/* Set projection matrix to an orthographic projection */
//		Matrix4f projection = Matrix4f.orthographic(-width / 2, width / 2, -height / 2, height / 2, -1f, 1f);
		Matrix4f projection = Matrix4f.orthographic(0, width, 0, height, -1f, 1f);
		int uniProjection = program.getUniformLocation("projection");
		program.setUniform(uniProjection, projection);

	}

	private static void specifyVertexAttributes() {
		/* Specify Vertex Pointer */
		int posAttrib = program.getAttributeLocation("position");
		program.enableVertexAttribute(posAttrib);
		program.pointVertexAttribute(posAttrib, 2, 11 * Float.BYTES, 0);

		/* Specify Color Pointer */
		int colAttrib = program.getAttributeLocation("color");
		program.enableVertexAttribute(colAttrib);
		program.pointVertexAttribute(colAttrib, 4, 11 * Float.BYTES, 2 * Float.BYTES);

		/* Specify Texture Pointer */
		int texAttrib = program.getAttributeLocation("texcoord");
		program.enableVertexAttribute(texAttrib);
		program.pointVertexAttribute(texAttrib, 2, 11 * Float.BYTES, 6 * Float.BYTES);
		
		int posigAttrib = program.getAttributeLocation("posInGame");
		program.enableVertexAttribute(posigAttrib);
		program.pointVertexAttribute(posigAttrib, 2, 11 * Float.BYTES, 8 * Float.BYTES);
		
		int jungleAttrib = program.getAttributeLocation("jungle");
		program.enableVertexAttribute(jungleAttrib);
		program.pointVertexAttribute(jungleAttrib, 1, 11* Float.BYTES, 10 * Float.BYTES);

	}

	
}
