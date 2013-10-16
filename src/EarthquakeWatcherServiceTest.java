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
	commands1.add("query");

	this.EWS.processCommands(commands1);

	assertEquals("Tristen 81.0 174.0 is added to the BST"
		+ "\nTristen 81.0 174.0 is added to the bintree"
		+ "\nNo record on MaxHeap", outContent.toString().trim());

	ArrayList<String> commands2 = new ArrayList<String>();
	commands2.add("delete	Tristen");

	this.EWS.processCommands(commands2);
	assertEquals("\nTristen is added to the watchers list"
		+ "\nNo record on MaxHeap"
		+ "\nTristen is removed from the watchers list", outContent
		.toString().trim());

	// TODO: check if processCommands handles debug command correctly
	ArrayList<String> commands3 = new ArrayList<String>();
	commands3.add("debug");
	assertEquals("\nTristen is added to the watchers list"
		+ "\nNo record on MaxHeap"
		+ "\nTristen is removed from the watchers list", outContent
		.toString().trim());

	// clean up stream
	System.setOut(null);
    }
}
