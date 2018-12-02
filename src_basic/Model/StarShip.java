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
		this.position = new Point2D(position.getX(), position.getY());
		this.destination = new Point2D(destination.getX(), destination.getY());
		this.speed = speed;
		this.damage = damage;
		this.angle = 0;
		this.hitbox = this.hitbox = new Hitbox(new Rectangle(this.position.getX(), this.position.getY(), this.getWidth(), this.getHeight()));
		
		this.className = "StarShip"; // Besoin pour la création dynamique des startships
		
	}
	
	public StarShip(StarShip starship) {
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
	
	
	private Point2D calculateNewPos(Point2D dest) {
		
		double angle = this.angleToPoint(this.position, dest);
		
		double dx = this.speed * Math.cos(Math.toRadians(angle));
		double dy = this.speed * Math.sin(Math.toRadians(angle));
		
		Point2D newPos = new Point2D(this.position.getX() + dx, this.position.getY() + dy);
		return newPos;
	}
	
	
	public void move() {
		if(this.path.isEmpty()) {
			return;
		}
		if (this.position.distance(this.path.get(0)) < 5) {
			this.path.remove(0);
			if (this.path.isEmpty()) {
				return; //Arrivé à destination
			}
		}
		Point2D newPos = this.calculateNewPos(this.path.get(0));
		
		//Checker si collision avec planete. Que faire s'il y a collision innatendue ?

		this.setPosition(newPos);
	}


	public Hitbox getHitbox() {
		return this.hitbox;
	}
	
	public Planet isPlanetCollision(ArrayList<Planet> planets, Point2D position1, Point2D position2) {
		Line l = new Line(position1.getX(), position1.getY(), position2.getX(), position2.getY());
		
		for (Planet planet:planets) {
			if (planet == this.destinationPlanet) {
				continue;//Osef si c'est la planète de destination
			}
			if (planet.getHitbox().collision(l)) {
				System.err.println("COLLISION DETECTED !");
				return planet;
			}
		}
		
		return null;
	}
	
	public void findPath(ArrayList<Planet> planets, Point2D position) {
		//On regarde si une planète se trouve entre nous et la planete de destination
		Planet block = this.isPlanetCollision(planets, position, this.destination);
		
		if (block == null) {
			//Si ce n'est pas le cas, on ajout la destination dans path, l'array qui contient toutes les destinations intermédiaires et on quitte
			this.path.add(this.destination);
		} else {
			
			double angleToPlanet = this.angleToPoint(position, this.destination);
			double distance = position.distance(block.getOrigin()); //Distance jusqu'au prochain point
			
			boolean alreadySeen = false;
			double diff = 1;
			Point2D newDest;
			
			//On cherche un point qui nous rapproche de la destination mais sans rencontrer de planete depuis notre position jusqu'à ce point
			while (diff < 360) {
				alreadySeen = false;
				
				newDest = new Point2D(
					position.getX() + distance * Math.cos(Math.toRadians(angleToPlanet + diff)),
					position.getY() + distance * Math.sin(Math.toRadians(angleToPlanet + diff))
				);
				
				//On vérifie qu'on n'est pas déjà passé par ce point, si oui on passe
				for (Point2D p:this.path) {
					if (p.getX() == newDest.getX() && p.getX() == newDest.getX()) {
						alreadySeen = true;
					}
				}
				if (!alreadySeen) {
					if (this.isPlanetCollision(planets, position, newDest) == null) {
						this.path.add(new Point2D(newDest.getX(), newDest.getY()));
						this.findPath(planets, newDest);
						return;
					}
				}
				alreadySeen = false;
				
				//Même chose mais dans l'autre sens
				newDest = new Point2D(
					position.getX() + distance * Math.cos(Math.toRadians(angleToPlanet - diff)),
					position.getY() + distance * Math.sin(Math.toRadians(angleToPlanet - diff))
				);
				for (Point2D p:this.path) {
					if (p.getX() == newDest.getX() && p.getX() == newDest.getX()) {
						alreadySeen = true;
					}
				}
				if (!alreadySeen) {
					if (this.isPlanetCollision(planets, position, newDest) == null) {
						this.path.add(new Point2D(newDest.getX(), newDest.getY()));
						this.findPath(planets, newDest);
						return;
					}
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

	public void calculatePath(ArrayList<Planet> planets, Point2D position) {
		this.path = new ArrayList<>();
		this.findPath(planets, position);
		
		this.displayPath();
	}
	
	public void displayPath() {
		System.err.println("====================================");
		for (Point2D p:this.path) {
			System.err.println(p.getX() + " " + p.getY());
		}
		System.err.println("====================================");
	}

}
