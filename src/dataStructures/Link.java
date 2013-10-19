package dataStructures;
/**
 * To view an interactive web page about array-based list implementation visit:
 * http://algoviz.org/OpenDSA/Books/CS3114PM/html/ListArray.html
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 1, 2013
 * @param <E>
 *            This link can hold any class.
 */
public class Link<E> {
    private E value; // for this node
    private Link<E> nextNode;

    /**
     * Create a new Link object with a value.
     *
     * @param value
     * @param nextNode
     */
    public Link(E value, Link<E> nextNode) {
	this.value = value;
	this.nextNode = nextNode;
    }

    /**
     * Create a new Link object without a value.
     *
     * @param nextNode
     */
    public Link(Link<E> nextNode) {
	this.value = null;
	this.nextNode = nextNode;
    }

    /**
     * @return The value for this Link.
     */
    public E getValue() {
	return this.value;
    }

    /**
     * @param value
     *            A new value for this Link.
     */
    public void setValue(E value) {
	this.value = value;
    }

    /**
     * @return The reference to the next node.
     */
    public Link<E> getNextNode() {
	return this.nextNode;
    }

    /**
     * @param nextNode
     *            A new reference to the next node.
     */
    public void setNextNode(Link<E> nextNode) {
	this.nextNode = nextNode;
    }
}
