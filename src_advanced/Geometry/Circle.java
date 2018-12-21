package src_advanced.Geometry;

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
	
	/**
	 * 
	 * Constructor by copy
	 * @param circle circle to copy.
	 */
	public Circle(Circle circle) {
		this(circle.getOrigin().getX(), circle.getOrigin().getY(), circle.getRadius());
	}
	
	
	@Override
	public boolean collision(Point p) {
		return this.origin.distance(p) <= this.radius;
	}
	
	/**
	 * Detect collision with another circle
	 * @param circle	the circle to check
	 * @return	true if there is collision, else false
	 */
	public boolean collision(Circle circle) {
		return this.origin.distance(circle.getOrigin()) < this.radius + circle.getRadius();
	}
	
	/**
	 * Detect a basic collision with rectangle.
	 * @param r	Rectangle
	 * @return	True if there is a collision, else false.
	 */
	public boolean collision(Rectangle r) {

		double distanceX = Math.abs(this.origin.getX()- (r.origin.getX() + r.getWidth() / 2));
		double distanceY = Math.abs(this.origin.getY() - (r.origin.getY() + r.getHeight() / 2));

	    if (distanceX > (r.getWidth() / 2 + this.radius)) { return false; }
	    if (distanceY > (r.getHeight() / 2 + this.radius)) { return false; }

	    if (distanceX <= (r.getWidth() / 2)) { return true; } 
	    if (distanceY <= (r.getHeight() / 2)) { return true; }

	    double cornerDistance_sq = (distanceX - r.getWidth() / 2) * (distanceX - r.getWidth() / 2) +
	                         (distanceY - r.getHeight() / 2) * (distanceY - r.getHeight() / 2);

	    return (cornerDistance_sq <= (this.radius * this.radius));
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
