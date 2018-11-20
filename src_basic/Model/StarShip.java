package src_basic.Model;

import javafx.geometry.*;

public class StarShip {
	private Point2D position;
	private Point2D destination;
	private int speed;
	private int damage;
	
	public StarShip() {
		this.position = new Point2D(0, 0);
		this.destination = new Point2D(0, 0);
		this.speed = 0;
		this.damage = 0;
		
	}
	
	public StarShip(Point2D position, Point2D destination,int speed, int damage) {
		this.position = new Point2D(position.getX(), position.getY());
		this.destination = new Point2D(destination.getX(), destination.getY());
		this.speed = speed;
		this.damage = damage;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public Point2D getDestination() {
		return destination;
	}

	public void setDestination(Point2D destination) {
		this.destination = destination;
	}
}