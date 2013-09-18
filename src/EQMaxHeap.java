import java.lang.Comparable;

/**
 * A heap is defined by the following 2 properties: 1) It is a complete binary
 * tree. 2) The values stored in a heaps nodes are partially ordered.
 *
 * A max-heap has the property that every node stores a value that is greater
 * than or equal to the value of either of its children. This causes the root
 * node to store the maximum value in the complete binary tree.
 *
 * Because the following max-heap is implemented using an array it is important
 * to understand that the variable arrayIndex corresponds to a nodes position
 * within the complete binary tree.
 *
 * To develop a deep understanding of max and min heaps please visit:
 * http://algoviz.org/OpenDSA/Books/CS3114PM/html/Heaps.html
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sept 15, 2013
 * @param <E>
 *            Nodes within the max-heap.
 */
public class EQMaxHeap<E extends Comparable<E> & NodeAwareOfIndex> {
    private E[] heap;
    private int capacity; // maximum size of heap
    private int numberOfNodes; // number of nodes in current heap

    /**
     * Create a new EQMaxHeap object.
     *
     * @param heap2
     * @param capacity
     * @param numberOfNodes
     */
    public EQMaxHeap(E[] heap2, int capacity, int numberOfNodes) {
	this.heap = heap2;
	this.capacity = capacity;
	this.numberOfNodes = numberOfNodes;

	this.buildHeap();
    }

    /**
     * Place a description of your method here.
     */
    public void buildHeap() {
	for (int i = (this.numberOfNodes / 2 - 1); i >= 0; i--) {
	    this.correctNodeIndexByShifting(i);
	}
    }

    /**
     * Place a description of your method here.
     *
     * @param nodeValue
     */
    public void insert(E nodeValue) {
	if (this.capacity <= this.numberOfNodes) {
	    throw new IllegalArgumentException("In method insert of class "
		    + "MaxHeap at heap index: "
		    + nodeValue.getIndexWithinHeapArray()
		    + " could not be inserted because the max-heap is full");
	}

	int currentNodePosition = this.numberOfNodes++;

	if (nodeValue instanceof EarthquakeNodeAwareOfHeapIndex) {
	    nodeValue.setIndexWithinHeapArray(currentNodePosition);
	}

	this.heap[currentNodePosition] = nodeValue;

	// start at the end of most bottom right leaf node and shift up
	// until the nodeValue has a parent with a greater or equal value
	while ((currentNodePosition != 0)
		&& (this.heap[currentNodePosition].compareTo(this.heap[this
			.getParentIndex(currentNodePosition)]) > 0)) {
	    this.swap(currentNodePosition,
		    this.getParentIndex(currentNodePosition));
	    currentNodePosition = this.getParentIndex(currentNodePosition);
	}
    }

    /**
     * Remove the node at arrayIndex within the MaxHeap and return the node
     * value that the removed node is replaced with.
     *
     * @param arrayIndex
     * @return The node that was removed at arrayIndex.
     */
    public E remove(int arrayIndex) {
	int changingArrayIndex = arrayIndex;
	if ((changingArrayIndex < 0) || (changingArrayIndex >= this.numberOfNodes)) {
	    throw new IllegalArgumentException("In method remove of class "
		    + "MaxHeap the input node postion to be removed is invalid");
	}

	// if the most bottom right node is being removed there is no work to be
	// done
	if (changingArrayIndex == (this.numberOfNodes - 1)) {
	    this.numberOfNodes--;
	} else {
	    // swap node to be removed with most bottom right node
	    this.swap(changingArrayIndex, --this.numberOfNodes);

	    // if swapped node is large, shift it up the tree
	    while ((changingArrayIndex > 0)
		    && (this.heap[changingArrayIndex].compareTo(this.heap[this
			    .getParentIndex(changingArrayIndex)]) > 0)) {
		this.swap(changingArrayIndex, this.getParentIndex(changingArrayIndex));
		changingArrayIndex = this.getParentIndex(changingArrayIndex);
	    }
	    if (this.numberOfNodes != 0) {
		// if swapped node is small, shift it down the tree
		this.correctNodeIndexByShifting(changingArrayIndex);
	    }
	}
	return this.heap[changingArrayIndex];
    }

    /**
     * @return maximum node value in max-heap.
     */
    public E removeMaximumValue() {
	if (this.numberOfNodes == 0) {
	    throw new IllegalStateException(
		    "In method removeMaximumValue of class "
			    + "MaxHeap the value you cannot remove a value from an "
			    + "empty max-heap");
	}
	// swap maximum with last value
	this.swap(0, --this.numberOfNodes);

	if (this.numberOfNodes != 0) {
	    // if not the last element
	    this.correctNodeIndexByShifting(0);
	}
	return this.heap[this.numberOfNodes];
    }

    /**
     * Place a description of your method here.
     *
     * @return The node with the maximxum magnitude.
     */
    public E getMaximumValue() {
	if (this.numberOfNodes == 0) {
	    throw new IllegalStateException(
		    "In method getMaximumValue of class "
			    + "MaxHeap the max-heap is currently empty");
	}

	return this.heap[0];
    }

    /**
     * Place given node position in the correct position within the complete
     * binary tree.
     *
     * @param arrayIndex
     *            The index with a node to be shifted into the correct position.
     */
    void correctNodeIndexByShifting(int arrayIndex) {
	int changingArrayIndex = arrayIndex;
	if ((changingArrayIndex < 0) || (changingArrayIndex >= this.numberOfNodes)) {
	    throw new IllegalArgumentException(
		    "In method shiftDown of class "
			    + "MaxHeap the value: "
			    + changingArrayIndex
			    + " represents a node that does not exist in the current heap");
	}
	while (!this.isLeafNode(changingArrayIndex)) {
	    int childIndex = this.getLeftChildIndex(changingArrayIndex);
	    if ((childIndex < (this.numberOfNodes - 1))
		    && (this.heap[childIndex]
			    .compareTo(this.heap[childIndex + 1]) < 0)) {
		childIndex++; // childIndex is not at index of child with
			      // greater node value
	    }
	    if (this.heap[changingArrayIndex].compareTo(this.heap[childIndex]) >= 0) {
		return;
	    }
	    this.swap(changingArrayIndex, childIndex);
	    changingArrayIndex = childIndex; // node shifted down
	}
    }

    /**
     * Place a description of your method here.
     *
     * @param arrayIndex1
     * @param arrayIndex2
     */
    void swap(int arrayIndex1, int arrayIndex2) {
	if (arrayIndex1 < 0 || arrayIndex1 > this.numberOfNodes) {
	    throw new IllegalArgumentException(
		    "In method swap of class "
			    + "MaxHeap the input arrayIndex1 is not a valid node position");
	} else if (arrayIndex2 < 0 || arrayIndex2 > this.numberOfNodes) {
	    throw new IllegalArgumentException(
		    "In method swap of class "
			    + "MaxHeap the input arrayIndex2 is not a valid node position");
	}
	E tempNodeValue = this.heap[arrayIndex1];

	// before earthquakeNodes are actually moved, their new indexes
	// are set
	if (tempNodeValue instanceof EarthquakeNodeAwareOfHeapIndex) {
	    this.heap[arrayIndex1].setIndexWithinHeapArray(arrayIndex2);
	    this.heap[arrayIndex2].setIndexWithinHeapArray(arrayIndex1);
	}

	this.heap[arrayIndex1] = this.heap[arrayIndex2];
	this.heap[arrayIndex2] = tempNodeValue;
    }

    /**
     * Place a description of your method here.
     *
     * @param arrayIndex
     * @return The index of the parent node.
     */
    public int getParentIndex(int arrayIndex) {
	if (arrayIndex <= 0) {
	    throw new IllegalArgumentException(
		    "In method getParentPosition of class "
			    + "MaxHeap your input node position at "
			    + arrayIndex + " must be > 0");
	} else {
	    return (arrayIndex - 1) / 2;
	}
    }

    /**
     * Place a description of your method here.
     *
     * @param arrayIndex
     * @return the index of the right child.
     */
    public int getRightChildIndex(int arrayIndex) {
	if (arrayIndex >= (this.numberOfNodes / 2)) {
	    throw new IllegalArgumentException("In method rightChild of class "
		    + "MaxHeap your input node position at " + arrayIndex
		    + " does not have a right child.");
	} else {
	    return 2 * arrayIndex + 2;
	}
    }

    /**
     * Place a description of your method here.
     *
     * @param arrayIndex
     * @return The index of the left child.
     */
    public int getLeftChildIndex(int arrayIndex) {
	if (arrayIndex >= (this.numberOfNodes / 2)) {
	    throw new IllegalArgumentException("In method leftChild of class "
		    + "MaxHeap your input node position at " + arrayIndex
		    + " does not have a left child.");
	} else {
	    return 2 * arrayIndex + 1;
	}
    }

    /**
     * Place a description of your method here.
     *
     * @param arrayIndex
     * @return if the node at the given index is a leaf node.
     */
    public boolean isLeafNode(int arrayIndex) {
	if ((arrayIndex >= (this.numberOfNodes / 2))
		&& (arrayIndex < this.numberOfNodes)) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Place a description of your method here.
     *
     * @return The number of nodes in this max-heap.
     */
    public int getNumberOfNodes() {
	return this.numberOfNodes;
    }

    /**
     * Place a description of your method here.
     *
     * @return The height of the heap.
     */
    public int getHeapHeight() {
	double approximateHeight = Math.log(this.numberOfNodes) / Math.log(2);
	int actualHeight = (int) (Math.floor(approximateHeight) + 1);
	return actualHeight;
    }

    /**
     * Place a description of your method here.
     *
     * @return The indices of elements in the max-heap.
     */
    public String printMaxHeapArrayIndexes() {
	StringBuilder maxHeapArray = new StringBuilder();
	for (int i = 0; i < this.numberOfNodes; i++) {
	    maxHeapArray.append(this.heap[i].getIndexWithinHeapArray() + " ");
	}
	return maxHeapArray.toString();
    }

    /**
     * Place a description of your method here.
     *
     * @return The current max-heap.
     */
    public E[] getHeap() {
	return this.heap;
    }
}
