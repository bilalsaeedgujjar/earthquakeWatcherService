/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 15, 2013
 */
public class WatcherTest extends junit.framework.TestCase {
    private Watcher watcher;

    public void setUp() {
	this.watcher = new Watcher("Quinn", 10, 12);
    }

    /**
     * Assert name was set correctly.
     */
    public void test_getName() {
	assertEquals("Quinn", this.watcher.getName());
    }

    /**
     * Assert longitude was set correctly.
     */
    public void test_getLongitude() {
	assertEquals(10.0, this.watcher.getLongitude(), 0.01);
    }

    /**
     * Assert latitude was set correctly
     */
    public void test_getLatitude() {
	assertEquals(12.0, this.watcher.getLatitude(), 0.01);
    }

    /**
     * Assert that 2 watchers can be checked to be equal to each other
     * correctly.
     */
    public void test_equals() {
	Watcher equivalentWatcher = new Watcher("Quinn", 10, 12);
	Watcher differentNamedWatcher = new Watcher("Sam", 10, 12);
	Watcher differentLocatedWatcher = new Watcher("Quinn", 12, 10);

	assertTrue(this.watcher.equals(equivalentWatcher));
	assertFalse(this.watcher.equals(differentNamedWatcher));
	assertFalse(this.watcher.equals(differentLocatedWatcher));
	assertFalse(this.watcher.equals(null));
    }

    public void test_toString() {
	assertEquals("Quinn -170.0 -78.0", this.watcher.toString());
    }
}
