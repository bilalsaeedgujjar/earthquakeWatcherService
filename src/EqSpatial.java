import customDataStructures.EarthquakeWatcherService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
 * Please visit http://mickey.cs.vt.edu/cs3114-earthquake/ to view the API for
 * the calls to EarthquakeService and WatcherService.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Oct 15, 2013
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

	    Report latestQuakesReport = earthquakeService.getEarthquakes(
		    Threshold.ALL, History.HOUR);

	    ews.processLatestEarthquakesReport(latestQuakesReport);
	}
    }
}
