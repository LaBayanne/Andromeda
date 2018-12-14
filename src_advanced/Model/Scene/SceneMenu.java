package src_advanced.Model.Scene;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import src_advanced.View.ViewMenu;

/**
 * This class represent the menu, and manage all inputs from the controller. 
 * It's not used on the game's first version.
 *
 */
public class SceneMenu implements Scenery {
	private GraphicsContext gc;
	private ViewMenu view;
	
	public SceneMenu(GraphicsContext gc) {
		this.gc = gc;
		this.view = new ViewMenu(gc);
	}
	
	public boolean tick(double delta) {
		this.view.tick();
		return false;
	}
	
	public void mouseClicked(int button, double x, double y, ArrayList<String> buttonOptions) {
		
	}
	
	public void moveWheel(int dy) {
		
	}
	
	public void inputMouseLeft(double x, double y) {
		
	}
	
	public void releasedMouseLeft(double x, double y, ArrayList<String> buttonOptions) {
		
	}
	
}