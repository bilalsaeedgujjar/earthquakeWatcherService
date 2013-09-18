

/**
 * Queue abstract data type.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 1, 2013
 */
public interface QueueInterface<E> {
    /**
     * Add an item to the read of the queue.
     */
    public void enqueue(E item);

    /**
     * Remove and return the item at the front of the queue.
     */
    public E dequeue();

    /**
     * Return the item at the front of the queue.
     */
    public E frontValue();

    /**
     * Return how many elements are in the queue.
     */
    public int length();

    /**
     * Remove all the items in the queue.
     */
    public void clear();
}
