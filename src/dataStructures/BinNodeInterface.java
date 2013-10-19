package dataStructures;
/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 2, 2013
 * @param <E>
 *            The type of element to store in this bin tree.
 */
public interface BinNodeInterface<E> {
    /**
     * @return The value of this node.
     */
    public E getValue();

    /**
     * @param value The new value for this node.
     */
    public void setValue(E value);

    /**
     * @return The left node of this node.
     */
    public BinNodeInterface<E> getLeftChild();

    /**
     * @return The right node of this node.
     */
    public BinNodeInterface<E> getRightChild();

    /**
     * @return true if this node is a leaf; otherwise return false.
     */
    public boolean isLeaf();
}
