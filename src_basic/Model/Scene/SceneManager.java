package src_basic.Model.Scene;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class SceneManager {
	private Scenery activeScene;
	private SceneGame gameScene;
	private Scenery menuScene;
	private boolean continueGame;
	
	private GraphicsContext gc;
	
	public SceneManager(GraphicsContext gc) {
		this.gc = gc;
		
		this.gameScene = new SceneGame(gc);
		this.menuScene = new SceneMenu(gc);
		
		// A changer si on voudra commencer le jeu avec le menu
		this.activeScene = this.gameScene;
		this.continueGame = true;
	}
	
	/////User events
	
	public void mouseClicked(int button, double x, double y, ArrayList<String> buttonOptions) {
		this.activeScene.mouseClicked(button, x, y, buttonOptions);
	}
	
	public void moveWheel(int dy) {
		this.activeScene.moveWheel(dy);
	}
	
	public void inputMouseLeft(double x, double y) {
		this.activeScene.inputMouseLeft(x, y);
	}
	
	public void releasedMouseLeft(double x, double y, ArrayList<String> buttonOptions) {
		this.activeScene.releasedMouseLeft(x, y, buttonOptions);
	}
	
	/**
	 * 
	 */
	public void inputEscape() {
		this.continueGame = false;
	}
	
	/**
	 * Run the tick of the actual scene
	 * @return true if the game continue, else false
	 */
	public boolean tick(double delta) {
		if(!this.continueGame)
			return false;
		return this.activeScene.tick(delta);	//C'est pour ça que Scene nous est utile et que je l'ai remis
	}
}