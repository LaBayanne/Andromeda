package src_advanced.Model.Planet;

import src_advanced.Geometry.Point;
import src_advanced.Model.StarShip.Finger;

public class File extends Planet{
	
	final static private double PRODUCTION_SPEED;
	static {
		PRODUCTION_SPEED = 0.008;
	}
	
	public File(Point origin, double radius, int owner) {
		super(origin, radius, PRODUCTION_SPEED, owner, new Finger(owner));
	}

}
