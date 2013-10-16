import java.text.DecimalFormat;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 15, 2013
 */
public class Watcher implements HasName {
    private String name;
    private double longitude;
    private double latitude;

    private DecimalFormat df = new DecimalFormat("#.0");

    /**
     * Create a new Watcher object.
     *
     * @param name
     * @param longitude
     * @param latitude
     */
    public Watcher(String name, double longitude, double latitude) {
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
    public double getLongitude() {
	return this.longitude;
    }

    /**
     * @return Latitude of watcher.
     */
    public double getLatitude() {
	return this.latitude;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(latitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(longitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
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
	if (Double.doubleToLongBits(latitude) != Double
		.doubleToLongBits(other.latitude))
	    return false;
	if (Double.doubleToLongBits(longitude) != Double
		.doubleToLongBits(other.longitude))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	double longitude = this.longitude - 180.0;
	double latitude = this.latitude - 90.0;
	return this.name + " " + this.df.format(longitude) + " " +
		this.df.format(latitude);
    }
}
