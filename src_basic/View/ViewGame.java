package src_basic.View;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
			
			if (planet.getOwner() == 0) {
				this.gc.setFill(Color.web("#4bf221"));
			} else {
				this.gc.setFill(Color.web("#f24b21"));
			}
			this.gc.fillOval(where.getX() - planet.getRadius(), where.getY() - planet.getRadius(), 
				planet.getRadius() * 2, planet.getRadius() * 2);
			this.gc.setFill(Color.web("#eeeeee"));
			
			//+7 cst magiques à changer en fonction de la taille de la police
			this.gc.setFont(Font.font("Verdana", 15));
			this.gc.fillText(Integer.toString(planet.getStock()), where.getX() - 7, where.getY() + 7);

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
	
	public void displaySelectedPlanets(ArrayList<Planet> planets) {
		this.gc.setFill(Color.web("#ffffff"));
		double edge = 5;
		
		for (Planet planet : planets) {
			Point2D where = planet.getOrigin();
			this.gc.fillOval(where.getX() - planet.getRadius() - edge / 2, where.getY() - planet.getRadius() - edge / 2, 
				planet.getRadius() * 2 + edge, planet.getRadius() * 2 + edge);

		}
	}
	
	public void displaySquadSize(int size) {
		this.gc.setFill(Color.web("#eeeeee"));
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		//ajuster en fonction de la taille de la fenêtre
		this.gc.fillText(Integer.toString(size) + "%", 10, 620);
	}
	

	public void tick(SceneGame game) {
		//this.gc.clearRect(0, 0, 960, 640); // PASSER EN PARAM
		this.gc.setFill(Color.web("#000000"));
		this.gc.fillRect(0,  0, 960, 640);
		this.displaySelectedPlanets(game.getSelectedPlanets());
		this.displayPlanets(game.getPlanets());
		this.displaySquads(game.getSquads());
		this.displaySquadSize(game.getSquadSize());
	}
}