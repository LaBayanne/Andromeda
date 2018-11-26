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
	
	public Squad(int nbUnit, StarShip starship, Planet planet) {
		this.nbUnit = nbUnit;
		this.starships = new ArrayList<StarShip>();
		this.starshipModel = new StarShip(starship);
		this.planetOrigin = planet;
	}

	public void add(StarShip starship) {
		this.starships.add(starship);
	}
	/**
	 * Set the positions of starship at the surface of the planet
	 * @param planetRadius  Radius of the planet
	 * @param planetPos		Position of the planet
	 */
	public int repartsStarships() {
		//Penser à bien répartir les vaisseaux du bon côté de la planette
		double x, y;
		double xCenter = this.planetOrigin.getOrigin().getX() + this.planetOrigin.getRadius();
		double yCenter = this.planetOrigin.getOrigin().getY() + this.planetOrigin.getRadius();
		
		int restUnit = 0;
		if(this.nbUnit > 360) {
			restUnit = this.nbUnit - 360;
			this.nbUnit = 360;
		}
		double angle = 0;
		double angleDist = 360.0 / this.nbUnit;
		
		for(int i = 0; i < this.nbUnit; i++) {
			
			x = xCenter + (this.planetOrigin.getRadius() + 5) * Math.cos(angle * 3.14 / 180);
			y = yCenter + (this.planetOrigin.getRadius() + 5) * Math.sin(angle * 3.14 / 180);
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