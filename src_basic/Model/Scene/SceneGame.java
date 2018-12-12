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
 * @author chocorion
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
	private int nbPlayers;
	
	private double timerDoubleClickTotal;
	private double timerDoubleClick;
	
	private ArrayList<AI> AIs;
	
	public SceneGame(GraphicsContext gc) {
		this.view = new ViewGame(gc);
		
		this.nbPlayers = 2;//By default
		this.planetGenerator = new PlanetGenerator(100, 20, 30, 10, 960, 640);
		
		this.planets = this.planetGenerator.generate();
		this.planetGenerator.givePlanet(2, this.planets);
		this.squads = new ArrayList<Squad>();
		this.selectedPlanets = new ArrayList<Planet>();
		this.selectedSquads = new ArrayList<Squad>();
		
		this.squadSize = 100;
		
		// this.generatePlanets();
		this.selectRect = new Rectangle(0, 0, 0, 0);
		this.isThereSelectRect = false;
		
		this.timerDoubleClickTotal = 60;
		this.timerDoubleClick = 0;
		
		this.AIs = new ArrayList<AI>();
		this.AIs.add(new AI(this.planets, this.squads, 2));
		
	}
	
	/* Manage user inputs */
	
	public void selectSquad(double x, double y) {
		for (Squad s : this.squads) {
			if (s.isStarshipCollision(x, y)) {
				this.selectedSquads.add(s);
				break;
			}
		}
	}
	
	/**
	 * Select planets
	 * 
	 * @param x	x coord of the click
	 * @param y	y coord of the click
	 */
	public boolean selectActivePlanet(double x, double y) {
		//this.selectedPlanets.clear();
		boolean touchPlanet = false;
		for(Planet planet:this.planets) {
			/* Ne pas mettre 1 mais une variable pour connaitre l'id du joueur ! */
			if(planet.getCollisionShape().collision(new Point(x, y)) && planet.getOwner() == 1) { 
				if(this.timerDoubleClick > 0) {
					touchPlanet = true;
				}
				else {
					if(!this.selectedPlanets.contains(planet)) {
						this.selectedPlanets.add(planet);
						planet.setSquadSize(this.squadSize);
						/* On peut quitter nan ? Il ne peut y avoir qu'une planète sur un point de l'espace */
					}
				}
			}
		}
		this.timerDoubleClick = this.timerDoubleClickTotal;
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
	 * Find the planet destination, and prepare attack for all selected planets
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
			
			Iterator it = this.selectedSquads.iterator();
			while(it.hasNext()) {
				Squad s =(Squad) it.next();
				s.setDestinationPlanet(target);
				it.remove();
			}
		}
		
	}
	
	
	/***
	* On click, call the function selectActivePlanet or selectTarget
	 * @param button	the value of button, 0 for left click, 1 for right click
	 * @param x 		x coord of the click
	 * @param y 		y coord of the click
	 * @param buttonOptions
	 */
	public void mouseClicked(int button, double x, double y, ArrayList<String> buttonOptions) {
		switch(button) {
			case 0:
				//If we don't click on a planet:
				if (!selectActivePlanet(x, y)) {
					selectSquad(x, y);
				}
				break;
			case 1:
				selectTarget(x, y);
				break;
		}
	}
	/***
	 * 
	 * param dy
	**/
	public void moveWheel(int dy) {
		this.setSquadSize(dy);
	}
	
	/***
	 * 
	 */
	public void inputMouseLeft(double x, double y) {
		Rectangle rect = this.selectRect;
		if(!this.isThereSelectRect ) {
			this.selectRectOrigin = new Point(x, y);
			this.isThereSelectRect = true;
		}
		if(rect.getOrigin().getX() >= x) {
			rect.setWidth(this.selectRectOrigin.getX() - x);
			rect.getOrigin().setX(x);
		}
		else {
			rect.getOrigin().setX(this.selectRectOrigin.getX());
			rect.setWidth(x - rect.getOrigin().getX());
		}
		if(rect.getOrigin().getY() >= y) {
			rect.setHeight(this.selectRectOrigin.getY() - y);
			rect.getOrigin().setY(y);
		}
		else {
			rect.getOrigin().setY(this.selectRectOrigin.getY());
			rect.setHeight(y - rect.getOrigin().getY());
		}

	}
	
	public void releasedMouseLeft(double x, double y, ArrayList<String> buttonOptions) {
		if(!buttonOptions.contains("CONTROL"))
			this.selectedPlanets.clear();
		for(Planet planet:this.planets) {
			if(planet.getOwner() == 1 && planet.getCollisionShape().collision(this.selectRect) && 
					!this.selectedPlanets.contains(planet)) {
				this.selectedPlanets.add(planet);
			}
		}
		this.isThereSelectRect = false;
	}
	
	/* End of user inputs */
	
	public void restor(GraphicsContext gc) {
		this.view = new ViewGame(gc);
	}
	
	public boolean tick(double delta) {
		
		this.moveSquad(delta);
		updateTimerClick(delta);
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
			
			if(planet.getOwner() == 1) {
				planet.setSquadSize(this.squadSize);
			}
			
		}
		cleanSelectedPlanets();
		updateAIs(delta);
		this.view.tick(this);
		
		return true;
	}
	
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
	
	public void updateAIs(double delta) {
		for(AI ai : this.AIs) {
			ai.setSquads(this.squads);
			ai.setPlanets(this.planets);
			ai.tick(delta);
		}
	}
	
	public void updateTimerClick(double delta) {
		if(this.timerDoubleClick > 0) {
			this.timerDoubleClick -= delta * 0.2;
		}
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
	
	public void setTimerDoubleClick(int value) {
		this.timerDoubleClick = value;
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