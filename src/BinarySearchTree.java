import java.lang.Comparable;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 9, 2013
 * @param <Key>
 * @param <Element>
 */
public class BinarySearchTree<Key extends Comparable<? super Key>, Element>
	implements DictionaryInterface<Key, Element> {

    private BinarySearchTreeNode<Key, Element> rootNode;
    private int numberOfNodes;

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
    public void insert(Key key, Element element) {
	this.rootNode = this.insertHelp(this.rootNode, key, element);
	this.numberOfNodes++;
    }

    @Override
    public Element remove(Key key) {
	// first find the node to remove
	Element nodeToRemove = this.findHelp(this.rootNode, key);
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
    public Element removeRandomElement() {
	if (this.rootNode == null) {
	    return null;
	}
	Element randomeNodeToRemove = this.rootNode.getValue();
	this.rootNode = this.removeHelp(this.rootNode, this.rootNode.getKey());
	this.numberOfNodes--;
	return randomeNodeToRemove;
    }

    @Override
    public Element find(Key key) {
	return this.findHelp(this.rootNode, key);
    }

    @Override
    public int size() {
	return this.numberOfNodes;
    }

    Element findHelp(BinarySearchTreeNode<Key, Element> rootNode, Key key) {
	if (rootNode == null) {
	    return null;
	}
	if (rootNode.getKey().compareTo(key) > 0) {
	    return this.findHelp(rootNode.getLeftChild(), key);
	} else if (rootNode.getKey().compareTo(key) == 0) {
	    return rootNode.getValue();
	} else {
	    return this.findHelp(rootNode.getRightChild(), key);
	}
    }

    BinarySearchTreeNode<Key, Element> insertHelp(
	    BinarySearchTreeNode<Key, Element> rootNode, Key key,
	    Element element) {
	if (rootNode == null) {
	    return new BinarySearchTreeNode<Key, Element>(key, element, null,
		    null);
	}
	if (rootNode.getKey().compareTo(key) > 0) {
	    rootNode.setLeftChild(this.insertHelp(rootNode.getLeftChild(), key,
		    element));
	} else {
	    rootNode.setRightChild(this.insertHelp(rootNode.getRightChild(),
		    key, element));
	}
	return rootNode;
    }

    BinarySearchTreeNode<Key, Element> removeHelp(
	    BinarySearchTreeNode<Key, Element> rootNode, Key key) {
	if (rootNode == null) {
	    return null;
	}
	if (rootNode.getKey().compareTo(key) > 0) {
	    rootNode.setLeftChild(this.removeHelp(rootNode.getLeftChild(), key));
	} else if (rootNode.getKey().compareTo(key) < 0) {
	    rootNode.setRightChild(this.removeHelp(rootNode.getRightChild(),
		    key));
	} else { // found node to remove and it is now denoted as rootNode
	    if (rootNode.getLeftChild() == null) {
		return rootNode.getRightChild();
	    } else if (rootNode.getRightChild() == null) {
		return rootNode.getLeftChild();
	    } else {
		// there are 2 children when deleting a node that has 2
		// non-empty children, the deleted node is replaced with the
		// greatest valued node in the left subtree
		BinarySearchTreeNode<Key, Element> greatestNodeInLeftStubtree = this
			.getNodeWithMaximumValue(rootNode.getLeftChild());

		rootNode.setValue(greatestNodeInLeftStubtree.getValue());
		rootNode.setKey(greatestNodeInLeftStubtree.getKey());

		// delete greatestNodeInLeftSubtree
		rootNode.setLeftChild(this.deleteNodeWithMaximumValue(rootNode
			.getLeftChild()));

	    }
	}
	return rootNode;
    }

    BinarySearchTreeNode<Key, Element> getNodeWithMaximumValue(
	    BinarySearchTreeNode<Key, Element> rootNode) {
	if (rootNode.getRightChild() == null) {
	    return rootNode;
	}
	return this.getNodeWithMaximumValue(rootNode.getRightChild());
    }

    BinarySearchTreeNode<Key, Element> deleteNodeWithMaximumValue(
	    BinarySearchTreeNode<Key, Element> rootNode) {
	if (rootNode.getRightChild() == null) {
	    return rootNode.getLeftChild();
	}
	rootNode.setRightChild(this.deleteNodeWithMaximumValue(rootNode
		.getRightChild()));
	return rootNode;
    }

    BinarySearchTreeNode getRootNode() {
	return this.rootNode;
    }

    public String inorderTraversal(BinarySearchTreeNode<Key, Element> rootNode) {
	StringBuilder stringBuilder = new StringBuilder();
	if (rootNode != null) {
	    stringBuilder
		    .append(this.inorderTraversal(rootNode.getLeftChild()));
	    stringBuilder.append(rootNode.getValue().toString() + " ");
	    stringBuilder
		    .append(this.inorderTraversal(rootNode.getRightChild()));
	    return stringBuilder.toString();
	} else {
	    return "";
	}
    }
}
