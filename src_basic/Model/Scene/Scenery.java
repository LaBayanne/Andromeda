package src_basic.Model.Scene;

import java.util.ArrayList;

public interface Scenery {
        public boolean tick(double delta);
        public void mouseClicked(int button, double x,double y, ArrayList<String> buttonOptions);
        public void moveWheel(int dy);
        public void inputMouseLeft(double x, double y);
        public void releasedMouseLeft(double x, double y, ArrayList<String> buttonOptions);
}

