package Geometry;

public class Rectangle extends Shape {
	private double width;
	private double height;
	
	public Rectangle(double x, double y, double width, double height) {
		super(x, y);
		
		this.width  = width;
		this.height = height;
	}
	
	@Override
	public boolean collision(Point p) {
		double x = p.getX();
		double y = p.getY();
		
		return (
				!(x < this.origin.getX() || x > this.origin.getX() + this.width || y < this.origin.getY() || y > this.origin.getY() + this.height)
		);
	}
	
	public double getWith()		{ return this.width;  }
	public double getHeight()	{ return this.height; }
	
	public void setWidth(double width) {
		this.width  = width;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
}