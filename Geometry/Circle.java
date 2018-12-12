package Geometry;

public class Circle extends Shape {
	double radius;
	
	public Circle(double x, double y, double radius) {
		super(x, y);
		this.radius = radius;
	}
	
	@Override
	public boolean collision(Point p) {
		return this.origin.distance(p) <= this.radius;
	}
	
	public double getRadius()	{ return this.radius; }
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
}
