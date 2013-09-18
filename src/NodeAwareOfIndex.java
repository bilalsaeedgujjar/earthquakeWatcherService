

import realtimeweb.earthquakeservice.domain.Earthquake;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 15, 2013
 */
public interface NodeAwareOfIndex {

    public void setIndexWithinHeapArray(int indexWithinHeapArray);

    public int getIndexWithinHeapArray();
}
