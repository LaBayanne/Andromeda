package src_basic.View;

import java.util.ArrayList;

//import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import src_basic.Model.Planet;
import src_basic.Model.Squad;
import src_basic.Model.StarShip;
import src_basic.Model.Scene.SceneGame;
import src_basic.Geometry.*;
/**
 * This class represent the view of the game, used to display the game on screen
 * @author chocorion and labayanne
 *
 */
public class ViewGame{
	private GraphicsContext gc;
	
	public ViewGame(GraphicsContext gc) {
		this.gc = gc;
	}
	
	/**
	 * Display all the planets
	 * @param planets	Array of planets to display
	 */
	public void displayPlanets(ArrayList<Planet> planets) {
		this.gc.setFill(Color.web("#4bf221"));
		
		for (Planet planet : planets) {
			Point where = planet.getCollisionShape().getOrigin();
			
			switch (planet.getOwner()) {
			
			/*Neutral planet by default*/
			case 0:
				this.gc.setFill(Color.web("#999999"));
				break;
			
			/*Human player by default*/
			case 1:
				this.gc.setFill(Color.web("#00bb00"));
				break;
			
			/*IA player by default*/
			case 2:
				this.gc.setFill(Color.web("#dd0000"));
				break;
			}

			this.gc.fillOval(where.getX() - planet.getRadius(), where.getY() - planet.getRadius(), 
				planet.getRadius() * 2, planet.getRadius() * 2);
			this.gc.setFill(Color.web("#eeeeee"));
			
			//+7 cst magiques à changer en fonction de la taille de la police
			this.gc.setFont(Font.font("Verdana", 15));
			String txt = Integer.toString(planet.getStock());
			this.gc.fillText(txt, where.getX() - 7, where.getY() + 7);

		}
	    
	}

	/**
	 * Display all the starships of a squad
	 * @param squads
	 */
	public void displaySquads(ArrayList<Squad> squads) {
		for (Squad squad: squads) {
			for (StarShip starship: squad.getStarships()) {
				if(starship.getOwner() == 1) {
					this.gc.setFill(Color.web("#00bb00"));
				}
				else {
					this.gc.setFill(Color.web("#dd0000"));
				}
				Point2D where = starship.getPosition();
				this.gc.fillRect(where.getX(), where.getY(), StarShip.getWidth(), StarShip.getHeight());
			}
		}
	}
	
	/**
	 * Draw a white circle around selected planet
	 * @param planets	List of selected planets
	 */
	public void displaySelectedPlanets(ArrayList<Planet> planets) {
		this.gc.setFill(Color.web("#ffffffff"));
		double edge = 5;
		
		for (Planet planet : planets) {
			Point2D where = planet.getOrigin();
			this.gc.fillOval(where.getX() - planet.getRadius() - edge / 2, where.getY() - planet.getRadius() - edge / 2, 
				planet.getRadius() * 2 + edge, planet.getRadius() * 2 + edge);

		}
	}
	
	/**
	 * Display the squad size
	 * @param size	value to display
	 */
	public void displaySquadSize(int size) {
		this.gc.setFill(Color.web("#eeeeee"));
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		//ajuster en fonction de la taille de la fenêtre
		this.gc.fillText(Integer.toString(size) + "%", 10, 620);
	}
	
	/**
	 * Show the rectangular selection
	 * @param rect	the dimension of the rectangle
	 */
	public void displaySelectRect(Rectangle rect) {
		this.gc.setStroke(Color.web("#ffffff"));
		this.gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}
	
	/**
	 * Tick function, draw the entire game
	 * @param game
	 */
	public void tick(SceneGame game) {
		//this.gc.clearRect(0, 0, 960, 640); // PASSER EN PARAM
		this.gc.setFill(Color.web("#000000"));
		this.gc.fillRect(0,  0, 960, 640);
		this.displaySelectedPlanets(game.getSelectedPlanets());
		this.displayPlanets(game.getPlanets());
		this.displaySquads(game.getSquads());
		this.displaySquadSize(game.getSquadSize());
		if(game.getIsThereSelectRect())
			displaySelectRect(game.getSelectRect());
	}
}