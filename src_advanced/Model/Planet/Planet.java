package src_advanced.Model.Planet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import src_advanced.Geometry.Circle;
import src_advanced.Geometry.Point;
import src_advanced.Model.StarShip.StarShip;
import src_advanced.Model.StarShip.Arrow;
import src_advanced.Model.StarShip.Finger;
import src_advanced.Model.StarShip.MoveCursor;
import src_advanced.Model.StarShip.Squad;


/**
 * Represent a planet in the game
 */
public class Planet implements Serializable {
	private static int WIDTH;
	private static int HEIGHT;
	
	static {
		WIDTH = 26;
		HEIGHT = WIDTH;
	}
	/** Rigid body of the planet **/
	protected Circle collisionShape;
	
	protected double productionSpeed;
	
	/* There is a realStock because we can't have half-starship but the speed production
	 * can't be an integer
	 */
	protected int stock;
	protected double realStock; 
	
	protected int squadSize;			//Percentage of the stock	
	protected StarShip starshipModel;	//Model of starship that planet can generate
	
	protected int owner;
	protected int nbStarshipToGenerate;
	
	protected Planet target;
	
	protected double timerMax;
	protected double timer;
	
	/**
	 * Constructor for planet
	 * @param origin			The top left position of the planet
	 * @param radius			Radius of the planet
	 * @param productionSpeed	Production of starship per frames
	 * @param owner				ID of the planet owner
	 */
	public Planet(Point origin, double radius, double productionSpeed, int owner) {
		this.collisionShape = new Circle (origin.getX(), origin.getY(), WIDTH);
		
		this.productionSpeed = productionSpeed;
		this.owner = owner;
		this.stock = 0;
		this.squadSize = 100;//100 percent by default
		this.starshipModel = new StarShip(new Point(0, 0), new Point(700, 540), 0.1, 0, 0, 0, owner, 20, 20);
		this.nbStarshipToGenerate = 0;
		this.timerMax = 60;
		this.timer = 0;
		this.target = null;
	}
	
	public Planet(Point origin, double radius, double productionSpeed, int owner, StarShip starshipModel) {
		this(origin, radius, productionSpeed, owner);
		String name = starshipModel.getClass().getName();
		name = name.replace("src_advanced.Model.StarShip.", "");
		switch (name) {
		
			case "Arrow":
				this.starshipModel = new Arrow(starshipModel);
				break;
			
			case "Finger":
				this.starshipModel = new Finger(starshipModel);
				break;
			
			case "MoveCursor":
				this.starshipModel = new MoveCursor(starshipModel);
				this.starshipModel.setWidth(28);
				this.starshipModel.setHeight(28);
				break;
		}
	}
	
	/**
	 * Basic constructor for Planet
	 */
	public Planet() {
		this(new Point(), 0, 0, 0);
	}
	

	public void destroyed(ArrayList<Planet> planets, ArrayList<Squad> squads) {
		for(Planet planet : planets){
			if(planet.getTarget() == this) {
				planet.setTarget(this);
			}
		}
		for(Squad squad : squads) {
			for(StarShip starship : squad.getStarships()){
				if(starship.getDestinationPlanet() == this) {
					starship.setDestination(this);
				}
			}
		}
	}

	/**
	 * Actualize the starship stock of the planet
	 */
	public void actualiseStock(double delta) {
		
		this.realStock += this.productionSpeed * delta;
		this.stock = (int) Math.round(this.realStock);
		
		if(this.nbStarshipToGenerate * this.starshipModel.getCost() > this.stock) {
			this.nbStarshipToGenerate = (this.stock - 1)* this.starshipModel.getCost();
		}
	}
	
	/**
	 * Decrease the stock and the real stock of the planet
	 * @param nbUnit	Number of starship to decrease
	 */
	public void decreaseStock(int nbUnit) {
		this.realStock -= (double) nbUnit;
		if(this.realStock < 1) {
			this.realStock = 1;
		}
		this.stock -= nbUnit;
		if(this.stock < 1) {
			this.stock = 1;
		}
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
		setNbStarshipToGenerate(nbAttackers/this.starshipModel.getCost());
		this.target = target;
	}
	
	/**
	 * Calculate of how many starship per squad, based on the stock and the squadsize
	 * @return the number of starship per squad
	 */
	public int getNbUnitPerSquad() {
		int nb = (this.stock - (this.nbStarshipToGenerate * this.starshipModel.getCost())) * this.squadSize/100;
		if(this.stock - nb <= 0) {
			nb =  stock/this.starshipModel.getCost();
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
		this.decreaseStock((this.nbStarshipToGenerate - restUnit) * this.starshipModel.getCost());
		
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
	
	public int getPower() {
		return this.stock * this.starshipModel.getDamage();
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
	 * 
	 * @return The Planet target
	 */
	public Planet getTarget() {
		return this.target;
	}
	
	public void setTarget(Planet target) {
		this.target = target;
	}
	
	/**
	 * @param p the new production speed for the planet.
	 */
	public void setProductionSpeed(double p) {
		this.productionSpeed = p;
	}
	
	/**
	 * @return the planet's collision shape.
	 */
	public Circle getCollisionShape() { 
		return this.collisionShape; 
	};
}