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
	
	public SceneGame(GraphicsContext gc) {
		this.gc = gc;
		this.view = new ViewGame(gc);
		
		this.planets = new ArrayList<Planet>();
		this.squads = new ArrayList<Squad>();
		this.selectedPlanets = new ArrayList<Planet>();
		
		this.generatePlanets();
		
	}
	
	//////User events
	
	public void mouseClicked(double x, double y) {
		for(Planet planet:this.planets) {
			if(planet.getHitbox().collision(new Point2D(x, y))) {
				planet.prepareAttack();
			}
		}
	}
	
	////end user events
	
	public boolean tick() {
		
		this.moveSquad();
		for(Planet planet:this.planets) {
			
			planet.actualiseStock();	//Augment the planet's stock
			
			if(planet.getNbStarshipToGenerate() > 0 && planet.decreaseTimer() == 0) {
				Squad newSquad = planet.generateSquad();
				this.squads.add(newSquad);
				
				//For debbug
				newSquad.setDestinationPlanet(this.planets.get(0));
				newSquad.findPath(this.planets);
				
			}
			
		}
		this.view.tick(this);
		
		return true;
	}
	
	public void moveSquad() {
		for (Squad squad: this.squads) {
			squad.moveStarships();
		}
	}
	
	public void generatePlanets() {
		// public Planet(Point2D origin, double radius, int productionSpeed, int owner)
		this.planets.add(new Planet(new Point2D(200, 500), 50, 0.04, 0));
		this.planets.add(new Planet(new Point2D(600, 200), 85, 0.01, 0));
		this.planets.add(new Planet(new Point2D(300, 150), 45, 0.02, 0));
		this.planets.add(new Planet(new Point2D(500, 350), 20, 0.03, 0));
		this.planets.add(new Planet(new Point2D(750, 300), 35, 0.02, 0));
		this.planets.add(new Planet(new Point2D(800, 475), 60, 0.01, 0));
	}
		
	
	public ArrayList<Planet> getPlanets() {
		return this.planets;
	}
	
	public ArrayList<Squad> getSquads() {
		return this.squads;
	}
}