package src_advanced.Model.StarShip;

import src_advanced.Geometry.Point;

public class MoveCursor extends StarShip{
	
	final static private double SPEED;
	final static private int DAMAGE;
	
	static {
		SPEED = 0.02;
		DAMAGE = 4;
	}
	
	public MoveCursor(Point position, Point destination, double angle, int owner) {
		super(position, destination, 0.02, 4, angle, owner);
	}
	
	public MoveCursor(StarShip starship) {
		super(starship);
		
	}
	
	public MoveCursor(int owner) {
		super(SPEED, DAMAGE, owner);
	}
}
