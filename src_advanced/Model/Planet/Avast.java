package src_advanced.Model.Planet;

import java.util.ArrayList;

import src_advanced.Geometry.Point;
import src_advanced.Model.StarShip.Squad;
import src_advanced.Model.StarShip.StarShip;

public class Avast extends Planet{
	
	private static int WIDTH = 250;
	
	private Point dest;
	
	final static private double PRODUCTION_SPEED;
	static {
		PRODUCTION_SPEED = 0.003;
	}
	
	public Avast(double x, double y) {
		super(new Point(x - WIDTH / 2 - 5, 700), WIDTH / 2, 0.05, 6);
		this.dest = new Point(x - WIDTH / 2 - 5, y - WIDTH / 2 - 35);
		this.getCollisionShape().setRadius(WIDTH / 2);
	}
	
	public int getWidth() {return WIDTH;}
	
	public void tick(double delta, ArrayList<Planet> planets, ArrayList<Squad> squads) {
		this.move(delta);
		this.destroyEncounter(planets, squads);
	}
	
	public void destroyEncounter(ArrayList<Planet> planets, ArrayList<Squad> squads) {
		ArrayList<Planet> toRemovePlanet = new ArrayList<Planet>();
		ArrayList<StarShip> toRemoveStarship = new ArrayList<StarShip>();
		
		for(Planet planet : planets){
			if(planet.getCollisionShape().collision(this.getCollisionShape())) {
				planet.destroyed(planets, squads);
				toRemovePlanet.add(planet);
			}
		}
		for(Squad squad : squads) {
			toRemoveStarship = new ArrayList<StarShip>();
			for(StarShip starship : squad.getStarships()){
				if(starship.getCollisionShape().collision(this.getCollisionShape())) {
					toRemoveStarship.add(starship);
				}
			}
			for(StarShip starship : toRemoveStarship) {
				squad.destroy(starship);
			}
		}
		
		for(Planet planet : toRemovePlanet) {
			planets.remove(planet);
		}
		
	}
	
	public void move(double delta) {
		if(this.dest.getY() < this.getOrigin().getY()) {
			this.getOrigin().translate(0, -0.11 * delta);
		}
	}

}
