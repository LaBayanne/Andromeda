package src_advanced.Model.Planet;

import src_advanced.Geometry.Point;
import src_advanced.Model.StarShip.MoveCursor;

public class Application extends Planet{
	
	final static private double PRODUCTION_SPEED;
	static {
		PRODUCTION_SPEED = 0.003;
	}
	
	public Application(Point origin, double radius, int owner) {
		super(origin, radius, PRODUCTION_SPEED, owner, new MoveCursor(owner));
	}

}