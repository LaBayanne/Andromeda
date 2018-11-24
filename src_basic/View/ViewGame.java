package src_basic.View;

import java.util.ArrayList;

import src_basic.Model.Planet;
import src_basic.Model.Squad;
import src_basic.Model.Scene.SceneGame;

public class ViewGame{
	public ViewGame() {
		
	}
	
	public void displayPlanets(ArrayList<Planet> planets) {
		
	}
	
	public void displaySquads(ArrayList<Squad> squads) {
		
	}
	
	public void tick(SceneGame game) {
		this.displayPlanets(game.getPlanets());
		this.displaySquads(game.getSquads());
	}
}