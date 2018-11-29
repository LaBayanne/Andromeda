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
	
	public boolean collision(Hitbox hitbox) {
		return hitbox.getShape().getBoundsInParent().intersects(this.shape.getBoundsInParent());
	}
	
	public boolean collision(Shape shape) {
		return shape.getBoundsInParent().intersects(this.shape.getBoundsInParent());
	}
	
	public Shape getShape() {
		return this.shape;
	}
}