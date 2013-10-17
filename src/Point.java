/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 12, 2013
 */
public class Point {
    private double x;
    private double y;

    /**
     * Create a new Point object.
     *
     * @param x
     * @param y
     */
    public Point(double x, double y) {
	this.x = x;
	this.y = y;
    }

    /**
     * @return the x coordinate
     */
    public double getX() {
	return this.x;
    }

    /**
     * @return the y coordinate
     */
    public double getY() {
	return this.y;
    }

    /**
     * @param x
     *            the new x coordinate
     */
    public void setX(double x) {
	this.x = x;
    }

    /**
     * @param y
     *            the new y coordinate
     */
    public void setY(double y) {
	this.y = y;
    }

    /**
     * Print out the x and y and in the format "(x, y) = (this.x, this.y)".
     */
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("(x, y) = ");
	stringBuilder.append("(" + this.x + ", " + this.y + ")");
	String pointInformation = stringBuilder.toString();
	return pointInformation;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	} if (obj == null) {
	    return false;
	} if (getClass() != obj.getClass()) {
	    return false;
	} Point other = (Point) obj;
	if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
	    return false;
	} if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
	    return false;
	}
	return true;
    }
}
