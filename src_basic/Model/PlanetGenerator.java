package src_basic.Model;

import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Point2D;

/**
 * This class is used for generating planets *
 * @author chocorion
 *
 */
public class PlanetGenerator {
	private double maxSize;
	private double minSize;
	
	private int nbMax;
	private int nbMin;
	
	private int windowWidth;
	private int windowHeight;
	
	private Random rand;
	
	public PlanetGenerator(double maxSize, double minSize, int nbMax, int nbMin, int windowWidth, int windowHeight) {
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
	
	private double getRandomDouble(double min, double max) {
		double random =  this.rand.nextDouble();
		
		return min + (random * (max - min));
	}
	
	/**
	 * Check if a planet is on the screen with no collisions with other
	 * planets already generates
	 * @param originX	The x coord of the planet's center
	 * @param originY	The y coord of the planet's center
	 * @param radius	Planet's radius
	 * @param planets	List of planet already generated
	 * @return
	 */
	private boolean isValidPlanet(int originX, int originY, double radius, ArrayList<Planet> planets) {
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
	/**
	 * Generate planets
	 * @return the ArrayList of planets generate
	 */
	public ArrayList<Planet> generate() {
		ArrayList<Planet> planetList = new ArrayList<>();
		
		int nbPlanet = getRandom(this.nbMin, this.nbMax);
		int originX, originY;
		double radius, productionSpeed;
		
		for (int i = 0; i < nbPlanet; i++) {
			//public Planet(Point2D origin, double radius, double productionSpeed, int owner)
			do {
				originX = this.getRandom(50, this.windowWidth);
				originY = this.getRandom(50, this.windowHeight);
				
				radius = this.getRandomDouble(this.minSize, this.maxSize);
				
				if (this.isValidPlanet(originX, originY, radius, planetList)) {
					productionSpeed = this.getRandomDouble(0.001, 0.2);//Faire en fonction de la taille et pas de manière aléatoire
					planetList.add(new Planet(new Point2D(originX, originY), radius, productionSpeed, 0)); //Production speed and owner to define
					break;
				} else {
					continue;
				}
			} while (true);
		}
		for (Planet p : planetList) {
			p.increaseStock(this.getRandom(0, 30));
		}
		return planetList;
	}
	
	/**
	 * Give a planet to all players
	 * @param nbPlayers	How many players in the game
	 * @param planets	The list of all planets
	 */
	public void givePlanet(int nbPlayers, ArrayList<Planet> planets) {
		System.err.println("Welcome in givePlanet !");
		int nbPlanet = planets.size();
		if (nbPlayers > nbPlanet) {
			System.err.println("Problem in planet generation : To many players !");
			System.exit(1);
		}
		
		int planetNumber[] = new int[nbPlayers];
		boolean alreadyIn;
		for (int i = 0; i < nbPlayers; i++) {
			System.err.println("Checking planet for player number " + i + 1);
			do {
				planetNumber[i] = this.getRandom(0, nbPlanet);
				System.err.println("\tGet planet number " + planetNumber[i]);
				alreadyIn = false;
				
				for (int j = 0; j < i; j++) {
					if (planetNumber[j] == planetNumber[i]) {
						System.err.println("\tPlayer number " + (j + 1) + " already pocess this planet !");
						alreadyIn = true;
						break;
					}
				}
			} while (alreadyIn);
			planets.get(planetNumber[i]).setOwner(i + 1);
		}
	}
}
