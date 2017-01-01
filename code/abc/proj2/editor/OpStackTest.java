/**
 * Created by shuhang on 8/18/16.
 */

import static org.junit.Assert.*;

import org.junit.Test;


public class OpStackTest {
    @Test
    public void testStack() {
        OpStack undo = new OpStack();
        assertEquals(undo.size(), 0);
        assertTrue(undo.isEmpty());
        undo.pop();
        OpNode op = new OpNode('i', 'c');
        undo.push(op);
        OpNode op2 = new OpNode('d', 'e');
        undo.push(op2);
        assertEquals(undo.size(), 2);
        assertEquals(undo.pop(), op2);
        assertFalse(undo.isEmpty());
        assertEquals(undo.size(), 1);
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests("all", OpStackTest.class);
    }
}
