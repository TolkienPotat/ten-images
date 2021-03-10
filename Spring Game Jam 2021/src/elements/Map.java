package elements;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import objects.MapObject;
import rendering.Renderer;
import rendering.Texture;

public class Map {

	public int tileSize;
	
	int xTiles, yTiles;

	public Texture[] texture;

	public Tile[][] tiles;
	
	int scale = 4;

	public int scaledTileSize;
	
	public int wallIDPos = 8;
	
	
	
	

	public Map() {

		texture = new Texture[16];
		setTextures();
		
		
		
	}

	public void loadMapFile(String filePath, int maxX, int maxY) {

		File file = new File(filePath);
		
		xTiles = maxX;
		yTiles = maxY;
		
		tiles = new Tile[maxX][maxY];
		
		try {

			Scanner sc = new Scanner(file);

			for (int l = 0; l < maxY; l++) {
				for (int m = 0; m < maxX; m++) {
					tiles[m][l] = new Tile();
					tiles[m][l].id = sc.nextInt();
					tiles[m][l].xInGame = m*tileSize*scale;
					tiles[m][l].yInGame = l*tileSize*scale;

				}

			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void loadMapString(String map, int maxX, int maxY) {

		xTiles = maxX;
		yTiles = maxY;
		
		tiles = new Tile[maxX][maxY];
		
		Scanner sc = new Scanner(map);

		for (int l = 0; l < maxY; l++) {
			for (int m = 0; m < maxX; m++) {
				tiles[m][l] = new Tile();
				tiles[m][l].id = sc.nextInt();
				tiles[m][l].xInGame = m*tileSize*scale;
				tiles[m][l].yInGame = l*tileSize*scale;
			}

		}
		sc.close();
		
	}

	public void render(Camera c, Renderer renderer) {
		for (int i = 0; i < xTiles; i++) {
			for (int j = 0; j < yTiles; j++) {
				
				tiles[i][j].x = tiles[i][j].xInGame - (c.x);
				tiles[i][j].y = tiles[i][j].yInGame - (c.y);
				
				if ((tiles[i][j].x > 3840 || tiles[i][j].x < -1920) || (tiles[i][j].y > 2160 || tiles[i][j].y < -1080)) {
					continue;
				}
				
				
				tiles[i][j].r.setBounds(tiles[i][j].xInGame, tiles[i][j].yInGame, tileSize*scale, tileSize*scale);
				
				renderer.begin();
				texture[tiles[i][j].id].bind();

				renderer.drawCustomTextureRegion(texture[tiles[i][j].id], tiles[i][j].x, tiles[i][j].y, 0, 0, tileSize*scale, tileSize*scale, new Color(1, 1, 1), tiles[i][j].xInGame, tiles[i][j].yInGame);
				renderer.end();
				
				
				
				
				
			}
		}
	}

	public void tickTiles(Point mousePos, int mouseButtons) {
		for (int i = 0; i < xTiles; i++) {
			for (int j = 0; j < yTiles; j++) {
				
				
				
				if ((tiles[i][j].x > 3840 || tiles[i][j].x < -1920) || (tiles[i][j].y > 2160 || tiles[i][j].y < -1080)) {
					continue;
				}
				
				
				tiles[i][j].tick(mousePos, mouseButtons);
				
				
				
				
				
				
				
			}
		}
	}
	
	
	public void renderObjects(Camera c, Renderer renderer) {
		for (int i = xTiles-1; i >= 0; i--) {
			for (int j = yTiles-1; j >= 0; j--) {
				
				
				
				if ((tiles[i][j].x > 3840 || tiles[i][j].x < -1920) || (tiles[i][j].y > 2160 || tiles[i][j].y < -1080)) {
					continue;
				}
				
				
				tiles[i][j].render(renderer, c);
				
				
				
				
				
				
				
			}
		}
	}
	
	public void addObject(MapObject m, int x, int y) {
		
		try {
			tiles[Math.floorDiv(x, scaledTileSize)][Math.floorDiv(y, scaledTileSize)].object = m;
			tiles[Math.floorDiv(x, scaledTileSize)][Math.floorDiv(y, scaledTileSize)].object.setPos(Math.floorDiv(x, scaledTileSize)*scaledTileSize, Math.floorDiv(y, scaledTileSize)*scaledTileSize);
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		
	}
	

	public void setTextures() {

		texture[0] = Texture.loadTexture("DefaultResources/Images/test-tile-1.png");
		texture[8] = Texture.loadTexture("DefaultResources/Images/test-wall.png");
		
		tileSize = texture[0].getWidth();
		
		
		scaledTileSize = texture[0].getWidth() * scale;
		
	}

}
