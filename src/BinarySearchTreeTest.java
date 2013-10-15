

/**
 * This class tests all logic within class BinarySearchTree.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 9, 2013
 */
public class BinarySearchTreeTest extends junit.framework.TestCase {
    private BinarySearchTree<Integer, String> BST;

    public void setUp() {
	this.BST = new BinarySearchTree<Integer, String>();
	// insert the nodes in this order to create a well balanced tree
	this.BST.insert(new Integer(4), "four");
	this.BST.insert(new Integer(3), "three");
	this.BST.insert(new Integer(6), "six");
	this.BST.insert(new Integer(1), "one");
	this.BST.insert(new Integer(2), "two");
	this.BST.insert(new Integer(5), "five");
	this.BST.insert(new Integer(7), "seven");
	this.BST.insert(new Integer(0), "zero");
    }

    public void test_clear() {
	this.BST.clear();
	assertEquals(0, this.BST.size());
    }

    public void test_insert() {
	assertEquals(8, this.BST.size());
    }

    public void test_remove() {
	assertEquals("zero", this.BST.remove(new Integer(0)));
	assertEquals("one", this.BST.remove(new Integer(1)));
	assertEquals("two", this.BST.remove(new Integer(2)));
	assertEquals("three", this.BST.remove(new Integer(3)));
	assertEquals("four", this.BST.remove(new Integer(4)));
	assertEquals("five", this.BST.remove(new Integer(5)));
	assertEquals("six", this.BST.remove(new Integer(6)));
	assertEquals("seven", this.BST.remove(new Integer(7)));
    }

    public void test_removeRandomElement() {
	assertEquals("four", this.BST.removeRandomElement());
	assertEquals("three", this.BST.removeRandomElement());
	assertEquals("two", this.BST.removeRandomElement());
	assertEquals("one", this.BST.removeRandomElement());
	assertEquals("zero", this.BST.removeRandomElement());
	assertEquals("six", this.BST.removeRandomElement());
	assertEquals("five", this.BST.removeRandomElement());
	assertEquals("seven", this.BST.removeRandomElement());
    }

    public void test_find() {
	assertEquals("zero", this.BST.find(new Integer(0)));
	assertEquals("one", this.BST.find(new Integer(1)));
	assertEquals("two", this.BST.find(new Integer(2)));
	assertEquals("three", this.BST.find(new Integer(3)));
	assertEquals("four", this.BST.find(new Integer(4)));
	assertEquals("five", this.BST.find(new Integer(5)));
	assertEquals("six", this.BST.find(new Integer(6)));
	assertEquals("seven", this.BST.find(new Integer(7)));
    }

    public void test_findHelp() {
	assertNull(this.BST.findHelp(null, null));
    }

    public void test_insertHelp() {
	BinarySearchTreeNode<Integer, String> bst = new BinarySearchTreeNode<Integer, String>(
		new Integer(0), "zero", null, null);
	assertEquals(10, this.BST.insertHelp(null, new Integer(10), "ten")
		.getKey().intValue());
    }

    public void test_removeHelp() {
	assertNull(this.BST.removeHelp(null, new Integer(0)));
    }

    public void test_getNodeWithMaximumValue() {
	assertEquals("seven",
		this.BST.getNodeWithMaximumValue(this.BST.getRootNode())
			.getValue().toString());
    }

    public void test_deleteNodeWithMaximumValue() {
	assertEquals("seven", this.BST.getRootNode().getRightChild()
		.getRightChild().getValue().toString());
	this.BST.deleteNodeWithMaximumValue(this.BST.getRootNode());
	assertNull(this.BST.getRootNode().getRightChild()
		.getRightChild());
    }

    public void test_inorderTraversal() {
	assertEquals("zero one two three four five six seven ", this.BST
		.inorderTraversal(this.BST.getRootNode()).toString());
    }
}
