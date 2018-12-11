package src_basic.Model;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.fail;
import static java.time.Duration.ofSeconds;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;

class PlanetGeneratorTest {
	
	@Rule
	 public Timeout globalTimeout = Timeout.seconds(3);
	
	@Test
	void testGenerate() {
		
		PlanetGenerator generator = new PlanetGenerator(100, 100, 1000, 500, 10000, 10000);
		ArrayList<Planet> planets = generator.generate();
		double dist = 0;
		double collisionLimit = PlanetGenerator.getCollisionLimit();
		double radiusSum = 0;
		
		for (Planet p : planets) {
			for (Planet p2 : planets) {
				if (p.equals(p2)) {
					continue;
				}
				
				dist = p.getOrigin().distance(p2.getOrigin());
				
				radiusSum = p.getRadius() + p2.getRadius();
				if (dist - (radiusSum) < collisionLimit * radiusSum) {
					fail("Two planets to close !");
				}
				
			}
		}
	}

	void testGivePlanet() {
		//PlanetGenerator(double maxSize, double minSize, int nbMax, int nbMin, int windowWidth, int windowHeight)
		System.err.println("Yoo");
		PlanetGenerator generator = new PlanetGenerator(5, 6, 5, 5, 50, 50);
		System.err.println("End planet generation");
		ArrayList<Planet> planets = generator.generate();
		generator.givePlanet(4, planets);
		System.err.println("End planet giving");
		
		boolean tab[] = { false, false, false, false, false };
		int index;
		
		for (int i = 0; i < 5; i++) {
			System.err.println("Yoo");
			index = planets.get(i).getOwner();
			
			if (tab[index]) {
				fail("Two players with same planet ! ");
			} else {
				tab[index] = true;
			}
		}
	}
	
	@Test
	void testGivePlanetNoExceed1Second() {
		assertTimeoutPreemptively(ofSeconds(1), () -> {
	         testGivePlanet();
	    });
	}
	

}
