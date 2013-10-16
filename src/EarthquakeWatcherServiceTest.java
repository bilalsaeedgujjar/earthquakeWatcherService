import realtimeweb.earthquakeservice.domain.Coordinate;
import realtimeweb.earthquakeservice.domain.Earthquake;

import java.io.PrintStream;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Tests all logic within class EarthquakeWatcherService.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 15, 2013
 */
public class EarthquakeWatcherServiceTest extends student.TestCase {
    private EarthquakeWatcherService EWS;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    public void setUp() {
	String[] args = { "Wsmall2.txt", "EQsmallP2.json" };

	this.EWS = new EarthquakeWatcherService(args);

	// set up stream to test strings printed out to the console
	System.setOut(new PrintStream(outContent));
    }

    /**
     * Test retrieval of earthquake file name for different formats of command
     * line arguments.
     */
    public void test_getEarthquakeFileName() {
	assertEquals("EQsmallP2.json", this.EWS.getEarthquakeFileName());
    }

    /**
     * Test retrieval of watcher file name for different formats of command line
     * arguments.
     */
    public void test_getWatcherFileName() {
	assertEquals("Wsmall2.txt", this.EWS.getWatcherFileName());
    }

    /**
     * TODO: Test different add, query and delete commands.
     */
    public void test_processCommands() {
	ArrayList<String> commands1 = new ArrayList<String>();
	commands1.add("add -105.7 -24.3 Riley");

	this.EWS.processCommands(commands1);

	assertFuzzyEquals("Riley -105.7 -24.3 is added to the BST"
		+ "\nRiley -105.7 -24.3 is added to the bintree", outContent
		.toString().trim());

//	ArrayList<String> commands2 = new ArrayList<String>();
//	commands2.add("delete	Tristen");
//
//	this.EWS.processCommands(commands2);
//	assertFuzzyEquals("Tristen 81.0 174.0 is added to the BST"
//		+ "\nTristen 81.0 174.0 is added to the bintree"
//		+ "\nTristen 81.0 174.0 is removed from the BST"
//		+ "\nTristen 81.0 174.0 is removed from the bintree",
//		outContent.toString().trim());
//
//	ArrayList<String> commands3 = new ArrayList<String>();
//	commands3.add("add -105.7 -24.3 Riley");
//	commands3.add("add 21.2 -38.6 Taylor");
//	commands3.add("debug");
//	this.EWS.processCommands(commands3);
//	assertEquals("\nTristen is added to the watchers list"
//		+ "\nNo record on MaxHeap"
//		+ "\nTristen is removed from the watchers list", outContent
//		.toString().trim());
    }

    /**
     * Test retrieving watcher name on all different possibilities of commands.
     */
    public void test_getWatcherName() {
	String command1 = "add	278	216	Riley";
	assertEquals("Riley", this.EWS.getWatcherName(command1));

	String command2 = "delete	Brooklyn";
	assertEquals("Brooklyn", this.EWS.getWatcherName(command2));

	try {
	    String command3 = "query";
	    this.EWS.getWatcherName(command3);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method getWatcherName of class EarthquakeWatcherService"
			    + " you cannot call this method on a query command",
		    expected.getMessage());
	}
    }

    /**
     * Test retrieving longitude on all different possibilities of commands.
     */
    public void test_getLongitude() {
	String command1 = "add	278	216	Riley";
	assertEquals(278.0, this.EWS.getLongitude(command1), 0.01);

	try {
	    String command2 = "delete	Brooklyn";
	    this.EWS.getLongitude(command2);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method getLongitude of class EarthquakeWatcherService"
			    + " the command in the parameter does not have a "
			    + "longitude", expected.getMessage());
	}

	try {
	    String command3 = "query";
	    this.EWS.getLongitude(command3);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method getLongitude of class EarthquakeWatcherService"
			    + " the command in the parameter does not have a "
			    + "longitude", expected.getMessage());
	}

	try {
	    String command4 = "debug";
	    this.EWS.getLongitude(command4);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method getLongitude of class EarthquakeWatcherService"
			    + " the command in the parameter does not have a "
			    + "longitude", expected.getMessage());
	}
    }

    /**
     * Test retrieving latitude on all different possibilities of commands.
     */
    public void test_getLatitude() {
	String command1 = "add	278	216	Riley";
	assertEquals(216.0, this.EWS.getLatitude(command1), 0.01);

	try {
	    String command2 = "delete	Brooklyn";
	    this.EWS.getLatitude(command2);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method getLatitude of class EarthquakeWatcherService"
			    + " the command in the parameter does not have a "
			    + "latitude", expected.getMessage());
	}

	try {
	    String command3 = "query";
	    this.EWS.getLatitude(command3);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method getLatitude of class EarthquakeWatcherService"
			    + " the command in the parameter does not have a "
			    + "latitude", expected.getMessage());
	}

	try {
	    String command4 = "debug";
	    this.EWS.getLatitude(command4);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals(
		    "In method getLatitude of class EarthquakeWatcherService"
			    + " the command in the parameter does not have a "
			    + "latitude", expected.getMessage());
	}
    }

    /**
     * Assert correct output was printed to console.
     */
    public void test_processWatcherAddRequest() {
	Watcher watcher1 = new Watcher("Sam", 5, 8);
	assertTrue(this.EWS.processWatcherAddRequest(watcher1));
	assertFuzzyEquals("Sam -175.0 -82.0 is added to the BST"
		+ "\nSam -175.0 -82.0 is added to the bintree", outContent
		.toString().trim());

	assertFalse(this.EWS.processWatcherAddRequest(watcher1));
	assertFuzzyEquals("Sam -175.0 -82.0 is added to the BST"
		+ "\nSam -175.0 -82.0 is added to the bintree"
		+ "\nSam duplicates a watcher already in the BST", outContent
		.toString().trim());

	Watcher watcher2 = new Watcher("Quinn", 10, 16);
	assertTrue(this.EWS.processWatcherAddRequest(watcher2));
	assertFuzzyEquals("Sam -175.0 -82.0 is added to the BST"
		+ "\nSam -175.0 -82.0 is added to the bintree"
		+ "\nSam duplicates a watcher already in the BST"
		+ "\nQuinn -170.0 -74.0 is added to the BST"
		+ "\nQuinn -170.0 -74.0 is added to the bintree", outContent
		.toString().trim());

	Watcher watcher3 = new Watcher("Byron", 10, 16);
	assertFalse(this.EWS.processWatcherAddRequest(watcher3));
	assertFuzzyEquals("Sam -175.0 -82.0 is added to the BST"
		+ "\nSam -175.0 -82.0 is added to the bintree"
		+ "\nSam duplicates a watcher already in the BST"
		+ "\nQuinn -170.0 -74.0 is added to the BST"
		+ "\nQuinn -170.0 -74.0 is added to the bintree"
		+ "\nByron -170.0 -74.0 is added to the BST"
		+ "\n-170.0 -74.0 duplicates a watcher already in the bintree"
		+ "\nByron is removed from the BST", outContent.toString()
		.trim());
    }

    /**
     * Assert correct output was printed to console.
     */
    public void test_processWatcherDeleteRequest() {
	Watcher watcher1 = new Watcher("Byron", 5, 8);
	Watcher watcher2 = new Watcher("Jeff Hawkins", 13, 21);

	assertTrue(this.EWS.processWatcherAddRequest(watcher1));
	assertTrue(this.EWS.processWatcherAddRequest(watcher2));
	assertTrue(this.EWS.processWatcherDeleteRequest(watcher1.getName()));
	assertFuzzyEquals("Byron -175.0 -82.0 is added to the BST"
		+ "\nByron -175.0 -82.0 is added to the bintree"
		+ "\nJeff Hawkins -167.0 -69.0 is added to the BST"
		+ "\nJeff Hawkins -167.0 -69.0 is added to the bintree"
		+ "\nByron -175.0 -82.0 is removed from the BST"
		+ "\nByron -175.0 -82.0 is removed from the bintree",
		outContent.toString().trim());

	assertFalse(this.EWS.processWatcherDeleteRequest(watcher1.getName()));
	assertFuzzyEquals("Byron -175.0 -82.0 is added to the BST"
		+ "\nByron -175.0 -82.0 is added to the bintree"
		+ "\nJeff Hawkins -167.0 -69.0 is added to the BST"
		+ "\nJeff Hawkins -167.0 -69.0 is added to the bintree"
		+ "\nByron -175.0 -82.0 is removed from the BST"
		+ "\nByron -175.0 -82.0 is removed from the bintree"
		+ "\nByron does not appear in the BST", outContent.toString()
		.trim());
    }

    /**
     * Assert correct output was printed to console.
     */
    public void test_printLargestRecentEarthquake() {
	this.EWS.printLargestRecentEarthquake();
	assertEquals("No record on MaxHeap", outContent.toString().trim());

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

	EarthquakeNodeAwareOfHeapIndex earthquakeNode1 = new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude10, 0);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode2 = new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude20, 1);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode3 = new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude30, 2);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode4 = new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude40, 3);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode5 = new EarthquakeNodeAwareOfHeapIndex(
		earthquakeWithMagnitude50, 4);
	this.EWS.getMaxHeapOfRecentEarthquakes().insert(earthquakeNode1);
	this.EWS.getMaxHeapOfRecentEarthquakes().insert(earthquakeNode2);
	this.EWS.getMaxHeapOfRecentEarthquakes().insert(earthquakeNode3);
	this.EWS.getMaxHeapOfRecentEarthquakes().insert(earthquakeNode4);
	this.EWS.getMaxHeapOfRecentEarthquakes().insert(earthquakeNode5);

	this.EWS.printLargestRecentEarthquake();

	assertFuzzyEquals("No record on MaxHeap"
		+ "\nLargest earthquake in past 6 hours:"
		+ "\nMagnitude 50.0 at San Fran", outContent.toString().trim());

	for (int i = 0; i < 5; i++) {
	    this.EWS.getMaxHeapOfRecentEarthquakes().removeMaximumValue();
	}

	this.EWS.printLargestRecentEarthquake();

	assertFuzzyEquals("No record on MaxHeap"
		+ "\nLargest earthquake in past 6 hours:"
		+ "\nMagnitude 50.0 at San Fran" + "\nNo record on MaxHeap",
		outContent.toString().trim());
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
	EarthquakeNodeAwareOfHeapIndex earthquakeNode1 = new EarthquakeNodeAwareOfHeapIndex(
		earthquake1, 0);
	EarthquakeNodeAwareOfHeapIndex earthquakeNode2 = new EarthquakeNodeAwareOfHeapIndex(
		earthquake2, 1);
	this.EWS.getMaxHeapOfRecentEarthquakes().insert(earthquakeNode1);
	this.EWS.getMaxHeapOfRecentEarthquakes().insert(earthquakeNode2);
	this.EWS.removeExpiredEarthquakesInQueueAndMaxHeap();
	assertEquals(20.0, this.EWS.getMaxHeapOfRecentEarthquakes()
		.getMaximumValue().getEarthquake().getMagnitude(), 0.01);
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

	assertTrue(this.EWS.isNewEarthquakeInQueueAndHeap(earthquake1));
	assertEquals(10000, this.EWS.getUnixTimeOfEarliestQuake());
	assertFalse(this.EWS.isNewEarthquakeInQueueAndHeap(earthquake1));

	assertTrue(this.EWS.isNewEarthquakeInQueueAndHeap(earthquake2));
	assertEquals(31600, this.EWS.getUnixTimeOfEarliestQuake());
    }
}
