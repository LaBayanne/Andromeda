package src_advanced.Model.Scene;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import src_advanced.Geometry.Rectangle;
import src_advanced.Model.Menus.Menu;
import src_advanced.View.ViewMenu;

/**
 * This class represent the menu, and manage all inputs from the controller. 
 * It's not used on the game's first version.
 *
 */
public class SceneMenu implements Scenery {
	private GraphicsContext gc;
	private ViewMenu view;
	private ArrayList<Menu> menus;
	private int screenWidth, screenHeight;
	
	public SceneMenu(GraphicsContext gc, int width, int height) {
		this.gc = gc;
		this.view = new ViewMenu(gc, width, height);
		this.screenWidth = width;
		this.screenHeight = height;
		
		
		this.menus = new ArrayList<Menu>();
		
		int menuWidth = 120;
		int menuHeight = 35;
		
		Menu startMenu = new Menu("Start", new Rectangle(0, height - menuHeight, menuWidth, menuHeight), true, false);
		
		Menu loadMenu = new Menu("Load", new Rectangle(0, height - menuHeight * 3, menuWidth, menuHeight), false, false);
		
		Menu playMenu = new Menu("Play", new Rectangle(0, height - menuHeight * 4, menuWidth, menuHeight), false, false);
		
		Menu nbPlayers2Menu = new Menu("2 Players", new Rectangle(menuWidth, height - menuHeight * 4, menuWidth, menuHeight), false, false);
		Menu nbPlayers3Menu = new Menu("3 Players", new Rectangle(menuWidth, height - menuHeight * 5, menuWidth, menuHeight), false, false);
		Menu nbPlayers4Menu = new Menu("4 Players", new Rectangle(menuWidth, height - menuHeight * 6, menuWidth, menuHeight), false, false);
		Menu nbPlayers5Menu = new Menu("5 Players", new Rectangle(menuWidth, height - menuHeight * 7, menuWidth, menuHeight), false, false);
		playMenu.addMenu(nbPlayers2Menu);
		playMenu.addMenu(nbPlayers3Menu);
		playMenu.addMenu(nbPlayers4Menu);
		playMenu.addMenu(nbPlayers5Menu);
		
		Menu quitMenu = new Menu("Quit", new Rectangle(menuWidth - menuHeight, height - menuHeight * 2, menuHeight, menuHeight), false, false);
		
		startMenu.addMenu(quitMenu);
		startMenu.addMenu(playMenu);
		startMenu.addMenu(loadMenu);
		this.menus.add(startMenu);
	}
	
	public boolean tick(double delta) {
		this.view.tick(this);
		return true;
	}
	
	public void mouseClicked(int button, double x, double y, ArrayList<String> buttonOptions) {
		
	}
	
	public void moveWheel(int dy) {
		
	}
	
	public void inputMouseLeft(double x, double y) {
		
	}
	
	public void releasedMouseLeft(double x, double y, ArrayList<String> buttonOptions) {
		
	}
	
	public ArrayList<Menu> getMenus(){
		return this.menus;
	}
	
}