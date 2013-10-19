package dataStructures;
/**
 * Queue abstract data type.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 1, 2013
 * @param <E> Generic class.
 */
public interface QueueInterface<E> {
    /**
     * Add an item to the read of the queue.
     * @param item Item to be added to queue in the back.
     */
    public void enqueue(E item);

    /**
     * Remove and return the item at the front of the queue.
     * @return Element at the front of the queue.
     */
    public E dequeue();

    /**
     * @return Element at the front of the queue.
     */
    public E frontValue();

    /**
     * @return How many elements are in the queue.
     */
    public int length();

    /**
     * Remove all the items in the queue.
     */
    public void clear();
}
