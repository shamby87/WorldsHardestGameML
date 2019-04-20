package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile extends GameObject {
	
	public Color color;
	public boolean isLeftWall = false, isRightWall = false, isTopWall = false, isBottomWall = false;
	public boolean isStart = false, isFinish = false;
	public boolean playerStart = false, enemyStart = false, enemyRender = true;

	public Tile(int x, int y, int w, int h, ID id, Color color) {
		super(x, y, w, h, id);
		this.color = color;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, w, h);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}
	
	public void makeTile() {
		if(isStart) {
			Game.handler.addObject(new Start(x, y, w, h, ID.Start));
		}
		if(isFinish) {
			Game.handler.addObject(new Finish(x, y, w, h, ID.Finish));
		}
		if(isLeftWall) {
			Game.handler.addObject(new Wall(x, y, 3, h, ID.Wall));
		}
		if(isRightWall) {
			Game.handler.addObject(new Wall(x + w-3, y, 3, h, ID.Wall));
		}
		if(isTopWall) {
			Game.handler.addObject(new Wall(x, y, w, 3, ID.Wall));
		}
		if(isBottomWall) {
			Game.handler.addObject(new Wall(x, y + h-3, w, 3, ID.Wall));
		}
		if(enemyStart) {
			if(enemyRender) {
				Game.handler.addObject(new Enemy(x + 10, y + 10, 20, 20, ID.Enemy, true));
			} else {
				Game.handler.addObject(new Enemy(x + 10, y + 10, 20, 20, ID.Enemy, false));
			}
		}
		if(playerStart) {
			if(Game.humanPlayer) {
				Game.handler.addObject(new Player(x + 6, y + 6, 28, 28, ID.Player, Game.handler));
			} else {
				for(int i = 0; i < Population.popSize; i++) {
					AIPlayer player = new AIPlayer(x + 6, y + 6, 28, 28, ID.Player, Game.handler);
					Game.handler.addObject(player);
					Game.population[i] = player;
				}
				Game.popReady = true;
			}
		}
	}
	
}
