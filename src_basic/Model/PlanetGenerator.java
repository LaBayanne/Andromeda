package src_basic.Model;

import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Point2D;

public class PlanetGenerator {
	private int maxSize;
	private int minSize;
	
	private int nbMax;
	private int nbMin;
	
	private int windowWidth;
	private int windowHeight;
	
	private Random rand;
	
	public PlanetGenerator(int maxSize, int minSize, int nbMax, int nbMin, int windowWidth, int windowHeight) {
		this.maxSize = maxSize;
		this.minSize = minSize;
		
		this.nbMax = nbMax;
		this.nbMin = nbMin;
		
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		this.rand = new Random();
	}
	public PlanetGenerator() {
		this(0, 0, 0, 0, 0, 0);
	}
	
	private int getRandom(int min, int max) {
		return this.rand.nextInt(max - min + 1) + min;
	}
	
	private boolean isValidPlanet(int originX, int originY, int radius, ArrayList<Planet> planets) {
		if (originX - radius <= 0)	return false;
		if (originY - radius <= 0)  return false;
		
		if (originX + radius >= this.windowWidth) 	return false;
		if (originY + radius >= this.windowHeight)	return false;
		
		for (Planet p: planets) {
			double distance = p.getOrigin().distance(originX, originY);
			double planetTotalSize = p.getRadius() + radius;
			
			double distanceBetweenPlanet = distance - planetTotalSize;
			
			//Distance min : 20% sum of the two radius
			if (distanceBetweenPlanet < 0.2 * (p.getRadius() + radius)) {
				return false;
			}
			
		}
		return true;
	}
	
	public ArrayList<Planet> generate() {
		ArrayList<Planet> planetList = new ArrayList<>();
		
		int nbPlanet = getRandom(this.nbMin, this.nbMax);
		int originX, originY;
		int radius;
		
		for (int i = 0; i < nbPlanet; i++) {
			//public Planet(Point2D origin, double radius, double productionSpeed, int owner)
			do {
				originX = this.getRandom(50, this.windowWidth);
				originY = this.getRandom(50, this.windowHeight);
				
				radius = this.getRandom(this.minSize, this.maxSize);
				
				
			}
		}
		return planetList;
	}
}
