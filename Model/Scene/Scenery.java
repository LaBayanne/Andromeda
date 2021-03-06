package Model.Scene;

import java.util.ArrayList;

import Model.Menus.Menu;

/**
 * This interface represents a scene in game. Scenery because Scene is already taken by javafx.
 */
public interface Scenery {
        public boolean tick(double delta);
        public void mouseClicked(int button, double x,double y, ArrayList<String> buttonOptions);
        public void moveWheel(int dy);
        public void inputMouseLeft(double x, double y);
        public void releasedMouseLeft(double x, double y, ArrayList<String> buttonOptions);
        public ArrayList<Menu> getMenus();
}

