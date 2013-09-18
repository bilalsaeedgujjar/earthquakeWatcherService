
/**
 * If you have a good idea of how many elements will be in your list that does
 * not change very much you should instead use an array based singly linked
 * list. On the other hand, if you have no idea how many elements you will add
 * to your list and removed from your list use a linked list of nodes as
 * implemented in this file.
 *
 * To view an interactive web page about linked list implementation visit:
 * http://algoviz.org/OpenDSA/Books/CS3114PM/html/ListArray.html
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 12, 2013
 */
public class NamedSinglyLinkedList<E extends HasName> implements
	ListInterface<E> {
    private Link<E> head;
    private Link<E> tail;
    protected Link<E> currentNode;
    private int size;

    public NamedSinglyLinkedList() {
	this.head = this.tail = this.currentNode = new Link<E>(null);
	this.size = 0;
    }

    @Override
    public void insert(E item) {
	this.currentNode.setNextNode(new Link<E>(this.currentNode.getValue(),
		this.currentNode.getNextNode()));
	this.currentNode.setValue(item);

	if (this.tail == this.currentNode) {
	    this.tail = this.currentNode.getNextNode();
	}
	this.size++;
    }

    @Override
    public void append(E item) {
	this.tail.setNextNode(new Link<E>(null));
	this.tail.setValue(item);
	// the current tail is no longer the tail so the
	// current tail must be changed to the link that was
	// just appended
	this.tail = this.tail.getNextNode();
	this.size++;
    }

    /**
     * Removes and returns current current node.
     */
    @Override
    public E remove() {
	if (this.currentNode.getNextNode() == null) {
	    // nothing to remove
	    return null;
	}

	// save value before deleting to be returned at end of method
	E item = this.currentNode.getValue();
	this.currentNode.setValue(this.currentNode.getNextNode().getValue());

	// in the case where the node to be removed is immediately
	// before the tail node
	if (this.tail == this.currentNode.getNextNode()) {
	    this.tail = this.currentNode;
	}

	// removes the node after the current node
	this.currentNode.setNextNode(this.currentNode.getNextNode()
		.getNextNode());
	this.size--;
	return item;
    }

    @Override
    public void clear() {
	this.head.setNextNode(null);
	this.currentNode = this.tail = this.head = new Link<E>(null);
	this.size = 0;
    }

    @Override
    public void moveToStart() {
	this.currentNode = this.head;
    }

    @Override
    public void moveToEnd() {
	this.currentNode = this.tail;
    }

    @Override
    public boolean previous() {
	if (this.currentNode == this.head) {
	    return false;
	}
	Link<E> tempNode = this.head;
	// iterate through singly linked list until you find the
	// previous node
	while (tempNode.getNextNode() != this.currentNode) {
	    tempNode = tempNode.getNextNode();
	}
	this.currentNode = tempNode;
	return true;
    }

    @Override
    public boolean next() {
	if (this.currentNode != this.tail) {
	    this.currentNode = this.currentNode.getNextNode();
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public int length() {
	return this.size;
    }

    @Override
    public int currentPosition() {
	Link<E> tempNode = this.head;
	int currentNodePostion;
	for (currentNodePostion = 0; this.currentNode != tempNode; currentNodePostion++) {
	    tempNode = tempNode.getNextNode();
	}
	return currentNodePostion;
    }

    @Override
    public void moveCurrentNodeToPosition(int position) {
	if (position < 0 || position > this.size) {
	    throw new IllegalArgumentException(
		    "In method moveToPosition of class "
			    + "SinglyLinkedList the input node postion to be "
			    + "removed is out of bounds");
	}
	this.currentNode = this.head;
	for (int i = 0; i < position; i++) {
	    this.currentNode = this.currentNode.getNextNode();
	}
    }

    @Override
    public E getValue() {
	if (this.currentNode == this.tail) {
	    return null;
	}
	return this.currentNode.getValue();
    }

    public boolean isAtEnd() {
	if (this.currentNode == this.tail) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * @return Correct position within linked list or -1 if value is not found
     *         within linked list.
     */
    public int findValuePosition(E item) {
	// move the current position of the linked list back to the original
	// position before this method was called
	int currentPosition = this.currentPosition();

	// counter to track where the found value is within the linked list
	int foundValuePosition = 0;

	// begin searching for item within the linked list from the beginning
	this.moveToStart();
	while (!this.isAtEnd()) {
	    if (this.currentNode.getValue().getName().equals(item.getName())) {
		this.moveCurrentNodeToPosition(currentPosition);
		return foundValuePosition;
	    } else {
		foundValuePosition++;
	    }
	    this.next();
	}

	this.moveCurrentNodeToPosition(currentPosition);
	return -1;
    }

    /**
     * Creates a easy to read String representation of the singly linked lists
     * contents.
     *
     * Example 1: < 1 2 3 4 | 5 6 >
     *
     * The vertical bar = the link immediately after the current node.
     *
     * @author Clifford A. Shaffer
     */
    public String toString() {
	int oldPosition = this.currentPosition();
	int length = this.length();
	StringBuffer linkedListAsString = new StringBuffer((length() + 1) * 4);

	this.moveToStart();
	linkedListAsString.append("< ");
	for (int i = 0; i < oldPosition; i++) {
	    linkedListAsString.append(this.getValue().getName());
	    linkedListAsString.append(" ");
	    this.next();
	}
	linkedListAsString.append("| ");
	for (int i = oldPosition; i < length; i++) {
	    linkedListAsString.append(this.getValue().getName());
	    linkedListAsString.append(" ");
	    this.next();
	}
	linkedListAsString.append(">");
	this.moveCurrentNodeToPosition(oldPosition);

	return linkedListAsString.toString();
    }
}
