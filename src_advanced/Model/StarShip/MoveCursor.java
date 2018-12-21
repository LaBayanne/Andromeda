package src_advanced.Model.StarShip;

import src_advanced.Geometry.Point;

public class MoveCursor extends StarShip{
	
	final static private double SPEED;
	final static private int DAMAGE;
	final static private int COST;
	
	static {
		SPEED = 0.02;
		DAMAGE = 4;
		COST = 3;
	}
	
	public MoveCursor(Point position, Point destination, double angle, int owner) {
		super(position, destination, 0.02, 4, COST, angle, owner, 30, 30);
	}
	
	public MoveCursor(StarShip starship) {
		super(starship);
		
	}
	
	public MoveCursor(int owner) {
		super(SPEED, DAMAGE, COST, owner);
	}
}
