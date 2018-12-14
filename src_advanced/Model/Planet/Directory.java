package src_advanced.Model.Planet;

import src_advanced.Geometry.Point;
import src_advanced.Model.StarShip.Arrow;
import src_advanced.Model.StarShip.StarShip;

public class Directory extends Planet{
	
	final static private double PRODUCTION_SPEED;
	static {
		PRODUCTION_SPEED = 0.008;
	}
	
	public Directory(Point origin, double radius, int owner) {
		super(origin, radius, PRODUCTION_SPEED, owner, new Arrow(owner));
	}

}
