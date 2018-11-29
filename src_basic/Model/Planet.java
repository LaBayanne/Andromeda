package src_basic.Model;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

public class Planet {
	private Point2D origin;
	private double radius;
	
	Hitbox hitbox;
	private double poductionSpeed;
	
	private int stock;
	private double realStock; // TODO Faire une fonction pour décrémenter le stock, qui prends en compte stock ET realStock
	
	private int squadSize;
	
	private StarShip starshipModel;
	
	private int owner;
	
	private int nbStarshipToGenerate;
	
	private double timerMax;
	private double timer;
	
	public Planet() {
		this.origin = new Point2D(0, 0);
		this.radius = 0;
		
		this.poductionSpeed = 0;
		this.stock = 0;
		this.squadSize = 1;
		
		this.owner = 0;
		this.hitbox = new Hitbox(new Circle(this.origin.getX(), this.origin.getY(), this.radius));
		this.starshipModel = new StarShip(new Point2D(0, 0), new Point2D(700, 540), 1.1, 0, 0);
		
		this.nbStarshipToGenerate = 0;
		this.timerMax = 60;
		this.timer = 0;
	}
	
	public Planet(Point2D origin, double radius, double productionSpeed, int owner) {
		this.origin = new Point2D(origin.getX(), origin.getY());
		this.radius = radius;
		this.poductionSpeed = productionSpeed;
		this.owner = owner;
		this.stock = 0;
		this.squadSize = 1;
		this.hitbox = new Hitbox(new Circle(this.origin.getX(), this.origin.getY(), this.radius));
		this.starshipModel = new StarShip(new Point2D(0, 0), new Point2D(700, 540), 1.1, 0, 0);
		this.nbStarshipToGenerate = 0;
		this.timerMax = 60;
		this.timer = 0;
	}

	public void actualiseStock() {
		this.realStock += this.poductionSpeed;
		this.stock = (int) Math.round(this.realStock);
	}
	
	public void prepareAttack() {
		this.nbStarshipToGenerate += getNbUnitPerSquad();
	}
	
	public int getNbUnitPerSquad() {
		return 20;
		//return Math.floorDiv(this.squadSize * this.stock, 100);
	}
	
	public Squad generateSquad() {
		reloadTimer();
		Squad squad = new Squad(this.nbStarshipToGenerate, this.starshipModel, this);
		this.nbStarshipToGenerate = squad.repartsStarships();
		return squad;
	}
	
	public int getNbStarshipToGenerate() {return this.nbStarshipToGenerate;}
	
	// Return true if the new squadSize is correct
	public boolean setSquadSize(int newSize) {
		
		if (newSize > 100 || newSize < 0) {
			return false;
		}
		
		this.squadSize = newSize;
		
		return true;
	}
	
	public double decreaseTimer() {
		if(this.timer > 0)
			this.timer -= 1;
		return this.timer;
	}
	
	public void reloadTimer() {
		this.timer = this.timerMax;
	}
	
	public void setOwner(int owner) {
		this.owner = owner;
	}
	
	public Hitbox getHitbox() {
		return this.hitbox;
	}
	
	public void setOrigin(Point2D point) {
		this.origin = new Point2D(point.getX(), point.getY());
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