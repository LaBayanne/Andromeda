package src_advanced.Model.StarShip;

import src_advanced.Geometry.Point;

public class Finger extends StarShip {
	
	final static private double SPEED;
	final static private int DAMAGE;
	
	static {
		SPEED = 0.05;
		DAMAGE = 2;
	}
	
	public Finger(Point position, Point destination, double angle, int owner) {
		super(position, destination, SPEED, DAMAGE, angle, owner);
	}
	
	public Finger(StarShip starship) {
		super(starship);
		
	}
	
	public Finger(int owner) {
		super(SPEED, DAMAGE, owner);
	}

}
