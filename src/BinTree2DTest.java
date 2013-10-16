import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import realtimeweb.earthquakeservice.domain.Coordinate;
import realtimeweb.earthquakeservice.domain.Earthquake;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 12, 2013
 */
public class BinTree2DTest extends junit.framework.TestCase {
    private BinTree2D<Point, String> binTree;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    public void setUp() {
	this.binTree = new BinTree2D<Point, String>(0.0, 100.0, 0.0, 100.0);

	// set up stream
	System.setOut(new PrintStream(outContent));
    }

    /**
     * Please refer to the images at the 2 following links while trying to
     * understand the bin tree insert method as it is very confusing: Figure 1 @:
     * Figure 2 @:
     */
    public void test_insert() {
	assertEquals("E",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	this.binTree.insert(new Point(10.0, 45.0), "A");
	assertEquals("A 10.0 45.0",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	this.binTree.insert(new Point(30.0, 70.0), "B");
	assertEquals("I\nI\nA 10.0 45.0\nB 30.0 70.0\nE", this.binTree
		.preorderTraversal(this.binTree.getRootNode()).trim());

	this.binTree.insert(new Point(52.0, 65.0), "C");
	assertEquals("I\nI\nA 10.0 45.0\nB 30.0 70.0\nC 52.0 65.0",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	// now we are going to add last element D that will dramatically change
	// rootNodes right subtree since it's (x, y) position is very close to
	// element C's (x, y) position
	this.binTree.insert(new Point(55.0, 90.0), "D");
	assertEquals(
		"I\nI\nA 10.0 45.0\nB 30.0 70.0\nI\nE\nI\nI\nC 52.0 65.0\nD 55.0 90.0\nE",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());
    }

    public void test_remove() {
	assertFalse(this.binTree.remove(new Point(10.0, 45.0), "A"));

	this.binTree.insert(new Point(10.0, 45.0), "A");
	this.binTree.insert(new Point(30.0, 70.0), "B");
	this.binTree.insert(new Point(52.0, 65.0), "C");
	this.binTree.insert(new Point(55.0, 90.0), "D");

	assertEquals(
		"I\nI\nA 10.0 45.0\nB 30.0 70.0\nI\nE\nI\nI\nC 52.0 65.0\nD 55.0 90.0\nE",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());
	assertTrue(this.binTree.remove(new Point(10.0, 45.0), "A"));
	assertEquals("I\nB 30.0 70.0\nI\nE\nI"
		+ "\nI\nC 52.0 65.0\nD 55.0 90.0\nE", this.binTree
		.preorderTraversal(this.binTree.getRootNode()).trim());

	assertTrue(this.binTree.remove(new Point(30.0, 70.0), "B"));
	assertEquals("I\nE\nI\nE\nI\nI\nC 52.0 65.0\nD 55.0 90.0\nE",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	assertTrue(this.binTree.remove(new Point(52.0, 65.0), "C"));
	assertEquals("D 55.0 90.0",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	assertTrue(this.binTree.remove(new Point(55.0, 90.0), "D"));
	assertEquals("E",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());
    }

    /**
     * First 4 letters are correctly inserted into the correct positions within
     * the bin tree.
     */
    public void test_find() {
	this.binTree.insert(new Point(10.0, 45.0), "A");
	this.binTree.insert(new Point(30.0, 70.0), "B");
	this.binTree.insert(new Point(52.0, 65.0), "C");
	this.binTree.insert(new Point(55.0, 90.0), "D");

	assertEquals("A", this.binTree.find(new Point(10.0, 45.0), "A"));
	assertEquals("B", this.binTree.find(new Point(30.0, 70.0), "B"));

	// if element is incorrect but key is correct
	assertNull(this.binTree.find(new Point(52.0, 65.0), "Z"));
	assertEquals("C", this.binTree.find(new Point(52.0, 65.0), "C"));

	// if key is incorrect but element is correct
	assertNull(this.binTree.find(new Point(55.0, 91.0), "D"));
	assertEquals("D", this.binTree.find(new Point(55.0, 90.0), "D"));
    }

    /**
     * The magnitude of the earthquake used for testing has purposely been
     * given a magnitude so large that is will affect all 3 different types of
     * nodes
     */
    public void test_regionSearch() {
	// earthquake1 is the very center of the world of the bintree
	// with a radius of magnitude^3 * 2 = 2^3 * 2 = 16
	Earthquake earthquake1 = new Earthquake(
		new Coordinate(50.0, 50.0, 1.0), 5.0, "San Fran", 1000,
		"www.walnutiq.com", 1, 1.0, 2.0, "red", "event", 1, "id", 3.0,
		4.0, 5.0);

	// First EQ coordinate test: (55, 80) Magnitude = 1
	// EQBox should be 53, 80
	this.binTree.regionSearch(earthquake1);
	assertEquals("Watcher search caused 1 bintree nodes to be visited.",
		this.binTree.regionSearch(earthquake1));

	this.binTree.insert(new Point(10.0, 45.0), "A");
	assertEquals("Watcher search caused 1 bintree nodes to be visited.",
		this.binTree.regionSearch(earthquake1));

	this.binTree.insert(new Point(30.0, 70.0), "B");
	assertEquals("Watcher search caused 5 bintree nodes to be visited.",
		this.binTree.regionSearch(earthquake1));

	this.binTree.insert(new Point(52.0, 65.0), "C");
	assertEquals("Watcher search caused 5 bintree nodes to be visited.",
		this.binTree.regionSearch(earthquake1));

	this.binTree.insert(new Point(55.0, 90.0), "D");
	assertEquals("Watcher search caused 11 bintree nodes to be visited.",
		this.binTree.regionSearch(earthquake1));
    }

    /**
     * Testing this method with sample input and output
     */
    public void test_regionSearchHelp() {

    }

    public void test_clear() {
	this.binTree.insert(new Point(10.0, 45.0), "A");
	this.binTree.insert(new Point(30.0, 70.0), "B");
	assertEquals(2, this.binTree.size());
	assertFalse(this.binTree.getRootNode() instanceof BinTreeEmptyNode<?>);

	this.binTree.clear();

	assertEquals(0, this.binTree.size());
	assertTrue(this.binTree.getRootNode() instanceof BinTreeEmptyNode<?>);
    }

    public void test_size() {
	// insert all 4 elements and check for correct bin tree size
	assertEquals(0, this.binTree.size());

	this.binTree.insert(new Point(10.0, 45.0), "A");
	assertEquals(1, this.binTree.size());

	this.binTree.insert(new Point(30.0, 70.0), "B");
	assertEquals(2, this.binTree.size());

	this.binTree.insert(new Point(52.0, 65.0), "C");
	assertEquals(3, this.binTree.size());

	this.binTree.insert(new Point(55.0, 90.0), "D");
	assertEquals(4, this.binTree.size());

	// remove all 4 elements and check for correct bin tree size
	assertTrue(this.binTree.remove(new Point(10.0, 45.0), "A"));
	assertEquals(3, this.binTree.size());

	assertTrue(this.binTree.remove(new Point(30.0, 70.0), "B"));
	assertEquals(2, this.binTree.size());

	assertTrue(this.binTree.remove(new Point(52.0, 65.0), "C"));
	assertEquals(1, this.binTree.size());

	assertTrue(this.binTree.remove(new Point(55.0, 90.0), "D"));
	assertEquals(0, this.binTree.size());
    }

    /**
     * Before asserting the preorder traversal printout is correct, the bin tree
     * is first correctly constructed WITHOUT using the bin tree's insert method
     * to prevent the possibility that an incorrectly implemented BinTree insert
     * method will create an incorrect preorder print out.
     */
    public void test_preorderTraversal() {
	assertEquals("E",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	BinTreeNode<String> rootNode = new BinTreeInternalNode<String>();
	this.binTree.setRootNode(rootNode);

	assertEquals("I\nE\nE",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	BinTreeInternalNode<String> internalRootNode = ((BinTreeInternalNode<String>) rootNode);

	// Set A & B correctly in left subtree of rootNode
	internalRootNode.setLeftChild(new BinTreeInternalNode<String>());

	BinTreeInternalNode<String> internalRootNodeLeftChild = (BinTreeInternalNode<String>) internalRootNode
		.getLeftChild();

	assertEquals("I\nI\nE\nE\nE",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	internalRootNodeLeftChild
		.setLeftChild(new BinTreeLeafNode<Point, String>(new Point(
			10.0, 45.0), "A"));
	internalRootNodeLeftChild
		.setRightChild(new BinTreeLeafNode<Point, String>(new Point(
			30.0, 70.0), "B"));

	assertEquals("I\nI\nA 10.0 45.0\nB 30.0 70.0\nE", this.binTree
		.preorderTraversal(this.binTree.getRootNode()).trim());

	// Set C & D correctly in right subtree of rootNode by first creating
	// the correct internal nodes to get to C & D
	internalRootNode.setRightChild(new BinTreeInternalNode<String>());

	BinTreeInternalNode<String> internalRootNodeRightChild = (BinTreeInternalNode<String>) internalRootNode
		.getRightChild();

	assertEquals("I\nI\nA 10.0 45.0\nB 30.0 70.0\nI\nE\nE", this.binTree
		.preorderTraversal(this.binTree.getRootNode()).trim());

	internalRootNodeRightChild
		.setRightChild(new BinTreeInternalNode<String>());

	assertEquals("I\nI\nA 10.0 45.0\nB 30.0 70.0\nI\nE\nI\nE\nE",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	BinTreeInternalNode<String> internalRootNodeRightRightChild = (BinTreeInternalNode<String>) internalRootNodeRightChild
		.getRightChild();

	internalRootNodeRightRightChild
		.setLeftChild(new BinTreeInternalNode<String>());

	assertEquals("I\nI\nA 10.0 45.0\nB 30.0 70.0\nI\nE\nI\nI\nE\nE\nE",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	BinTreeInternalNode<String> internalRootNodeRightRightLeftChild = (BinTreeInternalNode<String>) internalRootNodeRightRightChild
		.getLeftChild();

	internalRootNodeRightRightLeftChild
		.setLeftChild(new BinTreeLeafNode<Point, String>(new Point(
			52.0, 65.0), "C"));

	internalRootNodeRightRightLeftChild
		.setRightChild(new BinTreeLeafNode<Point, String>(new Point(
			55.0, 90.0), "D"));

	assertEquals(
		"I\nI\nA 10.0 45.0\nB 30.0 70.0\nI\nE\nI\nI\nC 52.0 65.0\nD 55.0 90.0\nE",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());
    }
}
