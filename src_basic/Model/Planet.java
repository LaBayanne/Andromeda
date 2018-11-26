package src_basic.Model;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

public class Planet {
	private Point2D origin;
	private double radius;
	
	Hitbox hitbox;
	private int poductionSpeed;
	private int stock;
	private int squadSize;
	
	private StarShip starshipModel;
	
	private int owner;
	
	public Planet() {
		this.origin = new Point2D(0, 0);
		this.radius = 0;
		
		this.poductionSpeed = 0;
		this.stock = 0;
		this.squadSize = 1;
		
		this.owner = 0;
		this.hitbox = new Hitbox(new Circle(this.origin.getX(), this.origin.getY(), this.radius));
		this.starshipModel = new StarShip(new Point2D(0, 0), new Point2D(700, 540), 1.1, 0, 0);
	}
	
	public Planet(Point2D origin, double radius, int productionSpeed, int owner) {
		this.origin = new Point2D(origin.getX(), origin.getY());
		this.radius = radius;
		this.poductionSpeed = productionSpeed;
		this.owner = owner;
		this.stock = 0;
		this.squadSize = 1;
		this.hitbox = new Hitbox(new Circle(this.origin.getX(), this.origin.getY(), this.radius));
		this.starshipModel = new StarShip(new Point2D(0, 0), new Point2D(700, 540), 1.1, 0, 0);
	}
	
	public int getNbUnitPerSquad() {
		return Math.floorDiv(this.squadSize * this.stock, 100);
	}
	
	public Squad generateSquad() {
		
		Squad squad = new Squad(10, this.starshipModel, this);
		squad.repartsStarships();
		return squad;
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
	
	public double getRadius() {
		return this.radius;
	}
}