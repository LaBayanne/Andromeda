package src_basic.Model.Scene;

import src_basic.Model.*;
import src_basic.View.*;
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import src_basic.View.ViewGame;

public class SceneGame implements Scenery{
	private GraphicsContext gc;
	private ViewGame view;
	
	ArrayList<Squad> squads;
	ArrayList<Planet> planets;
	ArrayList<Planet> selectedPlanets;
	
	int squadSize;
	
	public SceneGame(GraphicsContext gc) {
		this.gc = gc;
		this.view = new ViewGame(gc);
		
		this.planets = new ArrayList<Planet>();
		this.squads = new ArrayList<Squad>();
		this.selectedPlanets = new ArrayList<Planet>();
		
		this.squadSize = 100;
		
		this.generatePlanets();
		
	}
	
	//////User events
	public void selectActivePlanet(double x, double y) {
		for(Planet planet:this.planets) {
			if(planet.getHitbox().collision(new Point2D(x, y)) && planet.getOwner() == 0) { 
				this.selectedPlanets.clear();
				this.selectedPlanets.add(planet);
			}
		}
	}
	
	public void selectTarget(double x, double y) {
		Planet target = null;
		for(Planet planet:this.planets) {
			if(planet.getHitbox().collision(new Point2D(x, y))) {
				System.err.println("TOUCH");
				target = planet;
			}
		}
		if(target != null) {
			for(Planet planet:this.selectedPlanets) {
				planet.prepareAttack(target);
			}
		}
	}
	
	public void mouseClicked(int button, double x, double y) {
		switch(button) {
			case 0:
				System.err.println("LEFT");
				selectActivePlanet(x, y);
				break;
			case 1:
				System.err.println("RIGHT");
				selectTarget(x, y);
				break;
		}
	}
	
	public void moveWheel(int dy) {
		this.setSquadSize(dy);
		for(Planet planet:this.planets){
			if(planet.getOwner() == 0) {
				planet.setSquadSize(this.squadSize);
			}
		}
	}
	
	////end user events
	
	public boolean tick() {
		
		this.moveSquad();
		for(Planet planet:this.planets) {
			//System.err.println(planet.getOwner());
			
			planet.actualiseStock();	//Augment the planet's stock
			
			if(planet.getNbStarshipToGenerate() > 0 && planet.decreaseTimer() == 0) {
				Squad newSquad = planet.generateSquad();
				this.squads.add(newSquad);
				
				//For debbug
				//newSquad.setDestinationPlanet(this.planets.get(0));
				
			}
			
		}
		this.view.tick(this);
		
		return true;
	}
	
	public void moveSquad() {
		for (Squad squad: this.squads) {
			squad.moveStarships(this.planets);
		}
	}
	
	public void setSquadSize(int size) {
		this.squadSize += size;
		if(this.squadSize < 1) {
			this.squadSize = 1;
		}
		else if(this.squadSize > 100) {
			this.squadSize = 100;
		}
	}
	
	public void generatePlanets() {
		// public Planet(Point2D origin, double radius, int productionSpeed, int owner)
		this.planets.add(new Planet(new Point2D(200, 500), 50, 0.01, 0));
		this.planets.add(new Planet(new Point2D(600, 200), 85, 0.01, 1));
		this.planets.add(new Planet(new Point2D(300, 150), 45, 0.02, 0));
		this.planets.add(new Planet(new Point2D(500, 350), 20, 0.1, 1));
		this.planets.add(new Planet(new Point2D(750, 300), 35, 0.02, 0));
		this.planets.add(new Planet(new Point2D(800, 475), 60, 0.01, 1));
	}
		
	public int getSquadSize() {
		return this.squadSize;
	}
	
	public ArrayList<Planet> getPlanets() {
		return this.planets;
	}
	
	public ArrayList<Planet> getSelectedPlanets() {
		return this.selectedPlanets;
	}
	
	public ArrayList<Squad> getSquads() {
		return this.squads;
	}
}