package Model.Planet;

import Geometry.Point;
import Model.StarShip.MoveCursor;

/**
 * Represent a specific planet in the game
 * Application are special planet. Take more time to produce bigger units.
 */
public class Application extends Planet{
	
	final static private double PRODUCTION_SPEED;
	static {
		PRODUCTION_SPEED = 0.001;
	}
	
	/**
	 * Constructor of application planet
	 * @param origin	Origin of the planet
	 * @param radius	Radius of the application's hitbox
	 * @param owner		Application's owner
	 */
	public Application(Point origin, double radius, int owner) {
		super(origin, radius, PRODUCTION_SPEED, owner, new MoveCursor(owner));
	}
	
	@Override
	public double decreaseTimer() {
		if(this.timer - 0.3 > 0)
			this.timer -= 0.3;
		else
			this.timer = 0;
		return this.timer;
	}
	

}
