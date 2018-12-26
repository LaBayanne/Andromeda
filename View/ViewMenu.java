package View;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import Model.Menus.Menu;
import Model.Scene.SceneMenu;

/**
 * This class will represent the view of the menu.
 * Not used in the game's first version.
 */
public class ViewMenu {
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
	public ViewMenu(GraphicsContext gc, int width, int height) {
		this.gc = gc;
		this.screenWidth = width;
		this.screenHeight = height;
		this.imageBank = new ImageBank();
		
		
	}
	
	/**
	 * Display the task bar
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
					this.gc.drawImage(this.imageBank.getImage("mainMenu.png"), 0, 
							this.screenHeight - 30 - menuHeight, menuWidth, menuHeight);
					this.gc.fillText(gameName, 70, this.screenHeight - 290);
				}
				break;
				
			case "Play":
				if(menu.isActivated()) {
					System.out.println("Play activate");
					this.gc.drawImage(this.imageBank.getImage("mainMenuPlayersChoice.png"), 0, 
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
		
			case "Play":
				this.gc.setFill(Color.web("#000066"));
				this.gc.fillText(menu.getText(), x + 60, y + 25);
				break;
			case "Load":
				this.gc.setFill(Color.web("#000066"));
				this.gc.fillText(menu.getText(), x + 60, y + 25);
				break;
				
			case "2 Players":
				this.gc.setFont(Font.font("Verdana", 12));
				this.gc.setFill(Color.web("#000066"));
				this.gc.fillText(menu.getText(), x + 50, y + 23);
				break;
			case "3 Players":
				this.gc.setFont(Font.font("Verdana", 12));
				this.gc.setFill(Color.web("#000066"));
				this.gc.fillText(menu.getText(), x + 50, y + 23);
				break;
			case "4 Players":
				this.gc.setFont(Font.font("Verdana", 12));
				this.gc.setFill(Color.web("#000066"));
				this.gc.fillText(menu.getText(), x + 50, y + 23);
				break;
			case "5 Players":
				this.gc.setFont(Font.font("Verdana", 12));
				this.gc.setFill(Color.web("#000066"));
				this.gc.fillText(menu.getText(), x + 50, y + 23);
				break;
				
			case "Quit":
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
	 * Tick function, draw the entire game.
	 * @param game The game
	 */
	public void tick(SceneMenu menu) {
		this.gc.drawImage(this.imageBank.getImage("default_background.jpg"), 0, 0, this.screenWidth, this.screenHeight);
		
		this.displayTaskBar();
		this.displayMenus2(menu.getMenus());
		this.displayTextMenus(menu.getMenus());
		
	}
}