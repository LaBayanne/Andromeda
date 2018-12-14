package src_advanced.Geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RectangleTest {

	@Test
	void testCollision() {
		Rectangle r = new Rectangle(10, 10, 10, 10);
		
		assert(r.collision(new Point(10, 10)));
		assert(r.collision(new Point(20, 20)));
		
		assert(!r.collision(new Point(0, 10)));
		assert(!r.collision(new Point(11, 21)));
	}

}
