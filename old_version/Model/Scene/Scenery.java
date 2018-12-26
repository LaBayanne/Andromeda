package src_basic.Model.Scene;

import java.util.ArrayList;

/**
 * This interface represents a scene in game. Scenery because Scene is already taken by javafx.
 */
public interface Scenery {
        public boolean tick(double delta);
        public void mouseClicked(int button, double x,double y, ArrayList<String> buttonOptions);
        public void moveWheel(int dy);
}

