package Model.StarShip;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Geometry.Circle;
import Geometry.Point;
import Model.Planet.Planet;

/**
 * Represent an escadron on the game
 * @author chocorion and labayanne
 *
 */
public class Squad implements Serializable {


	private ArrayList<StarShip> starships;

	private int nbUnit;
	private StarShip starshipModel;
	
	private Planet planetOrigin;
	
	private Planet destinationPlanet;
	private Point destination;
	
	private int owner;
	
	public Squad(int nbUnit, StarShip starship, Planet planet, int owner) {
		this.nbUnit = nbUnit;
		this.starships = new ArrayList<StarShip>();
		String name = starship.getClass().getName();
		name = name.replace("Model.StarShip.", "");
		switch (name) {
		
			case "Arrow":
				this.starshipModel = new Arrow(starship);
				break;
			
			case "Finger":
				this.starshipModel = new Finger(starship);
				break;
			
			case "MoveCursor":
				this.starshipModel = new MoveCursor(starship);
				break;
		}
		this.planetOrigin = planet;
		this.owner = owner;
	}
	
	/**
	 * Change the destination of all starship in the squad
	 * @param planet	Destination of all starships
	 */
	public void setDestinationPlanet(Planet planet) {
		this.destinationPlanet = planet;
		this.destination = planet.getCollisionShape().getOrigin();
		
		for (StarShip starship : this.starships) {
			starship.setDestination(planet);
		}
	}
	
	/**
	 * Remove the starship of its list.
	 * @param starship	The starship to remove
	 */
	public void destroy(StarShip starship) {
		this.starships.remove(starship);
	}
	
	/**
	 * Check if a starship of the squad is in collition with a point
	 * @param x	x value of the point
	 * @param y	y value of the point
	 * @return	true if there is a collition, false else
	 */
	public boolean isStarshipCollision(double x, double y) {
		for (StarShip s : this.starships) {
			if (s.getCollisionShape().collision(new Point(x, y))) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check collision between circle and starship.
	 * @param c the circle
	 * @return	true if collision, else false.
	 */
	public boolean isStarshipCollision(Circle c) {
		for (StarShip ship : this.starships) {
			if (c.collision(ship.getCollisionShape())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @return destination planet
	 */
	public Planet getDestinationPlanet() {
		return this.destinationPlanet;
	}

	/**
	 * Add a starship in the squad
	 * @param starship	The starship to add
	 */
	public void add(StarShip starship) {
		this.starships.add(starship);
	}

	/**
	 * Reparts all the starships around the planet
	 * @return the number of starship that can't be placed on orbit
	 */
	public int repartsStarships() {
		
		double x, y;
		
		double xCenter = this.planetOrigin.getOrigin().getX();
		double yCenter = this.planetOrigin.getOrigin().getY();
		double radius = this.planetOrigin.getRadius();
		
		int restUnit = 0;
		double perimeter = 2 * 3.14 * radius;
		
		int width = this.starshipModel.getWidth();
		int height = this.starshipModel.getHeight();
		
		//To much starships for the planet !
		if(this.nbUnit * width > perimeter) {
			restUnit = (int)(this.nbUnit * width - perimeter) / width;
			this.nbUnit = (int)perimeter / width;
		}
		
		double angle = 0;
		double angleDist = 360.0 / this.nbUnit;
		
		for(int i = 0; i < this.nbUnit; i++) {
			
			x = xCenter + (radius + width) * Math.cos(angle * 3.14 / 180) - height / 2;
			y = yCenter + (radius + height) * Math.sin(angle * 3.14 / 180) - height / 2;
			
			this.starshipModel.setPosition(new Point(x, y));
			String name =  starshipModel.getClass().getName();
			name = name.replace("Model.StarShip.", "");
			switch (name) {
			
				case "Arrow":
					this.starships.add(new Arrow(this.starshipModel));
					break;
				
				case "Finger":
					this.starships.add(new Finger(this.starshipModel));
					break;
				
				case "MoveCursor":
					this.starships.add(new MoveCursor(this.starshipModel));
					break;
			}
			
			
			angle += angleDist;
		}
		
		return restUnit;
	}

	/**
	 * 
	 * @return A list of the starships of the squad
	 */
	public ArrayList<StarShip> getStarships() {
		return this.starships;
	}
	
	/**
	 * Move all the starships of the squad
	 * @param delta		current time of the frame
	 * @param planets	List of all planets of the game, need for starship's pathfinding
	 */
	public void moveStarships(double delta, ArrayList<Planet> planets) {
		Iterator<StarShip> starshipIterator = this.starships.iterator();
		StarShip s;
		while (starshipIterator.hasNext()) {
			s = starshipIterator.next();
			s.move(delta, planets);
			
			if (s.isFinished()) {
				starshipIterator.remove();
				nbUnit--;
			}
		}
	}
	
	/**
	 * Change the owner of all starships in the squad
	 * @param owner
	 */
	public void setOwner(int owner) {
		for (StarShip starship : this.starships) {
			starship.setOwner(owner);
		}
	}
	
	public int getNbUnit() { return this.nbUnit;}
	public int getOwner()  { return this.owner; }
	
}