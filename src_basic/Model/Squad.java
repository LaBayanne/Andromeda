package src_basic.Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Squad{
	private ArrayList<Squad> starships;
	private Point2D destination;
	
	
	public Squad() {
		
	}
	/**
	 * Generate a squad of starships
	 * @param planetRadius  The numbers of starships depends of the planet size
	 * @param nbUnits		The numbers of starships wanted
	 * @return	0 if all starships are in squads, 
	 * 			nb starships in excess else
	 */
	public int generateSquad(double planetRadius, int nbUnits) {
		double planetSurface = 2 * Math.PI * planetRadius;
		
		
		return 0;
	}
}