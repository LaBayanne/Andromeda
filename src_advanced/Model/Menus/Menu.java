package src_advanced.Model.Menus;

import java.io.Serializable;
import java.util.ArrayList;

import src_advanced.Geometry.Point;
import src_advanced.Geometry.Rectangle;

/**
 * Represent a menu who can manage any number of submenus
 *
 */
public class Menu implements Serializable{
	
	private String text;
	private Rectangle collisionShape;
	private boolean available;
	private boolean activated;
	ArrayList<Menu> subMenus;
	
	
	/**
	 * Basic contructor.
	 * @param text	The text of the menu
	 * @param collisionShape	The shape
	 * @param available			True if we can press the menu, false else
	 * @param activated			True if the menu is pressed
	 */
	public Menu(String text, Rectangle collisionShape, boolean available, boolean activated) {
		this.text = text;
		this.collisionShape = new Rectangle(collisionShape);
		this.available = available;
		this.activated = activated;
		this.subMenus = new ArrayList<Menu>();
	}
	
	/**
	 * Manage the collision with the mouse and the its consequences.
	 * Make available his submenus and return the menu who is pressed when collision.
	 * @param cursor  The position of the mouse
	 * @return 		The path of the menu who is pressed
	 */
	public String collision(Point cursor) {
		String textReturn = "";
		String path = "/" + this.text;
		String text = "";
		if(this.available) {
			if(this.collisionShape.collision(cursor)) {
				if(this.activated) 
					this.deactivate();
				else 
					this.activate();
				return textReturn += path;
			}
			
			if(textReturn == "") {
				for(Menu menu : this.subMenus) {
					if((text = menu.collision(cursor)) != "") {
						return textReturn += path + text;
					}
				}
			}
		}
		this.deactivate();
		return textReturn;
	}
	
	/**
	 * Activate the menu.
	 */
	public void activate() {
		this.activated = true;
		for(Menu menu : this.subMenus) {
			menu.makeAvailable();
		}
	}
	
	/**
	 * Deactivate the menu.
	 */
	public void deactivate() {
		this.activated = false;
		for(Menu menu : this.subMenus) {
			menu.makeUnavailable();
			menu.deactivate();
		}
	}
	
	/**
	 * Make available the menu.
	 */
	public void makeAvailable() {
		this.available = true;
	}
	
	/**
	 * Make unavailable the menu.
	 */
	public void makeUnavailable() {
		this.available = false;
	}
	
	public void addMenu(Menu menu) {
		this.subMenus.add(menu);
	}
	
	/**
	 * 
	 * @return ArrayList of submenus
	 */
	public ArrayList<Menu> getSubMenus() {return this.subMenus;}
	
	/**
	 * 
	 * @return The collisionShape
	 */
	public Rectangle getCollisionShape() {return this.collisionShape;}
	
	/**
	 * 
	 * @return True is the menu is available, else false
	 */
	public boolean isAvailable() {return this.available;}
	
	/**
	 * 
	 * @return True if the menu is activated, else false
	 */
	public boolean isActivated() {return this.activated;}
	
	/**
	 * 
	 * @return The text of the menu
	 */
	public String getText() {return this.text;}
}
