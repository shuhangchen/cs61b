/**
 * Created by mark on 6/21/16.
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class TestArrayDeque1B {
    @Test
    public void testArray() {
        StudentArrayDeque<Integer> test = new StudentArrayDeque<Integer>();
        assertTrue(test.isEmpty());
//        System.out.println(test.size());
        for (int i=0; i< 100; i+=1) {
            test.addLast(i);
        }
        /*
        for (int i=0; i< 100; i+=1) {
            Integer testi = test.removeFirst();
            System.out.println(testi);
            assertEquals(i, (int) testi);
        }
        */
//        test.printDeque();
        for (int i=0; i< 69; i+=1) {
            Integer testi = test.removeFirst();
//            System.out.println(testi);
            assertEquals(i, (int) testi);
        }

        Integer testi = test.get(0);
        assertEquals((Integer) 69, testi);
//        test.printDeque();
        /*
            assertEquals(" for (int i=0; i< 100; i+=1) {\n" +
                "            test.addLast(i);\n" +
                "        }\n " +
                "        for (int i=0; i< 69; i+=1) {\n" +
                "            Integer testi = test.removeFirst();\n" +
                "        }" +
                "actual : test.get(29) " + " not 99 as expected"
                , 99, (int) test.get(29));
                */
    }
}
