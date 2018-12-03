package src_basic.Model.Scene;

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
	
	public void mouseClicked(int button, double x, double y) {
		this.activeScene.mouseClicked(button, x, y);
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
	public boolean tick() {
		if(!this.continueGame)
			return false;
		return this.activeScene.tick();	//C'est pour ça que Scene nous est utile et que je l'ai remis
	}
}