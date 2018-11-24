package src_basic.Model.Scene;

import src_basic.Model.*;
import src_basic.View.*;
import java.util.ArrayList;

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
		this.view = new ViewGame();
	}
	
	@Override
	public boolean tick() {
		this.view.tick(this);
		return false;
	}
	
	public void generatePlanets() {
		
	}
	
	public ArrayList<Planet> getPlanets() {
		return this.planets;
	}
	
	public ArrayList<Squad> getSquads() {
		return this.squads;
	}
}