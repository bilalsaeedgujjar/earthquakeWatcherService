import java.io.IOException;
import realtimeweb.earthquakeservice.exceptions.EarthquakeException;
import realtimeweb.earthquakewatchers.WatcherParseException;

/**
 * Tests all logic within class EqSpatial.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 15, 2013
 */
public class EqSpatialTest extends junit.framework.TestCase {
    public void setUp() {
	new EqSpatial();
    }

    /**
     * Test different outputs to console.
     *
     * @throws IOException
     * @throws WatcherParseException
     * @throws EarthquakeException
     */
    public void test_main() throws IOException, WatcherParseException,
	    EarthquakeException {
	String[] args = { "Wsmall2.txt", "EQsmallP2.json" };

	EqSpatial.main(args);
	// TODO: anything else ?
    }
}
