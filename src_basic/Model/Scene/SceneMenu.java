package src_basic.Model.Scene;

import javafx.scene.canvas.GraphicsContext;
import src_basic.View.ViewMenu;

public class SceneMenu implements Scenery {
	private GraphicsContext gc;
	private ViewMenu view;
	
	public SceneMenu(GraphicsContext gc) {
		this.gc = gc;
		this.view = new ViewMenu(gc);
	}
	
	public boolean tick() {
		this.view.tick();
		return false;
	}
	
	public void mouseClicked(int button, double x, double y) {
		
	}
	
	public void moveWheel(int dy) {
		
	}
}