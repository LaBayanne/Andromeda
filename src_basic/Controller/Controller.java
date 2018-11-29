package src_basic.Controller;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
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
	}
	
	public void tick() {
		if(this.input.contains("ESCAPE")){
			this.sceneManager.inputEscape();
		}
	}
}