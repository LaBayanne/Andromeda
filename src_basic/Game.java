package src_basic;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import src_basic.Controller.Controller;
import src_basic.Model.Scene.SceneManager;

/**
 * Main class of the game.
 *
 */
public class Game extends Application {
	private int screenWidth = 960;
	private int screenHeight = 640;
	
	/**
	 * Main, launch the function start of javafx
	 * @param args Arguments of the command line
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Main function of the game, init all the element of javafx and lauch the game's loop.
	 */
	public void start(Stage stage) {
		
		stage.setTitle("Andromeda");
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		Canvas canvas = new Canvas(screenWidth , screenHeight);
	    root.getChildren().add( canvas );
	         
	    GraphicsContext gc = canvas.getGraphicsContext2D();
	    
	    final SceneManager sceneManager = new SceneManager(gc, screenWidth , screenHeight);
	    final Controller controller = new Controller(scene, sceneManager);
	    
		/** Game loop **/
	    new AnimationTimer(){
	    	long prevNanoTime = System.nanoTime();
	        public void handle(long currentNanoTime){
	        	double delta = (currentNanoTime - prevNanoTime) / 1000000;
	        	if (!sceneManager.tick(delta)) {
	        		System.exit(0);
	        	}
	        	controller.tick();
	        	prevNanoTime = currentNanoTime;
	        }
	    }.start();
		
		
		
		stage.show();
	}
}