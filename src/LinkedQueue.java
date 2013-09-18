

/**
 * To view an interactive web page about linked queue implementation visit:
 * http://algoviz.org/OpenDSA/Books/CS3114PM/html/Queue.html
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 1, 2013
 */
public class LinkedQueue<E> implements QueueInterface<E> {
    private Link<E> front;
    private Link<E> rear;
    private int size;

    /**
     * Create a new LinkedQueue object.
     */
    public LinkedQueue() {
	this.init();
    }

    private void init() {
	// only one object is constructed
	this.front = this.rear = new Link<E>(null);
	this.size = 0;
    }

    @Override
    public void enqueue(E value) {
	this.rear.setNextNode(new Link<E>(value, null));
	this.rear = this.rear.getNextNode();
	this.size++;
    }

    @Override
    public E dequeue() {
	if (this.size == 0) {
	    throw new IllegalStateException("In method dequeue of class "
		    + "LinkedQueue the linked queue is empty can cannot"
		    + " be dequeued");
	}

	E item = this.front.getNextNode().getValue();
	this.front.setNextNode(this.front.getNextNode().getNextNode());

	if (this.front.getNextNode() == null) {
	    this.rear = this.front;
	}
	this.size--;
	return item;
    }

    @Override
    public E frontValue() {
	if (this.size == 0) {
	    throw new IllegalStateException("In method frontValue of class "
		    + "LinkedQueue the linked queue is empty");
	}
	return this.front.getNextNode().getValue();
    }

    @Override
    public int length() {
	return this.size;
    }

    @Override
    public void clear() {
	this.init();
    }

    /**
     * Creates a easy to read String representation of the linked queue's
     * contents.
     *
     * Example: < 1 2 3 4 5 6 >
     */
    public String toString() {
	StringBuilder linkedQueueAsString = new StringBuilder();
	linkedQueueAsString.append("< ");
	System.out.println("front.getNextNode: " + this.front.getNextNode());
	for (Link<E> node = this.front.getNextNode(); node != null; node = node.getNextNode()) {
	    linkedQueueAsString.append(node.getValue());
	    System.out.println(node.getValue() + ",");
	    linkedQueueAsString.append(" ");
	}
	linkedQueueAsString.append(">");
	return linkedQueueAsString.toString();
    }
}
