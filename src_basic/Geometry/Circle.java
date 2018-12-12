package src_basic.Geometry;

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
	
	public boolean collision(Rectangle r) {
		Point points[] = {
				r.getOrigin(),
				new Point (r.getOrigin().getX() + r.getWidth(), r.getOrigin().getY()),
				new Point (r.getOrigin().getX(), r.getOrigin().getY() + r.getHeight()),
				new Point (r.getOrigin().getX() + r.getWidth(), r.getOrigin().getY() + r.getHeight())
		};
		
		for (Point p : points) {
			if (collision(p)) {
				return true;
			}
		}
		
		return false;
	}
	
	public double getRadius()	{ return this.radius; }
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
}
