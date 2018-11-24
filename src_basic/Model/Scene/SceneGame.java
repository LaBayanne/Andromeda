package src_basic.Model.Scene;

import src_basic.Model.*;
import src_basic.View.*;
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import src_basic.View.ViewGame;

public class SceneGame implements Scene{
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
	
	@Override
	public boolean tick() {
		this.view.tick(this);
		
		return true;
	}
	
	public void generatePlanets() {
		// public Planet(Point2D origin, double radius, int productionSpeed, int owner)
		this.planets.add(new Planet(new Point2D(200, 500), 100, 0, 0));
 
		this.planets.add(new Planet(new Point2D(600, 200), 170, 0, 0));
		this.planets.add(new Planet(new Point2D(300, 150), 90, 0, 0));
	}
	
	public ArrayList<Planet> getPlanets() {
		return this.planets;
	}
	
	public ArrayList<Squad> getSquads() {
		return this.squads;
	}
}