package Geometry;

import java.io.Serializable;


/**
 * Represent a basic point.
 * @author chocorion
 *
 */
public class Point implements Serializable {
	private double x;
	private double y;
	
	/**
	 * Default constructor
	 * @param x the x value 
	 * @param y the y value
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor by copy.
	 * @param p Point to copy.
	 */
	public Point(Point p) {
		this(p.getX(), p.getY());
	}
	
	/**
	 * Basic constructor
	 */
	public Point() {
		this(0, 0);
	}
	
	/**
	 * Calculate distance between point and coord
	 * @param x	the x value
	 * @param y	the y value
	 * @return	the distance
	 */
	public double distance(double x, double y) {
		double dx = this.x - x;
		double dy = this.y - y;
		
		dx *= dx;
		dy *= dy;
		
		return Math.sqrt(dx + dy);
	}
	
	/**
	 * Move point
	 * @param dx	x deplacement
	 * @param dy	y deplacement
	 */
	public void translate(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}
	
	/**
	 * Calculate angle between two point
	 * @param p	the second point
	 * @return
	 */
	public double angle(Point p) {
		return Math.toDegrees(Math.atan2(p.getY() - this.y, p.getX() - this.x));
	}
	
	/**
	 * Calculate disctance between two points
	 * @param p	the other point
	 * @return
	 */
	public double distance(Point p) {
		return distance(p.getX(), p.getY());
	}
	
	/**
	 * @return x coord
	 */
	public double getX() { 
		return this.x;
	}
	
	/**
	 * @return y coord
	 */
	public double getY() { 
		return this.y; 
	}
	
	/**
	 * @param x the new value for x.
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * @param y the new value for y.
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Set point to specific position
	 * @param p the new position of the point
	 */
	public void set(Point p) {
		setX(p.getX());
		setY(p.getY());
	}
	
	public void set(double x, double y) {
		setX(x);
		setY(y);
	}
}
