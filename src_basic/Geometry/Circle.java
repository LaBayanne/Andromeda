package src_basic.Geometry;

/**
 * Represent a basic circle
 * @author chocorion
 *
 */
public class Circle extends Shape {
	double radius;
	
	/**
	 * Basic constructor
	 * @param x		position x of the circle's center
	 * @param y		position y of the circle's center
	 * @param radius
	 */
	public Circle(double x, double y, double radius) {
		super(x, y);
		this.radius = radius;
	}
	
	
	@Override
	public boolean collision(Point p) {
		return this.origin.distance(p) <= this.radius;
	}
	
	/**
	 * Detect a basic collision with rectangle.
	 * @param r	Rectangle
	 * @return	True if there is a collision, else false.
	 */
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
		
		return r.collision(this);
		
	}
	
	/**
	 * @return	current radius (in pixel)
	 */
	public double getRadius()	{ return this.radius; }
	
	/**
	 * @param radius radius to set (in pixel)
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
}
