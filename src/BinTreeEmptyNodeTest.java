/**
 * This class tests all logic within class BinTreeEmptyNode.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 9, 2013
 */
public class BinTreeEmptyNodeTest extends junit.framework.TestCase {
    public void test_toString() {
	BinTreeNode<String> binTreeEmptyNode = BinTreeEmptyNode.getInstance();
	assertEquals("\n===Bin Tree Empty Node==="
		+ "\nthis node is a flyweight" + "\n=========================",
		binTreeEmptyNode.toString());
    }
}
