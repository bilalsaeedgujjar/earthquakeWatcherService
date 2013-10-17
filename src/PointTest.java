/**
 * Tests all logic within Point class.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 12, 2013
 */
public class PointTest extends junit.framework.TestCase {
    private Point point;

    public void setUp() {
	this.point = new Point(1.0, 2.0);
    }

    /**
     * Test getters and setters and the toString method.
     */
    public void test_everything() {
	assertEquals("(x, y) = (1.0, 2.0)", this.point.toString());
	assertEquals(1.0, this.point.getX());
	assertEquals(2.0, this.point.getY());

	this.point.setX(3.0);
	this.point.setY(4.0);
	assertEquals("(x, y) = (3.0, 4.0)", this.point.toString());
    }

    /**
     * Asserts if we can determine if 2 points equals each other.
     */
    public void test_equals() {
	Point point34 = new Point(3.0, 4.0);
	Point samePoint = new Point(3.0, 4.0);
	assertTrue(point34.equals(samePoint));

	Point differentPoint = new Point(3.0, 5.0);
	assertFalse(point34.equals(differentPoint));
    }
}
