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
	private static double angleMax;
	
	private static int height;
	private static int width;
	
	static {
		angleMax = 10.0;
		height = 10;
		width = 10;
	}
	
	private ArrayList<Point2D> path;
	
	private Point2D position;
	private Point2D destination;
	private Planet destinationPlanet;
	private double speed;
	private double angle;//In °
	private int damage;
	private static String className;
	private Hitbox hitbox;
	
	public StarShip(Point2D position, Point2D destination,double speed, int damage, double angle) {
		this.path = new ArrayList<>();
		this.position = new Point2D(position.getX(), position.getY());
		this.destination = new Point2D(destination.getX(), destination.getY());
		this.speed = speed;
		this.damage = damage;
		this.angle = 0;
		this.hitbox = this.hitbox = new Hitbox(new Rectangle(this.position.getX(), this.position.getY(), this.getWidth(), this.getHeight()));
		
		this.className = "StarShip"; // Besoin pour la création dynamique des startships
		
	}
	
	public StarShip(StarShip starship) {
		this.path = new ArrayList<>();
		this.position = new Point2D(starship.getPosition().getX(), starship.getPosition().getY());
		this.destination = new Point2D(starship.getDestination().getX(), starship.getDestination().getY());
		this.speed = starship.getSpeed();
		this.damage = starship.getDamage();
		this.angle = 0;
		this.hitbox = this.hitbox = new Hitbox(new Rectangle(this.position.getX(), this.position.getY(), this.getWidth(), this.getHeight()));
		
		this.className = "StarShip";
	}
	
	
	public StarShip() {
		this(Point2D.ZERO, Point2D.ZERO, 0, 0, 0.0);
	}
	
	
	public Point2D getPosition() {
		return new Point2D(this.position.getX(), this.position.getY());
	}

	// Plutôt faire une copie ?
	public void setPosition(Point2D position) {
		this.position = new Point2D(position.getX(), position.getY());
	}

	
	public Point2D getDestination() {
		return new Point2D(this.destination.getX(), this.destination.getY());
	}

	
	public void setDestination(Point2D destination) {
		this.destination = new Point2D(destination.getX(), destination.getY());;
	}
	
	public void setDestination(Planet destinationPlanet) {
		Point2D destination = destinationPlanet.getOrigin();
		this.setDestination(destination);
		
		this.destinationPlanet = destinationPlanet;
	}
	
	private double angleToPoint(Point2D position, Point2D destination) {
		return Math.toDegrees(Math.atan2(destination.getY() - position.getY(), destination.getX() - position.getX()));
	}
	
	private double angleToDestination() {
		return this.angleToPoint(this.position, this.destination);
	}
	
	
	private Point2D calculateNewPos() {
		
		double angle = this.angleToDestination();
		
		double dx = this.speed * Math.cos(Math.toRadians(angle));
		double dy = this.speed * Math.sin(Math.toRadians(angle));
		
		Point2D newPos = new Point2D(this.position.getX() + dx, this.position.getY() + dy);
		return newPos;
	}
	
	
	public void move() {
		if (this.position.distance(this.destination) < 20) {
			if (this.path.isEmpty()) {
				return;
			} else {
				this.setDestination(this.path.get(0));
				this.path.remove(0);
			}
		}
		Point2D newPos = this.calculateNewPos();
		
		//Checker si collision avec planete. Que faire s'il y a collision innatendue ?

		this.setPosition(newPos);
	}


	public Hitbox getHitbox() {
		return this.hitbox;
	}
	
	public Planet isPlanetCollision(ArrayList<Planet> planets, Point2D position) {
		Line l = new Line(position.getX(), position.getY(), this.destination.getX(), this.destination.getY());
		
		for (Planet planet:planets) {
			if (planet == this.destinationPlanet) {
				continue;//Osef si c'est la planète de destination
			}
			if (planet.getHitbox().collision(l)) {
				// System.err.println("COLLISION DETECTED !");
				return planet;
			}
		}
		
		return null;
	}
	
	public void findPath(ArrayList<Planet> planets, Point2D position) {
		Planet block = this.isPlanetCollision(planets, position);
		System.err.println(this + " Collision with "+ block);
		
		if (block == null) {
			this.path.add(this.destination);
			this.displayPath();
		} else {
			double angleToPlanet = this.angleToPoint(position, block.getOrigin());
			double distanceToPlanet = position.distance(block.getOrigin());
			
			int diff = 1;
			Point2D newDest;
			//Très très moche
			while (diff < 360) {
			newDest = new Point2D(
					position.getX() + distanceToPlanet * Math.cos(angleToPlanet + diff),
					position.getY() + distanceToPlanet * Math.sin(angleToPlanet + diff)
				);
			
			if (this.isPlanetCollision(planets, newDest) == null) {
				this.path.add(new Point2D(newDest.getX(), newDest.getY()));
				this.findPath(planets, newDest);
				return;
			}
			
			newDest = new Point2D(
					position.getX() + distanceToPlanet * Math.cos(angleToPlanet - diff),
					position.getY() + distanceToPlanet * Math.sin(angleToPlanet - diff)
				);
			
			if (this.isPlanetCollision(planets, newDest) == null) {
				this.path.add(new Point2D(newDest.getX(), newDest.getY()));
				this.findPath(planets, newDest);
				return;
			}
			
			diff += 1;
			}
			
		}
		
		
	}

		//Vont-elles rester statique lorsque l'on aura plusieurs types de vaisseaux ?

	public static int getWidth() {return width;}
	public static int getHeight() {return height;}
	
	public double getSpeed() {return this.speed;}
	public int getDamage() {return height;}
	
	
	
	public void displayPath() {
		System.err.println("====================================");
		for (Point2D p:this.path) {
			System.err.println(p.getX() + " " + p.getY());
		}
		System.err.println("====================================");
	}

}
