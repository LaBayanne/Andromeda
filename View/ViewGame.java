package View;

import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import Geometry.Point;
import Geometry.Rectangle;
import Model.Menus.Menu;
import Model.Planet.Avast;
import Model.Planet.Planet;
import Model.Scene.SceneGame;
import Model.StarShip.Squad;
import Model.StarShip.StarShip;
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
			
			String name =  planet.getClass().getName();
			name = name.replace("Model.Planet.", "");
			Point where = planet.getCollisionShape().getOrigin();
			
			double x = where.getX() - planet.getRadius();
			double y = where.getY() - planet.getRadius();
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
					this.gc.setFill(Color.web("#00bb0099"));
					break;
				case 4:
					this.gc.setFill(Color.web("#ee51aa99"));
					break;
				case 5:
					this.gc.setFill(Color.web("#ffff0099"));
					break;
				//Avast	
				case 6:
					this.gc.setFill(Color.web("#ffa50099"));
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
	
	private void drawRotate(Image image, double angle, double x, double y, int width, int height) {
		/*ImageView iv = new ImageView(image);
		iv.setRotate((angle + 90)%360);
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		Image rotatedImage = iv.snapshot(params, null);
		gc.drawImage(rotatedImage, x, y);*/
		
		ImageView iv = new ImageView(image);
	    SnapshotParameters params = new SnapshotParameters();
	    params.setFill(Color.TRANSPARENT);
	    params.setTransform(new Rotate((angle + 90)%360, image.getHeight() / 2, image.getWidth() / 2));
	    params.setViewport(new Rectangle2D(0, 0, image.getHeight(), image.getWidth()));
	    Image toDraw =  iv.snapshot(params, null);
	    gc.drawImage(toDraw, x, y, width, height);
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

	private String getImageName(StarShip s, int owner) {
		String imageName = "";
		String name = s.getClass().getName();
		name = name.replace("Model.StarShip.", "");
		
		switch (name) {
			
			case "Arrow":
				switch(owner) {
					case 1:
						imageName = "cursor_01_00.png";
						break;
					case 2:
						imageName = "cursor_01_01.png";
						break;
					case 3:
						imageName = "cursor_01_02.png";
						break;
					case 4:
						imageName = "cursor_01_03.png";
						break;
					case 5:
						imageName = "cursor_01_04.png";
						break;
					case 6:
						imageName = "avastStarship_00.png";
						break;
				}
				break;
			
			case "Finger":
				switch(owner) {
					case 1:
						imageName = "cursor_00_00.png";
						break;
					case 2:
						imageName = "cursor_00_01.png";
						break;
					case 3:
						imageName = "cursor_00_02.png";
						break;
					case 4:
						imageName = "cursor_00_03.png";
						break;
					case 5:
						imageName = "cursor_00_04.png";
						break;
					case 6:
						imageName = "avastStarship_00.png";
						break;
				}
				break;
			
			case "MoveCursor":
				switch(owner) {
					case 1:
						imageName = "cursor_02_00.png";
						break;
					case 2:
						imageName = "cursor_02_01.png";
						break;
					case 3:
						imageName = "cursor_02_02.png";
						break;
					case 4:
						imageName = "cursor_02_03.png";
						break;
					case 5:
						imageName = "cursor_02_04.png";
						break;
					case 6:
						imageName = "avastStarship_00.png";
						break;
				}
				break;
		}
		
		return imageName;
	}
	
	/**
	 * Display all the starships of a squad.
	 * @param squads All the squads of the game
	 */
	public void displaySquads(ArrayList<Squad> squads) {
		for (Squad squad: squads) {
			for (StarShip starship: squad.getStarships()) {
				switch(starship.getOwner()){
					case 0:
						this.gc.setFill(Color.web("#00bb00"));
						break;
					case 1:
						this.gc.setFill(Color.web("#dd0000"));
						break;
				}
				Point where = starship.getPosition();
				
				//Modifier la valeur de l'angle quand c'est le curseur
				this.drawRotate(this.imageBank.getImage(getImageName(starship, squad.getOwner())), starship.getAngle(), where.getX(), 
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
		this.gc.setFill(Color.web("#0000ff66"));
		double edge = 5;
		
		for (Planet planet : planets) {
			Point where = planet.getCollisionShape().getOrigin();
			//this.gc.drawImage(this.imageBank.getImage("zoom_00.png"), where.getX() - planet.getRadius(), 
					//where.getY() - planet.getRadius());
			this.gc.fillRect(where.getX() - planet.getRadius() - edge / 2, where.getY() - planet.getRadius() - edge / 2, 
				planet.getRadius() * 2 + edge, planet.getRadius() * 2 + edge);

		}
	}
	
	/**
	 * Display the squad size.
	 * @param size	Value to display
	 */
	public void displaySquadSize(int size) {
		this.gc.setFill(Color.web("#ffffff"));
		this.gc.setFont(Font.font("Verdana", 15));
		this.gc.fillText(Integer.toString(size) + "%", this.screenWidth - 65, this.screenHeight - 10);
	}
	
	/**
	 * Show the rectangular selection.
	 * @param rect	Dimension of the rectangle
	 */
	public void displaySelectRect(Rectangle rect) {
		this.gc.setStroke(Color.web("#0000ff"));
		this.gc.strokeRect(rect.getOrigin().getX(), rect.getOrigin().getY(), rect.getWidth(), rect.getHeight());
	}
	
	/**
	 * Show the task bar.
	 */
	public void displayTaskBar() {
		int taskBarHeight = 30;
		this.gc.drawImage(this.imageBank.getImage("taskBar.png"), 0, this.screenHeight - taskBarHeight,
				this.screenWidth, taskBarHeight);
	}
	
	
	/**
	 * Second version of the function displayMenu.
	 * Display the image of the menu
	 * @param menu	The menu to display
	 */
	public void displayMenu2(Menu menu) {
		int menuWidth = 350;
		int menuHeight = 450;
		this.gc.setFont(Font.font("Verdana", 17));
		this.gc.setFill(Color.web("#ffffff"));
		String gameName = "Windaube Defender";
		
		switch(menu.getText()) {
		
			case "Start":
				if(menu.isActivated()) {
					//System.out.println("Start activate");
					this.gc.drawImage(this.imageBank.getImage("pauseMenu.png"), 0, 
							this.screenHeight - 30 - menuHeight, menuWidth, menuHeight);
					this.gc.fillText(gameName, 70, this.screenHeight - 290);
				}
				break;
		}
	}
	
	/**
	 * Display the text of the menu.
	 * @param menu The menu to display
	 */
	public void displayTextMenu(Menu menu) {
		double x;
		double y;
		x = menu.getCollisionShape().getOrigin().getX();
		y = menu.getCollisionShape().getOrigin().getY();
		this.gc.setFont(Font.font("Verdana", 13));
		
		switch(menu.getText()) {
		
			case "Save":
				this.gc.setFill(Color.web("#000066"));
				this.gc.fillText(menu.getText(), x + 60, y + 25);
				break;
			case "Load":
				this.gc.setFill(Color.web("#000066"));
				this.gc.fillText(menu.getText(), x + 60, y + 25);
				break;
				
			case "Quit":
				this.gc.setFont(Font.font("Verdana", 13));
				this.gc.setFill(Color.web("#ffffff"));
				this.gc.fillText(menu.getText(), x + 40, y + 22);
				break;
			case "Back":
				this.gc.setFont(Font.font("Verdana", 13));
				this.gc.setFill(Color.web("#ffffff"));
				this.gc.fillText(menu.getText(), x + 40, y + 22);
				break;
		}
		
		
	}
	
	/**
	 * Display the text of the menus.
	 * @param menus The menus to display
	 */
	public void displayTextMenus(ArrayList<Menu> menus) {
		for(Menu menu : menus) {
			if(menu.isAvailable()) {
				displayTextMenu(menu);
				displayTextMenus(menu.getSubMenus());
			}
		}
	}
	
	/**
	 * Display the menus (seconde version of this function)
	 * @param menus The menus to display
	 */
	public void displayMenus2(ArrayList<Menu> menus) {
		
		for(Menu menu : menus) {
			if(menu.isAvailable()) {
				displayMenu2(menu);
				displayMenus2(menu.getSubMenus());
			}
		}
	}
	
	/**
	 * Display the BOSS Avast.
	 * @param avast Avast
	 */
	public void displayAvast(Avast avast) {
		if(avast != null) {
			double x = avast.getOrigin().getX() - avast.getRadius();
			double y = avast.getOrigin().getY() - avast.getRadius(); 
			this.gc.drawImage(this.imageBank.getImage("avast.png"), x, y,
				avast.getWidth(), avast.getWidth());
			
			/*this.gc.setFont(Font.font("Verdana", 16));
			this.gc.setFill(Color.web("#ff000088"));
			
			this.gc.fillOval(x, y, 
					avast.getRadius() * 2, avast.getRadius() * 2);*/
			
			this.gc.setFill(Color.web("#ffffff"));
			this.gc.fillText("La base des données virales", x + 16, y + 80);
			this.gc.fillText("VPS a été mise à jour", x + 16, y + 108);
			this.gc.fillText(avast.getStock() + " virus detected", x + 16, y + 150);
			this.gc.setFont(Font.font("Verdana", 15));
			this.gc.setFill(Color.web("#000000"));
			this.gc.fillText("Don't click  >>", x + 23, y + 197);

		}
	}
	
	/**
	 * Tick function, draw the entire game.
	 * @param game The game
	 */
	public void tick(SceneGame game) {
		this.gc.drawImage(this.imageBank.getImage("default_background.jpg"), 0, 0, this.screenWidth, this.screenHeight);
		
		
		this.displayPlanets(game.getPlanets());
		this.displaySelectedPlanets(game.getSelectedPlanets());
		this.displaySelectedSquads(game.getSelectedSquads());
		this.displaySquads(game.getSquads());
		this.displayAvast(game.getAvast());
		this.displayTaskBar();
		this.displaySquadSize(game.getSquadSize());
		this.displayMenus2(game.getMenus());
		this.displayTextMenus(game.getMenus());
		//this.displayMenus(game.getMenus());
		
		if(game.getIsThereSelectRect()) {
			this.displaySelectRect(game.getSelectRect());
		}
	}
}