package src_advanced.Geometry;

import java.io.Serializable;

/**
 * Represent shape in the game
 *
 */
public abstract class Shape implements Serializable {

	protected Point origin;
	
	/**
	 * Constructor of the shape
	 * @param x	the x coord	of the origin
	 * @param y the y coord of the origin 
	 */
	public Shape(double x, double y) {
		this.origin = new Point(x, y);
	}
	
	/**
	 * Constructor with a given origin
	 * @param p	the origin for the shape.
	 */
	public Shape(Point p) {
		this(p.getX(), p.getY());
	}
	
	/**
	 * Abstract method for collision with point
	 * @param p		The point to check
	 * @return		true if there is collision with point, else false
	 */
	public abstract boolean collision(Point p);
	
	/**
	 * @return the origin of the shape.
	 */
	public Point getOrigin() { 
		return this.origin; 
	}
	
	/**
	 * @param x coordinate for the origin 
	 * @param y coordinate for the origin
	 */
	public void setOrigin(double x, double y) {
		this.origin.setX(x);
		this.origin.setY(y);
	}
	
	/**
	 * Move point
	 * @param dx	x value for deplacement
	 * @param dy	y value for deplacement
	 */
	public void translate(double dx, double dy) {
		this.origin.setX(this.origin.getX() + dx);
		this.origin.setY(this.origin.getY() + dy);
	}
	
	/**
	 * Move the point with a vector
	 * @param p	the point representing the vector
	 */
	public void translate(Point p) {
		translate(p.getX(), p.getY());
	}
}
