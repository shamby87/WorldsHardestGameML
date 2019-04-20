package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy extends GameObject{
	
	boolean rend;

	public Enemy(int x, int y, int w, int h, ID id, boolean doRender) {
		super(x, y, w, h, id);
		startX = x;
		startY = y;
		if(x < 300) {
			velX = 5;
		}else {
			velX = -5;
		}
		velY = 0;
		this.rend = doRender;
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		for(int i = 0; i < Game.handler.object.size(); i++) {
			GameObject tempObject = Game.handler.object.get(i);
			
			if(tempObject.getID() == ID.Wall) {
				if(getBounds().intersects(tempObject.getBounds())) {
					velX *= -1;
				}
			}
		}

	}

	@Override
	public void render(Graphics g) {
		if(this.rend) {
			g.setColor(Color.blue);
			g.fillOval(x, y, w, h);
		}
	}
	
	public void resetEnemy() {
		
	}

}
