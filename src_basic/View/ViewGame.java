package src_basic.View;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import src_basic.Model.Planet;
import src_basic.Model.Squad;
import src_basic.Model.Scene.SceneGame;

public class ViewGame{
	private GraphicsContext gc;
	
	public ViewGame(GraphicsContext gc) {
		this.gc = gc;
	}
	
	public void displayPlanets(ArrayList<Planet> planets) {
		this.gc.setFill(Color.web("#4bf221"));
		
		for (Planet planet : planets) {
			Point2D where = planet.getOrigin();
			this.gc.fillOval(where.getX(), where.getY(), planet.getRadius(), planet.getRadius());
		}
	    
	}
	
	public void displaySquads(ArrayList<Squad> squads) {
		
	}
	

	public void tick(SceneGame game) {
		this.displayPlanets(game.getPlanets());
		this.displaySquads(game.getSquads());
	}
}