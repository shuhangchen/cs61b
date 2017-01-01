package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet;

public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        /* TODO: Write a test that ensures the hashCode is perfect,
          meaning no two SimpleOomages should EVER have the same
          hashCode!
         */
        int range = 255;
        int[][] rgbs1 = allRGB(range);
        int size = range * range * range;
        int row = 0;
        boolean collison = false;
        while (row < size) {
            SimpleOomage so = new SimpleOomage(rgbs1[row][0],rgbs1[row][1],rgbs1[row][2]);
            int row2 = 0;
            while (row2 < size) {
                SimpleOomage so2 = new SimpleOomage(rgbs1[row2][0],rgbs1[row2][1],rgbs1[row2][2]);
                if (!so.equals(so2)) {
                    if (so.hashCode() == so2.hashCode()) {
                        collison = true;
                        break;
                    }
                }
                row2 += 1;
            }
            if (collison) {
                break;
            }
            row += 1;
        }
        assertTrue(false);
    }

    public int[][] allRGB(int range) {
        int size = range * range * range;
        int[][] allPoss = new int[size][3];
        int index = 0;
        for (int i = 0; i < range; i += 1) {
            for (int j = 0; j < range; j += 1) {
                for (int k = 0; k < range; k +=1) {
                    allPoss[index][0] = i;
                    allPoss[index][1] = j;
                    allPoss[index][2] = k;
                    index += 1;
                }
            }
        }
        return allPoss;
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }

    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<SimpleOomage>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}
