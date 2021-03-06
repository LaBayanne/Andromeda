package Model.StarShip;

import java.io.Serializable;
import java.util.ArrayList;

import Geometry.Point;
import Geometry.Rectangle;
import Model.Planet.Planet;


/**
 * 
 * @author chocorion
 *	This class represent the basic starship generate by planets
 *
 */
public class StarShip implements Serializable {

	private int width;
	private int height;
	
	private Rectangle collisionShape;
	
	private Point   destination;
	private Planet 	destinationPlanet;
	
	private double 	speed;
	private double 	angle;
	private int 	damage;
	private int cost;
	private int 	owner;

	
	boolean destinationReached;
	
	/**
	 * Basic constructor.
	 * @param position	The position of the starship
	 * @param destination	The destination of the starship
	 * @param speed		Its speed
	 * @param damage	Its damage
	 * @param cost		Its cost
	 * @param angle		Its angle
	 * @param owner		Its owner
	 * @param width		Its width
	 * @param height	Its height
	 */
	public StarShip(Point position, Point destination,double speed, int damage, int cost, double angle, int owner, 
			int width, int height) {
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
	
	/**
	 * Constructor.
	 * @param speed		Its speed
	 * @param damage	Its damage
	 * @param cost		Its cost
	 * @param owner		Its owner
	 */
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
		
		double angle = this.collisionShape.getOrigin().angle(this.destination);
		Point newPos = new Point();
		double dx, dy;
		double diff = 0;
		int pivot = 1;
		
		
		do {
			dx = delta * this.speed * Math.cos(Math.toRadians(angle + diff * pivot));
			dy = delta * this.speed * Math.sin(Math.toRadians(angle + diff * pivot));
			
			newPos.set(this.collisionShape.getOrigin().getX() + dx, this.collisionShape.getOrigin().getY() + dy);
			
			if (pivot == -1) {
				pivot = 1;
				diff+= 0.1;
			} else {
				pivot = -1;
			}
		} while(this.planetCollision(planets, newPos) && this.destination.distance(newPos) < this.collisionShape.getOrigin().distance(destination));
		//Condition de distance empèche vaisseaux de rester coincé dans une boucle.
		// Comme il n'y a que des planètes rondes, impossible que cette condition bloque un vaisseaux
		this.angle = this.collisionShape.getOrigin().angle(newPos);
		return newPos;
	}
	
	/**
	 * Move all planets
	 * @param delta	delta time
	 * @param planets	The list of all planets
	 */
	public void move(double delta, ArrayList<Planet> planets) {
		Point newPos = this.calculateNewPos(delta, planets);
		this.setPosition(newPos);
		
		if (this.getCollisionShape().collision(this.destinationPlanet.getCollisionShape())) {
					
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
		
	}
	
	/**
	 * @return true if starship is arrived at destination, else false.
	 */
	public boolean isFinished() {
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
