/**
 * Interface for nodes within an array based heap to update their index.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 15, 2013
 */
public interface NodeAwareOfIndex {

    /**
     * @param indexWithinHeapArray
     *            New index of node within array based heap.
     */
    public void setIndexWithinHeapArray(int indexWithinHeapArray);

    /**
     * @return Index of node within array based heap.
     */
    public int getIndexWithinHeapArray();
}
