package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject {

	Random r = new Random();
	Handler handler;
	
	int startX;
	int startY;
	
	public Player(int x, int y, int w, int h, ID id, Handler handler) {
		super(x, y, w, h, id);
		this.handler = handler;
		startX = x;
		startY = y;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		//x = Game.clamp(x, 78, 685);
		//y = Game.clamp(y, 163, 408);
		
		collision();
	}
	
	private void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Enemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
					this.velX = 0;
					this.velY = 0;
					this.x = startX;
					this.y = startY;
				}
			}else if(tempObject.getID() == ID.Wall) {
				if(getBounds().intersects(tempObject.getBounds())) {
					if(tempObject.getW() == 3 && this.y + this.w >= tempObject.getY() && this.y <= tempObject.getY() + tempObject.getH()) {
						if(this.getVelX() > 0) {//Right side of Player
							//this.velX *= -1.1;
							System.out.println("RIGHT");
							this.x = tempObject.getX() - this.w;
						}
						else if(this.getVelX() < 0) {//Left side of Player
							//this.velX *= -1.1;
							System.out.println("LEFT");
							this.x = tempObject.getX() + 3;
						}
					}else if(tempObject.getH() == 3 && this.x + this.w >= tempObject.getX() && this.x <= tempObject.getX() + tempObject.getW()) {
						if(this.getVelY() > 0) {//Bottom of Player
							//this.velY *= -1.1;
							System.out.println("BOTTOM");
							this.y = tempObject.getY() - this.w;
						}
						else if(this.getVelY() < 0) {//Top of Player
							//this.velY *= -1.1;
							System.out.println("TOP");
							this. y = tempObject.getY() + 3;
						}
					}
				}
			}else if(tempObject.getID() == ID.Finish) {
				if(getBounds().intersects(tempObject.getBounds())) {
					Game.isWinner = true;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, w, h);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}
	
}
