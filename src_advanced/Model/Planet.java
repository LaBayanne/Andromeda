package src_advanced.Model;

import java.io.Serializable;

import src_advanced.Geometry.Circle;
import src_advanced.Geometry.Point;


/**
 * Represent a planet in the game
 */
public class Planet implements Serializable {
	/** Rigid body of the planet **/
	private Circle collisionShape;
	
	private double poductionSpeed;
	
	/* There is a realStock because we can't have half-starship but the speed production
	 * can't be an integer
	 */
	private int stock;
	private double realStock; 
	
	private int squadSize;			//Percentage of the stock	
	private StarShip starshipModel;	//Model of starship that planet can generate
	
	private int owner;
	private int nbStarshipToGenerate;
	
	private Planet target;
	
	private double timerMax;
	private double timer;
	

	
	/**
	 * Complete constructor for planet
	 * @param origin			The top left position of the planet
	 * @param radius			Radius of the planet
	 * @param productionSpeed	Production of starship per frames
	 * @param owner				ID of the planet owner
	 */
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
	 * Basic constructor for Planet
	 */
	public Planet() {
		this(new Point(), 0, 0, 0);
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
	
	/**
	 * Attack target with a specified number of attackers
	 * @param nbAttackers	number of attackers
	 * @param target		planet to attack/help
	 */
	public void setAttackGroup(int nbAttackers, Planet target) {
		setNbStarshipToGenerate(nbAttackers);
		this.target = target;
	}
	
	/**
	 * Calculate of how many starship per squad, based on the stock and the squadsize
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
		
		Squad squad = new Squad(this.nbStarshipToGenerate, this.starshipModel, this, this.owner);
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
	
	
	/**
	 * @return the number of starship to generate value.
	 */
	public int getNbStarshipToGenerate() {
		return this.nbStarshipToGenerate;	
	}
	
	/**
	 * @return the owner's id of the planet.
	 */
	public int getOwner() { 	
		return this.owner;  
	}
	
	/**
	 * @return the planet's stock in number of unit.
	 */
	public int getStock() {	
		return this.stock;  
	}
	
	/**
	 * @return planet's radius.
	 */
	public double getRadius() {	
		return this.collisionShape.getRadius();	
	}
	
	/**
	 * @return the origin of the planet's collision shape
	 */
	public Point getOrigin() {
		return this.collisionShape.getOrigin();
	}
	
	/**
	 * @param owner owner to set.
	 */
	public void setOwner(int owner) {	
		this.owner = owner;	
	}
	
	/**
	 * @param value the number of starship that the planet had to generate.
	 */
	public void setNbStarshipToGenerate(int value) {
		if(value < 0)
			value = 0;
		this.nbStarshipToGenerate += value;
		if(this.nbStarshipToGenerate >= this.stock) {
			this.nbStarshipToGenerate = this.stock - 1;
		}
	}
	
	/**
	 * @param point the origin of planet's collision shape.
	 */
	public void setOrigin(Point point) {
		this.collisionShape.getOrigin().set(point);
	}
	
	/**
	 * @param size the size in percent of squads.
	 */
	public void setSquadSize(int size) {
		this.squadSize = size;
		if(this.squadSize < 1) {
			this.squadSize = 1;
		}
		else if(this.squadSize > 100) {
			this.squadSize = 100;
		}
	}
	
	/**
	 * @param n  new value for stock.
	 */
	public void setStock(int n) {
		this.realStock = n;
		this.stock = n;
	}
	
	/**
	 * @param p the new production speed for the planet.
	 */
	public void setProductionSpeed(double p) {
		this.poductionSpeed = p;
	}
	
	/**
	 * @return the planet's collision shape.
	 */
	public Circle getCollisionShape() { 
		return this.collisionShape; 
	};
}