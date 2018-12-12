package src_basic.Geometry;

import java.io.Serializable;

/**
 * Represent shape in the game
 * @author chocorion
 *
 */
public abstract class Shape implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8900919479238777190L;
	protected Point origin;
	
	public Shape(double x, double y) {
		this.origin = new Point(x, y);
	}
	
	public Shape(Point p) {
		this(p.getX(), p.getY());
	}
	
	public abstract boolean collision(Point p);
	
	public Point getOrigin()	{ return this.origin; }
	
	public void setOrigin(double x, double y) {
		this.origin.setX(x);
		this.origin.setY(y);
	}
	
	public void translate(double dx, double dy) {
		this.origin.setX(this.origin.getX() + dx);
		this.origin.setY(this.origin.getY() + dy);
	}
	
	public void translate(Point p) {
		translate(p.getX(), p.getY());
	}
}
