package Model.Planet;

import Geometry.Point;
import Model.StarShip.Finger;

/**
 * Specific type of planet in the game.
 * Planet of type file are the basic planets in the game.
 */
public class File extends Planet{
	
	final static private double PRODUCTION_SPEED;
	static {
		PRODUCTION_SPEED = 0.002;
	}
	
	/**
	 * Constructor of the file.
	 * @param origin	File's origin.
	 * @param radius	Radius of the file's hitbox.
	 * @param owner		File's owner id.
	 */
	public File(Point origin, double radius, int owner) {
		super(origin, radius, PRODUCTION_SPEED, owner, new Finger(owner));
	}

}
