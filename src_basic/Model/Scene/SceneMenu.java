package src_basic.Model.Scene;

import javafx.scene.canvas.GraphicsContext;

public class SceneMenu implements Scene {
	private GraphicsContext gc;
	
	
	public SceneMenu(GraphicsContext gc) {
		this.gc = gc;
	}
	
	@Override
	public boolean tick() {
		return false;
	}
}