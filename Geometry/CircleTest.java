package Geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CircleTest {

	@Test
	void testCollision() {
		Circle c = new Circle(10, 10, 10);
		
		assert(c.collision(new Point(10, 10)));
		assert(c.collision(new Point(10, 20)));
		
		assert(!c.collision(new Point(10, 21)));
		assert(!c.collision(new Point(21, 10)));
	}

}
