package hw4.puzzle;
import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.*;
import org.junit.Test;
/**
 * Created by shuhang on 9/2/16.
 */
public class Boardtest {

    @Test
    public void testprority() {
        int[][] b = {{1, 2, 3}, {0,7, 6}, {4 ,5, 8}};
        System.out.println(Arrays.deepToString(b));
        Board board = new Board(b);
        assertEquals(4, board.hamming());
        assertEquals(5, board.manhattan());
        assertFalse(board.isGoal());
        int[][] b2 = {{1,2,3},{4,5,6},{7,8,0}};
        Board board2 = new Board(b2);
        assertEquals(0, board2.hamming());
        assertEquals(0, board2.manhattan());
        assertTrue(board2.isGoal());
        System.out.println(board.toString());
    }

    @Test
    public void testEquals() {
        int[][] b0 = {{1, 2, 3}, {0,7, 6}, {4 ,5, 8}};
        Board board0 = new Board(b0);
        int[][] b1 = {{1, 2, 3}, {0,7, 6}, {4 ,5, 8}};
        Board board1 = new Board(b1);
        int[][] b2 = {{1,2,3},{4,5,6},{7,8,0}};
        Board board2 = new Board(b2);
        Set<Board> seen = new HashSet<Board>();
        seen.add(board0);
        assertTrue(seen.contains(board0));
        assertTrue(seen.contains(board1));
        assertTrue(board0.equals(board1));
        assertFalse(board0.equals(board2));
        assertFalse(seen.contains(board2));
        seen.add(board2);
        assertTrue(seen.contains(board2));

    }

    @Test
    public void testNeigh() {
        int[][] b = {{1 , 2}, {3 , 0}};
        Board board = new Board(b);
        Iterable<Board> neighs = BoardUtils.neighbors(board);
        System.out.println(board.toString());
        BoardNode neighBoard;
        for (Board q: neighs) {
            System.out.println(q.toString());

        }
    }

    public static void main (String[] args) {
        jh61b.junit.TestRunner.runTests("all", Boardtest.class);
    }

}
