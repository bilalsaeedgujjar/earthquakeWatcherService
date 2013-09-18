/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 15, 2013
 */
public class WatcherTest extends junit.framework.TestCase {
    private Watcher watcher;

    public void setUp() {
	watcher = new Watcher("Quinn", 10, 12);
    }

    public void test_getName() {
	assertEquals("Quinn", this.watcher.getName());
    }

    public void test_getLongitude() {
	assertEquals(10, this.watcher.getLongitude());
    }

    public void test_getLatitude() {
	assertEquals(12, this.watcher.getLatitude());
    }
}
