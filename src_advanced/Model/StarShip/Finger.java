package src_advanced.Model.StarShip;

import src_advanced.Geometry.Point;

public class Finger extends StarShip {
	
	final static private double SPEED;
	final static private int DAMAGE;
	final static private int COST;
	
	static {
		SPEED = 0.05;
		DAMAGE = 2;
		COST = 1;
	}
	
	public Finger(Point position, Point destination, double angle, int owner) {
		super(position, destination, SPEED, DAMAGE, COST, angle, owner, 20, 20);
	}
	
	public Finger(StarShip starship) {
		super(starship);
		
	}
	
	public Finger(int owner) {
		super(SPEED, DAMAGE, COST, owner);
	}

}
