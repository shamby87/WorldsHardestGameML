package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends GameObject{

	public Wall(int x, int y, int w, int h, ID id) {
		super(x, y, w, h, id);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, w, h);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}
	
	

}
