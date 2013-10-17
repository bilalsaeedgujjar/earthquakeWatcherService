/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 12, 2013
 * @param <K>
 *            A class with an x and y field to be used to efficiently store and
 *            find an Element into the BinTree.
 * @param <E>
 *            The type to be stored in the BinTree.
 */
public class BinTreeLeafNode<K extends Point, E> extends
	BinTreeNode<E> {
    private K key;
    private E element;

    /**
     * Create a new BinTreeLeafNode object.
     *
     * @param key
     * @param element
     */
    public BinTreeLeafNode(K key, E element) {
	this.key = key;
	this.element = element;
    }

    /**
     * @return the key stored in this bin tree leaf node
     */
    public K getKey() {
	return this.key;
    }

    /**
     * @return the element stored in this bin tree leaf node.
     */
    public E getElement() {
	return this.element;
    }

    /**
     * @param element
     *            the new element being stored in this leaf node.
     */
    public void setElement(E element) {
	this.element = element;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append(this.element.toString());
	String binTreeInformation = stringBuilder.toString();
	return binTreeInformation;
    }
}
