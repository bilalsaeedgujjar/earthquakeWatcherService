import java.text.DecimalFormat;
import realtimeweb.earthquakeservice.domain.Report;
import java.util.List;
import realtimeweb.earthquakeservice.domain.Earthquake;
import java.util.ArrayList;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Byron Becker (byronb92@vt.edu)
 * @version Oct 15, 2013
 */
public class EarthquakeWatcherService {
    /**
     * Holds all the current Watchers to update about close by earthquakes. The
     * name of a watcher is used as a key to allow efficient search of watchers
     * in the binary search tree by name.
     */
    private BinarySearchTree<String, Watcher> BST;

    /**
     * Holds all the current Watchers to update about close by earthquakes. The
     * coordinate location of a watcher is used as a key to allow efficient
     * search of watchers in the 2 dimensional bin tree tree by (x, y) location.
     */
    private BinTree2D<Point, Watcher> binTree;

    /**
     * Holds earthquakes that have occurred in the past 6 hours in chronological
     * order. The front of the Queue contains the oldest earthquake.
     */
    private LinkedQueue<EarthquakeNodeAwareOfHeapIndex> linkedQueueOfRecentEarthquakes;

    /**
     * Holds the same earthquakes in the linked queue but now the earthquakes
     * are organized by earthquake magnitude.
     */
    private EQMaxHeap<EarthquakeNodeAwareOfHeapIndex> maxHeapOfRecentEarthquakes;

    private final long millisecondsInSixHours = 21600000;

    /**
     * Holds the time of the most recent earthquake in the queue and the
     * max-heap.
     */
    private long unixTimeOfEarliestQuake = -1;

    /**
     * The time at which the report was retrieved.
     */
    private long currentReportTime = -1;

    private boolean debugCommandGiven = false;
    private boolean liveCommandGiven = false;

    private String[] commandLineArguments;
    private DecimalFormat df = new DecimalFormat("#.0");

    /**
     * Construct a bin tree and binary search tree to store watchers. Construct
     * a linkedQueue to store earthquakes in order of time. Construct a max-heap
     * of earthquakes to efficiently query the largest recent earthquake.
     */
    public EarthquakeWatcherService(String[] commandLineArguments) {
	this.commandLineArguments = commandLineArguments;

	// initialize bin tree and binary search tree
	this.BST = new BinarySearchTree<String, Watcher>();
	this.binTree = new BinTree2D<>(0.0, 360.0, 0.0, 180.0);

	// store the list of recent earthquake records in order of arrival
	linkedQueueOfRecentEarthquakes = new LinkedQueue<EarthquakeNodeAwareOfHeapIndex>();

	int heapCapacity = 1000; // no testing of this program will require more
				 // than 1000 earthquakes
	EarthquakeNodeAwareOfHeapIndex[] heap = new EarthquakeNodeAwareOfHeapIndex[heapCapacity];

	// also stores the list of recent earthquakes ordered by earthquake
	// magnitude
	maxHeapOfRecentEarthquakes = new EQMaxHeap<EarthquakeNodeAwareOfHeapIndex>(
		heap, heapCapacity, 0);

	this.checkForOptionalLiveArguments();
    }

    /**
     * Check if the optional command line argument "--all" are given.
     *
     * @param commandLineArguments
     *            The commands to be checked.
     */
    private void checkForOptionalLiveArguments() {
	// check for the following 2 different possible valid commands
	// args = { watcher.txt, normal.earthquakes } OR
	// args = { watcher.txt, live } OR
	if (this.commandLineArguments.length == 2
		&& this.commandLineArguments[1].equals("live")) {
	    this.liveCommandGiven = true;
	}
    }

    /**
     * Return earthquake file name if input commands are valid.
     *
     * @return The earthquake file name.
     */
    public String getEarthquakeFileName() {
	if ((this.commandLineArguments.length == 2 && this.commandLineArguments[1]
		.equals("live"))) {
	    throw new IllegalArgumentException(
		    "In method getEarthquakeFileName of class EarthquakeWatcherService"
			    + "the commands state there is no earthquake file "
			    + "and that the program should instead be run live");
	} else if (this.commandLineArguments.length == 2) {
	    return this.commandLineArguments[1];
	} else {
	    throw new IllegalArgumentException(
		    "In method getEarthquakeFileName of class EarthquakeWatcherService"
			    + "the given commands are invalid");
	}
    }

    /**
     * Return watcher file name if input commands are valid.
     *
     * @return The watcher file name.
     */
    public String getWatcherFileName() {
	if (this.commandLineArguments.length == 2
		&& this.commandLineArguments[0].equals("debug")) {
	    throw new IllegalArgumentException(
		    "In method getEarthquakeFileName of class EarthquakeWatcherService"
			    + "the commands state there is no earthquake file "
			    + "and that the program should instead be run live");
	} else if (this.commandLineArguments.length == 2) {
	    return this.commandLineArguments[0];
	} else {
	    throw new IllegalArgumentException(
		    "In method getWatcherFileName of class EarthquakeWatcherService"
			    + "the given commands are invalid");
	}
    }

    /**
     * Process the input list of commands by calling other methods based on
     * whether the command is for adding deleting or querying.
     *
     * @param commands
     *            List of commands with instructions to be executed.
     */
    public void processCommands(ArrayList<String> commands) {
	if (commands.size() == 0) {
	    return; // since no commands to process
	}
	// commands = [add 81 274 Tristan, query]
	for (int i = 0; i < commands.size(); i++) {
	    String command = commands.get(i);

	    if (command.contains("add")) {
		String watcherName = this.getWatcherName(command);

		double longitude = this.getLongitude(command);
		double latitude = this.getLatitude(command);

		// convert to the bintree's coordinate system by
		// adding 180 to longitude and adding 90 to the latitude
		double convertedLongitude = longitude + 180.0;
		double convertedLatitude = latitude + 90.0;

		Watcher newWatcher = new Watcher(watcherName,
			convertedLongitude, convertedLatitude);
		this.processWatcherAddRequest(newWatcher);
	    } else if (command.contains("delete")) {
		String watcherName = this.getWatcherName(command);
		this.processWatcherDeleteRequest(watcherName);
	    } else if (command.contains("query")) {
		this.printLargestRecentEarthquake();
	    } else if (command.contains("debug")) {
		System.out.println(this.BST.inorderTraversal(
			this.BST.getRootNode(), 0));
		System.out.println(this.binTree.preorderTraversal(this.binTree
			.getRootNode()));
	    }
	}
    }

    /**
     * Get the watcherName of commands with the format: add/delete latitude
     * longitude watcherName
     *
     * @param command
     *            The command with the watcher name.
     * @return The watcher name in the command.
     */
    public String getWatcherName(String command) {
	String[] splitCommand = command.split("\t|[ ]+");

	// watcherName will always be either in the 1st index or 3rd index
	String watcherName = "";
	if (command.contains("query")) {
	    throw new IllegalArgumentException(
		    "In method getWatcherName of class EarthquakeWatcherService"
			    + " you cannot call this method on a "
			    + "query command");
	} else if (splitCommand.length == 2) {
	    watcherName = splitCommand[1];
	} else if (splitCommand.length == 4) {
	    watcherName = splitCommand[3];
	}
	return watcherName;
    }

    /**
     * Get the longitude of commands with the format: add/delete latitude
     * longitude watcherName
     *
     * @param command
     *            The command with the longitude information.
     * @return The longitude of the command.
     */
    public double getLongitude(String command) {
	// if the command has 4 elements longitude will always be the 2nd
	// element
	double longitude = 0;
	String[] splitCommand = command.split("\t|[ ]+");
	if (splitCommand.length == 4) {
	    longitude = Double.parseDouble(splitCommand[1]);
	} else {
	    throw new IllegalArgumentException(
		    "In method getLongitude of class EarthquakeWatcherService"
			    + " the command in the parameter does not have a "
			    + "longitude");
	}
	return longitude;
    }

    /**
     * Get the latitude of commands with the format: add/delete latitude
     * longitude watcherName
     *
     * @param command
     *            The command with the latitude information.
     * @return The latitude of the command.
     *
     */
    public double getLatitude(String command) {
	// if the command has 4 elements latitude will always be the 3rd element
	double latitude = 0;
	String[] splitCommand = command.split("\t|[ ]+");
	if (splitCommand.length == 4) {
	    latitude = Double.parseDouble(splitCommand[2]);
	} else {
	    throw new IllegalArgumentException(
		    "In method getLatitude of class EarthquakeWatcherService"
			    + " the command in the parameter does not have a "
			    + "latitude");
	}
	return latitude;
    }

    /**
     * Print to console message of adding a watcher to the
     *
     * @param watcher
     *            Watcher to be added.
     *
     */
    public boolean processWatcherAddRequest(Watcher watcher) {
	// adding a watcher can be successful or unsuccessful
	if (this.addedWatcherToBST(watcher)) {
	    return this.addedWatcherToBinTree(watcher);
	} else { // watcher name is a duplicate in the BST so don't add it
		 // to the bin tree
	    return false;
	}
    }

    boolean addedWatcherToBST(Watcher watcher) {
	if (this.BST.find(watcher.getName()) == null) {
	    // when the watcher's name is not duplicated in the BST
	    this.BST.insert(watcher.getName(), watcher);

	    // convert back to the coordinates given through the earthquake API
	    double originalLongitude = watcher.getLongitude() - 180.0;
	    double originalLatitude = watcher.getLatitude() - 90.0;
	    System.out.println(watcher.getName() + " " + originalLongitude
		    + " " + originalLatitude + " is added to the BST");
	    return true;
	} else {
	    // watcher already exists within BST and bintree
	    System.out.println(watcher.getName()
		    + " duplicates a watcher already in the BST");
	    return false;
	}
    }

    boolean addedWatcherToBinTree(Watcher watcher) {
	Point watcherLocation = new Point(watcher.getLongitude(),
		watcher.getLatitude());
	if (this.binTree.findKey(watcherLocation) == false) {
	    // watcherLocation is not duplicated in the bin tree
	    this.binTree.insert(watcherLocation, watcher);

	    double originalLongitude = watcher.getLongitude() - 180.0;
	    double originalLatitude = watcher.getLatitude() - 90.0;
	    System.out.println(watcher.getName() + " "
		    + this.df.format(originalLongitude) + " "
		    + this.df.format(originalLatitude)
		    + " is added to the bintree");
	    return true;
	} else { // watcherLocation is already in the bin tree
	    double originalLongitude = watcher.getLongitude() - 180.0;
	    double originalLatitude = watcher.getLatitude() - 90.0;
	    System.out.println(this.df.format(originalLongitude) + " "
		    + this.df.format(originalLatitude)
		    + " duplicates a watcher already in the bintree");

	    // remove the most recently added watcher's name in the BST
	    // since it's coordinate duplicated a coordinate already in the
	    // bintree
	    this.BST.remove(watcher.getName());
	    System.out.println(watcher.getName() + " is removed from the BST");
	    return false;
	}
    }

    public boolean processWatcherDeleteRequest(String watcherName) {
	if (this.BST.find(watcherName) == null) {
	    // watcher does not exist within BST or bintree
	    System.out.println(watcherName + " does not appear in the BST");
	    return false;
	} else {
	    // watcher does exist within BST & bintree
	    Watcher removedWatcher = this.BST.remove(watcherName);

	    Point removedWatcherLocation = new Point(
		    removedWatcher.getLongitude(), removedWatcher.getLatitude());
	    this.binTree.remove(removedWatcherLocation, removedWatcher);

	    // printout must be original longitude and latitude
	    double originalLongitude = removedWatcher.getLongitude() - 180.0;
	    double originalLatitude = removedWatcher.getLatitude() - 90.0;
	    System.out.println(watcherName + " "
		    + this.df.format(originalLongitude) + " "
		    + this.df.format(originalLatitude)
		    + " is removed from the BST");
	    System.out.println(watcherName + " "
		    + this.df.format(originalLongitude) + " "
		    + this.df.format(originalLatitude)
		    + " is removed from the bintree");
	    return true;
	}
    }

    /**
     * Print to the console the largest earthquake in the past 6 hours.
     */
    public void printLargestRecentEarthquake() {

	if (maxHeapOfRecentEarthquakes.getNumberOfNodes() == 0) {
	    System.out.println("No record on MaxHeap");
	} else {
	    // greatest magnitude earthquake in past 6 hours
	    Earthquake biggestEarthquake = maxHeapOfRecentEarthquakes
		    .getMaximumValue().getEarthquake();
	    System.out.println("Largest earthquake in past 6 hours:");
	    System.out.println("Magnitude " + biggestEarthquake.getMagnitude()
		    + " at " + biggestEarthquake.getLocationDescription());
	}
    }

    /**
     * As earthquake records arrive or expire, they are added to or removed from
     * both the queue or max heap.
     */
    public void removeExpiredEarthquakesInQueueAndMaxHeap() {
	// assume this boolean variable is initially the case
	boolean timeRangeBetweenOldQuakeAndCurrentTimeGreaterThan6Hours = true;
	while (linkedQueueOfRecentEarthquakes.length() > 0
		&& timeRangeBetweenOldQuakeAndCurrentTimeGreaterThan6Hours) {
	    // the front of the queue is holding the oldest earthquakes
	    Earthquake earthquakeToCheckToBeRemoved = linkedQueueOfRecentEarthquakes
		    .frontValue().getEarthquake();
	    // unix time definition can be found at:
	    // http://en.wikipedia.org/wiki/Unix_time
	    long unixTimeOfOldestQuake = earthquakeToCheckToBeRemoved.getTime();

	    // if this old earthquake is greater than 6 hours compared to
	    // the current reported time then remove the outdated
	    // earthquake
	    if ((currentReportTime - unixTimeOfOldestQuake) > millisecondsInSixHours) {
		int sameQuakeHeapIndex = linkedQueueOfRecentEarthquakes
			.dequeue().getIndexWithinHeapArray();
		maxHeapOfRecentEarthquakes.remove(sameQuakeHeapIndex);
	    } else {
		timeRangeBetweenOldQuakeAndCurrentTimeGreaterThan6Hours = false;
	    }
	}
    }

    /**
     * All expired earthquakes have already been removed from the queue and
     * max-heap.
     *
     * @param latestQuakes
     * @return The new latest earthquakes with duplicates removed.
     */
    public List<Earthquake> getNewEarthquakes(List<Earthquake> latestQuakes) {
	List<Earthquake> newQuakes = new ArrayList<Earthquake>();
	// check latestQuakes and deduce if any are actually new
	// earthquakes
	for (Earthquake earthquake : latestQuakes) {
	    if (isNewEarthquakeInQueueAndHeap(earthquake)) {
		newQuakes.add(earthquake);
	    }
	}
	return newQuakes;
    }

    /**
     * Checks if given earthquake is not already in the Queue.
     *
     * @param newEarthquake
     * @return True if the given earthquake is already in the queue; otherwise
     *         false.
     */
    public boolean isNewEarthquakeInQueueAndHeap(Earthquake newEarthquake) {
	// not duplicate if queue and heap are empty

	// begin checking against earthquakes in the queue since the earthquakes
	// are ordered by time.
	if (newEarthquake.getTime() > unixTimeOfEarliestQuake) {
	    unixTimeOfEarliestQuake = newEarthquake.getTime();
	    return true;
	} else {
	    return false;
	}
    }

    public void processLatestEarthquakesReport(Report latestQuakesReport) {
	long currentReportTime = latestQuakesReport.getGeneratedTime();
	this.setCurrentReportTime(currentReportTime);

	this.removeExpiredEarthquakesInQueueAndMaxHeap();

	// earthquakes that have occurred in the recent hour time step
	List<Earthquake> latestEarthquakes = latestQuakesReport
		.getEarthquakes();

	List<Earthquake> newEarthquakes = this
		.getNewEarthquakes(latestEarthquakes);

	// add new earthquakes to rear of the earthquakeQueue
	// and maxHeap based on magnitude
	for (int i = 0; i < newEarthquakes.size(); i++) {
	    EarthquakeNodeAwareOfHeapIndex newEarthquakeNode = new EarthquakeNodeAwareOfHeapIndex(
		    newEarthquakes.get(i), -1);

	    // add to linked queue and max heap within ews
	    this.addNewEarthquakeToQueueAndMaxHeap(newEarthquakeNode);

	    System.out.println("Earthquake "
		    + newEarthquakeNode.getEarthquake()
			    .getLocationDescription()
		    + " is inserted into the Heap");

	    // TODO: test for correct output to console
	    this.binTree.regionSearch(newEarthquakes.get(i));
	}
    }

    public void setCurrentReportTime(long currentReportTime) {
	this.currentReportTime = currentReportTime;
    }

    public void addNewEarthquakeToQueueAndMaxHeap(
	    EarthquakeNodeAwareOfHeapIndex newEarthquakeNode) {
	linkedQueueOfRecentEarthquakes.enqueue(newEarthquakeNode);
	maxHeapOfRecentEarthquakes.insert(newEarthquakeNode);
    }

    public EQMaxHeap<EarthquakeNodeAwareOfHeapIndex> getMaxHeapOfRecentEarthquakes() {
	return this.maxHeapOfRecentEarthquakes;
    }

    public long getUnixTimeOfEarliestQuake() {
	return this.unixTimeOfEarliestQuake;
    }
}
