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
	
	static {
		angleMax = 20.0;
	}
	
	private Point2D position;
	private Point2D destination;
	private int speed;
	private double angle;//In °
	private int damage;
	
	public StarShip(Point2D position, Point2D destination,int speed, int damage, double angle) {
		this.position = new Point2D(position.getX(), position.getY());
		this.destination = new Point2D(destination.getX(), destination.getY());
		this.speed = speed;
		this.damage = damage;
		this.angle = 0;
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
		//Checker si collision avec planete
		
		this.setPosition(newPos);
	}
}