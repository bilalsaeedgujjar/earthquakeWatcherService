

/**
 * List abstract data type. To learn more about this interface please visit:
 * http://algoviz.org/OpenDSA/Books/CS3114PM/html/ListADT.html
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 15, 2013
 */
public interface ListInterface<E> {
    /**
     * Insert an element behind the current position. Must check that the linked
     * list's capacity is not exceeded.
     */
    public void insert(E item);

    public void append(E item);

    /**
     * Remove the element after the current element and return the value of the
     * removed element.
     */
    public E remove();

    /**
     * Remove all contents from the list.
     */
    public void clear();

    public void moveToStart();

    public void moveToEnd();

    /**
     * Move the current position one element before. No change if already at the
     * beginning.
     */
    public boolean previous();

    /**
     * Move the current position one element after. No change if already at the
     * end.
     */
    public boolean next();

    /**
     * @return The number of items in the list.
     */
    public int length();

    public int currentPosition();

    public void moveCurrentNodeToPosition(int position);

    /**
     * @return The current item in the current position.
     */
    public E getValue();
}
