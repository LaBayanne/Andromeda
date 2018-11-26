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
	
	
	private void calculateNewAngle() {
		double angleToDest = Math.toDegrees(Math.atan2(this.destination.getY() - this.position.getY(),  this.destination.getX() - this.position.getX()));
		
		
		System.err.println("Destination : " + this.destination.getX() + " : " + this.destination.getY());
		System.err.println("Angle to dest : " + angleToDest);
		
		if (Math.abs(this.angle - angleToDest) > this.angleMax) {
			this.angle += (angleToDest/Math.abs(angleToDest)) * angleMax;
		} else {
			this.angle = this.angle - angleToDest;
		}
	}
	
	
	private Point2D calculateNewPos() {
		
		double dx = Math.round(this.speed * Math.cos(Math.toRadians(this.angle)));
		double dy = Math.round(this.speed * Math.sin(Math.toRadians(this.angle)));
		
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
	
	
	//Pourquoi mettre ça dans StarShip ? Ce n'est pas à l'unité de créer son escouade
	
	/*public static Squad makeSquad(int nbUnit) {
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
	}*/

	public static int getWidth() {return width;}
	public static int getHeight() {return height;}
	public static int getSpeed() {return width;}
	public static int getDamage() {return height;}

}
