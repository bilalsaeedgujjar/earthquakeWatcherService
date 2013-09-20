/**
 * To view an interactive web page about array-based list implementation visit:
 * http://algoviz.org/OpenDSA/Books/CS3114PM/html/ListArray.html
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 1, 2013
 * @param <E>
 *            This link can hold any class.
 */
class Link<E> {
    private E value; // for this node
    private Link<E> nextNode;

    /**
     * Create a new Link object with a value.
     *
     * @param value
     * @param nextNode
     */
    Link(E value, Link<E> nextNode) {
	this.value = value;
	this.nextNode = nextNode;
    }

    /**
     * Create a new Link object without a value.
     *
     * @param nextNode
     */
    Link(Link<E> nextNode) {
	this.value = null;
	this.nextNode = nextNode;
    }

    /**
     * @return The value for this Link.
     */
    E getValue() {
	return this.value;
    }

    /**
     * @param value
     *            A new value for this Link.
     */
    void setValue(E value) {
	this.value = value;
    }

    /**
     * @return The reference to the next node.
     */
    Link<E> getNextNode() {
	return this.nextNode;
    }

    /**
     * @param nextNode
     *            A new reference to the next node.
     */
    void setNextNode(Link<E> nextNode) {
	this.nextNode = nextNode;
    }
}
