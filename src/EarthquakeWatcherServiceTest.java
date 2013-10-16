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
public class EarthquakeWatcherServiceTest extends junit.framework.TestCase {
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
     * Test different add, query and delete commands.
     */
    public void test_processCommands() {
	ArrayList<String> commands1 = new ArrayList<String>();
	commands1.add("add	81	174	Tristen");

	this.EWS.processCommands(commands1);

//	assertEquals("Tristen 81.0 174.0 is added to the BST"
//		+ "\nTristen 81.0 174.0 is added to the bintree", outContent
//		.toString().trim());

	ArrayList<String> commands2 = new ArrayList<String>();
	commands2.add("delete	Tristen");

	this.EWS.processCommands(commands2);
	assertEquals("\nTristen is added to the watchers list"
		+ "\nNo record on MaxHeap"
		+ "\nTristen is removed from the watchers list", outContent
		.toString().trim());

	ArrayList<String> commands3 = new ArrayList<String>();
	commands3.add("add -105.7 -24.3 Riley");
	commands3.add("add 21.2 -38.6 Taylor");
	commands3.add("debug");
	this.EWS.processCommands(commands3);
	assertEquals("\nTristen is added to the watchers list"
		+ "\nNo record on MaxHeap"
		+ "\nTristen is removed from the watchers list", outContent
		.toString().trim());

	// clean up stream
	System.setOut(null);
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
	assertEquals(278, this.EWS.getLongitude(command1));

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
	assertEquals(216, this.EWS.getLatitude(command1));

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
	Watcher watcher = new Watcher("Sam", 5, 8);
	this.EWS.processWatcherAddRequest(watcher);
	assertEquals("Sam -175.0 -82.0 is added to the BST"
		+ "\nSam -175.0 -82.0 is added to the bintree", outContent
		.toString().trim());

	this.EWS.processWatcherAddRequest(watcher);
	assertEquals("Sam -175.0 -82.0 is added to the BST"
		+ "\nSam -175.0 -82.0 is added to the bintree", outContent
		.toString().trim());

	// clean up stream
	System.setOut(null);
    }
}
