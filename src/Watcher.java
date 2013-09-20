/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 15, 2013
 */
public class Watcher implements HasName {
    private String name;
    private int longitude;
    private int latitude;

    /**
     * Create a new Watcher object.
     * @param name
     * @param longitude
     * @param latitude
     */
    public Watcher(String name, int longitude, int latitude) {
	this.name = name;
	this.longitude = longitude;
	this.latitude = latitude;
    }

    @Override
    public String getName() {
	return this.name;
    }

    /**
     * @return Longitude of watcher.
     */
    public int getLongitude() {
	return this.longitude;
    }

    /**
     * @return Latitude of watcher.
     */
    public int getLatitude() {
	return this.latitude;
    }
}
