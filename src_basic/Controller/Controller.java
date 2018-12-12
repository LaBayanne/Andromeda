package src_basic.Controller;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import src_basic.Model.Scene.SceneManager;

/**
 * This class get all the input from the user, and call the associate fonction in scene manager
 * @author Labayanne
 *
 */
public class Controller{
	ArrayList<String> input;
	SceneManager sceneManager;
	Point2D mousePos;
	
	/**
	 * Basic constructor
	 * @param scene			Javafx scene
	 * @param scnManager	The scene manager
	 */
	public Controller(Scene scene, SceneManager scnManager) {
		this.sceneManager = scnManager;
		this.input = new ArrayList<String>();

		scene.setOnKeyPressed(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    System.out.println(code);
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
        
        scene.setOnMousePressed(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                    	setMousePos(e.getX(), e.getY());
                    	String code;
                    	if(e.getButton() == MouseButton.PRIMARY) {
                    		code = "MOUSE_LEFT";
	                        if ( !input.contains(code) )
	                            input.add( code );
                    	}
                    	if(e.getButton() == MouseButton.SECONDARY){
                    		code = "MOUSE_RIGHT";
	                        if ( !input.contains(code) )
	                            input.add( code );
                    	}
                    	
                    }
                });
        
        scene.setOnMouseReleased(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                    	if(e.getButton() == MouseButton.PRIMARY) 
                    		releasedMouseLeft();
                    	if(e.getButton() == MouseButton.SECONDARY)
                    		input.remove("MOUSE_RIGHT");
                    }
                });

        scene.setOnMouseDragged(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                    	setMousePos(e.getX(), e.getY());
                    	
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
	
	public void releasedMouseLeft() {
		this.input.remove("MOUSE_LEFT");
		this.sceneManager.releasedMouseLeft(this.mousePos.getX(), this.mousePos.getY(), this.input);
	}
	
	public void setMousePos(double x, double y) {
		this.mousePos = new Point2D(x, y);
	}
	
	public void mouseClicked(int button, double x, double y) {
		this.sceneManager.mouseClicked(button, x, y, this.input);
	}
	
	public void moveWheel(int dy) {
		//Les 20 magiques
		this.sceneManager.moveWheel(dy / 10);
	}
	
	public void tick() {
		if(this.input.contains("ESCAPE")){
			this.input.remove("ESCAPE");
			this.sceneManager.inputEscape();
		}
		if(this.input.contains("MOUSE_LEFT")){
			this.sceneManager.inputMouseLeft(this.mousePos.getX(), this.mousePos.getY());
		}
		if(this.input.contains("CONTROL")){
			if(this.input.contains("S")) {
				this.sceneManager.saveGame();
				this.input.remove("S");
			}
			else if(this.input.contains("L")) {
				this.sceneManager.restoreGame();
				this.input.remove("L");
			}
		}
	}
}