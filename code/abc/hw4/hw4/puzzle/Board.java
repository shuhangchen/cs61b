package hw4.puzzle;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class Board {

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    private final int[][] board;
    private final int size;
    private final int manhattanDis;
    private final int hammingDis;

    public Board(int[][] tiles) {
        // board = tiles;
        size = tiles.length;
        board = new int[size][size];
        for (int i=0; i< size; i+=1) {
            for (int j=0; j < size; j+=1) {
                board[i][j] = tiles[i][j];
            }
        }
        manhattanDis = compManhattan();
        hammingDis = compHamming();
    }

    public int tileAt(int i, int j){

        if (isValid(board[i][j])) {
            return board[i][j];
        } else {
            System.out.println(size);
            System.out.println(board[i][j]);
            throw new java.lang.IndexOutOfBoundsException("invalid board value at tile");
        }

    }

    public boolean isValid(int value) {
        if (value >= 0 && value < size * size ) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    private int compHamming() {
        int distance = 0;
        for (int i=0; i < size; i += 1) {
            for (int j=0; j < size; j+=1) {
                if(board[i][j] != 0) {
                    if (board[i][j] != two2one(i,j)) {
                        // compute its distance
                        distance += 1;
                    }
                }
            }
        }
        return distance;
    }

    public int hamming() {
        return hammingDis;
    }

    private int compManhattan() {
        int distance = 0;
        for (int i=0; i < size; i += 1) {
            for (int j=0; j < size; j+=1) {
                if(board[i][j] != 0) {
                    if (board[i][j] != two2one(i,j)) {
                        // find its goal position
                        int[] goalPos = one2two(board[i][j]);
                        distance += Math.abs(i - goalPos[0]) + Math.abs(j - goalPos[1]);
                    }
                }
            }
        }
        return distance;
    }

    public int manhattan() {
        return manhattanDis;
    }

    public boolean isGoal() {
        boolean isGoal = true;
        for (int i=0; i< size; i+=1) {
            for (int j=0; j < size; j += 1) {
                if (board[i][j] != two2one(i,j)) {
                    if (i != size - 1 || j != size - 1 ) {
                        isGoal = false;
                        break;
                    }
                }
            }
        }
        return isGoal;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        if (this.size() != that.size()) return false;
        boolean isEqual = true;
        for (int i=0; i< size; i+= 1) {
            for (int j=0;j<size; j+=1) {
                if (this.tileAt(i,j) != that.tileAt(i,j)) {
                    isEqual = false;
                    break;
                }
            }
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    public int two2one(int i, int j) {
        return i*size + j + 1;
    }

    public int[] one2two(int value) {
        int[] pos = new int[2];
        value -= 1;  // transform it to the zero index version
        pos[0] = value / size;
        pos[1] = value % size;
        return pos;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
