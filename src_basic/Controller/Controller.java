package src_basic.Controller;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import src_basic.Model.Scene.SceneManager;

public class Controller{
	ArrayList<String> input;
	SceneManager sceneManager;
	
	public Controller(Scene scene, SceneManager scnManager) {
		this.sceneManager = scnManager;
		this.input = new ArrayList<String>();
		scene.setOnKeyPressed(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
 
                    // only add once... prevent duplicates
                    if ( !input.contains(code) )
                        input.add( code );
                }
            });
	 
        scene.setOnKeyReleased(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    input.remove( code );
                }
            });
        
        scene.setOnMouseClicked(
            new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent e)
                {
                	if(e.getButton() == MouseButton.PRIMARY)
                		mouseClicked(0, e.getX(), e.getY());
                	if(e.getButton() == MouseButton.SECONDARY)
                		mouseClicked(1, e.getX(), e.getY());
                }
            });
        
        scene.setOnScroll(
        	new EventHandler<ScrollEvent>() 
        	{
        		public void handle(ScrollEvent e) {
        			moveWheel((int)e.getDeltaY());
        		}
        });
	}	
	
	public void mouseClicked(int button, double x, double y) {
		this.sceneManager.mouseClicked(button, x, y);
	}
	
	public void moveWheel(int dy) {
		//Les 20 magiques
		this.sceneManager.moveWheel(dy / 10);
	}
	
	public void tick() {
		if(this.input.contains("ESCAPE")){
			this.sceneManager.inputEscape();
		}
	}
}