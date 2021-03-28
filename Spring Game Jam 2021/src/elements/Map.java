package elements;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import objects.Jungle;
import objects.MapObject;
import objects.Tree;
import particles.ParticleHandler;
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
	
	public ParticleHandler particle;
	
	public MapItemHandler mapItems;
	
	
	Random random;
	

	public Map() {

		texture = new Texture[16];
		setTextures();
		
		random = new Random();
		
		particle = new ParticleHandler();
		
		mapItems = new MapItemHandler();
		
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
					if (random.nextInt(10) == 0) tiles[m][l].object = new Tree(tiles[m][l].xInGame, tiles[m][l].yInGame);

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

	public void tick(Point mousePos, int mouseButtons, Player player) {
		tickTiles(mousePos, mouseButtons);
		tickMapItems(player, mousePos, mouseButtons);
	}
	
	public void render(Camera c, Renderer renderer) { 
		renderTiles(c, renderer);
		renderParticles(c, renderer);
		renderObjects(c, renderer);
		renderMapItems(c, renderer);
	}
	
	public void renderMapItems(Camera c, Renderer r) {
		mapItems.render(r, c);
	}
	
	public void renderTiles(Camera c, Renderer renderer) {
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
				
				renderer.drawCustomTextureRegion(texture[tiles[i][j].id], tiles[i][j].x, tiles[i][j].y, 0, 0, tileSize*scale, tileSize*scale, new Color(1, 1, 1), tiles[i][j].xInGame, tiles[i][j].yInGame, tiles[i][j].jungle);
				renderer.end();
				
				
				
			
				
			}
		}
	}

	public void renderParticles(Camera c, Renderer r) {
		particle.render(r, c);
	}
	
	
	public void tickTiles(Point mousePos, int mouseButtons) {
		for (int i = 0; i < xTiles; i++) {
			for (int j = 0; j < yTiles; j++) {
				
				int a, b, c, d;
				try {
					a = tiles[i+1][j].getJungle();
				} catch (ArrayIndexOutOfBoundsException e) {
					a = 0;
				}
				try {
					b = tiles[i-1][j].getJungle();
				} catch (ArrayIndexOutOfBoundsException e) {
					b = 0;
				}
				try {
					c = tiles[i][j+1].getJungle();
				} catch (ArrayIndexOutOfBoundsException e) {
					c = 0;
				}
				try {
					d = tiles[i][j-1].getJungle();
				} catch (ArrayIndexOutOfBoundsException e) {
					d = 0;
				}
				
				if (tiles[i][j].getJungle() == 0 && a + b + c + d >=3 && random.nextInt(850-a*50+b*50+c*50+d*50) == 1) {
					if (tiles[i][j].object == null) {
						tiles[i][j].object = new Jungle(i * scaledTileSize, j * scaledTileSize);
					} else {
						tiles[i][j].setJungle(1);
					}
					
				}

				
				
				int t = tiles[i][j].tick(mousePos, mouseButtons);
				
				if (t == 1 && random.nextInt(5) == 0) {
					
					particle.add(tiles[i][j].getObjectTexture(), i*scaledTileSize, j*scaledTileSize, tiles[i][j].getObjectTexture().getWidth() * MapObject.scale, tiles[i][j].getObjectTexture().getHeight() * MapObject.scale, tiles[i][j].getJungle(), 1);
					mouseButtons = 0;
				} else if (t == -1) {
					
					particle.add(tiles[i][j].getObjectTexture(), i*scaledTileSize, j*scaledTileSize, tiles[i][j].getObjectTexture().getWidth() * MapObject.scale, tiles[i][j].getObjectTexture().getHeight() * MapObject.scale, tiles[i][j].getJungle(), 50);
					mouseButtons = 0;
					
				}
				
				

				
				
			}
		}
		particle.tick();
	}
	
	public void tickMapItems(Player player, Point mousePos, int mouseButtons) {
		mapItems.tick(player, mousePos, mouseButtons);
		mapItems.addItem(new TreeSeeds(6500, 6500));
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
	
	public MapObject getObject(int xInGame, int yInGame) {
		
		try {
			return tiles[Math.floorDiv(xInGame, scaledTileSize)][Math.floorDiv(yInGame, scaledTileSize)].object;
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
		
	}
	
	public Item pickUpItem(Rectangle r) {
		return mapItems.TakeClosestItem(r);
	}

}
