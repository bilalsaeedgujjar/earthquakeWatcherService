

/**
 * @version Oct 2, 2013
 */
public interface BinNodeInterface<E> {
    public E getValue();
    public void setValue(E value);

    public BinNodeInterface<E> getLeftChild();

    public BinNodeInterface<E> getRightChild();

    public boolean isLeaf();
}
