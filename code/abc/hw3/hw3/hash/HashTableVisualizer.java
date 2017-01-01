package hw3.hash;

import java.util.HashSet;
import java.util.Set;

public class HashTableVisualizer {

    public static void main(String[] args) {
        /* scale: StdDraw scale
           N:     number of items
           M:     number of buckets */

        double scale = 1.0;
        int N = 50;
        int M = 10;

        HashTableDrawingUtility.setScale(scale);
        /*
        Set<Oomage> oomies = new HashSet<Oomage>();
        for (int i = 0; i < N; i += 1) {
            oomies.add(SimpleOomage.randomSimpleOomage());
        }
        */
        Set<ComplexOomage> oomies = new HashSet<ComplexOomage>();
        for (int i = 0; i < N; i += 1) {
            oomies.add(ComplexOomage.randomComplexOomage());
        }
        visualize(oomies, M, scale);

    }

    public static void visualize(Set<ComplexOomage> set, int M, double scale) {
        HashTableDrawingUtility.drawLabels(M);
        int[] bucketSize = new int[M];
        /* TODO: Create a visualization of the given hash table. Use
           du.xCoord and du.yCoord to figure out where to draw
           Oomages.
         */
        for (Oomage omage: set) {
            int buckNo = (omage.hashCode() & 0x7FFFFFFF) % M;
            double ycoord = HashTableDrawingUtility.yCoord(buckNo, M);
            double xcoord = HashTableDrawingUtility.xCoord(bucketSize[buckNo]);
            omage.draw(xcoord, ycoord, scale);
            bucketSize[buckNo] += 1;
        }
        /* When done with visualizer, be sure to try 
           scale = 0.5, N = 2000, M = 100. */           
    }
} 
