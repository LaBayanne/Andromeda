package src_basic.Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Squad{
	private ArrayList<StarShip> starships;
	private Point2D destination;
	private int nbUnit;
	
	public Squad(int nbUnit) {
		this.nbUnit = nbUnit;
	}

	public void add(StarShip starship) {
		this.starships.add(starship);
	}
	/**
	 * Set the positions of starship at the surface of the planet
	 * @param planetRadius  Radius of the planet
	 * @param planetPos		Position of the planet
	 */
	public void repartsStarships(double planetRadius, Point2D planetPos) {
		//Penser à bien répartir les vaisseaux du bon côté de la planette
	}

	public ArrayList<StarShip> getStarships() {
		return this.starships;
	}
	
}