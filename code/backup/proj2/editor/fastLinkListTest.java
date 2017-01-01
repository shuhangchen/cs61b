/**
 * Created by mark on 7/24/16.
 */
// package editor;

import static org.junit.Assert.*;

import org.junit.Test;

public class fastLinkListTest {

    @Test
    public void testList() {
        fastLinkList<Character> list = new fastLinkList<Character>();
        assertTrue(list.isEmpty());
        list.insert('a');
        list.printList();
        list.insert('b');
        list.printList();
        list.insert('c');
        list.insert('d');
        list.printList();
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
        list.insert('1');
        list.insert('2');
        list.insert('3');
        list.printList();
        assertEquals((char) list.getCurrentPos().item, '3');
        assertEquals((char) list.delete().item, '3');
        assertEquals((char) list.getCurrentPos().item, '2');
        list.printList();
        list.delete();
        list.delete();
        list.delete();
        list.delete();
        list.printList();
        list.delete();
        list.printList();
        list.delete();
        list.printList();
        list.insert('q');
        assertEquals((char) list.getCurrentPos().item, 'q');
        list.printList();
        list.moveForward();
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, 'd');
        list.moveForward();
        assertEquals((char) list.getCurrentPos().item, 'd');
    }

    @Test
    public void testFastLinkOfFastLink() {
        fastLinkList<fastLinkList<Character>> text = new fastLinkList<fastLinkList<Character>>();
        fastLinkList<Character> firstLine = new fastLinkList<Character>();
        fastLinkList<Character> secondLine = new fastLinkList<Character>();
        firstLine.insert('a');
        firstLine.insert('b');
        secondLine.insert('1');
        secondLine.insert('2');
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
        fastLinkList<Character> list = new fastLinkList<Character>();
        assertEquals(list.toString(), "");
        list.insert('a');
        assertEquals(list.toString(), "a");
        list.insert('b');
        list.insert('c');
        list.insert('d');
        assertEquals(list.toString(), "abcd");
        list.insert(',');
        assertEquals(list.toString(), "abcd,");
        list.delete();
        assertEquals(list.toString(), "abcd");
    }

    @Test
    public void testReset() {
        fastLinkList<Character> list = new fastLinkList<Character> ();
        list.insert('a');
        list.insert('b');
        list.insert('c');
        list.insert('d');
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
        fastLinkList<Character> list = new fastLinkList<Character> ();
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
