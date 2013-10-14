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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + latitude;
	result = prime * result + longitude;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Watcher other = (Watcher) obj;
	if (latitude != other.latitude)
	    return false;
	if (longitude != other.longitude)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }
}
