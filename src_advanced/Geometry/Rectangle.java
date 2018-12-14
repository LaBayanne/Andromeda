package src_advanced.Geometry;

/**
 * Represent a basic rectangle in the game.
 *
 */
public class Rectangle extends Shape {
	private double width;
	private double height;
	
	/**
	 * Constructor for rectangle
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Rectangle(double x, double y, double width, double height) {
		super(x, y);
		
		this.width  = width;
		this.height = height;
	}
	
	/**
	 * Constructor by copy
	 * @param r the rectangle to copy
	 */
	public Rectangle(Rectangle r) {
		super(r.getOrigin());
		this.height = r.getHeight();
		this.width = r.getWidth();
	}
	
	/**
	 * Constructor with a top left position
	 * @param topLeft	The top left position
	 * @param width		Width of the rectangle
	 * @param height	Height of the rectangle
	 */
	public Rectangle(Point topLeft, double width, double height) {
		this(topLeft.getX(), topLeft.getY(), width, height);
	}
	
	@Override
	public boolean collision(Point p) {
		double x = p.getX();
		double y = p.getY();
		
		return (
				!(x < this.origin.getX() || x > this.origin.getX() + this.width || y < this.origin.getY() || y > this.origin.getY() + this.height)
		);
	}
	
	/**
	 * Basic function, will be modified later
	 * @param c		the cirlce to check
	 * @return		if there is a collision
	 */
	public boolean collision(Circle c) {
		return collision(c.getOrigin());
	}
	
	/**
	 * @return width of rectangle
	 */
	public double getWidth() {
		return this.width;  
	}
	
	/**
	 * @return height of rectangle
	 */
	public double getHeight() { 
		return this.height; 
	}
	
	/**
	 * @param width new width of the rectangle
	 */
	public void setWidth(double width) {
		this.width  = width;
	}
	
	/**
	 * @param height new height of the rectangle
	 */
	public void setHeight(double height) {
		this.height = height;
	}
}
