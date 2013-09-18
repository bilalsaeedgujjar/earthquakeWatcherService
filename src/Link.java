

/**
 * To view an interactive web page about array-based list implementation visit:
 * http://algoviz.org/OpenDSA/Books/CS3114PM/html/ListArray.html
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 1, 2013
 */
class Link<E> {
    private E value; // for this node
    private Link<E> nextNode;

    Link(E value, Link<E> nextNode) {
	this.value = value;
	this.nextNode = nextNode;
    }

    Link(Link<E> nextNode) {
	this.value = null;
	this.nextNode = nextNode;
    }

    E getValue() {
	return this.value;
    }

    void setValue(E value) {
	this.value = value;
    }

    Link<E> getNextNode() {
	return this.nextNode;
    }

    void setNextNode(Link<E> nextNode) {
	this.nextNode = nextNode;
    }
}
