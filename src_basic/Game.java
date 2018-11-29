package src_basic;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import src_basic.Controller.Controller;
import src_basic.Model.Scene.SceneManager;

public class Game extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {
		
		System.err.println("Yolo");
		
		stage.setTitle("Andromeda");
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		Canvas canvas = new Canvas( 960, 640 );
	    root.getChildren().add( canvas );
	         
	    GraphicsContext gc = canvas.getGraphicsContext2D();
	    
	    final SceneManager sceneManager = new SceneManager(gc);
	    final Controller controller = new Controller(scene, sceneManager);
		
	    new AnimationTimer(){
	        public void handle(long currentNanoTime){
	        	
	        	if (!sceneManager.tick()) {
	        		System.exit(0);
	        	}
	        	controller.tick();
	        }
	    }.start();
		
		
		
		stage.show();
	}
}