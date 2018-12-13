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
 * The scene manager manage the input from controller, and manage the current scene.
 *
 */
public class SceneManager {
	private Scenery activeScene;
	private SceneGame gameScene;
	private Scenery menuScene;//not used on game's first version
	private boolean continueGame;
	private GraphicsContext gc;
	
	/**
	 * Basic constructor.
	 * @param gc Graphic context
	 */
	public SceneManager(GraphicsContext gc, int width, int height) {
		this.gc = gc;
		
		this.gameScene = new SceneGame(gc, width, height);
		this.menuScene = new SceneMenu(gc);
		
		/* First scene to show. By default, it's the game */
		this.activeScene = this.gameScene;
		this.continueGame = true;
	}
	
	/* User events */
	
	/**
	 * Event triggered when the mouse is clicked. Call the associated fonction of the active scene.
	 * @param button Button of the mouse(left : 0, right : 1)
	 * @param x Mouse's x position
	 * @param y Mouse's y position
	 * @param buttonOptions List of inputs that are pressed (mainly control)
	 */
	public void mouseClicked(int button, double x, double y, ArrayList<String> buttonOptions) {
		this.activeScene.mouseClicked(button, x, y, buttonOptions);
	}
	
	/**
	 * Event triggered when the wheel is moved. Call the associated fonction of the active scene.
	 * @param dy Wheel's vertical delta
	 */
	public void moveWheel(int dy) {
		this.activeScene.moveWheel(dy);
	}
	
	/**
	 * Event triggered when the mouse left is pressed. Call the associated fonction of the active scene.
	 * @param x Mouse's x position
	 * @param y Mouse's y position
	 */
	public void inputMouseLeft(double x, double y) {
		this.activeScene.inputMouseLeft(x, y);
	}
	
	/**
	 * Event triggered when the mouse left is released. Call the associated fonction of the active scene.
	 * @param x Mouse's x position
	 * @param y Mouse's y position
	 * @param buttonOptions List of inputs that are pressed (mainly control)
	 */
	public void releasedMouseLeft(double x, double y, ArrayList<String> buttonOptions) {
		this.activeScene.releasedMouseLeft(x, y, buttonOptions);
	}
	
	/**
	 * 	End the game when escape is pressed
	 */
	public void inputEscape() {
		this.continueGame = false;
	}
	
	/**
	 * Run the tick of the actual scene
	 * @param delta Delay since the previous tick of the game
	 * @return true if the game continue, else false
	 */
	public boolean tick(double delta) {
		if(!this.continueGame)
			return false;
		return this.activeScene.tick(delta);
	}
	
	/**
	 * Save the actual context of the game.
	 */
	public void saveGame() {
		ObjectOutputStream oos = null;
		
		try {
			final FileOutputStream saveFile = new FileOutputStream("save.ser");
			oos = new ObjectOutputStream(saveFile);
			oos.writeObject(this.gameScene);
			oos.flush();
			saveFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Load the unique save file of the game.
	 */
	public void restoreGame() {
		ObjectInputStream ois = null;
		
		try {
			final FileInputStream saveFile = new FileInputStream("save.ser");
			ois = new ObjectInputStream(saveFile);
			
			this.gameScene = null;
			this.gameScene = (SceneGame) ois.readObject();
			this.gameScene.restor(this.gc);
			this.activeScene = gameScene;
			saveFile.close();
			
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