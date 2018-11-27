package src_basic.View;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import src_basic.Model.Planet;
import src_basic.Model.Squad;
import src_basic.Model.StarShip;
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
			this.gc.setFill(Color.web("#4bf221"));
			this.gc.fillOval(where.getX(), where.getY(), planet.getRadius() * 2, planet.getRadius() * 2);
		}
	    
	}

	public void displaySquads(ArrayList<Squad> squads) {
		this.gc.setFill(Color.web("#ff0405"));

		for (Squad squad: squads) {
			for (StarShip startship: squad.getStarships()) {
				Point2D where = startship.getPosition();
				this.gc.fillRect(where.getX(), where.getY(), StarShip.getWidth(), StarShip.getHeight());
			}
		}
	}
	

	public void tick(SceneGame game) {
		this.gc.clearRect(0, 0, 960, 640); // PASSER EN PARAM
		this.displayPlanets(game.getPlanets());
		this.displaySquads(game.getSquads());
	}
}