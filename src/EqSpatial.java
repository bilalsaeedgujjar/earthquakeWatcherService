import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import realtimeweb.earthquakeservice.domain.Earthquake;
import realtimeweb.earthquakeservice.domain.History;
import realtimeweb.earthquakeservice.domain.Report;
import realtimeweb.earthquakeservice.domain.Threshold;
import realtimeweb.earthquakeservice.exceptions.EarthquakeException;
import realtimeweb.earthquakeservice.regular.EarthquakeService;
import realtimeweb.earthquakewatchers.WatcherParseException;
import realtimeweb.earthquakewatchers.WatcherService;

/**
 * On my honor:
 *
 * - I have not used source code obtained from another student, or any other
 * unauthorized source, either modified or unmodified.
 *
 * - All source code and documentation used in my program is either my original
 * work, or was derived by me from the source code published in the textbook for
 * this course.
 *
 * - I have not discussed coding details about this project with anyone other
 * than my partner (in the case of a joint submission), instructor, ACM/UPE
 * tutors or the TAs assigned to this course. I understand that I may discuss
 * the concepts of this program with other students, and that another student
 * may help me debug my program so long as neither of us writes anything during
 * the discussion or modifies any computer file during the discussion. I have
 * violated neither the spirit nor letter of this restriction
 *
 * Please visit http://mickey.cs.vt.edu/cs3114-earthquake/ for more
 * documentation on the below code.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 6, 2013
 */
public class EqSpatial {
    private static EarthquakeService earthquakeService;
    private static WatcherService watcherService;

    private static EarthquakeWatcherService ews;

    /**
     * Prints to console information concerning watchers and earthquakes.
     * Optional commandLineArguments effect what information is printed to
     * console.
     *
     * @param commandLineArguments
     *            Commands describing how earthquakes and watchers are retrieved
     *            from and how they should be displayed to the console.
     * @throws IOException
     * @throws WatcherParseException
     * @throws EarthquakeException
     */
    public static void main(String[] commandLineArguments) throws IOException,
	    WatcherParseException, EarthquakeException {
	ews = new EarthquakeWatcherService(commandLineArguments);

	String earthquakeFileName = ews.getEarthquakeFileName();
	String watcherFileName = ews.getWatcherFileName();

	// -----------------------2 Input Streams-----------------------------
	// this can be live or a file with earthquake data
	InputStream normalEarthquakes = new FileInputStream(earthquakeFileName);
	earthquakeService = EarthquakeService.getInstance(normalEarthquakes);

	InputStream watcherCommandFile = new FileInputStream(watcherFileName);
	watcherService = WatcherService.getInstance(watcherCommandFile);
	// -------------------------------------------------------------------

	// Poll the earthquake and watcher service while there are still
	// commands
	while (watcherService.hasCommands()) {
	    // nextCommands contains the watchers at the same time step
	    // as the ArrayList index
	    ArrayList<String> nextCommands = watcherService.getNextCommands();

	    ews.processCommands(nextCommands);

	    Report latestQuakesInfo = earthquakeService.getEarthquakes(
		    Threshold.ALL, History.HOUR);


	    long currentReportTime = latestQuakesInfo.getGeneratedTime();
	    ews.setCurrentReportTime(currentReportTime);

	    ews.removeExpiredEarthquakesInQueueAndMaxHeap();

	    // earthquakes that have occurred in the recent hour time step
	    List<Earthquake> latestEarthquakes = latestQuakesInfo
		    .getEarthquakes();

	    // TODO: pass latestEarthquakes into ews object and have it figure
	    // out everything else including adding to linked queue and
	    // max heap

	    List<Earthquake> newEarthquakes = ews
		    .getNewEarthquakes(latestEarthquakes);

	    // add new earthquakes to rear of the earthquakeQueue
	    // and maxHeap based on magnitude
	    for (int i = 0; i < newEarthquakes.size(); i++) {
		EarthquakeNodeAwareOfHeapIndex newEarthquakeNode = new EarthquakeNodeAwareOfHeapIndex(
			newEarthquakes.get(i), -1);

		// TODO: add to linked queue and max heap within ews
		linkedQueueOfRecentEarthquakes.enqueue(newEarthquakeNode);
		maxHeapOfRecentEarthquakes.insert(newEarthquakeNode);

		System.out.println("Earthquake "
			+ newEarthquakeNode.getEarthquake()
				.getLocationDescription()
			+ " is inserted into the Heap");

		ews.updateRelevantWatchersOfNewEarthquake(newEarthquakes.get(i));
	    }
	}
    }
}
