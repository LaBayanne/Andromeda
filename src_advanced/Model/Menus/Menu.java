package src_advanced.Model.Menus;

import java.io.Serializable;
import java.util.ArrayList;

import src_advanced.Geometry.Point;
import src_advanced.Geometry.Rectangle;

public class Menu implements Serializable{
	
	private String text;
	private Rectangle collisionShape;
	private boolean available;
	private boolean activated;
	ArrayList<Menu> subMenus;
	
	
	public Menu(String text, Rectangle collisionShape, boolean available, boolean activated) {
		this.text = text;
		this.collisionShape = new Rectangle(collisionShape);
		this.available = available;
		this.activated = activated;
		this.subMenus = new ArrayList<Menu>();
	}
	
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
						textReturn += path + text;
					}
				}
			}
		}
		this.deactivate();
		return textReturn;
	}
	
	public void activate() {
		this.activated = true;
		for(Menu menu : this.subMenus) {
			menu.makeAvailable();
		}
	}
	
	public void deactivate() {
		this.activated = false;
		for(Menu menu : this.subMenus) {
			menu.makeUnavailable();
			menu.deactivate();
		}
	}
	
	public void makeAvailable() {
		this.available = true;
	}
	
	public void makeUnavailable() {
		this.available = false;
	}
	
	public void addMenu(Menu menu) {
		this.subMenus.add(menu);
	}
	
	public ArrayList<Menu> getSubMenus() {return this.subMenus;}
	public Rectangle getCollisionShape() {return this.collisionShape;}
	public boolean isAvailable() {return this.available;}
	public boolean isActivated() {return this.activated;}
	public String getText() {return this.text;}
}
