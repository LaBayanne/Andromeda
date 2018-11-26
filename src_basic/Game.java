package src_basic;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import src_basic.Model.Scene.SceneManager;

public class Game extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {
		stage.setTitle("Andromeda");
		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		Canvas canvas = new Canvas( 960, 640 );
	    root.getChildren().add( canvas );
	         
	    GraphicsContext gc = canvas.getGraphicsContext2D();
	    
	    final SceneManager sceneManager = new SceneManager(gc);
		
	    new AnimationTimer(){
	        public void handle(long currentNanoTime){
	        	if (!sceneManager.tick()) {
	        		return;
	        	}
	        }
	    }.start();
		
		
		
		stage.show();
	}
}