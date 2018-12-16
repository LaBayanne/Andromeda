package src_advanced.View;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import src_advanced.Model.Menus.Menu;
import src_advanced.Model.Scene.SceneMenu;

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
	
	public void displayTaskBar() {
		int taskBarHeight = 30;
		this.gc.drawImage(this.imageBank.getImage("taskBar.png"), 0, this.screenHeight - taskBarHeight,
				this.screenWidth, taskBarHeight);
	}
	
	public void displayMenu(Menu menu) {
		this.gc.setStroke(Color.web("#0000ff"));
		this.gc.setFont(Font.font("Verdana", 17));
		this.gc.setFill(Color.web("#ffffff"));
		double x;
		double y;
		double width;
		double height;
		x = menu.getCollisionShape().getOrigin().getX();
		y = menu.getCollisionShape().getOrigin().getY();
		width = menu.getCollisionShape().getWidth();
		height = menu.getCollisionShape().getHeight();
		this.gc.fillRect(x, y, width, height);
		this.gc.strokeRect(x, y, width, height);
		this.gc.setFill(Color.web("#0000ff"));
		this.gc.fillText(menu.getText(), x + 5, y + 22);
	}
	
	public void displayMenus(ArrayList<Menu> menus) {
		
		for(Menu menu : menus) {
			if(menu.isAvailable()) {
				displayMenu(menu);
				displayMenus(menu.getSubMenus());
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
		this.displayMenus(menu.getMenus());
	}
}