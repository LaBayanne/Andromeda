package Model.StarShip;

import Geometry.Point;

public class Finger extends StarShip {
	
	final static private double SPEED;
	final static private int DAMAGE;
	final static private int COST;
	
	static {
		SPEED = 0.05;
		DAMAGE = 2;
		COST = 1;
	}
	
	/**
	 * Constructor of the finger.
	 * @param position		The position of the arrow
	 * @param destination	Its destination
	 * @param angle			Its angle
	 * @param owner			Its owner
	 */
	public Finger(Point position, Point destination, double angle, int owner) {
		super(position, destination, SPEED, DAMAGE, COST, angle, owner, 20, 20);
	}
	
	/**
	 * Constructor of finger.
	 * @param starship The starship to copy
	 */
	public Finger(StarShip starship) {
		super(starship);
		
	}
	
	/**
	 * Constructor of finger.
	 * @param owner Its owner
	 */
	public Finger(int owner) {
		super(SPEED, DAMAGE, COST, owner);
	}

}
