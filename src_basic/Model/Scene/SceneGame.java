package src_basic.Model.Scene;

import src_basic.Model.*;
import java.util.ArrayList;

public class SceneGame implements Scene{
	ArrayList<Squad> squads;
	ArrayList<Planet> planets;
	ArrayList<Planet> selectedPlanets;
	
	@Override
	public boolean tick() {
		return false;
	}
}