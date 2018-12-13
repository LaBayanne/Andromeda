package src_basic.Model;

import src_basic.Geometry.*;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * 
 * @author chocorion
 *	This class represent the basic starship generate by planets
 *
 */
public class StarShip implements Serializable {

	static private int WIDTH;
	static private int HEIGHT;
	
	static {
		WIDTH  = 10;
		HEIGHT = 10;
	}
	
	private Rectangle collisionShape;
	
	private Point   destination;
	private Planet 	destinationPlanet;
	
	private double 	speed;
	private double 	angle;//In °
	private int 	damage;
	private int 	owner;

	
	boolean destinationReached;
	
	public StarShip(Point position, Point destination,double speed, int damage, double angle, int owner) {
		
		this.destination = new Point(destination);
		this.collisionShape = new Rectangle(position.getX(), position.getY(), WIDTH, HEIGHT);
		
		this.owner = owner;
		this.speed = speed;
		this.damage = damage;
		this.angle = 0;
		this.destinationReached = false;
		
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
		this.angle = 0;
		this.destinationReached = false;
		this.owner = starship.getOwner();
		
	}
	
	/**
	 * Basic constructor, build a starship with all value set to 0
	 */
	public StarShip() {
		this(new Point(), new Point(), 0, 0, 0.0, 0);
	}

	
	private boolean planetCollision(ArrayList<Planet> planets, Point position) {
		for (Planet planet : planets) {//
			if (planet.getCollisionShape().collision(new Rectangle(position, WIDTH, HEIGHT))) {
				if (planet != this.destinationPlanet) {
					return true;
				}
			}
		}
		return false;
	}
	
	private Point calculateNewPos(double delta, ArrayList<Planet> planets) {
		
		double angle = this.collisionShape.getOrigin().angle(this.destination);
		Point newPos;
		double dx, dy;
		double diff = 0;
		int pivot = 1;
		
		do {
			dx = delta * this.speed * Math.cos(Math.toRadians(angle + diff * pivot));
			dy = delta * this.speed * Math.sin(Math.toRadians(angle + diff * pivot));
			
			newPos = new Point(this.collisionShape.getOrigin().getX() + dx, this.collisionShape.getOrigin().getY() + dy);
			
			if (pivot == -1) {
				pivot = 1;
				diff+= 0.1;
			} else {
				pivot = -1;
			}
		} while(this.planetCollision(planets, newPos) && this.destination.distance(newPos) < this.collisionShape.getOrigin().distance(destination));
		//Condition de distance empèche vaisseaux de rester coincé dans une boucle.
		// Comme il n'y a que des planètes rondes, impossible que cette condition bloque un vaisseaux
		
		return newPos;
	}
	
	/**
	 * Move all planets
	 * @param delta	delta time
	 * @param planets	The list of all planets
	 */
	public void move(double delta, ArrayList<Planet> planets) {
		Point newPos = this.calculateNewPos(delta, planets);
		
		if (this.destination.distance(newPos) < this.destinationPlanet.getRadius()) {
					
			if (this.destinationPlanet.getOwner() == this.owner) {
				this.destinationPlanet.increaseStock(1);
			} else {
				if (this.destinationPlanet.getStock() <= 1) {
					this.destinationPlanet.setOwner(this.owner);
					//this.destinationPlanet.increaseStock(1);
				} else {
					this.destinationPlanet.decreaseStock(1);
				}
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
	
	public static int getWidth() {return WIDTH;}
	public static int getHeight() {return HEIGHT;}
	
	public double getSpeed() 		{ return this.speed;	}
	public int getDamage() 			{ return this.damage;	}//Was height
	public Point getPosition() 		{ return new Point(this.collisionShape.getOrigin()); }
	public Point getDestination() 	{ return this.destination; }//Pas retourner le point mais en faire une copie ?
	
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
}
