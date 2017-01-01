/**
 * Created by mark on 6/20/16.
 */

import static org.junit.Assert.*;

import org.junit.Test;
import sun.awt.image.ImageWatched;


public class TestLinkedListDeque {
    @Test
    public void testEmpty() {
        LinkedListDeque<String> test = new LinkedListDeque<String>();
        assertTrue(test.isEmpty());
        test.addFirst("first");
        assertFalse(test.isEmpty());
        test.removeFirst();
        assertTrue(test.isEmpty());
    }

    @Test
    public void testAdd() {
        LinkedListDeque<String> test = new LinkedListDeque<String>();
        test.addFirst("first");
        test.printDeque();
        test.addFirst("zero");
        test.printDeque();
        test.addLast("third");
        test.printDeque();
    }

    @Test
    public void testRemove() {
        LinkedListDeque<String> test = new LinkedListDeque<String>();
        test.addFirst("first");
        test.addFirst("zero");
        test.addLast("second");
        assertEquals("second", test.get(2));
        test.printDeque();
        assertEquals("zero",test.removeFirst());
        test.printDeque();
        assertEquals("second",test.removeLast());
        test.printDeque();
    }

}
