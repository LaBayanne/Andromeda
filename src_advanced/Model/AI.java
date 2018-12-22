package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import Model.Planet.Planet;
import Model.StarShip.Squad;

/**
 * Represent a AI in the game.
 */
public class AI implements Serializable {

	private int team;
	private ArrayList<Squad> squads;
	private ArrayList<Planet> planets;
	
	/**
	 * Basic constructor.
	 * @param planets List of the planets of the game
	 * @param squads List of the squads of the game
	 * @param team The team of this AI
	 */
	public AI(ArrayList<Planet> planets, ArrayList<Squad> squads, int team) {
		this.planets = planets;
		this.squads = squads;
		this.team = team;
	}
	
	/**
	 * Run every tick of the game's main loop.
	 * @param delta Delay since the previous tick of the game
	 */
	public void tick(double delta) {
		expansion();
	}
	
	/**
	 * Manage the expansion mode of the AI.
	 * Each planet of the AI's faction calculate the most vulnerable planet it can attack.
	 * If it can take it alone, it launch the number of starships required.
	 * Else, it's added to the group.
	 * While there is a planet in the group or the group can't take a planet,
	 * the group choose the most vulnerable planet for the group, and calculate the number of planets required.
	 * So they attacks the planet.
	 */
	public void expansion() {
		double defence;
		double defenceRef = 0;
		double distance;
		double distanceRef = 0;
		double prevFrequency = 0;
		double frequency = 0;
		
		ArrayList<Planet> targets = new ArrayList<Planet>();
		ArrayList<Planet> attackedTargets = new ArrayList<Planet>();
		ArrayList<Planet> restPlanets = new ArrayList<Planet>();
		ArrayList<AttackedPlanet> attackedPlanets = new ArrayList<AttackedPlanet>();
		ArrayList<Planet> attackers;
		
		Iterator<Planet> iterator;
		
		Planet bestTarget = null;
		Planet target = null;
		
		boolean first = true;
		boolean cont = false;
		
		int nbAttackers = 0;
		int powerAttack;
		int team = this.planets.get(0).getOwner();
		int newTeam = 0;
		
		
		
		//We check if there is a planet which is not in the AI's team
		for(Planet planet : this.planets) {
			newTeam = planet.getOwner();
			if(newTeam != team) {
				cont = true;
				break;
			}
			team = newTeam;
		}
		
		//If no, we didn't nothing
		if(!cont) {
			return;
		}
		
		
		
		//We calculate the numbers of attackers which are attacking each planet
		boolean find = false;
		
		for(Squad squad : this.squads) {
			
			if(squad.getNbUnit() > 0) {
				
				target = squad.getDestinationPlanet();
				find = false;
				
				for(AttackedPlanet planet : attackedPlanets) {
					if(planet.getPlanet() == target){
						find = true;
						planet.addAttackers(squad.getNbUnit());
						break;
					}
				}
				
				if(!find) {
					attackedPlanets.add(new AttackedPlanet(target, squad.getNbUnit()));
				}
			}
		}
		
		
		
		//We check if each planet can take another planet alone. Else we add this planet to a group.
		find = false;
		
		for(Planet ally : this.planets) {
			
			if(ally.getOwner() == this.team) {
				
				for(Planet ennemy : this.planets) {
					
					if(ennemy.getOwner() != this.team && !attackedTargets.contains(ennemy)) {
						
						defence = ennemy.getStock();
						distance = Math.sqrt((ennemy.getOrigin().getX() - ally.getOrigin().getX()) * (ennemy.getOrigin().getX() - ally.getOrigin().getX()) + 
								(ennemy.getOrigin().getY() - ally.getOrigin().getY()) * (ennemy.getOrigin().getY() - ally.getOrigin().getY())) / 10;
						
						if(first) {
							defenceRef = defence;
							distanceRef = distance;
							bestTarget = ennemy;
							first = false;
						}
						else {
							if(distance + defence <= distanceRef + defenceRef) {
								bestTarget = ennemy;
							}
							defenceRef = (defence < defenceRef)?defence:defenceRef;
							distanceRef = (distance < distanceRef)?distance:distanceRef;
						}
					}
				}
				
				//Search the number of attackers of the target
				nbAttackers = 0;
				for(AttackedPlanet planet : attackedPlanets) {
					if(planet.getPlanet() == bestTarget){
						nbAttackers = planet.getNbAttackers();
						break;
					}
				}
				
				//If this planet can take the target alone, attack
				if(bestTarget.getStock() - nbAttackers < ally.getStock()){
					ally.setAttackGroup(bestTarget.getStock() - nbAttackers, bestTarget);
					attackedTargets.add(bestTarget);
				}
				else {
					restPlanets.add(ally);
					targets.add(bestTarget);
				}
				first = true;
			}
		}
		
		
		
		//While the group is not empty, we search the most vulnerable target and we attack it.
		Planet ally;
		
		powerAttack = 0;
		
		while(!restPlanets.isEmpty() && !targets.isEmpty() && powerAttack == 0) {
			
			iterator = restPlanets.iterator();
			attackers = new ArrayList<Planet>();
			bestTarget = null;
			first = true;
			
			//We calculate the frequency of each planet in the targets tab. It will be the best target
			for(Planet planet : targets) {
				frequency = Collections.frequency(targets, planet);
				if(!first) {
					if(frequency > prevFrequency) {
						bestTarget = planet;
					}
				}
				else {
					bestTarget = planet;
				}
				prevFrequency = frequency;
				first = false;
			}
			
			//We search the number of attackers
			nbAttackers = 0;
			for(AttackedPlanet planet : attackedPlanets) {
				if(planet.getPlanet() == bestTarget){
					nbAttackers = planet.getNbAttackers();
					break;
				}
			}
			
			//We verify if we can attack the planet and determine the attacking planets
			while (!restPlanets.isEmpty() && iterator.hasNext() && powerAttack < bestTarget.getStock() - nbAttackers) {
				ally = iterator.next();
				powerAttack += ally.getPower();
				iterator.remove();
				attackers.add(ally);
			}
			
			//if we can
			if(powerAttack >= bestTarget.getStock() - nbAttackers) {
				for(Planet planet : attackers) {
					planet.setAttackGroup(bestTarget.getStock(), bestTarget);
				}
				targets.remove(bestTarget);
				powerAttack = 0;
			}
		}
		
	}
	
	/**
	 * 
	 * @param planets The list of planets
	 */
	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}
	
	/**
	 * 
	 * @param squads The list of squads
	 */
	public void setSquads(ArrayList<Squad> squads) {
		this.squads = squads;
	}
}

/**
 * Used to store the number of starships which are attacking a planet.
 */
class AttackedPlanet{
	private Planet planet;
	private int nbAttackers;
	
	/**
	 * Basic constructor.
	 * @param planet The planet  attacked
	 * @param nbAttackers The number of attackers
	 */
	public AttackedPlanet(Planet planet, int nbAttackers) {
		this.planet = planet;
		this.nbAttackers = nbAttackers;
	}
	
	/**
	 * Add a number of attackers.
	 * @param value The number of attackers to add
	 */
	public void addAttackers(int value) {
		this.nbAttackers += value;
	}
	
	/**
	 * 
	 * @param planet The planet to set.
	 */
	public void setPlanet(Planet planet) {
		this.planet = planet;
	}
	
	/**
	 * 
	 * @param nb The number of attackers to set
	 */
	public void setNbAttackers(int nb) {
		this.nbAttackers = nb;
	}
	
	public Planet getPlanet() {return this.planet;}
	public int getNbAttackers() {return this.nbAttackers;}
}
