package src_basic.Model;

import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * This class is used to detect collision between objects in the game
 * @author Labayanne
 *
 */
public class Hitbox implements Serializable {
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
	
	public boolean collision(Point2D point) {
		return this.shape.contains(point);
	}
	
	public Shape getShape() {
		return this.shape;
	}
}