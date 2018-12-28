package Model.Planet;

import java.util.ArrayList;
import java.util.Random;

import Geometry.Point;
import Model.StarShip.Arrow;
import Model.StarShip.Squad;
import Model.StarShip.StarShip;


/**
 * Represent the BOSS Avast who appears after a moment in the game.
 *
 */
public class Avast extends Planet{
	
	private static int WIDTH = 250;
	
	private Point dest;
	private int maxVirus;
	
	/**
	 * Basic constructor.
	 * @param x	The x position 
	 * @param y The y position
	 */
	public Avast(double x, double y) {
		super(new Point(x - WIDTH / 2 - 5, 700), WIDTH / 2, 0.01, 6);
		this.dest = new Point(x - WIDTH / 2 - 5, y - WIDTH / 2 - 35);
		this.getCollisionShape().setRadius(WIDTH / 2);
		this.maxVirus = 300;
	}
	
	/**
	 * 
	 * @return The width of Avast
	 */
	public int getWidth() {return WIDTH;}
	
	/**
	 * Run every tick of the game. Manage the movement, the stock and the attacks of avast.
	 * @param delta		The delay since last tick
	 * @param planets	ArrayList of planets of the game
	 * @param squads 	ArrayList of squads of the game
	 */
	public void tick(double delta, ArrayList<Planet> planets, ArrayList<Squad> squads) {
		this.move(delta);
		this.destroyEncounter(planets, squads);
		this.actualiseStock(delta);
		if(this.stock > this.maxVirus) {
			setAttackGroup(maxVirus, planets.get(0));
		}
	}
	
	/**
	 * Destroy all the planets and the starships who touch Avast
	 * @param planets ArrayList of planets of the game
	 * @param squads 	ArrayList of squads of the game
	 */
	public void destroyEncounter(ArrayList<Planet> planets, ArrayList<Squad> squads) {
		ArrayList<Planet>   toRemovePlanet = new ArrayList<Planet>();
		ArrayList<StarShip> toRemoveStarship = new ArrayList<StarShip>();
		
		for(Planet planet : planets){
			if(planet.getCollisionShape().collision(this.getCollisionShape())) {
				planet.destroyed(planets, squads);
				toRemovePlanet.add(planet);
			}
		}
		for(Squad squad : squads) {
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
	
	/**
	 * Actualise the stock of Avast.
	 */
	public void actualiseStock(double delta) {
		
		this.realStock += this.productionSpeed * delta;
		this.stock = (int) Math.round(this.realStock);
		
		if(this.nbStarshipToGenerate> this.stock) {
			this.nbStarshipToGenerate = (this.stock - 1);
		}
	}
	
	
	/**
	 * Create the attack group of Avast when the max number of virus is reached.
	 */
	public void setAttackGroup(int nbAttackers, Planet target) {
		setNbStarshipToGenerate(nbAttackers);
		this.target = target;
	}
	
	/**
	 * Reparts the targets of the starships at random.
	 * @param squad		The squad to disperse
	 * @param planets	ArrayList of planets of the game
	 */
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
	
	/**
	 * Move Avast while it has not reached is final position.
	 * @param delta
	 */
	public void move(double delta) {
		if(this.dest.getY() < this.getOrigin().getY()) {
			this.getOrigin().translate(0, -0.11 * delta);
		}
	}

}
