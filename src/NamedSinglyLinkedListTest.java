

/**
 * Tests all logic within class SinglyLinkedList.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 15, 2013
 */
public class NamedSinglyLinkedListTest extends junit.framework.TestCase {
    private NamedSinglyLinkedList<Watcher> linkedList;
    private Watcher watcher1;
    private Watcher watcher2;
    private Watcher watcher3;
    private Watcher watcher4;

    public void setUp() {
	this.linkedList = new NamedSinglyLinkedList<Watcher>();
	this.watcher1 = new Watcher("Quinn", 0, 1);
	this.watcher2 = new Watcher("AJ", 2, 3);
	this.watcher3 = new Watcher("Jason", 4, 5);
	this.watcher4 = new Watcher("Hunter", 6, 7);
    }

    /**
     * Assert for correct output.
     */
    public void test_insert() {
	this.linkedList.insert(this.watcher1);
	assertEquals("< | Quinn >", this.linkedList.toString());
	this.linkedList.insert(this.watcher2);
	assertEquals("< | AJ Quinn >", this.linkedList.toString());
    }

    /**
     * Assert for correct output.
     */
    public void test_append() {
	this.linkedList.insert(this.watcher1);
	this.linkedList.append(this.watcher2);
	assertEquals("< | Quinn AJ >", this.linkedList.toString());
    }

    /**
     * Assert for correct output.
     */
    public void test_remove() {
	assertNull(this.linkedList.remove());

	this.linkedList.append(this.watcher1);
	this.linkedList.append(this.watcher2);
	this.linkedList.append(this.watcher3);
	this.linkedList.append(this.watcher4);
	assertEquals("< | Quinn AJ Jason Hunter >", this.linkedList.toString());
	this.linkedList.remove();
	assertEquals("< | AJ Jason Hunter >", this.linkedList.toString());
    }

    /**
     * Assert for correct output.
     */
    public void test_clear() {
	this.linkedList.append(this.watcher1);
	this.linkedList.append(this.watcher2);
	assertEquals(2, this.linkedList.length());

	this.linkedList.clear();
	assertEquals(0, this.linkedList.length());
    }

    /**
     * Assert for correct output.
     */
    public void test_moveToStartAndEnd() {
	this.linkedList.insert(this.watcher1);
	this.linkedList.insert(this.watcher2);
	this.linkedList.insert(this.watcher3);
	assertEquals("Jason", this.linkedList.getValue().getName());
	this.linkedList.moveToEnd();
	assertEquals(null, this.linkedList.getValue());
	this.linkedList.moveToStart();
	assertEquals("Jason", this.linkedList.getValue().getName());
    }

    /**
     * Assert for correct output.
     */
    public void test_previousAndNext() {
	assertFalse(this.linkedList.previous());
	assertFalse(this.linkedList.next());
	this.linkedList.append(this.watcher1);
	assertTrue(this.linkedList.next());
	assertTrue(this.linkedList.previous());
    }

    /**
     * Assert for correct output.
     */
    public void test_length() {
	this.linkedList.insert(this.watcher1);
	this.linkedList.insert(this.watcher2);
	this.linkedList.insert(this.watcher3);
	assertEquals(3, this.linkedList.length());
    }

    /**
     * Assert for correct output.
     */
    public void test_currentPosition() {
	this.linkedList.insert(this.watcher1);
	this.linkedList.insert(this.watcher2);
	assertEquals(0, this.linkedList.currentPosition());
	this.linkedList.next();
	assertEquals(1, this.linkedList.currentPosition());
	this.linkedList.next();
	assertEquals(2, this.linkedList.currentPosition());
    }

    /**
     * Assert for correct output.
     */
    public void test_moveCurrentNodeToPosition() {
	this.linkedList.insert(this.watcher1); // position 3
	this.linkedList.insert(this.watcher2); // position 2
	this.linkedList.insert(this.watcher3); // position 1
	this.linkedList.insert(this.watcher4); // position 0
	try {
	    this.linkedList.moveCurrentNodeToPosition(5);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("In method moveToPosition of class "
		    + "SinglyLinkedList the input node postion to be "
		    + "removed is out of bounds", expected.getMessage());
	}

	this.linkedList.moveCurrentNodeToPosition(3);
	assertEquals("Quinn", this.linkedList.getValue().getName());

	this.linkedList.moveCurrentNodeToPosition(1);
	assertEquals("Jason", this.linkedList.getValue().getName());
    }

    /**
     * Assert for correct output.
     */
    public void test_getValue() {
	assertNull(this.linkedList.getValue());
	this.linkedList.insert(this.watcher1);
	assertEquals("Quinn", this.linkedList.getValue().getName());
    }

    /**
     * Assert for correct output.
     */
    public void test_isAtEnd() {
	assertTrue(this.linkedList.isAtEnd());
	this.linkedList.insert(this.watcher1);
	assertFalse(this.linkedList.isAtEnd());
	this.linkedList.insert(this.watcher2);
	this.linkedList.moveToEnd();
	assertTrue(this.linkedList.isAtEnd());
    }

    /**
     * Assert for correct output.
     */
    public void test_findValuePosition() {
	assertEquals(-1, this.linkedList.findValuePosition(this.watcher2));

	this.linkedList.insert(this.watcher1); // at position 2 after all
					       // inserted
	assertEquals(-1, this.linkedList.findValuePosition(this.watcher2));

	this.linkedList.insert(this.watcher2); // at position 1 after all
					       // inserted
	this.linkedList.insert(this.watcher3); // at position 0 after all
					       // inserted
	assertEquals(0, this.linkedList.findValuePosition(this.watcher3));
	assertEquals(1, this.linkedList.findValuePosition(this.watcher2));
	assertEquals(2, this.linkedList.findValuePosition(this.watcher1));
    }

    /**
     * Assert for correct output.
     */
    public void test_toString() {
	this.linkedList.append(this.watcher1);
	this.linkedList.append(this.watcher2);
	this.linkedList.append(this.watcher3);
	this.linkedList.append(this.watcher4);
	this.linkedList.append(this.watcher3);
	this.linkedList.append(this.watcher2);
	this.linkedList.moveCurrentNodeToPosition(4);
	assertEquals("< Quinn AJ Jason Hunter | Jason AJ >",
		this.linkedList.toString());
    }
}
