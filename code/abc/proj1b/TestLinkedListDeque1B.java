/**
 * Created by mark on 6/21/16.
 */
import static org.junit.Assert.*;

import org.junit.Test;

public class TestLinkedListDeque1B {
    @Test
    public void testLinkedList() {
        StudentLinkedListDeque<Integer> test = new StudentLinkedListDeque<Integer>();
        assertTrue(test.isEmpty());
        test.addFirst(5);
        test.addFirst(4);
        test.addLast(7);
        assertEquals(4, (int) test.removeFirst());
        assertEquals(7, (int) test.removeLast());
        Integer i = test.get(0);
        assertEquals( "test.get(0) actuall null but expecting 4",
                (Integer) 5, test.get(0));
        /*
        test.printDeque();
        assertEquals(5, (int) test.removeFirst());
        assertTrue(test.isEmpty());
        assertEquals(0, test.size());
        */
    }
}
