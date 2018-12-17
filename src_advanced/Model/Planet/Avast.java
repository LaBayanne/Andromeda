package src_advanced.Model.Planet;

import java.util.ArrayList;
import java.util.Random;

import src_advanced.Geometry.Point;
import src_advanced.Model.StarShip.Arrow;
import src_advanced.Model.StarShip.Squad;
import src_advanced.Model.StarShip.StarShip;

public class Avast extends Planet{
	
	private static int WIDTH = 250;
	
	private Point dest;
	private int maxVirus;
	
	public Avast(double x, double y) {
		super(new Point(x - WIDTH / 2 - 5, 700), WIDTH / 2, 0.01, 6);
		this.dest = new Point(x - WIDTH / 2 - 5, y - WIDTH / 2 - 35);
		this.getCollisionShape().setRadius(WIDTH / 2);
		this.maxVirus = 300;
	}
	
	public int getWidth() {return WIDTH;}
	
	public void tick(double delta, ArrayList<Planet> planets, ArrayList<Squad> squads) {
		this.move(delta);
		this.destroyEncounter(planets, squads);
		this.actualiseStock(delta);
		if(this.stock > this.maxVirus) {
			setAttackGroup(maxVirus, planets.get(0));
		}
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
	
	public void actualiseStock(double delta) {
		
		this.realStock += this.productionSpeed * delta;
		this.stock = (int) Math.round(this.realStock);
		
		if(this.nbStarshipToGenerate> this.stock) {
			this.nbStarshipToGenerate = (this.stock - 1);
		}
	}
	
	
	public void setAttackGroup(int nbAttackers, Planet target) {
		setNbStarshipToGenerate(nbAttackers);
		this.target = target;
	}
	
	public void squadDisperseAttack(Squad squad, ArrayList<Planet> planets) {
		int length = planets.size();
		int number;
		Random rand = new Random();
		for(StarShip starship : squad.getStarships()) {
			number = rand.nextInt(length);
			starship.setDestination(planets.get(number));
		}
	}
	
	/**
	 * Create the squad with nbStarshipToGenerate Unit
	 * @return	The squad create
	 */
	public Squad generateSquad(ArrayList<Planet> planets) {
		reloadTimer();
		this.starshipModel = new Arrow(this.owner);
		
		Squad squad = new Squad(this.nbStarshipToGenerate, this.starshipModel, this, this.owner);
		int restUnit = squad.repartsStarships();
		this.squadDisperseAttack(squad, planets);
		
		this.decreaseStock((this.nbStarshipToGenerate - restUnit));
		
		this.nbStarshipToGenerate = restUnit;
		return squad;
	}
	
	public void move(double delta) {
		if(this.dest.getY() < this.getOrigin().getY()) {
			this.getOrigin().translate(0, -0.11 * delta);
		}
	}

}
