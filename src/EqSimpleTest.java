import java.util.ArrayList;
import realtimeweb.earthquakeservice.domain.Coordinate;
import realtimeweb.earthquakeservice.domain.Earthquake;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import realtimeweb.earthquakeservice.exceptions.EarthquakeException;
import realtimeweb.earthquakewatchers.WatcherParseException;

/**
 * Tests all logic within class EqSimple.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 17, 2013
 */
public class EqSimpleTest extends junit.framework.TestCase {

    private final ByteArrayOutputStream outContent =
	    new ByteArrayOutputStream();

    public void setUp() {
	new EqSimple();
	EqSimple.setUpDataStructures();

	// set up stream
	System.setOut(new PrintStream(outContent));
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
	// check for the following 2 different possible commands
	String[] args1 = { "--all", "watcher.txt", "normal.earthquakes.json" };

	EqSimple.main(args1);
    }

    /**
     * Check all different possible command line arguments.
     *
     * @throws IOException
     * @throws WatcherParseException
     * @throws EarthquakeException
     */
    public void test_checkForOptionalCommandLineArguments() throws IOException,
	    WatcherParseException, EarthquakeException {
	String[] args1 = { "--all", "watcher.txt", "normal.earthquakes" };
	EqSimple.checkForOptionalCommandLineArguments(args1);
	assertTrue(EqSimple.allParameterGiven);

	EqSimple.allParameterGiven = false;
	String[] args2 = { "--all", "watcher.txt", "live" };
	EqSimple.checkForOptionalCommandLineArguments(args2);
	assertTrue(EqSimple.allParameterGiven);

	EqSimple.allParameterGiven = false;
	String[] args3 = { "watcher.txt", "normal.earthquakes" };
	EqSimple.checkForOptionalCommandLineArguments(args3);
	assertFalse(EqSimple.allParameterGiven);

	EqSimple.allParameterGiven = false;
	String[] args4 = { "watcher.txt", "live" };
	EqSimple.checkForOptionalCommandLineArguments(args4);
	assertFalse(EqSimple.allParameterGiven);
    }

    /**
     * Test retrieval of earthquake file name for different formats of command
     * line arguments.
     */
    public void test_getEarthquakeFileName() {
	String[] args1 = { "--all", "watcher.txt", "live" };
	try {
	    EqSimple.getEarthquakeFileName(args1);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method getEarthquakeFileName of class EqSimple"
		    + "the commands state there is no earthquake file "
		    + "and that the program should instead be run live",
		    expected.getMessage());
	}

	String[] args2 = { "--all", "watcher.txt", "normal.earthquakes" };
	assertEquals("normal.earthquakes",
		EqSimple.getEarthquakeFileName(args2));

	String[] args3 = { "watcher.txt", "normal.earthquakes" };
	assertEquals("normal.earthquakes",
		EqSimple.getEarthquakeFileName(args3));

	String[] args4 = { "watcher.txt", "live" };
	try {
	    EqSimple.getEarthquakeFileName(args4);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method getEarthquakeFileName of class EqSimple"
		    + "the commands state there is no earthquake file "
		    + "and that the program should instead be run live",
		    expected.getMessage());
	}

	String[] args5 = { "--all", "watcher.txt", "normal.earthquakes",
		"extraCommand" };
	try {
	    EqSimple.getEarthquakeFileName(args5);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method getEarthquakeFileName of class EqSimple"
		    + "the given commands are invalid", expected.getMessage());
	}
    }

    /**
     * Test retrieval of watcher file name for different formats of command line
     * arguments.
     */
    public void test_getWatcherFileName() {
	String[] args1 = { "--all", "watcher.txt", "live" };
	assertEquals("watcher.txt", EqSimple.getWatcherFileName(args1));

	String[] args2 = { "--all", "watcher.txt", "normal.earthquakes" };
	assertEquals("watcher.txt", EqSimple.getWatcherFileName(args2));

	String[] args3 = { "watcher.txt", "normal.earthquakes" };
	assertEquals("watcher.txt", EqSimple.getWatcherFileName(args3));

	String[] args4 = { "watcher.txt", "live" };
	assertEquals("watcher.txt", EqSimple.getWatcherFileName(args4));

	String[] args5 = { "--all", "watcher.txt", "normal.earthquakes",
		"extraCommand" };
	try {
	    EqSimple.getWatcherFileName(args5);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method getWatcherFileName of class EqSimple"
		    + "the given commands are invalid", expected.getMessage());
	}
    }

    /**
     * Test different add, query and delete commands.
     */
    public void test_processCommands() {
	ArrayList<String> commands1 = new ArrayList<String>();
	commands1.add("add	81	174	Tristen");
	commands1.add("query");

	ArrayList<String> commands2 = new ArrayList<String>();
	commands2.add("delete	Tristen");

	EqSimple.processCommands(commands1);

	assertEquals("\nTristen is added to the watchers list"
		+ "\nNo record on MaxHeap", outContent.toString());

	EqSimple.processCommands(commands2);
	assertEquals("\nTristen is added to the watchers list"
		+ "\nNo record on MaxHeap"
		+ "\nTristen is removed from the watchers list",
		outContent.toString());

	// clean up stream
	System.setOut(null);
    }

    /**
     * Test retrieving watcher name on all different possibilities of commands.
     */
    public void test_getWatcherName() {
	String command1 = "add	278	216	Riley";
	assertEquals("Riley", EqSimple.getWatcherName(command1));

	String command2 = "delete	Brooklyn";
	assertEquals("Brooklyn", EqSimple.getWatcherName(command2));

	try {
	    String command3 = "query";
	    EqSimple.getWatcherName(command3);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method getWatcherName of class EqSimple"
		    + " you cannot call this method on a query command",
		    expected.getMessage());
	}
    }

    /**
     * Test retrieving longitude on all different possibilities of commands.
     */
    public void test_getLongitude() {
	String command1 = "add	278	216	Riley";
	assertEquals(278, EqSimple.getLongitude(command1));

	try {
	    String command2 = "delete	Brooklyn";
	    EqSimple.getLongitude(command2);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method getLongitude of class EqSimple"
		    + "the command in the parameter does not have a "
		    + "longitude", expected.getMessage());
	}

	try {
	    String command3 = "query";
	    EqSimple.getLongitude(command3);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method getLongitude of class EqSimple"
		    + "the command in the parameter does not have a "
		    + "longitude", expected.getMessage());
	}
    }

    /**
     * Test retrieving latitude on all different possibilities of commands.
     */
    public void test_getLatitude() {
	String command1 = "add	278	216	Riley";
	assertEquals(216, EqSimple.getLatitude(command1));

	try {
	    String command2 = "delete	Brooklyn";
	    EqSimple.getLatitude(command2);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method getLatitude of class EqSimple"
		    + "the command in the parameter does not have a "
		    + "latitude", expected.getMessage());
	}

	try {
	    String command3 = "query";
	    EqSimple.getLatitude(command3);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method getLatitude of class EqSimple"
		    + "the command in the parameter does not have a "
		    + "latitude", expected.getMessage());
	}
    }

    /**
     * Assert correct output was printed to console.
     */
    public void test_processWatcherAddRequest() {
	Watcher watcher = new Watcher("Sam", 5, 8);
	EqSimple.processWatcherAddRequest(watcher);
	assertEquals("Sam is added to the watchers list",
		outContent.toString());

	// clean up stream
	System.setOut(null);
    }

    /**
     * Assert correct output was printed to console.
     */
    public void test_processWatcherDeleteRequest() {
	Watcher watcher1 = new Watcher("Byron", 5, 8);
	Watcher watcher2 = new Watcher("Jeff Hawkins", 13, 21);
	Watcher watcher3 = new Watcher("Bill Gate", 34, 55);

	EqSimple.processWatcherAddRequest(watcher1);
	EqSimple.processWatcherAddRequest(watcher2);
	EqSimple.processWatcherDeleteRequest(watcher1);
	assertEquals("\nByron is added to the watchers list"
		+ "\nJeff Hawkins is added to the watchers list"
		+ "\nByron is removed from the watchers list",
		outContent.toString());

	try {
	    EqSimple.processWatcherDeleteRequest(watcher3);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method processWatcherDeleteRequest of class EqSimple"
			    + "the given watcher is not in the queue or heap",
		    expected.getMessage());
	}

	// clean up stream
	System.setOut(null);
    }

    /**
     * Assert correct output was printed to console.
     */
    public void test_printLargestRecentEarthquake() {
	EqSimple.printLargestRecentEarthquake();
	assertEquals("\nNo record on MaxHeap", outContent.toString());

	// add a few earthquakes with different magnitudes
	Earthquake earthquakeWithMagnitude10 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 10.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);

	Earthquake earthquakeWithMagnitude20 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 20.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);

	Earthquake earthquakeWithMagnitude30 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 30.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);

	Earthquake earthquakeWithMagnitude40 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 40.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);

	Earthquake earthquakeWithMagnitude50 = new Earthquake(new Coordinate(
		1.0, 1.0, 1.0), 50.0, "San Fran", 1000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);

	EarthquakeNodeAwareOfHeapIndex earthquakeNode1 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude10, 0);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode2 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude20, 1);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode3 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude30, 2);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode4 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude40, 3);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode5 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude50, 4);
	EqSimple.maxHeapOfRecentEarthquakes.insert(earthquakeNode1);
	EqSimple.maxHeapOfRecentEarthquakes.insert(earthquakeNode2);
	EqSimple.maxHeapOfRecentEarthquakes.insert(earthquakeNode3);
	EqSimple.maxHeapOfRecentEarthquakes.insert(earthquakeNode4);
	EqSimple.maxHeapOfRecentEarthquakes.insert(earthquakeNode5);

	EqSimple.printLargestRecentEarthquake();

	assertEquals("\nNo record on MaxHeap"
		+ "\nLargest earthquake in past 6 hours:"
		+ "\nMagnitude 50.0 at San Fran", outContent.toString());

	for (int i = 0; i < 5; i++) {
	    EqSimple.maxHeapOfRecentEarthquakes.removeMaximumValue();
	}

	EqSimple.printLargestRecentEarthquake();

	assertEquals("\nNo record on MaxHeap"
		+ "\nLargest earthquake in past 6 hours:"
		+ "\nMagnitude 50.0 at San Fran" + "\nNo record on MaxHeap",
		outContent.toString());

	// clean up stream
	System.setOut(null);
    }

    /**
     * Assert correct output was printed to console.
     */
    public void test_removeExpiredEarthquakesInQueueAndMaxHeap() {
	Earthquake earthquake1 = new Earthquake(new Coordinate(1.0, 1.0, 1.0),
		10.0, "San Fran", 10000, "www.walnutiq.com", 1, 1.0, 2.0,
		"red", "event", 1, "id", 3.0, 4.0, 5.0);

	Earthquake earthquake2 = new Earthquake(new Coordinate(1.0, 1.0, 1.0),
		20.0, "San Fran", 31600, "www.walnutiq.com", 1, 1.0, 2.0,
		"red", "event", 1, "id", 3.0, 4.0, 5.0);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode1 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquake1, 0);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode2 =
		new EarthquakeNodeAwareOfHeapIndex(
		earthquake2, 1);
	EqSimple.maxHeapOfRecentEarthquakes.insert(earthquakeNode1);
	EqSimple.maxHeapOfRecentEarthquakes.insert(earthquakeNode2);
	EqSimple.removeExpiredEarthquakesInQueueAndMaxHeap();
	assertEquals(20.0, EqSimple.maxHeapOfRecentEarthquakes
		.getMaximumValue().getEarthquake().getMagnitude());
    }

    /**
     * Assert that checking for duplicates in queue and max-heap method is
     * correct.
     */
    public void test_isNewEarthquakeInQueueAndHeap() {
	Earthquake earthquake1 = new Earthquake(new Coordinate(1.0, 1.0, 1.0),
		10.0, "San Fran", 10000, "www.walnutiq.com", 1, 1.0, 2.0,
		"red", "event", 1, "id", 3.0, 4.0, 5.0);

	Earthquake earthquake2 = new Earthquake(new Coordinate(1.0, 1.0, 1.0),
		20.0, "San Fran", 31600, "www.walnutiq.com", 1, 1.0, 2.0,
		"red", "event", 1, "id", 3.0, 4.0, 5.0);

	assertTrue(EqSimple.isNewEarthquakeInQueueAndHeap(earthquake1));
	assertEquals(10000, EqSimple.unixTimeOfEarliestQuake);
	assertFalse(EqSimple.isNewEarthquakeInQueueAndHeap(earthquake1));

	assertTrue(EqSimple.isNewEarthquakeInQueueAndHeap(earthquake2));
	assertEquals(31600, EqSimple.unixTimeOfEarliestQuake);
    }

    /**
     * Assert correct output was printed to console for when a new earthquake is
     * close to a watcher and when it is not close to a watcher.
     */
    public void test_updateRelevantWatchersOfNewEarthquakes() {
	// add 2 watchers to linked list where one is close enough
	// to the new earthquake to be alerted while the other is not
	Watcher closeByWatcher = new Watcher("Quinn", 10, 10);
	Watcher farAwayWatcher = new Watcher("AJ", 150, 100);

	EqSimple.linkedListWatcher.append(farAwayWatcher);
	EqSimple.linkedListWatcher.append(closeByWatcher);

	Earthquake earthquakeWithMagnitude4 = new Earthquake(new Coordinate(
		20.0, 30.0, 1.0), 4, "San Fran", 10000, "www.walnutiq.com", 1,
		1.0, 2.0, "red", "event", 1, "id", 3.0, 4.0, 5.0);

	EqSimple.updateRelevantWatchersOfNewEarthquake(earthquakeWithMagnitude4);

	// distance = ((longitude_earthquake - longitude_watcher)^2 +
	// (latitude_earthquake
	// - latitude_watcher)^2)^0.5 < 2 * Magnitude^3

	// if distance < 2 * Magnitude^3 is true check for closeBy print
	// statement

	// for closeByWatcher named Quinn
	// distance = (10^2 + 20^2)^0.5 < 2 * 4^3
	// = 22.3607 < 124

	// for farAwayWatcher name AJ
	// distance = (130^2 + 70^2)^0.5 < 2 * 4^3
	// = 147.6482 < 124
	assertEquals("\nEarthquake San Fran is close to Quinn",
		outContent.toString());

	// clean up stream
	System.setOut(null);
    }
}
