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
		angleMax = 20.0;
		height = 30;
		width = 30;
	}
	
	private Point2D position;
	private Point2D destination;
	private int speed;
	private double angle;//In °
	private int damage;
	private static String className;
	private Hitbox hitbox;
	
	public StarShip(Point2D position, Point2D destination,int speed, int damage, double angle) {
		this.position = new Point2D(position.getX(), position.getY());
		this.destination = new Point2D(destination.getX(), destination.getY());
		this.speed = speed;
		this.damage = damage;
		this.angle = 0;
		
		this.className = "StarShip"; // Besoin pour la création dynamique des startships
	}
	
	
	public StarShip() {
		this(Point2D.ZERO, Point2D.ZERO, 0, 0, 0.0);
	}
	
	
	public Point2D getPosition() {
		return new Point2D(this.position.getX(), this.position.getY());
	}

	// Plutôt faire une copie ?
	public void setPosition(Point2D position) {
		this.position = position;
	}

	
	public Point2D getDestination() {
		return new Point2D(this.destination.getX(), this.destination.getY());
	}

	
	public void setDestination(Point2D destination) {
		this.destination = destination;
	}
	
	
	private void calculateNewAngle() {
		double angleToDest = Math.toDegrees(Math.atan2(this.destination.getY() - this.position.getY(),  this.destination.getX() - this.position.getX()));
		
		
		System.err.println("Destination : " + this.destination.getX() + " : " + this.destination.getY());
		System.err.println("Angle to dest : " + angleToDest);
		
		if (Math.abs(this.angle - angleToDest) > this.angleMax) {
			this.angle += (angleToDest/Math.abs(angleToDest)) * angleMax;
		} else {
			this.angle += this.angle - angleToDest;
		}
	}
	
	
	private Point2D calculateNewPos() {
		
		double dx = Math.floor(this.speed * Math.cos(Math.toRadians(this.angle)));
		double dy = Math.floor(this.speed * Math.sin(Math.toRadians(this.angle)));
		
		System.err.println("DX : " + dx + " DY : " + dy);
		
		Point2D newPos = new Point2D(this.position.getX() + dx, this.position.getY() + dy);
		return newPos;
	}
	
	
	public void move() {
		System.err.println("\n\n\n\nOld angle : " + this.angle);
		this.calculateNewAngle();
		System.err.println("Calculate angle : " + this.angle);
		Point2D newPos = this.calculateNewPos();
		
		//Checker si collision avec planete. Que faire s'il y a collision innatendue ?
		
		System.err.println("New ship pos : " + newPos.getX() + ":" + newPos.getY());
		this.setPosition(newPos);
	}
	
	public static Squad makeSquad(int nbUnit) {
		Squad squad = new Squad(nbUnit);
		
		for (int i = 0; i < nbUnit; i++) {
			try {
				squad.add((StarShip) Class.forName(className).newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return squad;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

}
