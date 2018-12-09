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
	
	private Planet target;
	
	private double timerMax;
	private double timer;
	
	public Planet() {
		this.origin = new Point2D(0, 0);
		this.radius = 0;
		
		this.poductionSpeed = 0;
		this.stock = 0;
		this.squadSize = 100;//100 percents by default
		
		this.owner = 0;
		this.hitbox = new Hitbox(new Circle(this.origin.getX(), this.origin.getY(), this.radius));
		this.starshipModel = new StarShip(new Point2D(0, 0), new Point2D(700, 540), 0.1, 0, 0, 0);
		
		this.nbStarshipToGenerate = 0;
		this.timerMax = 60;
		this.timer = 0;
		this.target = null;
	}
	
	public Planet(Point2D origin, double radius, double productionSpeed, int owner) {
		this.origin = new Point2D(origin.getX(), origin.getY());
		this.radius = radius;
		this.poductionSpeed = productionSpeed;
		this.owner = owner;
		this.stock = 0;
		this.squadSize = 100;//100 percent by default
		this.hitbox = new Hitbox(new Circle(this.origin.getX(), this.origin.getY(), this.radius));
		this.starshipModel = new StarShip(new Point2D(0, 0), new Point2D(700, 540), 0.1, 0, 0, owner);
		this.nbStarshipToGenerate = 0;
		this.timerMax = 60;
		this.timer = 0;
		this.target = null;
	}

	public void actualiseStock() {
		this.realStock += this.poductionSpeed;
		this.stock = (int) Math.round(this.realStock);
	}
	
	public void decreaseStock(int nbUnit) {
		this.realStock -= (double) nbUnit;
		this.stock -= nbUnit;
	}
	
	public void increaseStock(int nb) {
		this.realStock += nb;
		this.stock += nb;
	}
	
	public void prepareAttack(Planet target) {
		this.nbStarshipToGenerate += getNbUnitPerSquad();//+= was here //Wut ?
		this.target = target;
	}
	
	public int getNbUnitPerSquad() {
		System.out.println("Squadsize : " + this.squadSize);
		System.out.println("Stock : " + this.stock + " nbUnit : " + 
						(this.stock - this.nbStarshipToGenerate) * this.squadSize/100 + "\n");
		return (this.stock - this.nbStarshipToGenerate) * this.squadSize/100;
	}
	
	public Squad generateSquad() {
		reloadTimer();
		this.starshipModel.setOwner(this.owner);
		Squad squad = new Squad(this.nbStarshipToGenerate, this.starshipModel, this);
		int restUnit = squad.repartsStarships();
		squad.setDestinationPlanet(this.target);
		this.decreaseStock(this.nbStarshipToGenerate - restUnit);
		
		this.nbStarshipToGenerate = restUnit;
		return squad;
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
	
	public int getNbStarshipToGenerate() {return this.nbStarshipToGenerate;}
	
	public Hitbox getHitbox() {
		return this.hitbox;
	}
	
	public void setOrigin(Point2D point) {
		this.origin = new Point2D(point.getX(), point.getY());
	}
	
	public int getStock() {
		return this.stock;
	}
	
	public void setSquadSize(int size) {
		this.squadSize = size;
		if(this.squadSize < 1) {
			this.squadSize = 1;
		}
		else if(this.squadSize > 100) {
			this.squadSize = 100;
		}
	}
	
	public Point2D getOrigin() {
		return new Point2D(this.origin.getX(), this.origin.getY());
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	public int getOwner()	{ return this.owner; }
	
}