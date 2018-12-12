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
		
		this.hitbox = new Hitbox(new Rectangle(this.position.getX(), this.position.getY(), this.getWidth(), this.getHeight()));
	}
	
	/**
	 * Copy constructor
	 * @param starship	starship to copy
	 */
	public StarShip(StarShip starship) {
		
		this.position = new Point2D(starship.getPosition().getX(), starship.getPosition().getY());
		this.destination = new Point2D(starship.getDestination().getX(), starship.getDestination().getY());
		
		this.speed = starship.getSpeed();
		this.damage = starship.getDamage();
		this.angle = 0;
		this.destinationReached = false;
		this.owner = starship.getOwner();
		
		this.hitbox = new Hitbox(new Rectangle(this.position.getX(), this.position.getY(), this.getWidth(), this.getHeight()));

	}
	
	/**
	 * Basic constructor, build a starship with all value set to 0
	 */
	public StarShip() {
		this(Point2D.ZERO, Point2D.ZERO, 0, 0, 0.0, 0);
	}
	
	
	/**
	 * Compute the angle value between two points
	 * @param position		The first point
	 * @param destination	The second point
	 * @return
	 */
	private double angleToPoint(Point2D position, Point2D destination) {
		return Math.toDegrees(Math.atan2(destination.getY() - position.getY(), destination.getX() - position.getX()));
	}
	
	private boolean detectCollision(Planet planet, Point2D p1) {
		Point2D origin = planet.getOrigin();
		double radius = planet.getRadius();
		
		Point2D corner[] = new Point2D[4];
		
		corner[0] = p1;
		corner[1]= new Point2D(corner[0].getX(), corner[0].getY() + this.getHeight());
		corner[2] = new Point2D(corner[0].getX() + this.getWidth(), corner[0].getY());
		corner[3] = new Point2D(corner[2].getX(), corner[1].getY());
		
		for (Point2D p:corner) {
			if (origin.distance(p) < radius) {
				return true;
			}
		}
		return false;
	}
	
	private boolean planetCollision(ArrayList<Planet> planets, Point2D position) {
		for (Planet planet : planets) {
			if (this.detectCollision(planet, position)) {
				if (planet != this.destinationPlanet) {
					return true;
				}
			}
		}
		return false;
	}
	
	private Point2D calculateNewPos(double delta, ArrayList<Planet> planets) {
		
		double angle = this.angleToPoint(this.position, this.destination);
		Point2D newPos;
		double dx, dy;
		double diff = 0;
		int pivot = 1;
		
		do {
			dx = delta * this.speed * Math.cos(Math.toRadians(angle + diff * pivot));
			dy = delta * this.speed * Math.sin(Math.toRadians(angle + diff * pivot));
			
			newPos = new Point2D(this.position.getX() + dx, this.position.getY() + dy);
			
			if (pivot == -1) {
				pivot = 1;
				diff+= 0.1;
			} else {
				pivot = -1;
			}
		} while(this.planetCollision(planets, newPos) && this.destination.distance(newPos) < this.destination.distance(this.position));
		//Condition de distance empèche vaisseaux de rester coincé dans une boucle.
		// Comme il n'y a que des planètes rondes, impossible que cette condition bloque un vaisseaux
		
		return newPos;
	}
	
	
	public void move(double delta, ArrayList<Planet> planets) {
		Point2D newPos = this.calculateNewPos(delta, planets);
		
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
	
	public boolean asFinished() {
		return this.destinationReached;
	}

	
/**
 * 
 * 		Getter - Setter
 * 
 */
	
	public static int getWidth() {return width;}
	public static int getHeight() {return height;}
	
	public double getSpeed() 		{ return this.speed;	}
	public int getDamage() 			{ return height;		}
	public Hitbox getHitbox() 		{ return this.hitbox;	}
	public Point2D getPosition() 	{ return new Point2D(this.position.getX(), this.position.getY()); }
	public Point2D getDestination() { return new Point2D(this.destination.getX(), this.destination.getY()); }
	
	public int getOwner()			{ return this.owner;	}
	
	public void setPosition(Point2D position) {
		this.position = new Point2D(position.getX(), position.getY());
	}

	public void setDestination(Point2D destination) {
		this.destination = new Point2D(destination.getX(), destination.getY());;
	}
	
	public void setDestination(Planet destinationPlanet) {
		Point2D destination = destinationPlanet.getOrigin();
		this.setDestination(destination);
		
		this.destinationPlanet = destinationPlanet;
	}
	
	public void setOwner(int owner) {
		this.owner = owner;
	}
}
