package objects;

import java.awt.Point;

import elements.Camera;
import rendering.Renderer;

public class MapObjectHandler {

	public MapObjectChunk[][] mapObjectChunks;

	public MapObjectHandler() {

		mapObjectChunks = new MapObjectChunk[1][2];
		mapObjectChunks[0][0] = new MapObjectChunk(0, 0);
		mapObjectChunks[0][1] = new MapObjectChunk(0, 1080);

	}

	public void tick(Point cursorPos, Camera c, int mouse) {
		for (int i = 0; i < mapObjectChunks.length; i++) {
			for (int j = 0; j < mapObjectChunks[0].length; j++) {
				if (mapObjectChunks[i][j] == null) {
					continue;
				}
				if (mapObjectChunks[i][j].x == c.x && mapObjectChunks[i][j].y == c.y) {
					cursorPos.x += mapObjectChunks[i][j].x;
					cursorPos.y += mapObjectChunks[i][j].y;
					mapObjectChunks[i][j].tick(cursorPos, mouse);
				}
			}
		}

	}

	public void Render(Renderer r, Camera c) {

		for (int i = 0; i < mapObjectChunks.length; i++) {
			for (int j = 0; j < mapObjectChunks[0].length; j++) {
				mapObjectChunks[i][j].render(r, c);
			}
		}

	}
	
	public void add(MapObject m, int x, int y) {
		
		mapObjectChunks[Math.floorDiv(x, 1920)][Math.floorDiv(y, 1080)].add(m);
		
	}
	
}
