package src_advanced.View;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import src_advanced.Model.Planet.Planet;
import src_advanced.Model.Scene.SceneGame;
import src_advanced.Model.StarShip.StarShip;
import src_advanced.Model.StarShip.Squad;
import src_advanced.Geometry.*;
/**
 * This class represent the view of the game, used to display the game on screen.
 *
 */
public class ViewGame{
	private GraphicsContext gc;
	private int screenWidth;
	private int screenHeight;
	private ImageBank imageBank;
	
	/**
	 * Basic constructor.
	 * @param gc Graphic context
	 * @param width	Screen width
	 * @param height Screen height
	 */
	public ViewGame(GraphicsContext gc, int width, int height) {
		this.gc = gc;
		this.screenWidth = width;
		this.screenHeight = height;
		this.imageBank = new ImageBank();
		
		
	}
	
	/**
	 * Display all the planets
	 * @param planets	Array of planets to display
	 */
	public void displayPlanets(ArrayList<Planet> planets) {
		//this.gc.setFill(Color.web("#4bf221"));
		
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
			
			//this.gc.fillOval(where.getX() - planet.getRadius(), where.getY() - planet.getRadius(), 
				//planet.getRadius() * 2, planet.getRadius() * 2);
			
			double x = where.getX() - planet.getRadius();
			double y = where.getY() - planet.getRadius();
			String name =  planet.getClass().getName();
			name = name.replace("src_advanced.Model.Planet.", "");
			String imageName = "";
			switch (name) {
				
				case "File":
					this.gc.setFill(Color.web("#bbbb0044"));
					imageName = "file_00.png";
					x += 2;
					y += 2;
					break;
				
				case "Directory":
					this.gc.setFill(Color.web("#0000aa88"));
					imageName = "folder_00.png";
					x += 6;
					y += 4;
					break;
				
				case "Application":
					this.gc.setFill(Color.web("#aa40aa44"));
					imageName = "computer_00.png";
					x += 4;
					y += 4;
					break;
			}
			
			switch(planet.getOwner()) {
			
				case 0:
					this.gc.setFill(Color.web("#88888899"));
					break;
				case 1:
					this.gc.setFill(Color.web("#0000ff99"));
					break;
				case 2:
					this.gc.setFill(Color.web("#ff000099"));
					break;
				case 3:
					this.gc.setFill(Color.web("#00ff0099"));
					break;
			}
			
			//this.gc.fillOval(where.getX() - planet.getRadius(), where.getY() - planet.getRadius(), 
				//planet.getRadius() * 2, planet.getRadius() * 2);
			this.gc.drawImage(this.imageBank.getImage(imageName), x, y);
			
			this.gc.fillRect(where.getX() - 20 , where.getY() + planet.getRadius() + 2, 
					planet.getRadius() + 15, planet.getRadius() - 5);
			
			this.gc.setFill(Color.web("#ffffff"));
			this.gc.setFont(Font.font("Verdana", 13));
			String txt = Integer.toString(planet.getStock());
			this.gc.fillText(txt + "Ko", where.getX() - 15, where.getY() + planet.getRadius() + 17);

		}
	    
	}
	
	/**
	 * Display all the squads selected by the player.
	 * @param selectedSquads squads selected by the player.
	 */
	public void displaySelectedSquads(ArrayList<Squad> selectedSquads) {
		this.gc.setFill(Color.web("#ffffffff"));
		final int edge = 2;
		
		for (Squad squad:selectedSquads) {
			for (StarShip starship:squad.getStarships()) {
				Point where = starship.getPosition();
				where.translate(-edge, -edge);
				this.gc.fillRect(where.getX(), where.getY(), starship.getWidth() + 2 * edge, 
						starship.getHeight() + 2 * edge);
			}
		}
	}

	/**
	 * Display all the starships of a squad.
	 * @param squads All the squads of the game
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
				Point where = starship.getPosition();
				String name = starship.getClass().getName();
				name = name.replace("src_advanced.Model.StarShip.", "");
				String imageName = "";
				switch (name) {
					
					case "Arrow":
						if (squad.getOwner() == 1)
							imageName = "cursor_01.png";
						else 
							imageName = "cursor_04.png";
						break;
					
					case "Finger":
						if (squad.getOwner() == 1)
							imageName = "cursor_00.png";
						else
							imageName = "cursor_03.png";
						break;
					
					case "MoveCursor":
						if (squad.getOwner() == 1)
							imageName = "cursor_02.png";
						else
							imageName = "cursor_05.png";
						break;
				}

				this.gc.drawImage(this.imageBank.getImage(imageName), where.getX(), 
						where.getY(), starship.getWidth(), starship.getHeight());
				//this.gc.fillRect(where.getX(), where.getY(), StarShip.getWidth(), StarShip.getHeight());
			}
		}
	}
	
	/**
	 * Draw a white circle around selected planet.
	 * @param planets	List of selected planets
	 */
	public void displaySelectedPlanets(ArrayList<Planet> planets) {
		this.gc.setFill(Color.web("#ffffffff"));
		double edge = 5;
		
		for (Planet planet : planets) {
			Point where = planet.getCollisionShape().getOrigin();
			this.gc.drawImage(this.imageBank.getImage("zoom_00.png"), where.getX() - planet.getRadius(), 
					where.getY() - planet.getRadius());
			//this.gc.fillOval(where.getX() - planet.getRadius() - edge / 2, where.getY() - planet.getRadius() - edge / 2, 
				//planet.getRadius() * 2 + edge, planet.getRadius() * 2 + edge);

		}
	}
	
	/**
	 * Display the squad size.
	 * @param size	Value to display
	 */
	public void displaySquadSize(int size) {
		this.gc.setFill(Color.web("#eeeeee"));
		this.gc.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		this.gc.fillText(Integer.toString(size) + "%", 10, 620);
	}
	
	/**
	 * Show the rectangular selection.
	 * @param rect	Dimension of the rectangle
	 */
	public void displaySelectRect(Rectangle rect) {
		this.gc.setStroke(Color.web("#ffffff"));
		this.gc.strokeRect(rect.getOrigin().getX(), rect.getOrigin().getY(), rect.getWidth(), rect.getHeight());
	}
	
	/**
	 * Tick function, draw the entire game.
	 * @param game The game
	 */
	public void tick(SceneGame game) {
		//this.gc.setFill(Color.web("#000000"));
		this.gc.drawImage(this.imageBank.getImage("default_background.jpg"), 0, 0, this.screenWidth, this.screenHeight);
		//this.gc.fillRect(0,  0, this.screenWidth, this.screenHeight);
		
		
		this.displayPlanets(game.getPlanets());
		this.displaySelectedPlanets(game.getSelectedPlanets());
		this.displaySelectedSquads(game.getSelectedSquads());
		this.displaySquads(game.getSquads());
		this.displaySquadSize(game.getSquadSize());
		
		if(game.getIsThereSelectRect()) {
			displaySelectRect(game.getSelectRect());
		}
	}
}