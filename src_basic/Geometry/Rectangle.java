package src_basic.Geometry;

public class Rectangle extends Shape {
	private double width;
	private double height;
	
	public Rectangle(double x, double y, double width, double height) {
		super(x, y);
		
		this.width  = width;
		this.height = height;
	}
	
	public Rectangle(Rectangle r) {
		super(r.getOrigin());
		this.height = r.getHeight();
		this.width = r.getWidth();
	}
	
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
	
	public double getWidth()		{ return this.width;  }
	public double getHeight()	{ return this.height; }
	
	public void setWidth(double width) {
		this.width  = width;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
}
