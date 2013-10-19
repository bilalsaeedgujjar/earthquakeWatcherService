package dataStructures;
/**
 * @version Oct 2, 2013
 * @param <Key>
 *            A unique identifier property of the element to be used to
 *            efficiently store elements in the binary search tree.
 * @param <Element>
 *            The type of element to be stored in this node.
 */
public class BinarySearchTreeNode<Key, Element> implements
	BinNodeInterface<Element> {
    private Key key;
    private Element value;

    private BinarySearchTreeNode<Key, Element> leftChild;
    private BinarySearchTreeNode<Key, Element> rightChild;

    /**
     * Create a new BinarySearchTreeNode object.
     *
     * @param key
     *            The type of key to use for comparing this node with other
     *            nodes.
     * @param value
     *            The type of the value to be stored in this node.
     * @param leftChild
     *            A reference to this node's left node.
     * @param rightChild
     *            A reference to this node's right node.
     */
    public BinarySearchTreeNode(Key key, Element value,
	    BinarySearchTreeNode<Key, Element> leftChild,
	    BinarySearchTreeNode<Key, Element> rightChild) {
	this.key = key;
	this.value = value;
	this.leftChild = leftChild;
	this.rightChild = rightChild;
    }

    /**
     * @return The key for this node.
     */
    public Key getKey() {
	return this.key;
    }

    /**
     * @param key
     *            The new key for this node.
     */
    public void setKey(Key key) {
	this.key = key;
    }

    @Override
    public Element getValue() {
	return this.value;
    }

    @Override
    public void setValue(Element value) {
	this.value = value;
    }

    @Override
    public BinarySearchTreeNode<Key, Element> getLeftChild() {
	return this.leftChild;
    }

    /**
     * @param leftChild
     *            The new left child for this node.
     */
    public void setLeftChild(BinarySearchTreeNode<Key, Element> leftChild) {
	this.leftChild = leftChild;
    }

    @Override
    public BinarySearchTreeNode<Key, Element> getRightChild() {
	return this.rightChild;
    }

    /**
     * @param rightChild
     *            The new right child for this node.
     */
    public void setRightChild(BinarySearchTreeNode<Key, Element> rightChild) {
	this.rightChild = rightChild;
    }

    @Override
    public boolean isLeaf() {
	if (this.leftChild == null && this.rightChild == null) {
	    return true;
	} else {
	    return false;
	}
    }
}
