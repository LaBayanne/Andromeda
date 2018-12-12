package src_basic.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class AI implements Serializable {
	private int team;
	private ArrayList<Squad> squads;
	private ArrayList<Planet> planets;
	private double exterminationRate;
	private double expansionRate;
	private double protectionRate;
	
	public AI(ArrayList<Planet> planets, ArrayList<Squad> squads, int team) {
		this.planets = planets;
		this.squads = squads;
		this.team = team;
	}
	
	public void tick(double delta) {
		expansion();
	}
	
	public void extermination() {
		
	}
	
	public void expansion() {
		double defence;
		double defenceRef = 0;
		double distance;
		double distanceRef = 0;
		ArrayList<Planet> targets = new ArrayList<Planet>();
		ArrayList<Planet> attackedTargets = new ArrayList<Planet>();
		ArrayList<Planet> restPlanets = new ArrayList<Planet>();
		Planet bestTarget = null;
		boolean first = true;
		double prevFrequency = 0;
		double frequency = 0;
		int nbAttackers = 0;
		int attackCapacity = 0;
		int powerAttack;
		ArrayList<AttackedPlanet> attackedPlanets = new ArrayList<AttackedPlanet>();
		Planet target = null;
		AttackedPlanet targetAttackedPlanet = null;
		int team = this.planets.get(0).getOwner();
		int newTeam = 0;
		boolean cont = false;
		
		for(Planet planet : this.planets) {
			newTeam = planet.getOwner();
			if(newTeam != team) {
				cont = true;
				break;
			}
			team = newTeam;
		}
		
		if(!cont) {
			return;
		}
		
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
				nbAttackers = 0;
				for(AttackedPlanet planet : attackedPlanets) {
					if(planet.getPlanet() == bestTarget){
						nbAttackers = planet.getNbAttackers();
						break;
					}
				}
				
				if(bestTarget.getStock() - nbAttackers < ally.getStock()){
					ally.setAttackGroup(bestTarget.getStock() - nbAttackers, bestTarget);
					attackedTargets.add(bestTarget);
				}
				else {
					restPlanets.add(ally);
					attackCapacity += ally.getStock();
					targets.add(bestTarget);
				}
				first = true;
			}
		}
		
		Planet ally;
		powerAttack = 0;
		Iterator<Planet> iterator;
		ArrayList<Planet> attackers;
		
		
		while(!restPlanets.isEmpty() && !targets.isEmpty() && powerAttack == 0) {
			iterator = restPlanets.iterator();
			attackers = new ArrayList<Planet>();
			bestTarget = null;
			first = true;
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
			
			nbAttackers = 0;
			for(AttackedPlanet planet : attackedPlanets) {
				if(planet.getPlanet() == bestTarget){
					nbAttackers = planet.getNbAttackers();
					break;
				}
			}
			
			while (!restPlanets.isEmpty() && iterator.hasNext() && powerAttack < bestTarget.getStock() - nbAttackers) {
				ally = iterator.next();
				powerAttack += ally.getStock();
				iterator.remove();
				attackers.add(ally);
			}
			if(powerAttack >= bestTarget.getStock() - nbAttackers) {
				for(Planet planet : attackers) {
					planet.setAttackGroup(bestTarget.getStock(), bestTarget);
				}
				targets.remove(bestTarget);
				powerAttack = 0;
			}
			
		}
		
		
		
		/*bestTarget = null;
		first = true;
		for(Planet planet : this.planets) {
			frequency = Collections.frequency(targets, planet);
			if(!first) {
				if(frequency > prevFrequency) {
					bestTarget = planet;
				}
			}
			prevFrequency = frequency;
			first = false;
			
		}
		
		if(bestTarget != null) {
			System.out.println("Prepare to attack");
			if(attackCapacity > bestTarget.getStock()) {
				System.out.println("ATTACK");
				for(Planet planet : groupAttackers) {
					if(planet.getOwner() == this.team) {
						planet.prepareAttack(bestTarget);
					}
				}
			}
		}*/
	}
	
	public void protection() {
		
	}
	
	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}
	
	public void setSquads(ArrayList<Squad> squads) {
		this.squads = squads;
	}
}

class AttackedPlanet{
	private Planet planet;
	private int nbAttackers;
	
	public AttackedPlanet(Planet planet, int nbAttackers) {
		this.planet = planet;
		this.nbAttackers = nbAttackers;
	}
	
	public void addAttackers(int value) {
		this.nbAttackers += value;
	}
	
	public void setPlanet(Planet planet) {
		this.planet = planet;
	}
	
	public void setNbAttackers(int nb) {
		this.nbAttackers = nb;
	}
	
	public Planet getPlanet() {return this.planet;}
	public int getNbAttackers() {return this.nbAttackers;}
}

class DistancePlanet{
	private Planet planet;
	private Planet target;
	private double distance;
	
	
}
