package dataStructures;
/**
 * This class tests all logic within class BinTreeInternalNode.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 9, 2013
 */
public class BinTreeInternalNodeTest extends junit.framework.TestCase {
    private BinTreeInternalNode<String> internalNode;

    public void setUp() {
	this.internalNode = new BinTreeInternalNode<String>();
    }

    /**
     * Assert bin tree empty node toString prints to the console in the correct
     * format.
     */
    public void test_toString() {
	assertEquals("\n==Bin Tree Internal Node==" + "\n leftChild: \n"
		+ "\n===Bin Tree Empty Node===" + "\nthis node is a flyweight"
		+ "\n=========================\n" + "\nrightChild:\n"
		+ "\n===Bin Tree Empty Node===" + "\nthis node is a flyweight"
		+ "\n========================="
		+ "\n==========================", this.internalNode.toString());
    }
}