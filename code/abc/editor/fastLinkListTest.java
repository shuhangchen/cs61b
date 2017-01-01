/**
 * Created by mark on 7/24/16.
 */


import static org.junit.Assert.*;

import org.junit.Test;

public class fastLinkListTest {

    @Test
    public void testList() {
        FastLinkListOfChar list = new FastLinkListOfChar();
        assertTrue(list.isEmpty());
        list.insert('a', 1);
        list.insert('b', 1);
        list.insert('c', 1);
        list.insert('d', 1);
        assertEquals(list.size(), 4);
        assertEquals((char) list.getCurrentPos().item, 'd');
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, 'd');
        list.moveBackward();
        assertEquals((char) list.getCurrentPos().item, 'c');
        list.moveBackward();
        assertEquals((char) list.getCurrentPos().item, 'b');
        list.moveBackward();
        assertEquals((char) list.getCurrentPos().item, 'a');
        list.moveBackward();
        assertEquals(list.getCurrentPos(), null);
        list.moveForward();
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, 'b');
        list.insert('1', 0.5);
        list.insert('2', 0.5);
        list.insert('3', 0.5);
        assertEquals((char) list.getCurrentPos().item, '3');
        assertEquals((char) list.delete().item, '3');
        assertEquals((char) list.getCurrentPos().item, '2');
        list.delete();
        list.delete();
        list.delete();
        list.delete();
        list.delete();
        list.delete();
        list.insert('q', 1);
        assertEquals((char) list.getCurrentPos().item, 'q');
        list.moveForward();
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, 'd');
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, 'd');
    }

    @Test
    public void testFastLinkOfFastLink() {
        FastLinkListOfLine text = new FastLinkListOfLine();
        FastLinkListOfChar firstLine = new FastLinkListOfChar();
        FastLinkListOfChar secondLine = new FastLinkListOfChar();
        firstLine.insert('a', 1);
        firstLine.insert('b', 1);
        secondLine.insert('1', 0.5);
        secondLine.insert('2', 0.5);
        text.insert(firstLine);
        text.insert(secondLine);
        assertEquals(text.getCurrentPos().item, secondLine);
        text.moveForward();
        assertEquals(text.getCurrentPos().item, secondLine);
        text.moveBackward();
        assertEquals(text.getCurrentPos().item, firstLine);
        text.moveBackward();
        assertEquals(text.getCurrentPos(), null);
        System.out.println("printing the fastlinklist of fastlinklist");
        System.out.println(text.toString());

    }

    @Test
    public void testToString() {
        FastLinkListOfChar list = new FastLinkListOfChar();
        assertEquals(list.toString(), "");
        list.insert('a', 1);
        assertEquals(list.toString(), "a");
        list.insert('b', 1);
        list.insert('c', 1);
        list.insert('d', 1);
        assertEquals(list.toString(), "abcd");
        list.insert(',',0.2);
        assertEquals(list.toString(), "abcd,");
        list.delete();
        assertEquals(list.toString(), "abcd");
    }

    @Test
    public void testReset() {
        FastLinkListOfChar list = new FastLinkListOfChar();
        list.insert('a', 1);
        list.insert('b', 1);
        list.insert('c', 1);
        list.insert('d', 1);
        assertEquals((char) list.getCurrentPos().item, 'd');
        list.resetCurrentPosToFront();
        assertEquals(list.getCurrentPos(), null);
        list.moveBackward();
        assertEquals(list.getCurrentPos(), null);
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, 'a');
        list.resetCurrentPosToEnd();
        assertEquals((char) list.getCurrentPos().item, 'd');
    }

    @Test
    public void testAddRemoveFirstLast() {
        FastLinkListOfChar list = new FastLinkListOfChar();
        list.insert('a',1);
        list.insert('b',1);
        list.insert('c',1);
        list.insert('d',1);
        assertEquals((char) list.getCurrentPos().item, 'd');
        list.addFirst('1',0.5);
        assertEquals(list.toString(), "1abcd");
        list.addLast('2', 0.5);
        assertEquals(list.toString(), "1abcd2");
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, '2');
        assertTrue(list.isCurrentPosAtEnd());
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, '2');
        list.moveBackward();
        assertFalse(list.isCurrentPosAtEnd());
        assertEquals((char) list.getCurrentPos().item, 'd');
        list.moveBackward();
        assertEquals((char) list.getCurrentPos().item, 'c');
        list.moveBackward();
        assertEquals((char) list.getCurrentPos().item, 'b');
        list.moveBackward();
        assertEquals((char) list.getCurrentPos().item, 'a');
        list.moveBackward();
        assertFalse(list.isCurrentPosAtFront());
        assertEquals((char) list.getCurrentPos().item, '1');
        assertFalse(list.isCurrentPosAtFront());
        list.moveBackward();
        assertEquals(list.getCurrentPos(), null);
        assertTrue(list.isCurrentPosAtFront());
        list.moveBackward();
        assertEquals(list.getCurrentPos(), null);
        assertTrue(list.isCurrentPosAtFront());
        assertEquals((char) list.removeFirst().item, '1');
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, 'a');
        list.removeFirst();
        assertEquals(list.getCurrentPos(), null);
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, 'b');
        assertEquals(list.toString(), "bcd2");
        list.moveForward(); // currPos at c now
        list.removeLast();
        assertEquals(list.toString(), "bcd");
        list.removeLast();
        assertEquals(list.toString(), "bc");
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, 'c');
        list.removeLast();
        assertEquals((char) list.getCurrentPos().item, 'b');
        assertEquals(list.toString(), "b");
    }

    public static void main (String[] args) {
        jh61b.junit.TestRunner.runTests("all", fastLinkListTest.class);
    }
}
