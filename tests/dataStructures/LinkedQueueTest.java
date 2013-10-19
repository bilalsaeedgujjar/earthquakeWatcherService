package dataStructures;

/**
 * Tests all logic within class LinkedQueue.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Sep 1, 2013
 */
public class LinkedQueueTest extends junit.framework.TestCase {
    private LinkedQueue<Integer> queue;

    public void setUp() {
	this.queue = new LinkedQueue<Integer>();
	this.queue.enqueue(1);
	this.queue.enqueue(2);
	this.queue.enqueue(3);
    }

    /**
     * Test dequeuing queue starting at 3 elements until you are trying to
     * dequeue an empty queue and throw and exception.
     */
    public void test_dequeue() {
	this.queue.dequeue();
	assertEquals(2, this.queue.frontValue().intValue());
	this.queue.dequeue();
	assertEquals(3, this.queue.frontValue().intValue());
	this.queue.dequeue();
	try {
	    this.queue.dequeue();
	    fail("should've thrown an exception!");
	} catch (IllegalStateException expected) {
	    assertEquals("In method dequeue of class "
		    + "LinkedQueue the linked queue is empty can cannot"
		    + " be dequeued", expected.getMessage());
	}
    }

    /**
     * Test retrieving the front value for an empty queue and queue with
     * elements.
     */
    public void test_frontValue() {
	assertEquals(1, this.queue.frontValue().intValue());
	this.queue.dequeue();
	this.queue.dequeue();
	this.queue.dequeue();
	try {
	    this.queue.frontValue();
	    fail("should've thrown an exception!");
	} catch (IllegalStateException expected) {
	    assertEquals("In method frontValue of class "
		    + "LinkedQueue the linked queue is empty",
		    expected.getMessage());
	}
    }

    /**
     * Tests the length of the queue is properly incremented in decremented.
     */
    public void test_length() {
	assertEquals(3, this.queue.length());
	this.queue.dequeue();
	assertEquals(2, this.queue.length());
    }

    /**
     * Test whether all elements within a queue can be removed.
     */
    public void test_clear() {
	assertEquals(3, this.queue.length());
	this.queue.clear();
	assertEquals(0, this.queue.length());
    }

    /**
     * Tests contents of queue can be accurately represented characters.
     */
    public void test_toString() {
	this.queue.toString();
	assertEquals("< 1 2 3 >", this.queue.toString());
    }
}
