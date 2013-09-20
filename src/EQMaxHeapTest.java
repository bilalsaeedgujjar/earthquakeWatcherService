import realtimeweb.earthquakeservice.domain.Coordinate;
import realtimeweb.earthquakeservice.domain.Earthquake;

/**
 * Tests all logic within class SinglyLinkedList.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 18, 2013
 */
public class EQMaxHeapTest extends junit.framework.TestCase {
    private EQMaxHeap<EarthquakeNodeAwareOfHeapIndex> maxHeap;

    /**
     * Create five earthquake nodes and add them to the maxHeap to be tested.
     */
    public void setUp() {
	int numberOfNodes = 5;
	int capacity = 6;
	EarthquakeNodeAwareOfHeapIndex[] heap =
		new EarthquakeNodeAwareOfHeapIndex[capacity];

	Earthquake earthquakeWithMagnitude0 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 0.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);

	Earthquake earthquakeWithMagnitude10 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 10.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);

	Earthquake earthquakeWithMagnitude20 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 20.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);

	Earthquake earthquakeWithMagnitude30 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 30.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);

	Earthquake earthquakeWithMagnitude40 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 40.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);

	EarthquakeNodeAwareOfHeapIndex earthquakeNode0 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude0, 0);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode1 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude10, 1);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode2 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude20, 2);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode3 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude30, 3);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode4 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude40, 4);

	heap[0] = earthquakeNode0;
	heap[1] = earthquakeNode1;
	heap[2] = earthquakeNode2;
	heap[3] = earthquakeNode3;
	heap[4] = earthquakeNode4;
	this.maxHeap = new EQMaxHeap<EarthquakeNodeAwareOfHeapIndex>(heap,
		capacity, numberOfNodes);
	// this.maxHeap.insert(earthquakeNode0);
	// this.maxHeap.insert(earthquakeNode1);
	// this.maxHeap.insert(earthquakeNode2);
	// this.maxHeap.insert(earthquakeNode3);
	// this.maxHeap.insert(earthquakeNode4);
    }

    /**
     * Place a description of your method here.
     */
    public void test_insert() {
	assertEquals("0 1 2 3 4 ", this.maxHeap.printMaxHeapArrayIndexes());
	Earthquake earthquakeWithMagnitude50 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 50.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode5 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude50, 5);

	this.maxHeap.insert(earthquakeNode5);
	assertEquals("0 1 2 3 4 5 ", this.maxHeap.printMaxHeapArrayIndexes());

	Earthquake earthquakeWithMagnitude60 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 60.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode6 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude60, 6);

	try {
	    this.maxHeap.insert(earthquakeNode6);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method insert of class "
		    + "MaxHeap at heap index: 6"
		    + " could not be inserted because the max-heap is full",
		    expected.getMessage());
	}
    }

    /**
     * Place a description of your method here.
     */
    public void test_remove() {
	assertEquals("0 1 2 3 4 ", this.maxHeap.printMaxHeapArrayIndexes());
	assertEquals(4, this.maxHeap.remove(4).getIndexWithinHeapArray());
	assertEquals("0 1 2 3 ", this.maxHeap.printMaxHeapArrayIndexes());
	assertEquals(3, this.maxHeap.remove(3).getIndexWithinHeapArray());
	assertEquals("0 1 2 ", this.maxHeap.printMaxHeapArrayIndexes());
	assertEquals(2, this.maxHeap.remove(2).getIndexWithinHeapArray());
	assertEquals("0 1 ", this.maxHeap.printMaxHeapArrayIndexes());
	assertEquals(1, this.maxHeap.remove(1).getIndexWithinHeapArray());
	assertEquals("0 ", this.maxHeap.printMaxHeapArrayIndexes());
	assertEquals(0, this.maxHeap.remove(0).getIndexWithinHeapArray());
	assertEquals("", this.maxHeap.printMaxHeapArrayIndexes());
	assertEquals(0, this.maxHeap.getNumberOfNodes());

	try {
	    this.maxHeap.remove(-1);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method remove of class "
			    + "MaxHeap the input node postion to be " +
			    "removed is invalid",
		    expected.getMessage());
	}
    }

    /**
     * Place a description of your method here.
     */
    public void test_removeMaximumValue() {
	assertEquals("0 1 2 3 4 ", this.maxHeap.printMaxHeapArrayIndexes());
	assertEquals(4, this.maxHeap.removeMaximumValue()
		.getIndexWithinHeapArray());
	assertEquals("0 1 2 3 ", this.maxHeap.printMaxHeapArrayIndexes());

	assertEquals(3, this.maxHeap.removeMaximumValue()
		.getIndexWithinHeapArray());
	assertEquals("0 1 2 ", this.maxHeap.printMaxHeapArrayIndexes());

	assertEquals(2, this.maxHeap.removeMaximumValue()
		.getIndexWithinHeapArray());
	assertEquals("0 1 ", this.maxHeap.printMaxHeapArrayIndexes());

	assertEquals(1, this.maxHeap.removeMaximumValue()
		.getIndexWithinHeapArray());
	assertEquals("0 ", this.maxHeap.printMaxHeapArrayIndexes());

	assertEquals(0, this.maxHeap.removeMaximumValue()
		.getIndexWithinHeapArray());
	assertEquals("", this.maxHeap.printMaxHeapArrayIndexes());

	try {
	    this.maxHeap.removeMaximumValue();
	    fail("should've thrown an exception!");
	} catch (IllegalStateException expected) {
	    assertEquals("In method removeMaximumValue of class "
		    + "MaxHeap the value you cannot remove a value from an "
		    + "empty max-heap", expected.getMessage());
	}
    }

    /**
     * Place a description of your method here.
     */
    public void test_getMaximumValue() {
	assertEquals("0 1 2 3 4 ", this.maxHeap.printMaxHeapArrayIndexes());
	// earthquake at index 0 has the largest magnitude which is always
	// stored in the first index
	assertEquals(0, this.maxHeap.getMaximumValue()
		.getIndexWithinHeapArray());
    }

    /**
     * Place a description of your method here.
     */
    public void test_correctNodeIndexByShifting() {
	assertEquals("0 1 2 3 4 ", this.maxHeap.printMaxHeapArrayIndexes());
	this.maxHeap.swap(0, 2);
	assertEquals("0 1 2 3 4 ", this.maxHeap.printMaxHeapArrayIndexes());
	this.maxHeap.correctNodeIndexByShifting(0);
	assertEquals("0 1 2 3 4 ", this.maxHeap.printMaxHeapArrayIndexes());

	try {
	    this.maxHeap.correctNodeIndexByShifting(-1);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method shiftDown of class "
			    + "MaxHeap the value: -1"
			    + " represents a node that does not exist " +
			    "in the current heap",
		    expected.getMessage());
	}
    }

    /**
     * Place a description of your method here.
     */
    public void test_swap() {
	assertEquals("0 1 2 3 4 ", this.maxHeap.printMaxHeapArrayIndexes());

	EarthquakeNodeAwareOfHeapIndex[] heap = this.maxHeap.getHeap();
	assertEquals(30.0, heap[1].getEarthquake().getMagnitude());
	assertEquals(40.0, heap[0].getEarthquake().getMagnitude());

	this.maxHeap.swap(0, 1);

	// EarthquakeNodeAwareOfHeapIndex objects have been swapped but because
	// they are also updated in the swap method it cannot seen through
	// the print method
	assertEquals("0 1 2 3 4 ", this.maxHeap.printMaxHeapArrayIndexes());

	// instead you must verify the actual heap
	assertEquals(40.0, heap[1].getEarthquake().getMagnitude());
	assertEquals(30.0, heap[0].getEarthquake().getMagnitude());

	try {
	    this.maxHeap.swap(12, 1);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method swap of class "
			    + "MaxHeap the input arrayIndex1 is not a " +
			    "valid node position",
		    expected.getMessage());
	}
	try {
	    this.maxHeap.swap(1, 12);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method swap of class "
			    + "MaxHeap the input arrayIndex2 is not a " +
			    "valid node position",
		    expected.getMessage());
	}
    }

    /**
     * Place a description of your method here.
     */
    public void test_getParentIndex() {
	assertEquals(2, this.maxHeap.getParentIndex(5));
	try {
	    this.maxHeap.getParentIndex(-1);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method getParentPosition of class "
			    + "MaxHeap your input node position at -1 "
			    + "must be > 0", expected.getMessage());
	}
    }

    /**
     * Place a description of your method here.
     */
    public void test_getRightChildIndex() {
	assertEquals(4, this.maxHeap.getRightChildIndex(1));
	try {
	    this.maxHeap.getRightChildIndex(5);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method rightChild of class "
		    + "MaxHeap your input node position at 5 "
		    + "does not have a right child.", expected.getMessage());
	}
    }

    /**
     * Place a description of your method here.
     */
    public void test_getLeftChildIndex() {
	assertEquals(3, this.maxHeap.getLeftChildIndex(1));
	try {
	    this.maxHeap.getLeftChildIndex(4);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method leftChild of class "
		    + "MaxHeap your input node position at 4 "
		    + "does not have a left child.", expected.getMessage());
	}
    }

    /**
     * Place a description of your method here.
     */
    public void test_isLeafNode() {
	assertTrue(this.maxHeap.isLeafNode(4));
	assertFalse(this.maxHeap.isLeafNode(1));
	assertFalse(this.maxHeap.isLeafNode(6));
    }

    /**
     * Place a description of your method here.
     */
    public void test_getNumberOfNodes() {
	assertEquals(5, this.maxHeap.getNumberOfNodes());

	Earthquake earthquakeWithMagnitude0 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 0.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode0 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude0, 0);

	this.maxHeap.removeMaximumValue();
	assertEquals(4, this.maxHeap.getNumberOfNodes());
	this.maxHeap.insert(earthquakeNode0);
	assertEquals(5, this.maxHeap.getNumberOfNodes());

	for (int i = 0; i < 5; i++) {
	    this.maxHeap.removeMaximumValue();
	}
	assertEquals(0, this.maxHeap.getNumberOfNodes());
    }

    /**
     * Place a description of your method here.
     */
    public void test_getHeapHeight() {
	assertEquals(3, this.maxHeap.getHeapHeight());
    }

    /**
     * Place a description of your method here.
     */
    public void test_printMaxHeapArray() {
	assertEquals("0 1 2 3 4 ", this.maxHeap.printMaxHeapArrayIndexes());
    }
}
