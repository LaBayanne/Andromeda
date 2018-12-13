package src_basic.Geometry;

import java.io.Serializable;


/**
 * Represent a basic point.
 * @author chocorion
 *
 */
public class Point implements Serializable {
	private double x;
	private double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point p) {
		this(p.getX(), p.getY());
	}
	
	public Point() {
		this(0, 0);
	}
	
	
	public double distance(double x, double y) {
		double dx = this.x - x;
		double dy = this.y - y;
		
		dx *= dx;
		dy *= dy;
		
		return Math.sqrt(dx + dy);
	}
	
	public void translate(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public double angle(Point p) {
		return Math.toDegrees(Math.atan2(p.getY() - this.y, p.getX() - this.x));
	}
	
	public double distance(Point p) {
		return distance(p.getX(), p.getY());
	}
	
	public double getX()	{ return this.x; }
	public double getY()	{ return this.y; }
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void set(Point p) {
		setX(p.getX());
		setY(p.getY());
	}
}
