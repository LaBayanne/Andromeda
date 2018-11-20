package src_basic.Model;

import java.awt.Point;

import javafx.geometry.*;

public class Planet {
	private Point2D origin;
	private double radius;
	
	private int poductionSpeed;
	private int stock;
	private int squadSize;
	
	private int owner;
	
	public Planet() {
		this.origin = new Point2D(0, 0);
		this.radius = 0;
		
		this.poductionSpeed = 0;
		this.stock = 0;
		this.squadSize = 0;
		
		this.owner = 0;
	}
	
	public Planet(Point2D origin, double radius, int productionSpeed) {
		this.origin = new Point2D(origin.getX(), origin.getY());
		this.radius = radius;
		this.poductionSpeed = productionSpeed;
	}
	
	public int getNbUnitPerSquad() {
		return Math.floorDiv(this.squadSize * this.stock, 100);
	}
	
	// Return true if the new squadSize is correct
	public boolean setSquadSize(int newSize) {
		
		if (newSize > 100 || newSize < 0) {
			return false;
		}
		
		this.squadSize = newSize;
		
		return true;
	}
	
	public void setOwner(int owner) {
		this.owner = owner;
	}
	
	public int getStock() {
		return this.stock;
	}
	
	public Point2D getOrigin() {
		return new Point2D(this.origin.getX(), this.origin.getY());
	}
}