package main;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

	protected int x, y, w, h, startX, startY;
	protected ID id;
	protected int velX, velY;
	
	public GameObject(int x, int y, int w, int h, ID id) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.id = id;
		this.startX = x;
		this.startY = y;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public void setX(int x) {
		this.x = x;
	}	
	public void setY(int y) {
		this.y = y;
	}
	public void setID(ID id) {
		this.id = id;
	}	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public ID getID() {
		return id;
	}
	public void setVelX(int velX) {
		this.velX = velX;
	}
	public void setVelY(int velY) {
		this.velY = velY;
	}
	public int getVelX() {
		return velX;
	}
	public int getVelY() {
		return velY;
	}
	public int getW() {
		return w;
	}
	public int getH() {
		return h;
	}

}
