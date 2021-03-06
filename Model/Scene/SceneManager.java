package Model.Scene;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import Geometry.Point;
import Model.Menus.Menu;
import View.SongController;

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
	private int screenWidth;
	private int screenHeight;
	private SongController songController;
	
	/**
	 * Basic constructor.
	 * @param gc Graphic context
	 */
	public SceneManager(GraphicsContext gc, int width, int height) {
		this.gc = gc;
		this.screenWidth = width;
		this.screenHeight = height;
		
		this.continueGame = true;
		this.songController = new SongController();
		songController.playSong("song_01.wav");
		songController.setVolume(0.5);
		
		this.newMainMenu();
	}
	
	/**
	 * Manage the menus of the game
	 * @param x	The x position of the mouse
	 * @param y	The y position of the mouse
	 */
	public void selectMenu(double x, double y) {
		String text = "";
		
		for(Menu menu : this.activeScene.getMenus()) {
			if((text = menu.collision(new Point(x, y))) != "")
				break;
		}
		
		System.out.println("Texte menu : " + text);
		
		switch(text) {
			
			//In game
			case "/Start/Save":
				this.saveGame();
				break;
			case "/Start/Load":
				this.restoreGame();
				break;
			case "/Start/Back":
				this.newMainMenu();
				break;
			case "/Start/Quit":
				this.continueGame = false;
				break;
				
			//In main menu
			case "/Start/Play/2 Players":
				this.newGame(2);
				break;
			case "/Start/Play/3 Players":
				this.newGame(3);
				break;
			case "/Start/Play/4 Players":
				this.newGame(4);
				break;
			case "/Start/Play/5 Players":
				this.newGame(5);
				break;
			
		}
	}
	
	/**
	 * Create a new game
	 * @param nbPlayers
	 */
	public void newGame(int nbPlayers) {
		System.out.println("NewGame");
		this.songController.playSong("song_01.wav");
		this.gameScene = new SceneGame(this.gc, this.screenWidth, this.screenHeight, nbPlayers);
		this.activeScene = this.gameScene;
	}
	
	/**
	 * Create a new Main menu
	 */
	public void newMainMenu() {
		System.out.println("NewMainMenu");
		this.songController.playSong("song_00.wav");
		this.menuScene = new SceneMenu(this.gc, this.screenWidth, this.screenHeight);
		this.activeScene = this.menuScene;
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
		selectMenu(x, y);
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
		if(this.activeScene.getMenus().get(0).isActivated())
			this.activeScene.getMenus().get(0).deactivate();
		else
			this.activeScene.getMenus().get(0).activate();
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