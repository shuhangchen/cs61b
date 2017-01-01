package hw4.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import java.util.*;

public final class Solver {

    private MinPQ<BoardNode> queue;
    private int currentMoves;
    int allMoves;
    Stack<Board> solutionSeq = new Stack<Board>();
    private Set<Board> seeBoards = new HashSet<Board>();

    public Solver(Board initial) {
        currentMoves = 0;
        BoardNode node = new BoardNode(initial, 0);
        queue = new MinPQ<BoardNode>(node);
        queue.insert(node);
        //System.out.println(queue.size());
        while(!node.getBoard().isGoal()) {
            node = queue.delMin();
            seeBoards.add(node.getBoard());
            //System.out.println(deletedNode.getBoard().toString());
            Iterable<Board> neighs = BoardUtils.neighbors(node.getBoard());
            currentMoves = node.getMoves() + 1;
            BoardNode neighBoard;
            for (Board b: neighs) {
                //System.out.println(b.toString());
                if (!seeBoards.contains(b)) {
                    neighBoard = new BoardNode(b, currentMoves, node);
                    queue.insert(neighBoard);
                }
            }
            //break;
        }
        allMoves = currentMoves;
        while (node != null) {
            solutionSeq.push(node.getBoard());
            node = node.prev;
        }
    }

    public int moves() {
        return allMoves;
    }

    public Iterable<Board> solution() {
        return solutionSeq;
    }

    // DO NOT MODIFY MAIN METHOD
    // Uncomment this method once your Solver and Board classes are ready.

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution()) {
            StdOut.println(board);
       }
    }

}
