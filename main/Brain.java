package main;

import java.util.Random;

public class Brain {
	
	Pair[] directions;
	public int brainStep = 0;
	
	public Brain(int moves) {
		directions = new Pair[moves];
		randomDirections(0, moves);
	}
	
	public void randomDirections(int firstMove, int finalMove) {
		Random r = new Random();
		for(int i = firstMove; i < finalMove; i++) {
			int randomNumber = r.nextInt(9);
		    switch(randomNumber) {
			    case 0:
			    	this.directions[i] = new Pair(1, 0);
			    	break;
			    case 1:
			    	this.directions[i] = new Pair(1, 1);
			    	break;
			    case 2:
			    	this.directions[i] = new Pair(1, 0);
			    	break;
			    case 3:
			    	this.directions[i] = new Pair(1, -1);
			    	break;
			    case 4:
			    	this.directions[i] = new Pair(0, -1);
			    	break;
			    case 5:
			    	this.directions[i] = new Pair(-1, -1);
			    	break;
			    case 6:
			    	this.directions[i] = new Pair(-1, 0);
			    	break;
			    case 7:
			    	this.directions[i] = new Pair(-1, 1);
			    	break;
			    case 8:
			    	this.directions[i] = new Pair(0, 0);
			    	break;
		    }
		}
	}

	public Brain clone() {
		Brain clone = new Brain(this.directions.length);
		for(int i = 0; i < this.directions.length; i++) {
			clone.directions[i] = this.directions[i];
		}
		return clone;
	}
	
	public void mutate(boolean died, int deathStep) {
		for(int i = 0; i < this.directions.length; i++) {
			double rand = Math.random();
			if(died && i < deathStep - 10) {
				rand = Math.random() * .15;
			}
			
			if(rand < Game.mutateRate) {
				randomDirections(i, i + 1);
			}
		}
	}
	
	public void increaseMoves() {
		Pair[] tempDir = this.directions;
		this.directions = new Pair[tempDir.length + Game.increaseBy];
		for(int i = 0; i < tempDir.length; i++) {
			this.directions[i] = tempDir[i];
		}
		this.randomDirections(tempDir.length, tempDir.length + Game.increaseBy);
	}
	
}
class Pair { 
	public int x, y;
	public Pair(int _x, int _y) {
		x = _x;
		y = _y;
	}
}
