package dataStructures;
/**
 * List abstract data type. To learn more about this interface please visit:
 * http://algoviz.org/OpenDSA/Books/CS3114PM/html/ListADT.html
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 15, 2013
 * @param <E>
 *            The type of element to be held by list.
 */
public interface ListInterface<E> {
    /**
     * Insert an element behind the current position. Must check that the linked
     * list's capacity is not exceeded.
     *
     * @param item
     *            Generic object to be inserted.
     */
    public void insert(E item);

    /**
     * Add an element to the end of the list.
     *
     * @param item
     *            Generic object ot be appended.
     */
    public void append(E item);

    /**
     * Remove the element after the current element and return the value of the
     * removed element.
     *
     * @return Element at current position within the list.
     */
    public E remove();

    /**
     * Remove all contents from the list.
     */
    public void clear();

    /**
     * Move current position to the head of the list.
     */
    public void moveToStart();

    /**
     * Move current position to the tail of the list.
     */
    public void moveToEnd();

    /**
     * Move the current position one element before. No change if already at the
     * beginning.
     *
     * @return True if current position was moved to previous element; otherwise
     *         return false.
     */
    public boolean previous();

    /**
     * Move the current position one element after. No change if already at the
     * end.
     *
     * @return True if current position was moved to next element; otherwise
     *         return false.
     */
    public boolean next();

    /**
     * @return The number of items in the list.
     */
    public int length();

    /**
     * @return The index where the current position reference is pointing to.
     */
    public int currentPosition();

    /**
     * @param position
     *            The index within the list to move the current position.
     */
    public void moveCurrentNodeToPosition(int position);

    /**
     * @return The current item in the current position.
     */
    public E getValue();
}
