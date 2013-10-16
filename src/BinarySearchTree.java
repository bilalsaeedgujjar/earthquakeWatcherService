import java.lang.Comparable;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 16, 2013
 * @param <K>
 * @param <E>
 */
public class BinarySearchTree<K extends Comparable<? super K>, E> implements
	DictionaryInterface<K, E> {

    private BinarySearchTreeNode<K, E> rootNode;
    private int numberOfNodes;

    /**
     * Create a new BinarySearchTree object.
     */
    public BinarySearchTree() {
	this.rootNode = null;
	this.numberOfNodes = 0;
    }

    @Override
    public void clear() {
	this.rootNode = null;
	this.numberOfNodes = 0;
    }

    @Override
    public void insert(K key, E element) {
	this.rootNode = this.insertHelp(this.rootNode, key, element);
	this.numberOfNodes++;
    }

    @Override
    public E remove(K key) {
	// first find the node to remove
	E nodeToRemove = this.findHelp(this.rootNode, key);
	if (nodeToRemove != null) {
	    // now remove the found node
	    this.rootNode = this.removeHelp(this.rootNode, key);
	    this.numberOfNodes--;
	}
	return nodeToRemove;
    }

    /**
     * Removes the root element of the binary search tree.
     */
    @Override
    public E removeRandomElement() {
	if (this.rootNode == null) {
	    return null;
	}
	E randomeNodeToRemove = this.rootNode.getValue();
	this.rootNode = this.removeHelp(this.rootNode, this.rootNode.getKey());
	this.numberOfNodes--;
	return randomeNodeToRemove;
    }

    @Override
    public E find(K key) {
	return this.findHelp(this.rootNode, key);
    }

    @Override
    public int size() {
	return this.numberOfNodes;
    }

    /**
     * @param node
     *            The node to find within the binary search tree.
     * @param key
     *            The value used to search for an equivalent key in the binary
     *            search tree.
     * @return The found element; otherwise return null.
     */
    E findHelp(BinarySearchTreeNode<K, E> node, K key) {
	if (node == null) {
	    return null;
	}
	if (node.getKey().compareTo(key) > 0) {
	    return this.findHelp(node.getLeftChild(), key);
	} else if (node.getKey().compareTo(key) == 0) {
	    return node.getValue();
	} else {
	    return this.findHelp(node.getRightChild(), key);
	}
    }

    /**
     * @param node
     *            The node that holds a key value pair to insert into the binary
     *            search tree.
     * @param key
     *            The key used to insert the given node into the correct
     *            location.
     * @param element
     *            The generic element to add to this binary search tree.
     * @return The node with the given node inserted in the correct location.
     */
    BinarySearchTreeNode<K, E> insertHelp(BinarySearchTreeNode<K, E> node,
	    K key, E element) {
	if (node == null) {
	    return new BinarySearchTreeNode<K, E>(key, element, null, null);
	}
	if (node.getKey().compareTo(key) > 0) {
	    node.setLeftChild(this.insertHelp(node.getLeftChild(), key, element));
	} else {
	    node.setRightChild(this.insertHelp(node.getRightChild(), key,
		    element));
	}
	return node;
    }

    /**
     * @param node
     *            The node to be removed.
     * @param key
     *            Used to find the node in the binary search tree.
     * @return The removed node if it exists within the binary search tree;
     *         otherwise return null.
     */
    BinarySearchTreeNode<K, E> removeHelp(BinarySearchTreeNode<K, E> node, K key) {
	if (node == null) {
	    return null;
	}
	if (node.getKey().compareTo(key) > 0) {
	    node.setLeftChild(this.removeHelp(node.getLeftChild(), key));
	} else if (node.getKey().compareTo(key) < 0) {
	    node.setRightChild(this.removeHelp(node.getRightChild(), key));
	} else { // found node to remove and it is now denoted as rootNode
	    if (node.getLeftChild() == null) {
		return node.getRightChild();
	    } else if (node.getRightChild() == null) {
		return node.getLeftChild();
	    } else {
		// there are 2 children when deleting a node that has 2
		// non-empty children, the deleted node is replaced with the
		// greatest valued node in the left subtree
		BinarySearchTreeNode<K, E> greatestNodeInLeftStubtree = this
			.getNodeWithMaximumValue(node.getLeftChild());

		node.setValue(greatestNodeInLeftStubtree.getValue());
		node.setKey(greatestNodeInLeftStubtree.getKey());

		// delete greatestNodeInLeftSubtree
		node.setLeftChild(this.deleteNodeWithMaximumValue(node
			.getLeftChild()));
	    }
	}
	return node;
    }

    /**
     * @param node
     *            The root node of a tree.
     * @return a descendant of the current node with the greatest key value.
     */
    BinarySearchTreeNode<K, E> getNodeWithMaximumValue(
	    BinarySearchTreeNode<K, E> node) {
	if (node.getRightChild() == null) {
	    return node;
	}
	return this.getNodeWithMaximumValue(node.getRightChild());
    }

    /**
     * Remove a descendant of the current node with the greatest key value.
     *
     * @param node
     *            The root node of a tree.
     * @return a descendant of the current node with the greatest key value.
     */
    BinarySearchTreeNode<K, E> deleteNodeWithMaximumValue(
	    BinarySearchTreeNode<K, E> node) {
	if (node.getRightChild() == null) {
	    return node.getLeftChild();
	}
	node.setRightChild(this.deleteNodeWithMaximumValue(node.getRightChild()));
	return node;
    }

    /**
     * @return The current binary search tree's root node.
     */
    BinarySearchTreeNode<K, E> getRootNode() {
	return this.rootNode;
    }

    /**
     * @param node
     *            The node to begin the inorder traversal.
     * @param nodeDepth
     *            the depth of the current node within the binary search tree.
     *            The root node has a depth of 0.
     * @return A String representation of the current binary search tree. The
     *         inorder traversal prints each element on a new line. To indicate
     *         the depth of the node within the binary search tree a depth
     *         number of ".." is printed infront of the node's value to
     *         textually represent that node's depth.
     */
    public String inorderTraversal(BinarySearchTreeNode<K, E> node,
	    int nodeDepth) {
	StringBuilder stringBuilder = new StringBuilder();
	if (node != null) {

	    stringBuilder.append(this.inorderTraversal(node.getLeftChild(),
		    ++nodeDepth));

	    // print (depth of current node - 1) * 2 "dots" to represent
	    // how deep the current node is within the binary search tree
	    for (int i = 0; i < (nodeDepth - 1); i++) {
		stringBuilder.append("..");
	    }
	    nodeDepth = nodeDepth - 1;

	    stringBuilder.append(node.getValue().toString() + "\n");

	    stringBuilder.append(this.inorderTraversal(node.getRightChild(),
		    ++nodeDepth));
	    return stringBuilder.toString();
	} else {
	    return "";
	}
    }
}
