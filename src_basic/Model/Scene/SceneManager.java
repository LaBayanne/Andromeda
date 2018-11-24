package src_basic.Model.Scene;

import javafx.scene.canvas.GraphicsContext;

public class SceneManager {
	private Scene activeScene;
	private Scene gameScene;
	private Scene menuScene;
	
	private GraphicsContext gc;
	
	public SceneManager(GraphicsContext gc) {
		this.gc = gc;
		
		this.gameScene = new SceneGame(gc);
		this.menuScene = new SceneMenu(gc);
	}
	
	/**
	 * Run the tick of the actual scene
	 * @return true if the game continue, else false
	 */
	public boolean tick() {
		return this.activeScene.tick();	//C'est pour ça que Scene nous est utile et que je l'ai remis
	}
}