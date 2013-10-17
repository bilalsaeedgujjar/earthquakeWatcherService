/**
 * This class tests all logic within class BinTreeLeafNode.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 9, 2013
 */
public class BinTreeLeafNodeTest extends junit.framework.TestCase {
    private BinTreeLeafNode<Point, String> leafNode;

    public void setUp() {
	this.leafNode = new BinTreeLeafNode<Point, String>(new Point(1.0, 2.0),
		"A");
    }

    /**
     * Assert all getters, setters, and toString are working.
     */
    public void test_everything() {
	assertEquals(new Point(1.0, 2.0), this.leafNode.getKey());

	assertEquals("A", this.leafNode.getElement());

	this.leafNode.setElement("B");

	assertEquals("B", this.leafNode.toString());
    }
}
