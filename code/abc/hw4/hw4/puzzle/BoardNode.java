package hw4.puzzle;

import java.util.*;
/**
 * Created by shuhang on 8/31/16.
 */
public class BoardNode implements Comparator<BoardNode> {

    private final int priority;
    private final int moves;
    private final Board board;
    public final BoardNode prev;

    public BoardNode(Board b, int m) {
        board = b;
        moves = m;
        priority = moves + b.manhattan();
        prev = null;
    }

    public BoardNode(Board b, int m, BoardNode p) {
        board = b;
        moves = m;
        priority = moves + b.manhattan();
        prev = p;
    }


    @Override
    public int compare(BoardNode a, BoardNode b ) {
        if (a.priority  < b.priority)  return -1;
        if (a.priority  > b.priority)  return +1;
        return 0;
    }

    public BoardNode getPrev() {
        return prev;
    }

    public Board getBoard() {
        return board;
    }

    public int getMoves() {
        return moves;
    }

}
