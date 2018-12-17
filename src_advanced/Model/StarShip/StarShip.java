package src_advanced.Model.StarShip;

import src_advanced.Geometry.*;
import src_advanced.Model.Planet.Planet;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * 
 * @author chocorion
 *	This class represent the basic starship generate by planets
 *
 */
public class StarShip implements Serializable {

		private static int ANGLE_MAX = 10;
	
	private int width;
	private int height;
	
	private Rectangle collisionShape;
	
	private Point   destination;
	private Planet 	destinationPlanet;
	
	private double 	speed;
	private double 	angle;
	private int 	damage;
	private int 	cost;
	private int 	owner;
	
	private boolean collisionPlanetState;

	
	boolean destinationReached;
	
	public StarShip(Point position, Point destination,double speed, int damage, int cost, double angle, int owner, 
			int width, int height) {
		
		this.collisionPlanetState = false;
		
		this.width = width;
		this.height = height;
		this.destination = new Point(destination);
		this.collisionShape = new Rectangle(position.getX(), position.getY(), this.width, this.height);
		
		this.owner = owner;
		this.speed = speed;
		this.damage = damage;
		this.cost = cost;
		this.angle = 0;
		this.destinationReached = false;
		
	}
	
	public StarShip(double speed, int damage, int cost, int owner) {
		
		this();
		this.owner = owner;
		this.speed = speed;
		this.damage = damage;
		this.cost = cost;
		
	}
	
	/**
	 * Copy constructor
	 * @param starship	starship to copy
	 */
	public StarShip(StarShip starship) {
		
		this.collisionShape = new Rectangle(starship.getCollisionShape());
		this.destination = new Point(starship.getDestination());
		
		this.speed = starship.getSpeed();
		this.damage = starship.getDamage();
		this.cost = starship.getCost();
		this.angle = 0;
		this.destinationReached = false;
		this.owner = starship.getOwner();
		this.width = starship.getWidth();
		this.height = starship.getHeight();
		
	}
	
	/**
	 * Basic constructor, build a starship with all value set to 0
	 */
	public StarShip() {
		this(new Point(), new Point(), 0, 0, 0, 0.0, 0, 20, 20);
	}

	
	private boolean planetCollision(ArrayList<Planet> planets, Point position) {
		for (Planet planet : planets) {//
			if (planet.getCollisionShape().collision(new Rectangle(position, this.width, this.height))) {
				if (planet != this.destinationPlanet) {
					return true;
				}
			}
		}
		return false;
	}
	
	private Point calculateNewPos(double delta, ArrayList<Planet> planets) {		
		double destinationAngle = this.collisionShape.getOrigin().angle(this.destination);
		double diff =  (this.angle < destinationAngle)? -0.2:0.2;
		
		Point newPos = new Point(
				this.collisionShape.getOrigin().getX() + delta * this.speed * Math.toRadians(destinationAngle),
				this.collisionShape.getOrigin().getY() + delta * this.speed * Math.toRadians(destinationAngle)
				);
		while(!planetCollision(planets, newPos)) {
			destinationAngle += diff;
			
			newPos.setX(this.collisionShape.getOrigin().getX() + delta * this.speed * Math.toRadians(destinationAngle));
			newPos.setY(this.collisionShape.getOrigin().getY() + delta * this.speed * Math.toRadians(destinationAngle));
		}
		this.angle = destinationAngle - diff;
		
		
		
		newPos.setX(this.collisionShape.getOrigin().getX() + delta * this.speed * Math.toRadians(this.angle));
		newPos.setY(this.collisionShape.getOrigin().getY() + delta * this.speed * Math.toRadians(this.angle));
		
		return newPos;
	}
	
	/**
	 * Move all planets
	 * @param delta	delta time
	 * @param planets	The list of all planets
	 */
	public void move(double delta, ArrayList<Planet> planets) {
		Point newPos = this.calculateNewPos(delta, planets);
		
		if ((new Rectangle(newPos, this.width, this.height).collision(this.destinationPlanet.getCollisionShape()))) {
					
			if (this.destinationPlanet.getOwner() == this.owner) {
				this.destinationPlanet.increaseStock(this.damage);
			} else {
				if (this.destinationPlanet.getStock() <= this.damage) {
					this.destinationPlanet.setOwner(this.owner);
					//this.destinationPlanet.increaseStock(1);
				} 
				this.destinationPlanet.decreaseStock(this.damage);
				
			}
			this.destinationReached = true;
		}
		this.setPosition(newPos);
	}
	
	/**
	 * @return true if starship is arrived at destination, else false.
	 */
	public boolean asFinished() {
		return this.destinationReached;
	}

	
/**
 * 
 * 		Getter - Setter
 * 
 */
	
	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
	
	public double getSpeed() 		{ return this.speed;	}
	public int getDamage() 			{ return this.damage;	}//Was height
	public Point getPosition() 		{ return new Point(this.collisionShape.getOrigin()); }
	public Point getDestination() 	{ return this.destination; }//Pas retourner le point mais en faire une copie ?
	public Planet getDestinationPlanet() 	{ return this.destinationPlanet; }
	
	public int getOwner()			{ return this.owner;	}
	
	public void setPosition(Point position) {
		this.collisionShape.getOrigin().set(position);
	}

	public void setDestination(Point destination) {
		this.destination.set(destination);
	}
	
	public void setDestination(Planet destinationPlanet) {
		this.setDestination(destinationPlanet.getOrigin());
		this.destinationPlanet = destinationPlanet;
	}
	
	public void setOwner(int owner) {
		this.owner = owner;
	}
	
	public Rectangle getCollisionShape() {
		return this.collisionShape;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public double getAngle() {
		return this.angle;
	}
	
	public int getCost() {
		return this.cost;
	}
}
