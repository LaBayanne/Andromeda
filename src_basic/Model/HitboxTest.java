package src_basic.Model;

import org.junit.jupiter.api.Test;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

class HitboxTest {

	@Test
	void testCollisionHitbox() {
		Rectangle r1 = new Rectangle(0, 0, 30, 30);
		Rectangle r2 = new Rectangle(10, 10, 20, 20);
		
		Hitbox h1 = new Hitbox(r1);
		Hitbox h2 = new Hitbox(r2);
		
		assert(h1.collision(h2));
	}

	@Test
	void testCollisionShape() {
		Hitbox h = new Hitbox(new Rectangle(100, 100, 10, 10));
		Line l = new Line(100, 50, 100, 150);
		
		assert(h.collision(l));
	}

	@Test
	void testCollisionPoint2D() {
		Hitbox h = new Hitbox(new Rectangle(100, 100, 10, 10));
		
		Point2D p1 = new Point2D(100, 100);
		Point2D p2 = new Point2D(109, 109);
		
		Point2D p3 = new Point2D(99, 99);
		
		assert(h.collision(p1) && h.collision(p2) && ! h.collision(p3));
	}

}
