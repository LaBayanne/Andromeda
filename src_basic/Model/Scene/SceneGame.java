package src_basic.Model.Scene;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import src_basic.Model.Planet;
import src_basic.Model.PlanetGenerator;
import src_basic.Model.Squad;
import src_basic.View.ViewGame;

public class SceneGame implements Scenery{
	private GraphicsContext gc;
	private ViewGame view;
	
	ArrayList<Squad> squads;
	ArrayList<Planet> planets;
	ArrayList<Planet> selectedPlanets;
	
	int squadSize;
	Rectangle selectRect;
	Point2D selectRectOrigin;
	boolean isThereSelectRect;
	
	private PlanetGenerator planetGenerator;
	private int nbPlayers;
	
	public SceneGame(GraphicsContext gc) {
		this.gc = gc;
		this.view = new ViewGame(gc);
		
		this.nbPlayers = 2;//By default
		this.planetGenerator = new PlanetGenerator(100, 20, 30, 10, 960, 640);
		
		this.planets = this.planetGenerator.generate();
		this.planetGenerator.givePlanet(2, this.planets);
		this.squads = new ArrayList<Squad>();
		this.selectedPlanets = new ArrayList<Planet>();
		
		this.squadSize = 100;
		
		// this.generatePlanets();
		this.selectRect = new Rectangle(0, 0, 0, 0);
		this.isThereSelectRect = false;
		
	}
	
	//////User events
	public void selectActivePlanet(double x, double y) {
		//this.selectedPlanets.clear();
		for(Planet planet:this.planets) {
			if(planet.getHitbox().collision(new Point2D(x, y)) && planet.getOwner() == 0) { 	
				this.selectedPlanets.add(planet);
			}
		}
	}
	
	public void selectTarget(double x, double y) {
		Planet target = null;
		for(Planet planet:this.planets) {
			if(planet.getHitbox().collision(new Point2D(x, y))) {
				target = planet;
			}
		}
		if(target != null) {
			for(Planet planet:this.selectedPlanets) {
				if(planet != target)
					planet.prepareAttack(target);
			}
		}
	}
	
	public void mouseClicked(int button, double x, double y) {
		switch(button) {
			case 0:
				selectActivePlanet(x, y);
				break;
			case 1:
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
	
	public void inputMouseLeft(double x, double y) {
		Rectangle rect = this.selectRect;
		if(!this.isThereSelectRect ) {
			this.selectRectOrigin = new Point2D(x, y);
			this.isThereSelectRect = true;
		}
		if(rect.getX() >= x) {
			rect.setWidth(this.selectRectOrigin.getX() - x);
			rect.setX(x);
		}
		else {
			rect.setX(this.selectRectOrigin.getX());
			rect.setWidth(x - rect.getX());
		}
		if(rect.getY() >= y) {
			rect.setHeight(this.selectRectOrigin.getY() - y);
			rect.setY(y);
		}
		else {
			rect.setY(this.selectRectOrigin.getY());
			rect.setHeight(y - rect.getY());
		}

	}
	
	public void releasedMouseLeft(double x, double y) {
		this.selectedPlanets.clear();
		for(Planet planet:this.planets) {
			if(planet.getOwner() == 0 && planet.getHitbox().collision(this.selectRect)) {
				this.selectedPlanets.add(planet);
			}
		}
		this.isThereSelectRect = false;
	}
	
	////end user events
	
	public boolean tick(double delta) {
		
		this.moveSquad(delta);
		for(Planet planet:this.planets) {
			//System.err.println(planet.getOwner());
			
			if (planet.getOwner() != 0) //0 is neutral planet
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
	
	public void moveSquad(double delta) {
		for (Squad squad: this.squads) {
			squad.moveStarships(delta, this.planets);
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
		this.planets.add(new Planet(new Point2D(500, 350), 20, 0.03, 1));
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
	
	public Rectangle getSelectRect() {
		return this.selectRect;
	}
	
	public boolean getIsThereSelectRect() {
		return this.isThereSelectRect;
	}
}