package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{
	
	public static boolean humanPlayer = false;
	
	private static final long serialVersionUID = -381014417800033391L;

	public static final int WIDTH = 804, HEIGHT = 628;
	private Thread thread;
	private boolean running = false;
	public static boolean isWinner = false;
	
	public static Handler handler;
	
	public final int TILE_SIZE = 40;
	public Tile[][] tiles;
	
	public static AIPlayer[] population;
	public Population realPop;
	public static boolean popReady = false;
	public static double mutateRate = 0.01;
	public static int increaseBy = 5;
	
	public static int goalX;
	public static int goalY;
	
	public Game() {
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));
		
		tiles = new Tile[(int) (WIDTH/TILE_SIZE)][(int) (HEIGHT/TILE_SIZE)];
		
		new Window(WIDTH, HEIGHT, "Worlds Hardest Game", this);
				
		population = new AIPlayer[Population.popSize];
		
		createTiles();
		createLevel();
		
		realPop = new Population(population);
	}
	
	private void createTiles() {
		Color color;
		for(int i = 0; i < WIDTH/TILE_SIZE; i++) {
			for(int j = 0; j < HEIGHT/TILE_SIZE; j++) {
				if(i % 2 == 0) {
					if(j % 2 == 0) {
						color = Color.decode("#F8F6FF");
					} else {
						color = Color.decode("#E0DAFE");
					}
				} else {
					if(j % 2 == 0) {
						color = Color.decode("#E0DAFE");
					} else {
						color = Color.decode("#F8F6FF");
					}
				}
				tiles[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, ID.Tile, color);
				handler.addObject(tiles[i][j]);
			}
		}
	}
	
	private void createLevel() {
		setStart();
		setFinish();
		setWalls();
		
		for(int i = 0; i < WIDTH/TILE_SIZE; i++) {
			for(int j = 0; j < HEIGHT/TILE_SIZE; j++) {
				tiles[i][j].makeTile();
			}
		}
		tiles[4][10].playerStart = true;
		tiles[4][10].makeTile();
		//Fill in corners
		handler.addObject(new Wall(277, 397, 3, 3, ID.Wall));
		handler.addObject(new Wall(520, 200, 3, 3, ID.Wall));
		handler.addObject(new Wall(557, 197, 3, 3, ID.Wall));
		handler.addObject(new Wall(600, 197, 3, 3, ID.Wall));
		
	}
	private void setStart() {
		for(int i = 2; i < 5; i++) {
			for(int j = 4; j < 11; j++) {
				tiles[i][j].isStart = true;
			}
		}
	}
	private void setFinish() {
		for(int i = 15; i < 18; i++) {
			for(int j = 4; j < 11; j++) {
				tiles[i][j].isFinish = true;
				if(i == 15 && j == 4) {
					goalX = tiles[i][j].x;
					goalY = tiles[i][j].y;
				}
			}
		}
	}
	private void setWalls() {
		tiles[2][4].isLeftWall = true;
		tiles[2][4].isTopWall = true;
		tiles[2][5].isLeftWall = true;
		tiles[2][6].isLeftWall = true;
		tiles[2][7].isLeftWall = true;
		tiles[2][8].isLeftWall = true;
		tiles[2][9].isLeftWall = true;
		tiles[2][10].isLeftWall = true;
		tiles[2][10].isBottomWall = true;
		
		tiles[3][4].isTopWall = true;
		tiles[3][10].isBottomWall = true;
		
		tiles[4][4].isRightWall = true;
		tiles[4][4].isTopWall = true;
		tiles[4][5].isRightWall = true;
		tiles[4][6].isRightWall = true;
		tiles[4][7].isRightWall = true;
		tiles[4][8].isRightWall = true;
		tiles[4][9].isRightWall = true;
		tiles[4][10].isBottomWall = true;
		if(!Game.humanPlayer) {
			tiles[4][9].isBottomWall = true;
			tiles[4][10].isLeftWall = true;
		}
		
		tiles[5][9].isBottomWall = true;
		tiles[5][9].enemyStart = true;
		tiles[5][9].enemyRender = false;
		tiles[5][10].isBottomWall = true;
		
		tiles[6][10].isBottomWall = true;
		tiles[6][10].isRightWall = true;
		tiles[6][9].isLeftWall = true;
		tiles[6][8].isLeftWall = true;
		tiles[6][8].enemyStart = true;
		tiles[6][7].isLeftWall = true;
		tiles[6][6].isLeftWall = true;
		tiles[6][6].enemyStart = true;
		tiles[6][5].isLeftWall = true;
		tiles[6][5].isTopWall = true;
		
		tiles[7][5].isTopWall = true;
		tiles[7][9].isBottomWall = true;
		
		tiles[8][5].isTopWall = true;
		tiles[8][9].isBottomWall = true;
		
		tiles[9][5].isTopWall = true;
		tiles[9][9].isBottomWall = true;
		
		tiles[10][5].isTopWall = true;
		tiles[10][9].isBottomWall = true;
		
		tiles[11][5].isTopWall = true;
		tiles[11][9].isBottomWall = true;
		
		tiles[12][5].isTopWall = true;
		tiles[12][9].isBottomWall = true;
		
		tiles[13][4].isTopWall = true;
		tiles[13][4].isLeftWall = true;
		tiles[13][5].isRightWall = true;
		tiles[13][5].enemyStart = true;
		tiles[13][6].isRightWall = true;
		tiles[13][7].isRightWall = true;
		tiles[13][7].enemyStart = true;
		tiles[13][8].isRightWall = true;
		tiles[13][9].isRightWall = true;
		tiles[13][9].isBottomWall = true;
		tiles[13][9].enemyStart = true;
		
		tiles[14][4].isTopWall = true;
		tiles[14][4].isBottomWall = true;
		
		tiles[15][4].isTopWall = true;
		tiles[15][5].isLeftWall = true;
		tiles[15][6].isLeftWall = true;
		tiles[15][7].isLeftWall = true;
		tiles[15][8].isLeftWall = true;
		tiles[15][9].isLeftWall = true;
		tiles[15][10].isLeftWall = true;
		tiles[15][10].isBottomWall = true;
		
		tiles[16][4].isTopWall = true;
		tiles[16][10].isBottomWall = true;
		
		tiles[17][4].isTopWall = true;
		tiles[17][4].isRightWall = true;
		tiles[17][5].isRightWall = true;
		tiles[17][6].isRightWall = true;
		tiles[17][7].isRightWall = true;
		tiles[17][8].isRightWall = true;
		tiles[17][9].isRightWall = true;
		tiles[17][10].isRightWall = true;
		tiles[17][10].isBottomWall = true;
	}

	/*Adapted from RealTutsGML on YouTube:
	 * https://www.youtube.com/watch?v=1gir2R7G9ws*/
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	/*Adapted from RealTutsGML on YouTube:
	 * https://www.youtube.com/watch?v=1gir2R7G9ws*/
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*Adapted from RealTutsGML on YouTube:
	 * https://www.youtube.com/watch?v=1gir2R7G9ws*/
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns= 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now- lastTime) / ns;
			lastTime = now;
			while(delta >=1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
	}
	
	/*Adapted from RealTutsGML on YouTube:
	 * https://www.youtube.com/watch?v=1gir2R7G9ws*/
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.decode("#AAA5FF"));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
		
		if(popReady && realPop.allPlayersDead() && !humanPlayer) {
			for(int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				
				if(tempObject.getID() == ID.Enemy) {
					tempObject.x = tempObject.startX;
					tempObject.y = tempObject.startY;
				}
			}
			realPop.calculateFitness();
			realPop.naturalSelection();
			realPop.mutateBabies();
						
			if(realPop.gen % realPop.genStep == 0) {
				realPop.increaseMoves();
			}
			
			realPop.restartLevel();
			System.out.println(realPop.gen);
		}
		
		if(isWinner == true) {
			System.out.println("WINNER!");
			for(int i = 0; i < handler.object.size(); i++) {
				handler.object.get(i).velX = 0;
				handler.object.get(i).velY = 0;
			}
			
		}
	}
	
	public static int clamp(int var, int min, int max) {
		if(var >= max) {
			return var = max;
		}else if(var <= min) {
			return var = min;
		}else {
			return var;
		}
		
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
