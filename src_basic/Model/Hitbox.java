package src_basic.Model;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Hitbox{
	Shape shape;
	
	public Hitbox(Rectangle rect) {
		this.shape = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}
	
	public Hitbox(Circle circle) {
		this.shape = new Circle(circle.getCenterX(), circle.getCenterY(), circle.getRadius());
	}
}