package src_basic.Model;

import java.util.Random;

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
}
