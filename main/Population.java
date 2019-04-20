package main;

import java.util.Random;

public class Population {
	
	public static int popSize = 100;
	public AIPlayer[] realPop;
	public int gen = 1;
	public int genStep = 5;
	public double bestFitness = 0;
	public double genFitnessMax = 0;
	public int bestPlayer = 0;
	public static int numberOfSteps = 10;
	public int moveStep = 5;
	public double fitnessSum = 0.0;
	public boolean solutionFound;
	
	public Population(AIPlayer[] pop) {
		realPop = new AIPlayer[pop.length];
		for(int i = 0; i < pop.length; i++) {
			realPop[i] = pop[i];
		}
	}
	
	public void calculateFitness() {
		for(int i = 0; i < realPop.length - 1; i++) {
			realPop[i].calculateFitness();
		}
	}
	
	public boolean allPlayersDead() {
		for(int i = 0; i < realPop.length; i++) {
			if(!realPop[i].isDead && !realPop[i].reachedGoal) {
				return false;
			}
		}
		return true;
	}
	
	public void naturalSelection() {
		AIPlayer[] newPop = new AIPlayer[realPop.length];

		this.setBestPlayer();
		this.calcFitnessSum();
		
		newPop[0] = realPop[this.bestPlayer].makeBaby();
		newPop[0].isBest = true;
				
		for(int i = 1; i < realPop.length; i++) {
			AIPlayer parent = this.selectParent();
			
			newPop[i] = parent.makeBaby();
			newPop[i].isBest = false;
		}
		
		for(int i = 0; i < realPop.length; i++) {
			realPop[i].brain = newPop[i].brain;
		}
		this.gen++;
	}
	
	public void calcFitnessSum() {
		this.fitnessSum = 0;
		for(int i = 0; i < realPop.length; i++) {
			this.fitnessSum += realPop[i].fitness;
		}
	}
	
	public void setBestPlayer() {
		genFitnessMax = 0;
		int maxIndex = 0;
		for(int i = 0; i < realPop.length - 1; i++) {
			if(realPop[i].fitness > genFitnessMax) {
				genFitnessMax = realPop[i].fitness;
				maxIndex = i;
			}
		}
		this.bestPlayer = maxIndex;
		if(genFitnessMax > this.bestFitness) {
			this.bestFitness = genFitnessMax;
		}
		realPop[maxIndex].isBest = true;
		
		if(this.realPop[this.bestPlayer].reachedGoal) {
			this.solutionFound = true;
		}
		
	}
	
	AIPlayer selectParent() {
		double rand = Math.random() * this.genFitnessMax;
		
		for(int i = 0; i < realPop.length; i++) {
			if(realPop[i].fitness > rand) {
				return realPop[i];
			}
		}
		return null;
	}
	
	public void mutateBabies() {
		for(int i = 1; i < realPop.length; i++) {
			realPop[i].brain.mutate(realPop[i].deathByDot, realPop[i].deathAtStep);
			realPop[i].deathByDot = false;
			realPop[i].gen = this.gen;
			realPop[i].isBest = false;
		}
		realPop[0].deathByDot = false;
		realPop[0].gen = this.gen;
		realPop[0].isBest = true;
	}
	
	public void increaseMoves() {
		if(this.realPop[0].brain.directions.length < 120 && !this.solutionFound) {
			for(int i = 0; i < this.realPop.length; i++) {
				this.realPop[i].brain.increaseMoves();
			}
		}
	}
	
	public void restartLevel() {
		for(int i = 0; i < realPop.length; i++) {
			realPop[i].x = realPop[i].startX;
			realPop[i].y = realPop[i].startY;
			realPop[i].brain.brainStep = 0;
			realPop[i].isDead = false;
		}
	}
}
