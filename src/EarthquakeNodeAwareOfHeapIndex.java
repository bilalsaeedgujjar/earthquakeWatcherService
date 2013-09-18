import realtimeweb.earthquakeservice.domain.Earthquake;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 15, 2013
 */
public class EarthquakeNodeAwareOfHeapIndex implements
	Comparable<EarthquakeNodeAwareOfHeapIndex>, NodeAwareOfIndex {
    private int indexWithinHeapArray;
    private Earthquake earthquake;

    public EarthquakeNodeAwareOfHeapIndex(Earthquake earthquake,
	    int indexWithinHeapArray) {
	this.indexWithinHeapArray = indexWithinHeapArray;
	this.earthquake = earthquake;
    }

    @Override
    public void setIndexWithinHeapArray(int indexWithinHeapArray) {
	this.indexWithinHeapArray = indexWithinHeapArray;
    }

    @Override
    public int getIndexWithinHeapArray() {
	return this.indexWithinHeapArray;
    }

    public Earthquake getEarthquake() {
	return this.earthquake;
    }

    @Override
    public int compareTo(EarthquakeNodeAwareOfHeapIndex otherEarthquakeNode) {
	if (this.earthquake.getMagnitude() < otherEarthquakeNode
		.getEarthquake().getMagnitude()) {
	    return -1;
	} else if (this.earthquake.getMagnitude() == otherEarthquakeNode
		.getEarthquake().getMagnitude()) {
	    return 0;
	} else { // earthquake.getMagnitude() > otherEarthquake.getMagnitude()
	    return 1;
	}
    }
}
