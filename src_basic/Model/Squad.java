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

	
}