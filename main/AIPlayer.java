package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

public class AIPlayer extends GameObject{
	Handler handler;
	
	double fitness = 0;
	boolean isDead;
	boolean reachedGoal;
	boolean isBest;
	boolean deathByDot = false;
	int deathAtStep = 0;
	int moveCount = 0;
	int gen = 1;
	Brain brain = new Brain(Population.numberOfSteps);
	
	int startX;
	int startY;
	
	public AIPlayer(int x, int y, int w, int h, ID id, Handler handler) {
		super(x, y, w, h, id);
		this.handler = handler;
		startX = x;
		startY = y;
	}

	@Override
	public void tick() {
		if(!Game.humanPlayer) {
			move();
		}
		if(!this.isDead) {
			x += velX;
			y += velY;
		}
		
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
					isDead = true;
					deathByDot = true;
					deathAtStep = this.brain.brainStep;
				}
			}else if(tempObject.getID() == ID.Wall) {
				if(getBounds().intersects(tempObject.getBounds())) {
					if(tempObject.getW() == 3 && this.y + this.w >= tempObject.getY() && this.y <= tempObject.getY() + tempObject.getH()) {
						if(this.getVelX() > 0) {//Right side of Player
							//this.velX *= -1.1;
							//System.out.println("RIGHT");
							this.x = tempObject.getX() - this.w;
						}
						else if(this.getVelX() < 0) {//Left side of Player
							//this.velX *= -1.1;
							//System.out.println("LEFT");
							this.x = tempObject.getX() + 3;
						}
					}else if(tempObject.getH() == 3 && this.x + this.w >= tempObject.getX() && this.x <= tempObject.getX() + tempObject.getW()) {
						if(this.getVelY() > 0) {//Bottom of Player
							//this.velY *= -1.1;
							//System.out.println("BOTTOM");
							this.y = tempObject.getY() - this.w;
						}
						else if(this.getVelY() < 0) {//Top of Player
							//this.velY *= -1.1;
							//System.out.println("TOP");
							this. y = tempObject.getY() + 3;
						}
					}
				}
			}else if(tempObject.getID() == ID.Finish) {
				if(getBounds().intersects(tempObject.getBounds())) {
					Game.isWinner = true;
					this.reachedGoal = true;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(!isDead) {
			if(isBest) {
				g.setColor(Color.green);
			} else {
				g.setColor(Color.red);
			}
			g.fillRect(x, y, w, h);
			Graphics2D g2 = (Graphics2D) g;
			Stroke oldStroke = g2.getStroke();
			g2.setColor(Color.decode("#8A0200"));
			g2.setStroke(new BasicStroke(3));
			g2.drawRect(this.x, this.y, this.w, this.h);
			g2.setStroke(oldStroke);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}
	
	private void move() {
		if (this.moveCount == 0) {//move in the direction for 6 frames
	        if (this.brain.directions.length > this.brain.brainStep) {
	        	this.setVelX(this.brain.directions[this.brain.brainStep].x);
	        	this.setVelY(this.brain.directions[this.brain.brainStep].y);
	        	this.brain.brainStep++;
	        } else {//if at the end of the directions array then the player is dead
	        	this.isDead = true;
	        }
	        this.moveCount = 6;
		} else {
			this.moveCount--;
	      }
	}
	
	public void calculateFitness() {
		double dist = 0.0;
		if (this.reachedGoal) {
			this.fitness = 1.0/16.0 + 10000.0/(this.brain.brainStep * this.brain.brainStep);
		} else {
		    dist = Math.sqrt(Math.pow(Game.goalX - this.x, 2) + Math.pow(Game.goalY - this.y, 2));
		    if (this.deathByDot) {
		    	dist *= 1.1;
		    }
		    this.fitness = 1.0/(dist * dist);
		}
		this.fitness*=this.fitness;
	}
	
	public AIPlayer makeBaby() {
		AIPlayer baby = new AIPlayer(this.x, this.y, this.w, this.h, this.id, this.handler);
		baby.brain = this.brain.clone();//babies have the same brain as their parents
		baby.deathByDot = this.deathByDot;
		baby.deathAtStep = this.deathAtStep;
		baby.gen = this.gen;
		baby.isBest = this.isBest;
		return baby;
	}
}
