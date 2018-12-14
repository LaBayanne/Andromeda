package src_basic.Model.Scene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import src_basic.Geometry.*;
import javafx.scene.canvas.GraphicsContext;
import src_basic.Model.AI;
import src_basic.Model.Planet;
import src_basic.Model.PlanetGenerator;
import src_basic.Model.Squad;
import src_basic.View.ViewGame;

/**
 * Represent the model of the game.
 *
 */
public class SceneGame implements Scenery, Serializable{


	private transient ViewGame view;
	
	private ArrayList<Squad> squads;
	private ArrayList<Planet> planets;
	private ArrayList<Planet> selectedPlanets;
	private ArrayList<Squad> selectedSquads;
	
	private int squadSize;
	private Rectangle selectRect;
	private Point selectRectOrigin;
	private boolean isThereSelectRect;
	
	private PlanetGenerator planetGenerator;
	private int nbPlayers;//not used on the game's first version
	
	private double timerDoubleClickTotal;
	private double timerDoubleClick;
	
	private ArrayList<AI> AIs;
	
	private int screenWidth, screenHeight;
	/**
	 * Basic constructor.
	 * @param gc Graphic context
	 */
	public SceneGame(GraphicsContext gc, int width, int height) {
		this.view = new ViewGame(gc, width, height);
		this.screenWidth = width;
		this.screenHeight = height;
		
		this.nbPlayers = 2;//By default
		this.planetGenerator = new PlanetGenerator(100, 20, 30, 10, 960, 640);
		
		this.planets = this.planetGenerator.generate();
		this.planetGenerator.givePlanet(2, this.planets);
		this.squads = new ArrayList<Squad>();
		this.selectedPlanets = new ArrayList<Planet>();
		this.selectedSquads = new ArrayList<Squad>();
		
		this.squadSize = 100;
		
		this.selectRect = new Rectangle(0, 0, 0, 0);
		this.isThereSelectRect = false;
		
		this.timerDoubleClickTotal = 60;
		this.timerDoubleClick = 0;
		
		this.AIs = new ArrayList<AI>();
		this.AIs.add(new AI(this.planets, this.squads, 2));
		
	}
	
	/* Manage user inputs */
	
	/**
	 * Select squad if there is a starship where the player click.
	 * @param x		x value of the click
	 * @param y		y value of the click
	 */
	public void selectSquad(double x, double y) {
		final double selectionRadius = 20;
		for (Squad s : this.squads) {
			if (s.getOwner() == 1 &&  s.isStarshipCollision(new Circle(x, y, selectionRadius))) {
				this.selectedSquads.add(s);
			}
		}
	}
	
	/**
	 * Select planet at the mouse position. If double click, select all the planets of the player.
	 * @param x	x coord of the click
	 * @param y	y coord of the click
	 */
	public boolean selectActivePlanet(double x, double y, ArrayList<String> buttonOptions) {
		if(!buttonOptions.contains("CONTROL")) {
			this.selectedPlanets.clear();
		}
		boolean touchPlanet = false;
		for(Planet planet:this.planets) {
			if(planet.getCollisionShape().collision(new Point(x, y)) && planet.getOwner() == 1) { 
				if(this.timerDoubleClick > 0) {
					touchPlanet = true;
				}
				else {
					if(!this.selectedPlanets.contains(planet)) {
						this.selectedPlanets.add(planet);
						break;
					}
				}
			}
		}
		
		this.timerDoubleClick = this.timerDoubleClickTotal;
		
		//If double click, add all planets of the player at selected planets.
		if(touchPlanet) {
			this.timerDoubleClick = 0;
			this.selectedPlanets.clear();
			for(Planet planet:this.planets) {
				if(planet.getOwner() == 1)
					this.selectedPlanets.add(planet);
			}
		}
		
		return touchPlanet;
	}

	/**
	 * Find the planet destination, and prepare attack for all selected planets.
	 * @param x	x coord of click
	 * @param y y coord of click
	 */
	public void selectTarget(double x, double y) {
		Planet target = null;
		for(Planet planet:this.planets) {
			if(planet.getCollisionShape().collision(new Point(x, y))) {
				target = planet;
			}
		}
		
		if(target != null) {
			for(Planet planet:this.selectedPlanets) {
				if(planet != target)
					planet.prepareAttack(target);
			}
			
			Iterator<Squad> it = this.selectedSquads.iterator();
			while(it.hasNext()) {
				Squad s =(Squad) it.next();
				s.setDestinationPlanet(target);
				it.remove();
			}
		}
		
	}
	
	
	/**
	* On click, call the function selectActivePlanet or selectTarget.
	 * @param button	the value of button, 0 for left click, 1 for right click
	 * @param x 		x coord of the click
	 * @param y 		y coord of the click
	 * @param buttonOptions
	 */
	public void mouseClicked(int button, double x, double y, ArrayList<String> buttonOptions) {
		switch(button) {
			case 0:
				//If we don't click on a planet:
				if (!selectActivePlanet(x, y, buttonOptions)) {
					selectSquad(x, y);
				}
				break;
			case 1:
				selectTarget(x, y);
				break;
		}
	}
	
	/**
	 * Triggered when the mouse is moved. Modify the value of the squadsize with the @param dy.
	 * @param dy Wheel's vertical delta
	 */
	public void moveWheel(int dy) {
		this.setSquadSize(dy);
	}
	
	/* End of user inputs */
	
	/**
	 * Restore the sceneGame.
	 * @param gc Graphic context
	 */
	public void restor(GraphicsContext gc) {
		this.view = new ViewGame(gc, this.screenWidth, this.screenHeight);
	}
	
	/**
	 * Run every tick of the game. Manage main elements of the game.
	 * @param delta Delay since the previous tick of the game
	 */
	public boolean tick(double delta) {
		
		for(Planet planet:this.planets) {
			if (planet.getOwner() != 0) 
				planet.actualiseStock();
			
			if(planet.getNbStarshipToGenerate() > 0 && planet.decreaseTimer() == 0) {
				this.squads.add(planet.generateSquad());
			}
			
			if(planet.getOwner() == 1) {
				planet.setSquadSize(this.squadSize);
			}
			
		}
		
		cleanSelectedPlanets();
		
		moveSquad(delta);
		
		updateTimerClick(delta);
		
		updateAIs(delta);
		
		this.view.tick(this);
		
		return true;
	}
	
	/**
	 * Remove the planets whose the owner isn't the player from the selected planets.
	 */
	public void cleanSelectedPlanets() {
		Iterator<Planet> iterator = this.selectedPlanets.iterator();
		Planet planet;
		while(iterator.hasNext()) {
			planet = iterator.next();
			if(planet.getOwner() != 1) {
				iterator.remove();
			}
		}
		
	}
	
	/**
	 * Update and run the tick of the AIs.
	 * @param delta Delay since the previous tick of the game.
	 */
	public void updateAIs(double delta) {
		for(AI ai : this.AIs) {
			ai.setSquads(this.squads);
			ai.setPlanets(this.planets);
			ai.tick(delta);
		}
	}
	
	/**
	 * Update the timerClick.
	 * @param delta Delay since the previous tick of the game.
	 */
	public void updateTimerClick(double delta) {
		if(this.timerDoubleClick > 0) {
			this.timerDoubleClick -= delta * 0.2;
		}
	}
	
	/**
	 * Move all the squads of the game.
	 * @param delta Delay since the previous tick of the game.
	 */
	public void moveSquad(double delta) {
		for(Squad squad: this.squads) {
			squad.moveStarships(delta, this.planets);
		}
	}
	
	/**
	 * @param size Size of the squad
	 */
	public void setSquadSize(int size) {
		this.squadSize += size;
		if(this.squadSize < 1) {
			this.squadSize = 1;
		}
		else if(this.squadSize > 100) {
			this.squadSize = 100;
		}
	}
	
	/**
	 * @param value Value of the timer
	 */
	public void setTimerDoubleClick(int value) {
		this.timerDoubleClick = value;
	}

	/**
	 * 
	 * @return Return the squadSize
	 */
	public int getSquadSize() {
		return this.squadSize;
	}
	
	/**
	 * 
	 * @return Return the list of the planets
	 */
	public ArrayList<Planet> getPlanets() {
		return this.planets;
	}
	
	/**
	 * 
	 * @return Return the list of the selected planets
	 */
	public ArrayList<Planet> getSelectedPlanets() {
		return this.selectedPlanets;
	}
	
	/**
	 * 
	 * @return Return the list of the selected squads
	 */
	public ArrayList<Squad> getSelectedSquads() {
		return this.selectedSquads;
	}
	
	/**
	 * 
	 * @return Return the list of the squads
	 */
	public ArrayList<Squad> getSquads() {
		return this.squads;
	}
	
	/**
	 * 
	 * @return Return the select rectangle
	 */
	public Rectangle getSelectRect() {
		return this.selectRect;
	}
	
	/**
	 * 
	 * @return Return true if there is a select rectangle
	 */
	public boolean getIsThereSelectRect() {
		return this.isThereSelectRect;
	}
}