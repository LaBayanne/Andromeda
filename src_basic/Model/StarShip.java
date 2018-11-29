package src_basic.Model;

import javafx.geometry.*;

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
	
	private Point2D position;
	private Point2D destination;
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
		
		this.className = "StarShip"; // Besoin pour la création dynamique des startships
	}
	
	public StarShip(StarShip starship) {
		this.position = new Point2D(starship.getPosition().getX(), starship.getPosition().getY());
		this.destination = new Point2D(starship.getDestination().getX(), starship.getDestination().getY());
		this.speed = starship.getSpeed();
		this.damage = starship.getDamage();
		this.angle = 0;
		
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
	}
	
	private double angleToDestination() {
		return Math.toDegrees(Math.atan2(this.destination.getY() - this.position.getY(),  this.destination.getX() - this.position.getX()));
	}
	
	
	private Point2D calculateNewPos() {
		
		double angle = this.angleToDestination();
		
		double dx = this.speed * Math.cos(Math.toRadians(angle));
		double dy = this.speed * Math.sin(Math.toRadians(angle));
		
		Point2D newPos = new Point2D(this.position.getX() + dx, this.position.getY() + dy);
		return newPos;
	}
	
	
	public void move() {
		Point2D newPos = this.calculateNewPos();
		
		//Checker si collision avec planete. Que faire s'il y a collision innatendue ?

		this.setPosition(newPos);
	}

		//Vont-elles rester statique lorsque l'on aura plusieurs types de vaisseaux ?
	public static int getWidth() {return width;}
	public static int getHeight() {return height;}
	
	public double getSpeed() {return this.speed;}
	public int getDamage() {return height;}

}
