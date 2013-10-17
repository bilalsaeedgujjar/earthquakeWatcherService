/**
 * The internal nodes of a bin tree only store references to it's left and right
 * child.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 12, 2013
 * @param <E> The type of element to store in this node.
 */
public class BinTreeInternalNode<E> extends BinTreeNode<E> {
    /**
     * Current internal node left child.
     */
    BinTreeNode<E> leftChild;

    /**
     * Current internal node right child.
     */
    BinTreeNode<E> rightChild;

    /**
     * Create a new BinTreeInternalNode object with 2 empty bin tree leaf nodes.
     */
    @SuppressWarnings("unchecked")
    public BinTreeInternalNode() {
	this.leftChild = BinTreeEmptyNode.getInstance();
	this.rightChild = BinTreeEmptyNode.getInstance();
    }

    /**
     * @return left child node
     */
    public BinTreeNode<E> getLeftChild() {
	return this.leftChild;
    }

    /**
     * @param leftChild
     *            the new left child node
     */
    public void setLeftChild(BinTreeNode<E> leftChild) {
	this.leftChild = leftChild;
    }

    /**
     * @return left child node
     */
    public BinTreeNode<E> getRightChild() {
	return this.rightChild;
    }

    /**
     * @param rightChild
     *            the new right child node
     */
    public void setRightChild(BinTreeNode<E> rightChild) {
	this.rightChild = rightChild;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n==Bin Tree Internal Node==");
	stringBuilder.append("\n leftChild: ");
	stringBuilder.append("\n" + this.leftChild.toString() + "\n");
	stringBuilder.append("\nrightChild:");
	stringBuilder.append("\n" + this.rightChild.toString());
	stringBuilder.append("\n==========================");
	String binTreeInformation = stringBuilder.toString();
	return binTreeInformation;
    }
}
