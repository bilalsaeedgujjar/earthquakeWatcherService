package dataStructures;
import customDataStructures.Point;

import customDataStructures.BoundingBox;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

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
     * Asserts inserts of nodes create the correct bin tree structure.
     */
    public void test_insert() {
	assertEquals("E",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	this.binTree.insert(new Point(10.0, 45.0), "A");
	assertEquals("A",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	this.binTree.insert(new Point(30.0, 70.0), "B");
	assertEquals("I\nI\nA\nB\nE",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	this.binTree.insert(new Point(52.0, 65.0), "C");
	assertEquals("I\nI\nA\nB\nC",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	// now we are going to add last element D that will dramatically change
	// rootNodes right subtree since it's (x, y) position is very close to
	// element C's (x, y) position
	this.binTree.insert(new Point(55.0, 90.0), "D");
	assertEquals("I\nI\nA\nB\nI\nE\nI\nI\nC\nD\nE", this.binTree
		.preorderTraversal(this.binTree.getRootNode()).trim());
    }

    /**
     * Asserts inserts of nodes create the correct bin tree structure.
     */
    public void test_insertHelp() {
	BinTreeNode<String> leafNode = new BinTreeLeafNode<Point, String>(new Point(10.0, 45.0), "A");

	BoundingBox currentWorld = new BoundingBox(new Point(0.0, 0.0), 100.0,
		100.0);

	this.binTree.insertHelp(leafNode, currentWorld, new Point(10.0, 55.0), "B", false);
    }

    /**
     * Assert nodes are removed correctly and the bin tree structure is
     * preserved.
     */
    public void test_remove() {
	assertFalse(this.binTree.remove(new Point(10.0, 45.0), "A"));

	this.binTree.insert(new Point(10.0, 45.0), "A");
	this.binTree.insert(new Point(30.0, 70.0), "B");
	this.binTree.insert(new Point(52.0, 65.0), "C");
	this.binTree.insert(new Point(55.0, 90.0), "D");

	assertEquals("I\nI\nA\nB\nI\nE\nI\nI\nC\nD\nE", this.binTree
		.preorderTraversal(this.binTree.getRootNode()).trim());
	assertTrue(this.binTree.remove(new Point(10.0, 45.0), "A"));
	assertEquals("I\nB\nI\nE\nI" + "\nI\nC\nD\nE", this.binTree
		.preorderTraversal(this.binTree.getRootNode()).trim());

	assertTrue(this.binTree.remove(new Point(30.0, 70.0), "B"));
	assertEquals("I\nE\nI\nE\nI\nI\nC\nD\nE", this.binTree
		.preorderTraversal(this.binTree.getRootNode()).trim());

	assertTrue(this.binTree.remove(new Point(52.0, 65.0), "C"));
	assertEquals("D",
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
     * The magnitude of the earthquake used for testing has purposely been given
     * a magnitude so large that is will affect all 3 different types of nodes
     */
    public void test_regionSearch() {
	// earthquake1 is the very center of the world of the bintree
	// with a radius of magnitude^3 * 2 = 2^3 * 2 = 16

	// earthquake fields
	// longitude = 50.0
	// latitude = 50.0
	// magnitude = 5.0

	this.binTree.regionSearch(50.0, 50.0, 5.0);
	assertEquals("Watcher search caused 1 bintree nodes to be visited.",
		this.binTree.regionSearch(50.0, 50.0, 5.0));

	this.binTree.insert(new Point(10.0, 45.0), "A");
	assertEquals("Watcher search caused 1 bintree nodes to be visited.",
		this.binTree.regionSearch(50.0, 50.0, 5.0));

	this.binTree.insert(new Point(30.0, 70.0), "B");
	assertEquals("Watcher search caused 5 bintree nodes to be visited.",
		this.binTree.regionSearch(50.0, 50.0, 5.0));

	this.binTree.insert(new Point(52.0, 65.0), "C");
	assertEquals("Watcher search caused 5 bintree nodes to be visited.",
		this.binTree.regionSearch(50.0, 50.0, 5.0));

	this.binTree.insert(new Point(55.0, 90.0), "D");
	assertEquals("Watcher search caused 11 bintree nodes to be visited.",
		this.binTree.regionSearch(50.0, 50.0, 5.0));
    }

    /**
     * Assert a bin tree can be correclty cleared.
     */
    public void test_clear() {
	this.binTree.insert(new Point(10.0, 45.0), "A");
	this.binTree.insert(new Point(30.0, 70.0), "B");
	assertEquals(2, this.binTree.size());
	assertFalse(this.binTree.getRootNode() instanceof BinTreeEmptyNode<?>);

	this.binTree.clear();

	assertEquals(0, this.binTree.size());
	assertTrue(this.binTree.getRootNode() instanceof BinTreeEmptyNode<?>);
    }

    /**
     * Assert size if the bin tree is correctly changed through inserts and
     * removes of nodes.
     */
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

	assertEquals("I\nI\nA\nB\nE",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	// Set C & D correctly in right subtree of rootNode by first creating
	// the correct internal nodes to get to C & D
	internalRootNode.setRightChild(new BinTreeInternalNode<String>());

	BinTreeInternalNode<String> internalRootNodeRightChild = (BinTreeInternalNode<String>) internalRootNode
		.getRightChild();

	assertEquals("I\nI\nA\nB\nI\nE\nE",
		this.binTree.preorderTraversal(this.binTree.getRootNode())
			.trim());

	internalRootNodeRightChild
		.setRightChild(new BinTreeInternalNode<String>());

	assertEquals("I\nI\nA\nB\nI\nE\nI\nE\nE", this.binTree
		.preorderTraversal(this.binTree.getRootNode()).trim());

	BinTreeInternalNode<String> internalRootNodeRightRightChild = (BinTreeInternalNode<String>) internalRootNodeRightChild
		.getRightChild();

	internalRootNodeRightRightChild
		.setLeftChild(new BinTreeInternalNode<String>());

	assertEquals("I\nI\nA\nB\nI\nE\nI\nI\nE\nE\nE", this.binTree
		.preorderTraversal(this.binTree.getRootNode()).trim());

	BinTreeInternalNode<String> internalRootNodeRightRightLeftChild = (BinTreeInternalNode<String>) internalRootNodeRightRightChild
		.getLeftChild();

	internalRootNodeRightRightLeftChild
		.setLeftChild(new BinTreeLeafNode<Point, String>(new Point(
			52.0, 65.0), "C"));

	internalRootNodeRightRightLeftChild
		.setRightChild(new BinTreeLeafNode<Point, String>(new Point(
			55.0, 90.0), "D"));

	assertEquals("I\nI\nA\nB\nI\nE\nI\nI\nC\nD\nE", this.binTree
		.preorderTraversal(this.binTree.getRootNode()).trim());
    }
}
