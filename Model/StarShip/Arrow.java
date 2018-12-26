package Model.StarShip;

import Geometry.Point;

/**
 * Represent a specific Starship.
 * Fast but few powerful
 *
 */
public class Arrow extends StarShip {
	
	final static private double SPEED;
	final static private int DAMAGE;
	final static private int COST;
	
	static {
		SPEED = 0.1;
		DAMAGE = 1;
		COST = 1;
	}
	
	
	/**
	 * Constructor of the arrow.
	 * @param position		The position of the arrow
	 * @param destination	Its destination
	 * @param angle			Its angle
	 * @param owner			Its owner
	 */
	public Arrow(Point position, Point destination, double angle, int owner) {
		super(position, destination, SPEED, DAMAGE, COST, angle, owner, 20, 20);
	}
	
	/**
	 * Constructor of arrow.
	 * @param starship The starship to copy
	 */
	public Arrow(StarShip starship) {
		super(starship);
		
	}
	
	/**
	 * Constructor of arrow.
	 * @param owner Its owner
	 */
	public Arrow(int owner) {
		super(SPEED, DAMAGE, COST, owner);
	}

}
