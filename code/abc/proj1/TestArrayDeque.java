/**
 * Created by mark on 6/21/16.
 */

import static org.junit.Assert.*;

import org.junit.Test;


public class TestArrayDeque {
    @Test
    public void testEmpty() {
        ArrayDeque<String> test = new ArrayDeque<String>();
        assertTrue(test.isEmpty());
        test.addFirst("first");
        assertFalse(test.isEmpty());
 //       test.printDeque();
    }

//    @Test
    public void testAddAndRmoveFirst() {
        ArrayDeque<String> test = new ArrayDeque<String>();
        test.addFirst("first");
        test.addFirst("zero");
        test.addFirst("-1");
        test.addFirst("-2");
        test.addFirst("-3");
        test.removeFirst();
        test.printDeque();
        assertEquals(4, test.size());

    }

//    @Test
    public void testAddAndRmoveLast() {
        ArrayDeque<String> test = new ArrayDeque<String>();
        test.addLast("first");
        assertFalse(test.isEmpty());
        test.addLast("second");
        test.addLast("third");
        test.printDeque();
        test.addLast("forth");
        test.printDeque();
        test.removeLast();
        test.printDeque();
        test.removeLast();
        test.printDeque();
    }

    @Test
    public void testFull() {
        ArrayDeque<String> test = new ArrayDeque<String>();
        test.addFirst("first");
        test.addFirst("zero");
        test.addFirst("-1");
        test.addFirst("-2");
        test.addFirst("-3");
        test.addFirst("-4");
        test.addFirst("-5");
        assertEquals(7, test.size());
        assertFalse(test.isFull());
        test.addLast("second");
        assertEquals(8, test.size());
        assertTrue(test.isFull());
        test.addFirst("hhhh");
        test.printDeque();
    }
}
