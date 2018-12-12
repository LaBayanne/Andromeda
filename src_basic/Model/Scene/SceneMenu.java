package src_basic.Model.Scene;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import src_basic.View.ViewMenu;

/**
 * This class represent the menu, and manage all inputs from the controller
 * @author chocorion
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