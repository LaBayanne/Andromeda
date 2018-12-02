package src_basic.Model;

import java.util.ArrayList;
import java.lang.Math;

import javafx.geometry.Point2D;

public class Squad{
	private ArrayList<StarShip> starships;
	private Point2D destination;
	private int nbUnit;
	private StarShip starshipModel;
	private Planet planetOrigin;
	private Planet destinationPlanet;
	
	public Squad(int nbUnit, StarShip starship, Planet planet) {
		this.nbUnit = nbUnit;
		this.starships = new ArrayList<StarShip>();
		this.starshipModel = new StarShip(starship);
		this.planetOrigin = planet;
	}
	
	public void setDestinationPlanet(Planet planet) {
		this.destinationPlanet = planet;
		for (StarShip starship : this.starships) {
			starship.setDestination(planet);
		}
	}
	
	public void findPath(ArrayList<Planet> planets) {
		for (StarShip starship: this.starships) {
			starship.findPath(planets, starship.getPosition());
		}
	}

	public void add(StarShip starship) {
		this.starships.add(starship);
	}
	/**
	 * 
	 * @return
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
		
		if(this.nbUnit * width > perimeter) {
			restUnit = (int)(this.nbUnit * width - perimeter) / width;
			this.nbUnit = (int)perimeter / width;
		}
		double angle = 0;
		double angleDist = 360.0 / this.nbUnit;
		
		for(int i = 0; i < this.nbUnit; i++) {
			
			x = xCenter + (radius + width) * Math.cos(angle * 3.14 / 180) - height / 2;
			y = yCenter + (radius + height) * Math.sin(angle * 3.14 / 180) - height / 2;
			this.starshipModel.setPosition(new Point2D(x, y));
			this.starships.add(new StarShip(this.starshipModel));
			angle += angleDist;
		}
		return restUnit;
	}

	public ArrayList<StarShip> getStarships() {
		return this.starships;
	}
	
	public void moveStarships() {
		for (StarShip starship: this.starships) {
			starship.move();
		}
	}
	
}