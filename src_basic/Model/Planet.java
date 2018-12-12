package src_basic.Model;

import java.io.Serializable;

import src_basic.Geometry.Circle;
import src_basic.Geometry.Point;


/**
 * Represent a planet in the game
 * @author Chocorion and Labayanne
 *
 */
public class Planet implements Serializable {



	private Circle collisionShape;
	
	
	private double poductionSpeed;
	
	private int stock;
	private double realStock; // TODO Faire une fonction pour décrémenter le stock, qui prends en compte stock ET realStock
	
	private int squadSize;	
	private StarShip starshipModel;
	
	private int owner;
	private int nbStarshipToGenerate;
	
	private Planet target;
	
	private double timerMax;
	private double timer;
	
	public Planet() {
		this.collisionShape = new Circle(0, 0, 0);
		
		this.poductionSpeed = 0;
		this.stock = 0;
		this.squadSize = 100;//100 percents by default
		
		this.owner = 0;
		
		this.starshipModel = new StarShip(new Point(0, 0), new Point(700, 540), 0.1, 0, 0, 0);
		
		this.nbStarshipToGenerate = 0;
		this.timerMax = 60;
		this.timer = 0;
		this.target = null;
	}
	
	public Planet(Point origin, double radius, double productionSpeed, int owner) {
		this.collisionShape = new Circle (origin.getX(), origin.getY(), radius);
		
		this.poductionSpeed = productionSpeed;
		this.owner = owner;
		this.stock = 0;
		this.squadSize = 100;//100 percent by default
		this.starshipModel = new StarShip(new Point(0, 0), new Point(700, 540), 0.1, 0, 0, owner);
		this.nbStarshipToGenerate = 0;
		this.timerMax = 60;
		this.timer = 0;
		this.target = null;
	}

	/**
	 * Actualize the starship stock of the planet
	 */
	public void actualiseStock() {
		this.realStock += this.poductionSpeed;
		this.stock = (int) Math.round(this.realStock);
		if(this.nbStarshipToGenerate > this.stock) {
			this.nbStarshipToGenerate = this.stock - 1;
		}
	}
	
	/**
	 * Decrease the stock and the real stock of the planet
	 * @param nbUnit	Number of starship to decrease
	 */
	public void decreaseStock(int nbUnit) {
		this.realStock -= (double) nbUnit;
		this.stock -= nbUnit;
	}
	
	/**
	 * Increase the stock and the real stock of the planet 
	 * @param nbUnit 	Number of starship to increase
	 */
	public void increaseStock(int nbUnit) {
		this.realStock += nbUnit;
		this.stock += nbUnit;
	}
	
	/**
	 * Set the number of unit needed for attack
	 * @param target	The destination planet
	 */
	public void prepareAttack(Planet target) {
		this.nbStarshipToGenerate += getNbUnitPerSquad();
		this.target = target;
	}
	
	public void setAttackGroup(int nbAttackers, Planet target) {
		setNbStarshipToGenerate(nbAttackers);
		this.target = target;
	}
	
	/**
	 * Calcul of many starship per squad, based on the stock and the squadsize
	 * @return the number of starship per squad
	 */
	public int getNbUnitPerSquad() {
		int nb = (this.stock - this.nbStarshipToGenerate) * this.squadSize/100;
		if(this.stock - nb <= 0) {
			nb--;
		}
		return nb;
	}
	
	/**
	 * Create the squad with nbStarshipToGenerate Unit
	 * @return	The squad create
	 */
	public Squad generateSquad() {
		reloadTimer();
		this.starshipModel.setOwner(this.owner);
		
		Squad squad = new Squad(this.nbStarshipToGenerate, this.starshipModel, this);
		int restUnit = squad.repartsStarships();
		
		squad.setDestinationPlanet(this.target);
		this.decreaseStock(this.nbStarshipToGenerate - restUnit);
		
		this.nbStarshipToGenerate = restUnit;
		return squad;
	}
	
	/**
	 * Decrease the timer used for fragmenting the attack in multiple wave
	 * @return the new value of the timer
	 */
	public double decreaseTimer() {
		if(this.timer > 0)
			this.timer -= 1;
		return this.timer;
	}
	
	/**
	 * Set the timer to it's maximum value
	 */
	public void reloadTimer() {
		this.timer = this.timerMax;
	}
	
	
	
	public int getNbStarshipToGenerate() 	{	return this.nbStarshipToGenerate;	}
	public int getOwner()					{ 	return this.owner;  }
	public int getStock() 					{	return this.stock;  }
	public void setOwner(int owner) 		{	this.owner = owner;	}
	public double getRadius() 				{	return this.collisionShape.getRadius();	}
	public Point getOrigin() {
		return this.collisionShape.getOrigin();
	}
	
	public void setNbStarshipToGenerate(int value) {
		if(value < 0)
			value = 0;
		this.nbStarshipToGenerate += value;
		if(this.nbStarshipToGenerate >= this.stock) {
			this.nbStarshipToGenerate = this.stock - 1;
		}
	}
	
	public void setOrigin(Point point) {
		this.collisionShape.getOrigin().set(point);
	}
	
	public void setSquadSize(int size) {
		this.squadSize = size;
		if(this.squadSize < 1) {
			this.squadSize = 1;
		}
		else if(this.squadSize > 100) {
			this.squadSize = 100;
		}
	}
	
	public void setStock(int n) {
		this.realStock = n;
		this.stock = n;
	}
	
	public Circle getCollisionShape() 	{ return this.collisionShape; };
}