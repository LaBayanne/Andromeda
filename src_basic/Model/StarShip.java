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
	private String className;
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
		double angleToDest = this.position.angle(destination);
		
		if (Math.abs(angleToDest) > this.angleMax) {
			this.angle += angleToDest/Math.abs(angleToDest) * angleMax;
		} else {
			this.angle += angleToDest;
		}
	}
	
	
	private Point2D calculateNewPos() {
		
		double dx = this.speed * Math.cos(this.angle);
		double dy = this.speed * Math.sin(this.angle);
		
		Point2D newPos = new Point2D(this.position.getX() + dx, this.position.getY() + dy);
		return newPos;
	}
	
	
	public void move() {
		this.calculateNewAngle();
		Point2D newPos = this.calculateNewPos();
		//Checker si collision avec planete. Que faire s'il y a collision innatendue ?
		
		this.setPosition(newPos);
	}
	
	public Squad makeSquad(int nbUnit) {
		Squad squad = new Squad(nbUnit);
		
		for (int i = 0; i < nbUnit; i++) {
			try {
				squad.add((StarShip) Class.forName(this.className).newInstance());
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
