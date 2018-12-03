package src_basic.Model.Scene;

public interface Scenery {
        public boolean tick();
        public void mouseClicked(int button, double x,double y);
        public void moveWheel(int dy);
}

