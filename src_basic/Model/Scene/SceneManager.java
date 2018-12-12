package src_basic.Model.Scene;

import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import javafx.scene.canvas.GraphicsContext;

/**
 * Represent the scene manager.
 * The scene manager manage the input from controller, 
 * and manage the current scene.
 * @author chocorion
 *
 */
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
		
		/* First scene to show. By default, it's the game */
		this.activeScene = this.gameScene;
		this.continueGame = true;
	}
	
	/* User events */
	
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
	 * 	End the game
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
	
	public void saveGame() {
		System.err.println("Saving game !");
		ObjectOutputStream oos = null;
		
		try {
			final FileOutputStream saveFile = new FileOutputStream("save.ser");
			oos = new ObjectOutputStream(saveFile);
			oos.writeObject(this.gameScene);
			oos.flush();
			saveFile.close();
			System.err.println("Save OK !");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
					System.err.println("Closing stream");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void restoreGame() {
		System.err.println("RESTORING GAME !");
		ObjectInputStream ois = null;
		
		try {
			final FileInputStream saveFile = new FileInputStream("save.ser");
			ois = new ObjectInputStream(saveFile);
			
			System.err.println("Scenegame value before restauration : " + this.gameScene);
			this.gameScene = null;
			this.gameScene = (SceneGame) ois.readObject();
			this.gameScene.restor(this.gc);
			this.activeScene = gameScene;
			saveFile.close();
			System.err.println("Scenegame value after restauration : " + this.gameScene);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}