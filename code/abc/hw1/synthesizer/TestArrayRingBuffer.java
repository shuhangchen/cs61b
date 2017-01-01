package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<String>(4);
        assertTrue(arb.isEmpty());
        arb.enqueue("a");
        assertFalse(arb.isEmpty());
        assertEquals("a", arb.peek());
        arb.enqueue("b");
        assertEquals("a", arb.peek());
        arb.enqueue("c");
        assertEquals("a", arb.peek());
        arb.enqueue("d");
        assertEquals("a", arb.peek());
        assertTrue(arb.isFull());
        arb.dequeue();
        assertEquals("b", arb.peek());
        assertFalse(arb.isFull());
        assertEquals(3, arb.fillCount);
        arb.dequeue();
        assertEquals("c", arb.peek());
        arb.enqueue("e");
        arb.enqueue("f");
        assertEquals("c", arb.peek());
        assertTrue(arb.isFull());

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
