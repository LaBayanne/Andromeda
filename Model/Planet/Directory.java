package Model.Planet;

import Geometry.Point;
import Model.StarShip.Arrow;

/**
 * Specific planet in the game.
 * Directory are special planets, produce a lot of little units.
 */
public class Directory extends Planet{
	
	final static private double PRODUCTION_SPEED;
	static {
		PRODUCTION_SPEED = 0.004;
	}
	
	/**
	 * Constructor of the directory planet
	 * @param origin	Origin of the directory.
	 * @param radius	Radius of the circular hitbox.
	 * @param owner		Directory's owner.
	 */
	public Directory(Point origin, double radius, int owner) {
		super(origin, radius, PRODUCTION_SPEED, owner, new Arrow(owner));
	}

}
