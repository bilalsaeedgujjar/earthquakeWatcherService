import realtimeweb.earthquakeservice.domain.Report;

import java.util.List;
import realtimeweb.earthquakeservice.domain.Coordinate;

import realtimeweb.earthquakeservice.domain.Earthquake;

import java.util.ArrayList;

import realtimeweb.earthquakeservice.regular.EarthquakeService;
import realtimeweb.earthquakewatchers.WatcherService;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 6, 2013
 */
public class EarthquakeWatcherService {
    /**
     * Holds all the current Watchers to update about close by earthquakes.
     */
    // TODO: add bin tree and binary search tree

    /**
     * Holds earthquakes that have occured in the past 6 hours in chronological
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

    /**
     * Construct a bin tree and binary search tree to store watchers. Construct
     * a linkedQueue to store earthquakes in order of time. Construct a max-heap
     * of earthquakes to efficiently query the largest recent earthquake.
     */
    public EarthquakeWatcherService(String[] commandLineArguments) {
	this.commandLineArguments = commandLineArguments;

	// TODO: initialize bin tree and binary search tree

	// store the list of recent earthquake records in order of arrival
	linkedQueueOfRecentEarthquakes = new LinkedQueue<EarthquakeNodeAwareOfHeapIndex>();

	int heapCapacity = 1000; // no testing of this program will require more
				 // than 1000 earthquakes
	EarthquakeNodeAwareOfHeapIndex[] heap = new EarthquakeNodeAwareOfHeapIndex[heapCapacity];

	// also stores the list of recent earthquakes ordered by earthquake
	// magnitude
	maxHeapOfRecentEarthquakes = new EQMaxHeap<EarthquakeNodeAwareOfHeapIndex>(
		heap, heapCapacity, 0);

	this.checkForOptionalDegbugAndLiveArguments();
    }

    /**
     * Check if the optional command line argument "--all" are given.
     *
     * @param commandLineArguments
     *            The commands to be checked.
     */
    private void checkForOptionalDegbugAndLiveArguments() {
	// check for the following 4 different possible valid commands
	// args = { watcher.txt, normal.earthquakes } OR
	// args = { debug, normal.earthquakes } OR
	// args = { watcher.txt, live } OR
	// args = { debug, live }
	if (this.commandLineArguments.length == 2
		&& this.commandLineArguments[0].equals("debug")) {
	    this.debugCommandGiven = true;
	} else if (this.commandLineArguments.length == 2
		&& this.commandLineArguments[1].equals("live")) {
	    this.liveCommandGiven = true;
	} else {
	    // optional arguments debug and live where not given
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

		int longitude = this.getLongitude(command);
		int latitude = this.getLatitude(command);
		Watcher newWatcher = new Watcher(watcherName, longitude,
			latitude);
		this.processWatcherAddRequest(newWatcher);
	    } else if (command.contains("delete")) {
		String watcherName = this.getWatcherName(command);
		Watcher newWatcher = new Watcher(watcherName, -1, -1);
		this.processWatcherDeleteRequest(newWatcher);
	    } else if (command.contains("query")) {
		this.printLargestRecentEarthquake();
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
		    "In method getWatcherName of class EqSimpleEarthquakeWatcherService"
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
    public int getLongitude(String command) {
	// if the command has 4 elements longitude will always be the 2nd
	// element
	int longitude = 0;
	String[] splitCommand = command.split("\t|[ ]+");
	if (splitCommand.length == 4) {
	    longitude = Integer.parseInt(splitCommand[1]);
	} else {
	    throw new IllegalArgumentException(
		    "In method getLongitude of class EarthquakeWatcherService"
			    + "the command in the parameter does not have a "
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
    public int getLatitude(String command) {
	// if the command has 4 elements latitude will always be the 3rd element
	int latitude = 0;
	String[] splitCommand = command.split("\t|[ ]+");
	if (splitCommand.length == 4) {
	    latitude = Integer.parseInt(splitCommand[2]);
	} else {
	    throw new IllegalArgumentException(
		    "In method getLatitude of class EarthquakeWatcherService"
			    + "the command in the parameter does not have a "
			    + "latitude");
	}
	return latitude;
    }

    /**
     * Print to console message of adding a watcher to the linked list.
     *
     * @param watcher
     *            Watcher to be added.
     *
     */
    public void processWatcherAddRequest(Watcher watcher) {
	// TODO: adding a watcher can be successful or unsuccessful.
	// if (watcherName is duplicate in BST or watcherCoordinate is duplicat
	// in BinTree)
	// then reject

	// TODO: first attempt to insert into BST then BinTree and print
	System.out.println(watcher.getName()
		+ " duplicates a watcher already in the BST");

	// if the coordinate is a duplicate, since you have already added the
	// watcher's name to the BST, you also need to remove it again
	System.out.println("<coordinate>"
		+ " duplicates a watcher already in the bintree");
	// remove watcher from BST
	System.out.println(watcher.getName() + " is removed from the BST");

	linkedListWatcher.append(watcher);
	// when the wacther's name or coordinate is not duplicated
	System.out.println(watcher.getName() + " at " + "<coordinate>"
		+ " is added to the BST");
	System.out.println(watcher.getName() + " at " + "<coordinate>"
		+ " is added to the bintree");
    }

    /**
     * Print to console message of deleting a watcher from the linked list.
     *
     * @param watcher
     *            Watcher to be deleted.
     * @return The location in the linked list where the watcher was deleted.
     */
    public int processWatcherDeleteRequest(Watcher watcher) {
	// remove watcher from linkedListWatcher
	int valuePosition = linkedListWatcher.findValuePosition(watcher);
	if (valuePosition != -1) {
	    linkedListWatcher.moveCurrentNodeToPosition(valuePosition);
	    linkedListWatcher.remove();
	} else {
	    throw new IllegalArgumentException(
		    "In method processWatcherDeleteRequest of class EarthquakeWatcherService"
			    + "the given watcher is not in the queue or heap");
	}

	System.out.println(watcher.getName()
		+ " is removed from the watchers list");
	return valuePosition; // use to check correct Watcher was removed
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

    /**
     * Update any watchers that have a distance < 2 * Magnitude of
     * newEarthquake. Here distance is defined as ((longitude_earthquake -
     * longitude_watcher)^2 + (latitude_earthquake - latitude_watcher)^2)^0.5
     *
     * @param newEarthquake
     *            The new earthquake that needs to be known by close by
     *            watchers.
     */
    public void updateRelevantWatchersOfNewEarthquake(Earthquake newEarthquake) {
	Coordinate earthquakeCoordinate = newEarthquake.getLocation();
	double longitudeEarthquake = earthquakeCoordinate.getLongitude();
	double latitudeEarthquake = earthquakeCoordinate.getLatitude();

	// iterate through all current watchers and see if they are close to the
	// current earthquake
	linkedListWatcher.moveToStart(); // current node at head
	while (!linkedListWatcher.isAtEnd()) {

	    Watcher watcher = linkedListWatcher.getValue();
	    double longitudeWatcher = watcher.getLongitude();
	    double latitudeWatcher = watcher.getLatitude();

	    double longitudeDifference = longitudeEarthquake - longitudeWatcher;
	    double latitudeDifference = latitudeEarthquake - latitudeWatcher;

	    double distance = Math.sqrt(Math.pow(longitudeDifference, 2)
		    + Math.pow(latitudeDifference, 2));

	    double earthquakeMagnitude = newEarthquake.getMagnitude();

	    if (distance < (2 * Math.pow(earthquakeMagnitude, 3))) {
		System.out.println("Earthquake "
			+ newEarthquake.getLocationDescription()
			+ " is close to " + watcher.getName());
	    }
	    linkedListWatcher.next();
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

	    this.updateRelevantWatchersOfNewEarthquake(newEarthquakes.get(i));
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
}
