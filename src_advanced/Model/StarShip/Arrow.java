package src_advanced.Model.StarShip;

import src_advanced.Geometry.Point;

public class Arrow extends StarShip {
	
	final static private double SPEED;
	final static private int DAMAGE;
	
	static {
		SPEED = 0.1;
		DAMAGE = 1;
	}
	
	public Arrow(Point position, Point destination, double angle, int owner) {
		super(position, destination, SPEED, DAMAGE, angle, owner);
	}
	
	public Arrow(int owner) {
		super(SPEED, DAMAGE, owner);
	}

}
