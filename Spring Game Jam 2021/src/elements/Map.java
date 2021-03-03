package elements;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import rendering.Renderer;
import rendering.Texture;

public class Map {

	public int tileWidth, tileHeight;
	
	int xTiles, yTiles;

	public Texture[] texture;

	public Tile[][] tiles;
	
	int scale = 4;

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
					tiles[m][l].xInGame = l*tileHeight*scale;
					tiles[m][l].yInGame = m*tileHeight*scale;

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
				tiles[m][l].xInGame = l*tileHeight*scale;
				tiles[m][l].yInGame = m*tileHeight*scale;
			}

		}
		sc.close();
		
	}

	public void render(Camera c, Renderer renderer) {
		for (int i = 0; i < xTiles; i++) {
			for (int j = 0; j < yTiles; j++) {
				
				tiles[i][j].x = tiles[i][j].xInGame - (c.x);
				tiles[i][j].y = tiles[i][j].yInGame - (c.y);
				
				
				tiles[i][j].r.setBounds(tiles[i][j].xInGame, tiles[i][j].yInGame, tileWidth*scale, tileHeight*scale);
				
				renderer.begin();
				texture[tiles[i][j].id].bind();
//				renderer.drawTexture(texture[tiles[i][j].id],tiles[i][j].x,tiles[i][j].y, tiles[i][j].xInGame, tiles[i][j].yInGame);
				renderer.drawCustomTextureRegion(texture[tiles[i][j].id], tiles[i][j].x, tiles[i][j].y, 0, 0, tileWidth*scale, tileHeight*scale, new Color(1, 1, 1), tiles[i][j].xInGame, tiles[i][j].yInGame);
				
				renderer.end();
				
				
			}
		}
	}
	
	public void setTextures() {

		texture[0] = Texture.loadTexture("DefaultResources/Images/test-tile-1.png");
		
		tileWidth = texture[0].getWidth();
		tileHeight = texture[0].getHeight();
		
	}

}
