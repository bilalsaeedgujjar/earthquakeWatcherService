
/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 15, 2013
 */
public class Watcher implements HasName {
    private String name;
    private int longitude;
    private int latitude;

    public Watcher(String name, int longitude, int latitude) {
	this.name = name;
	this.longitude = longitude;
	this.latitude = latitude;
    }

    @Override
    public String getName() {
	return this.name;
    }

    public int getLongitude() {
	return this.longitude;
    }

    public int getLatitude() {
	return this.latitude;
    }
}
