import realtimeweb.earthquakeservice.domain.Earthquake;

/**
 * The bin tree is a spatial data structure that can be used to unify search
 * across any arbitrary set of keys. Most commonly it is used to efficiently
 * search through on multi-dimensional coordinates such as 2D or 3D spaces. This
 * bin tree efficiently stores 2 dimensional keys.
 *
 * To learn more about the bin tree please visit:
 * http://algoviz.org/OpenDSA/Books/CS3114PM/html/Bintree.html
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 15, 2013
 * @param <K>
 *            a 2 dimensional point type in space such as a (x, y) coordinate
 *            that extends the Point class
 * @param <E>
 *            the item type to be stored in the leaf nodes of the 2D bin tree
 */
public class BinTree2D<K extends Point, E> {
    /**
     * Create a flyweight leaf node to represent a single empty leaf node since
     * on average, half of the leaf nodes in a BinTree are empty.
     */
    private BinTreeNode<E> emptyLeafNodeFlyweight;

    private BinTreeNode<E> rootNode;

    private double minimumXAxis;
    private double maximumXAxis;
    private double minimumYAxis;
    private double maximumYAxis;

    /**
     * Number of leaf nodes with data in this bin tree.
     */
    private int size;

    /**
     * Create a new BinTree object.
     *
     * @param minimumXAxis
     * @param maximumXAxis
     * @param minimumYAxis
     * @param maximumYAxis
     */
    @SuppressWarnings("unchecked")
    public BinTree2D(double minimumXAxis, double maximumXAxis,
	    double minimumYAxis, double maximumYAxis) {
	this.emptyLeafNodeFlyweight = BinTreeEmptyNode.getInstance();
	this.rootNode = this.emptyLeafNodeFlyweight;

	this.minimumXAxis = minimumXAxis;
	this.maximumXAxis = maximumXAxis;
	this.minimumYAxis = minimumYAxis;
	this.maximumYAxis = maximumYAxis;
	this.size = 0;
    }

    /**
     * Recursive insertion of a new element.
     *
     * @param key
     *            The key value be used to compare to discriminators when the
     *            bin tree.
     * @param element
     *            The element to be stored.
     */
    public void insert(K key, E element) {
	// world coordinates
	BoundingBox currentWorld = new BoundingBox(new Point(this.minimumXAxis,
		this.minimumYAxis), this.maximumXAxis - this.minimumXAxis,
		this.maximumYAxis - this.minimumYAxis);

	this.rootNode = this.insertHelp(this.rootNode, currentWorld, key,
		element, true);
	this.size++;
    }

    /**
     * This insert forces the BinTree to have multi-dimensional keys of lowest
     * value in the leftmost leaf and multi-dimensional keys of greatest value
     * in the rightmost leaf of the BinTree.
     *
     * @param node
     *            The node to be inserted.
     * @param currentWorld
     *            A box representation of the current world.
     * @param key
     *            The key value be used to compare to discriminators when the
     *            bin tree.
     * @param element
     *            The element to be inserted.
     * @param isSplittingXAxis
     *            If true, split world along x-axis; otherwise split along y-
     *            axis.
     * @return A subtree with the given node inserted.
     */
    BinTreeNode<E> insertHelp(BinTreeNode<E> node, BoundingBox currentWorld,
	    K key, E element, boolean isSplittingXAxis) {
	// in a bin tree with many elements
	if (node instanceof BinTreeEmptyNode) {
	    return new BinTreeLeafNode<K, E>(key, element);
	} else if (node instanceof BinTreeInternalNode<?>) {
	    if (isSplittingXAxis) {
		isSplittingXAxis = false; // so y-axis can be split next time
		if (key.getX() < currentWorld
			.getCurrentMidpointOfBoxAlongXAxis()) {
		    // current node should go to left subtree
		    currentWorld.changeToLeftHalfBoundingBox();

		    // recursive call on left child until base case of empty
		    // node is reached
		    ((BinTreeInternalNode<E>) node).setLeftChild(this
			    .insertHelp(((BinTreeInternalNode<E>) node)
				    .getLeftChild(), currentWorld, key,
				    element, isSplittingXAxis));
		} else { // current node should go to right subtree
		    currentWorld.changeToRightHalfBoundingBox();

		    // recursive call on right child until base case of empty
		    // node is reached
		    ((BinTreeInternalNode<E>) node).setRightChild(this
			    .insertHelp(((BinTreeInternalNode<E>) node)
				    .getRightChild(), currentWorld, key,
				    element, isSplittingXAxis));
		}
	    } else { // splitting y-axis
		isSplittingXAxis = true; // so x-axis can be split next time
		if (key.getY() < currentWorld
			.getCurrentMidpointOfBoxAlongYAxis()) {
		    currentWorld.changeToBottomHalfBoundingBox();

		    ((BinTreeInternalNode<E>) node).setLeftChild(this
			    .insertHelp(((BinTreeInternalNode<E>) node)
				    .getLeftChild(), currentWorld, key,
				    element, isSplittingXAxis));
		} else {
		    currentWorld.changeToTopHalfBoundingBox();

		    ((BinTreeInternalNode<E>) node).setRightChild(this
			    .insertHelp(((BinTreeInternalNode<E>) node)
				    .getRightChild(), currentWorld, key,
				    element, isSplittingXAxis));
		}
	    }
	} else if (node instanceof BinTreeLeafNode<?, ?>) { // this is
	    // BinTreeLeafNode
	    // that is not empty
	    @SuppressWarnings("unchecked")
	    BinTreeLeafNode<K, E> tempNode = (BinTreeLeafNode<K, E>) node;

	    node = new BinTreeInternalNode<E>();

	    // To ensure that the same worlds are used when inserting into
	    // the current node all fields given to constructor of
	    // currentWorldStateDuplicate must be of type double and NOT
	    // a reference of the currentWorlds fields.
	    // For example, if currentWorld.getBottomLeftPoint() is used
	    // set the x and y for currentWorldStateDuplicate, then whenever
	    // currentWorld's bottom left point changes, so will
	    // currentWorldStateDuplicate
	    double currentWorldX = currentWorld.getBottomLeftPoint().getX();
	    double currentWorldY = currentWorld.getBottomLeftPoint().getY();
	    BoundingBox cuurrentWorldStateDuplicate = new BoundingBox(
		    new Point(currentWorldX, currentWorldY),
		    currentWorld.getWidth(), currentWorld.getHeight());

	    this.insertHelp(node, currentWorld, tempNode.getKey(),
		    tempNode.getElement(), isSplittingXAxis);

	    this.insertHelp(node, cuurrentWorldStateDuplicate, key, element,
		    isSplittingXAxis);

	    return node;
	}
	return node;
    }

    /**
     * @param key
     *            The key of the element to remove.
     * @param element
     *            The element to remove.
     * @return true if element with key was removed from bin tree; otherwise
     *         return false.
     */
    public boolean remove(K key, E element) {
	if (this.find(key, element) == null) {
	    return false;
	} else { // key value pair exists in bin tree
	    BoundingBox currentWorld = new BoundingBox(new Point(
		    this.minimumXAxis, this.minimumYAxis), this.maximumXAxis
		    - this.minimumXAxis, this.maximumYAxis - this.minimumYAxis);

	    this.rootNode = this.removeHelp(this.rootNode, currentWorld, key,
		    element, true);
	    this.size--;

	    this.rootNode = this.pruneBinTree(this.rootNode);
	    return true;
	}
    }

    /**
     * @param node
     *            The node to remove.
     * @param currentWorld
     *            A box representation of the current world.
     * @param key
     *            The key of the element to remove.
     * @param element
     *            The element to remove.
     * @param isSplittingXAxis
     * @return The node that was removed.
     */
    BinTreeNode<E> removeHelp(BinTreeNode<E> node, BoundingBox currentWorld,
	    K key, E element, boolean isSplittingXAxis) {
	if (node instanceof BinTreeEmptyNode<?>) {
	    return null;
	} else if (node instanceof BinTreeInternalNode<?>) {
	    if (isSplittingXAxis) {
		isSplittingXAxis = false; // so y-axis can be split next
					  // time
		if (key.getX() < currentWorld
			.getCurrentMidpointOfBoxAlongXAxis()) {
		    // current node should go to left subtree
		    currentWorld.changeToLeftHalfBoundingBox();

		    ((BinTreeInternalNode<E>) node).setLeftChild(this
			    .removeHelp(((BinTreeInternalNode<E>) node)
				    .getLeftChild(), currentWorld, key,
				    element, isSplittingXAxis));
		} else { // current node should go to right subtree
		    currentWorld.changeToRightHalfBoundingBox();

		    ((BinTreeInternalNode<E>) node).setRightChild(this
			    .removeHelp(((BinTreeInternalNode<E>) node)
				    .getRightChild(), currentWorld, key,
				    element, isSplittingXAxis));
		}
	    } else { // splitting y-axis
		isSplittingXAxis = true; // so x-axis can be split next time
		if (key.getY() < currentWorld
			.getCurrentMidpointOfBoxAlongYAxis()) {
		    currentWorld.changeToBottomHalfBoundingBox();

		    ((BinTreeInternalNode<E>) node).setLeftChild(this
			    .removeHelp(((BinTreeInternalNode<E>) node)
				    .getLeftChild(), currentWorld, key,
				    element, isSplittingXAxis));
		} else {
		    currentWorld.changeToTopHalfBoundingBox();

		    ((BinTreeInternalNode<E>) node).setRightChild(this
			    .removeHelp(((BinTreeInternalNode<E>) node)
				    .getRightChild(), currentWorld, key,
				    element, isSplittingXAxis));
		}
	    }
	} else if (node instanceof BinTreeLeafNode<?, ?>) {
	    if (element.equals(((BinTreeLeafNode<?, E>) node).getElement())
		    && key.equals(((BinTreeLeafNode<?, E>) node).getKey())) {
		node = this.emptyLeafNodeFlyweight;
		return node;
	    } else {
		return null;
	    }
	}
	return node;
    }

    /**
     * Given a bin tree root node with internal nodes that have exactly one leaf
     * node and one empty node, this method will remove all of these internal
     * node states and replace the internal node with the leaf node recursively.
     *
     * @param node
     *            The root node of a bin tree that has unnecessary
     *            BinTreeInternalNodes and BinTreeEmptyNodes.
     * @return The given bin tree with only necessary nodes.
     */
    BinTreeNode<E> pruneBinTree(BinTreeNode<E> node) {
	if (node instanceof BinTreeInternalNode<?>) {
	    // ---------------------traverse the bin tree-----------------------
	    // now check if left or right child is an internal node
	    if (((BinTreeInternalNode<E>) node).getLeftChild() instanceof BinTreeInternalNode<?>) {
		((BinTreeInternalNode<E>) node).setLeftChild(this
			.pruneBinTree(((BinTreeInternalNode<E>) node)
				.getLeftChild()));
	    }

	    if (((BinTreeInternalNode<E>) node).getRightChild() instanceof BinTreeInternalNode<?>) {
		((BinTreeInternalNode<E>) node).setRightChild(this
			.pruneBinTree(((BinTreeInternalNode<E>) node)
				.getRightChild()));
	    }

	    // ------------------------------merge------------------------------
	    if (((BinTreeInternalNode<E>) node).getLeftChild() instanceof BinTreeEmptyNode<?>
		    && ((BinTreeInternalNode<E>) node).getRightChild() instanceof BinTreeLeafNode<?, ?>) {
		// sets the current internal to it's right child leaf node
		// !!!!! replacedNode needs to be of type BinTreeLeafNode
		BinTreeNode<E> replacedNode = ((BinTreeInternalNode<E>) node)
			.getRightChild();
		return replacedNode;
	    } else if (((BinTreeInternalNode<E>) node).getLeftChild() instanceof BinTreeLeafNode<?, ?>
		    && ((BinTreeInternalNode<E>) node).getRightChild() instanceof BinTreeEmptyNode<?>) {
		// sets the current internal to it's left child leaf node
		// !!!!! replacedNode needs to be of type BinTreeLeafNode
		BinTreeNode<E> replacedNode = ((BinTreeInternalNode<E>) node)
			.getLeftChild();
		return replacedNode;
	    }
	    return node;
	}
	return node;
    }

    /**
     * @param key
     *            Used to search through the bin tree.
     * @param element
     *            The element to find within the bin tree.
     * @return The element in the bin tree if the given element with the given
     *         key exists within the bin tree. If the given element does not
     *         exist within the bin tree return null.
     */
    public E find(K key, E element) {
	BoundingBox world = new BoundingBox(new Point(this.minimumXAxis,
		this.minimumYAxis), this.maximumXAxis - this.minimumXAxis,
		this.maximumYAxis - this.minimumYAxis);

	return this.findHelp(this.rootNode, world, key, element, true);
    }

    E findHelp(BinTreeNode<E> node, BoundingBox currentWorld, K key, E element,
	    boolean isSplittingXAxis) {
	if (node instanceof BinTreeEmptyNode<?>) {
	    return null;
	} else if (node instanceof BinTreeInternalNode<?>) {
	    if (isSplittingXAxis) {
		isSplittingXAxis = false; // so y-axis can be split next time
		if (key.getX() < currentWorld
			.getCurrentMidpointOfBoxAlongXAxis()) {
		    // current node should go to left subtree
		    currentWorld.changeToLeftHalfBoundingBox();

		    return this.findHelp(
			    ((BinTreeInternalNode<E>) node).getLeftChild(),
			    currentWorld, key, element, isSplittingXAxis);
		} else { // current node should go to right subtree
		    currentWorld.changeToRightHalfBoundingBox();

		    return this.findHelp(
			    ((BinTreeInternalNode<E>) node).getRightChild(),
			    currentWorld, key, element, isSplittingXAxis);
		}
	    } else { // splitting y-axis
		isSplittingXAxis = true; // so x-axis can be split next time
		if (key.getY() < currentWorld
			.getCurrentMidpointOfBoxAlongYAxis()) {
		    currentWorld.changeToBottomHalfBoundingBox();

		    return this.findHelp(
			    ((BinTreeInternalNode<E>) node).getLeftChild(),
			    currentWorld, key, element, isSplittingXAxis);
		} else {
		    currentWorld.changeToTopHalfBoundingBox();

		    return this.findHelp(
			    ((BinTreeInternalNode<E>) node).getRightChild(),
			    currentWorld, key, element, isSplittingXAxis);
		}
	    }
	} else if (node instanceof BinTreeLeafNode<?, ?>) {
	    if (element.equals(((BinTreeLeafNode<?, E>) node).getElement())
		    && key.equals(((BinTreeLeafNode<?, E>) node).getKey())) {
		return element;
	    } else {
		return null;
	    }
	}
	return null;
    }

    // TODO: test
    public boolean findKey(K key) {
	BoundingBox world = new BoundingBox(new Point(this.minimumXAxis,
		this.minimumYAxis), this.maximumXAxis - this.minimumXAxis,
		this.maximumYAxis - this.minimumYAxis);

	return this.findKeyHelp(this.rootNode, world, key, true);
    }

    // TODO: test
    boolean findKeyHelp(BinTreeNode<E> node, BoundingBox currentWorld, K key,
	    boolean isSplittingXAxis) {
	if (node instanceof BinTreeEmptyNode<?>) {
	    return false;
	} else if (node instanceof BinTreeInternalNode<?>) {
	    if (isSplittingXAxis) {
		isSplittingXAxis = false; // so y-axis can be split next time
		if (key.getX() < currentWorld
			.getCurrentMidpointOfBoxAlongXAxis()) {
		    // current node should go to left subtree
		    currentWorld.changeToLeftHalfBoundingBox();

		    return this.findKeyHelp(
			    ((BinTreeInternalNode<E>) node).getLeftChild(),
			    currentWorld, key, isSplittingXAxis);
		} else { // current node should go to right subtree
		    currentWorld.changeToRightHalfBoundingBox();

		    return this.findKeyHelp(
			    ((BinTreeInternalNode<E>) node).getRightChild(),
			    currentWorld, key, isSplittingXAxis);
		}
	    } else { // splitting y-axis
		isSplittingXAxis = true; // so x-axis can be split next time
		if (key.getY() < currentWorld
			.getCurrentMidpointOfBoxAlongYAxis()) {
		    currentWorld.changeToBottomHalfBoundingBox();

		    return this.findKeyHelp(
			    ((BinTreeInternalNode<E>) node).getLeftChild(),
			    currentWorld, key, isSplittingXAxis);
		} else {
		    currentWorld.changeToTopHalfBoundingBox();

		    return this.findKeyHelp(
			    ((BinTreeInternalNode<E>) node).getRightChild(),
			    currentWorld, key, isSplittingXAxis);
		}
	    }
	} else if (node instanceof BinTreeLeafNode<?, ?>) {
	    if (key.equals(((BinTreeLeafNode<?, E>) node).getKey())) {
		return true;
	    } else {
		return false;
	    }
	}
	return false;
    }

    /**
     * @param earthquake
     * @return A string describing how many nodes were visited during the
     *         search.
     */
    public String regionSearch(Earthquake earthquake) {
	double objectLongitude = earthquake.getLocation().getLongitude() + 180.0;
	double objectLatitude = earthquake.getLocation().getLatitude() + 90.0;
	double objectMagnitude = earthquake.getMagnitude();

	double radius = Math.pow(objectMagnitude, 3) * 2;
	double earthquakeBoundingBoxBottomLeftX = objectLongitude - radius;
	double earthquakeBoundingBoxBottomLeftY = objectLatitude - radius;

	double width = radius * 2;
	double height = radius * 2;
	BoundingBox objectBoundingBox = new BoundingBox(new Point(
		earthquakeBoundingBoxBottomLeftX,
		earthquakeBoundingBoxBottomLeftY), width, height);

	BoundingBox currentWorld = new BoundingBox(new Point(this.minimumXAxis,
		this.minimumYAxis), this.maximumXAxis - this.minimumXAxis,
		this.maximumYAxis - this.minimumYAxis);

	int numberOfBinTreeNodesVisited = this.regionSearchHelp(this.rootNode,
		currentWorld, objectBoundingBox, new Point(objectLongitude,
			objectLatitude), radius, true);
	return "Watcher search caused " + numberOfBinTreeNodesVisited
		+ " bintree nodes to be visited.";
    }

    /**
     * Assume we want to print out a list of all records that are within a
     * certain distance d of a given point P.
     *
     * Search proceeds by means of a directed traversal. When we visit a node of
     * the tree, we only proceed if the bounding box for the search circle
     * intersects the bounding box for the node if it does not, we stop and
     * return. If it does intersect an internal node, we visit the node's
     * children. If it is a leaf node, then we ask whether the data point it
     * contains is within distance d of the search point.
     *
     * Note: In the average case, the number of nodes that must be visited
     * during a range query is linear on the number of data records that fall
     * within the query circle.
     *
     * @param node
     * @param currentWorld
     * @param objectBoundingBox
     * @param objectPoint
     * @param objectDistance
     * @param isSplittingXAxis
     * @return The number of nodes that were visited.
     */
    @SuppressWarnings("unchecked")
    int regionSearchHelp(BinTreeNode<E> node, BoundingBox currentWorld,
	    BoundingBox objectBoundingBox, Point objectPoint,
	    double objectDistance, boolean isSplittingXAxis) {
	if (node instanceof BinTreeEmptyNode) {
	    if (BoundingBox.isOverlapping(currentWorld, objectBoundingBox)) {
		return 1;
	    } else {
		return 0;
	    }
	} else if (node instanceof BinTreeInternalNode<?>) {
	    if (isSplittingXAxis) {
		if (BoundingBox.isOverlapping(currentWorld, objectBoundingBox)) {
		    // current node should go to left subtree
		    double worldX = currentWorld.getBottomLeftPoint().getX();
		    double worldY = currentWorld.getBottomLeftPoint().getY();

		    BoundingBox leftWorld = new BoundingBox(new Point(worldX,
			    worldY), currentWorld.getWidth(),
			    currentWorld.getHeight());
		    leftWorld.changeToLeftHalfBoundingBox();

		    // current node should go to right subtree
		    BoundingBox rightWorld = new BoundingBox(new Point(worldX,
			    worldY), currentWorld.getWidth(),
			    currentWorld.getHeight());
		    rightWorld.changeToRightHalfBoundingBox();

		    return 1
			    + this.regionSearchHelp(
				    ((BinTreeInternalNode<E>) node)
					    .getLeftChild(), leftWorld,
				    objectBoundingBox, objectPoint,
				    objectDistance, !isSplittingXAxis)
			    + this.regionSearchHelp(
				    ((BinTreeInternalNode<E>) node)
					    .getRightChild(), rightWorld,
				    objectBoundingBox, objectPoint,
				    objectDistance, !isSplittingXAxis);
		} else {
		    return 0;
		}
	    } else {
		if (BoundingBox.isOverlapping(currentWorld, objectBoundingBox)) {
		    // current node should go to left subtree
		    double worldX = currentWorld.getBottomLeftPoint().getX();
		    double worldY = currentWorld.getBottomLeftPoint().getY();
		    BoundingBox leftWorld = new BoundingBox(new Point(worldX,
			    worldY), currentWorld.getWidth(),
			    currentWorld.getHeight());
		    leftWorld.changeToBottomHalfBoundingBox();

		    // current node should go to right subtree
		    BoundingBox rightWorld = new BoundingBox(new Point(worldX,
			    worldY), currentWorld.getWidth(),
			    currentWorld.getHeight());
		    rightWorld.changeToTopHalfBoundingBox();

		    return 1
			    + this.regionSearchHelp(
				    ((BinTreeInternalNode<E>) node)
					    .getLeftChild(), leftWorld,
				    objectBoundingBox, objectPoint,
				    objectDistance, !isSplittingXAxis)
			    + this.regionSearchHelp(
				    ((BinTreeInternalNode<E>) node)
					    .getRightChild(), rightWorld,
				    objectBoundingBox, objectPoint,
				    objectDistance, !isSplittingXAxis);
		} else {
		    return 0;
		}
	    }
	} else if (node instanceof BinTreeLeafNode<?, ?>) {
	    if (BoundingBox.isOverlapping(currentWorld, objectBoundingBox)) {

		double distance = objectDistance;
		double EP_x = objectPoint.getX();
		double EP_y = objectPoint.getY();
		double WP_x = ((BinTreeLeafNode<?, E>) node).getKey().getX();
		double WP_y = ((BinTreeLeafNode<?, E>) node).getKey().getY();
		// check to see if the current node parameter is actually
		// close enough to the object parameter. There is the
		// possibility that the node's watcher's bounding box will
		// overlap with the object bounding box but will not
		// actually be within the object bounding box circle(that
		// perfectly fits inside of the object bounding box)

		// objectPoint EP is defined to be within distance of
		// point WP (node's objects point location) if:
		// (EP_x - WP_x)^2 + (EP_y - WP_y)^2 <= distance^2
		double distanceSquared = Math.pow(distance, 2);
		double leftSideOfEquation = (Math.pow((EP_x - WP_x), 2) + Math
			.pow((EP_y - WP_y), 2));

		if (leftSideOfEquation <= distanceSquared) {
		    System.out.println(((BinTreeLeafNode<?, E>) node)
			    .getElement().toString());
		} else {
		    // don't print out information about element since
		    // point is not close enough to the object
		}
		return 1; // to add to the counter of how many nodes are visited
	    } else {
		return 0;
	    }
	}
	return 0;
    }

    /**
     * @param box1
     * @param box2
     * @return true if box1 overlaps box2; otherwise return false.
     */
    boolean isOverlapping(BoundingBox box1, BoundingBox box2) {
	boolean isOverlapping = false;

	// all coordinates for box 1
	double aNEx = box1.getBottomLeftPoint().getX() + box1.getWidth();
	double aNEy = box1.getBottomLeftPoint().getY() + box1.getHeight();
	double aSWx = box1.getBottomLeftPoint().getX();
	double aSWy = box1.getBottomLeftPoint().getY();

	// all coordinates for box 2
	double bNEx = box2.getBottomLeftPoint().getX() + box2.getWidth();
	double bNEy = box2.getBottomLeftPoint().getY() + box2.getHeight();
	double bSWx = box2.getBottomLeftPoint().getX();
	double bSWy = box2.getBottomLeftPoint().getY();
	double aHeight = box2.getHeight();

	if (aSWx <= bNEx && aNEx >= bSWx && aNEy >= bSWy && aSWy <= bNEy) {
	    isOverlapping = true;
	}
	return isOverlapping;
    }

    /**
     * Remove all nodes in bin tree and replace root node with empty node
     * flyweight.
     */
    public void clear() {
	this.rootNode = this.emptyLeafNodeFlyweight;
	this.size = 0;
    }

    /**
     * @return the number of BinTreeNodes in this bin tree.
     */
    public int size() {
	return this.size;
    }

    /**
     * @param node
     *            The rootNode of a bin tree to begin preorder traversal.
     * @return Starting at the rootNode of the bin tree this method returns a
     *         string representation of a preorder traversal of the rootNode
     *         parameter with the following notation: 1) "I" for internal nodes
     *         2) "E" for empty leaf nodes 3) the key and element of a leaf node
     */
    @SuppressWarnings({ "unchecked" })
    public String preorderTraversal(BinTreeNode<E> node) {
	StringBuilder stringBuilder = new StringBuilder();
	if (node instanceof BinTreeEmptyNode<?>) {
	    return "E\n";
	} else if (node instanceof BinTreeInternalNode<?>) {
	    stringBuilder.append("I\n");
	    stringBuilder.append(this
		    .preorderTraversal(((BinTreeInternalNode<E>) node)
			    .getLeftChild()));
	    stringBuilder.append(this
		    .preorderTraversal(((BinTreeInternalNode<E>) node)
			    .getRightChild()));
	} else if (node instanceof BinTreeLeafNode<?, ?>) {
	    stringBuilder.append(((BinTreeLeafNode<K, E>) node).toString()
		    + "\n");
	}
	return stringBuilder.toString();
    }

    /**
     * @return the root node.
     */
    BinTreeNode<E> getRootNode() {
	return this.rootNode;
    }

    /**
     * @param rootNode
     *            the new root node.
     */
    void setRootNode(BinTreeNode<E> rootNode) {
	this.rootNode = rootNode;
    }
}
