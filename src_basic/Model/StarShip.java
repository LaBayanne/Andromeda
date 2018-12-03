package src_basic.Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author chocorion
 *	This class represent the basic starship generate by planets
 *
 */
public class StarShip {
	
	private static int height;
	private static int width;

	static {
		height = 10;
		width = 10;
	}
	
	private Point2D position;
	private Point2D destination;
	private Planet 	destinationPlanet;
	
	private double 	speed;
	private double 	angle;//In °
	private int 	damage;
	private int 	owner;

	private Hitbox 	hitbox;
	
	boolean destinationReached;
	
	public StarShip(Point2D position, Point2D destination,double speed, int damage, double angle, int owner) {
		
		this.position = new Point2D(position.getX(), position.getY());
		this.destination = new Point2D(destination.getX(), destination.getY());
		
		this.owner = owner;
		this.speed = speed;
		this.damage = damage;
		this.angle = 0;
		this.destinationReached = false;
		
		this.hitbox = new Hitbox(new Rectangle(this.position.getX(), this.position.getY(), this.getWidth(), this.getHeight()));
	}
	
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
	
	public StarShip() {
		this(Point2D.ZERO, Point2D.ZERO, 0, 0, 0.0, 0);
	}
	
	
	
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
	
	private Point2D calculateNewPos(ArrayList<Planet> planets) {
		
		double angle = this.angleToPoint(this.position, this.destination);
		Point2D newPos;
		double dx, dy;
		double diff = 0;
		int pivot = 1;
		
		do {
			dx = this.speed * Math.cos(Math.toRadians(angle + diff * pivot));
			dy = this.speed * Math.sin(Math.toRadians(angle + diff * pivot));
			
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
	
	
	public void move(ArrayList<Planet> planets) {
		Point2D newPos = this.calculateNewPos(planets);
		
		if (this.destination.distance(newPos) < this.destinationPlanet.getRadius()) {
			
			System.err.println("Destination : " + this.destinationPlanet );
			System.err.println("This: " + this.owner + " Planet : " + this.destinationPlanet.getOwner());
			
			if (this.destinationPlanet.getOwner() == this.owner) {
				System.err.println("It's an ally !");
				this.destinationPlanet.increaseStock(1);
			} else {
				System.err.println("It's an enemy !");
				this.destinationPlanet.decreaseStock(1);
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
