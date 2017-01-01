package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdRandom;


public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    public boolean haveNiceHashCodeSpread(Set<ComplexOomage> oomages) {
        /* TODO: Write a utility function that ensures that the oomages have
         * hashCodes that would distribute them fairly evenly across
         * buckets To do this, mod each's hashCode by M = 10,
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int M = 10;
        int[] bucketSize = new int[M];
        boolean nice = true;
        /* TODO: Create a visualization of the given hash table. Use
           du.xCoord and du.yCoord to figure out where to draw
           Oomages.
         */
        for (Oomage omage: oomages) {
            int buckNo = (omage.hashCode() & 0x7FFFFFFF) % M;
            bucketSize[buckNo] += 1;
        }
        for (int i = 0; i < M ; i += 1) {
            if(bucketSize[i] < oomages.size() / 50.0) {
                nice = false;
                break;
            }
        }
        return nice;
    }


    @Test
    public void testRandomItemsHashCodeSpread() {
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    @Test
    public void testWithDeadlyParams() {
        /* TODO: Create a Set that shows the flaw in the hashCode function.
         */
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();
        int N = 100;
        for (int i = 0; i < N; i += 1) {
            int Num = StdRandom.uniform(100, 200);
            ArrayList<Integer> params = new ArrayList<Integer>(Num);
            for (int j = 0; j < Num; j += 1) {
                int powers = StdRandom.uniform(2,8);
                params.add((int) Math.pow(2, powers));
            }
            oomages.add(new ComplexOomage(params));
        }
        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
