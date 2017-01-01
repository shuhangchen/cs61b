package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    boolean[][] grid;
    WeightedQuickUnionUF system;
    WeightedQuickUnionUF systemNoBottom;
    int dim;
    int openedSites;

    public Percolation(int N) {
        dim = N;
        grid = new boolean[N][N];
        openedSites = 0;
        system = new WeightedQuickUnionUF(dim * dim + 2); // the last one is bottom terminal node,
        // the second last one is the top terminal node
        systemNoBottom = new WeightedQuickUnionUF(dim * dim + 1);
        // only one node for the top terminal node
    }

    public void open(int row, int col) {
        if (row < 0 || row > dim -1 || col < 0 || col > dim -1) {
            System.err.println("The row and col indexes are wrong");
            System.exit(0);
        }
        grid[row][col] = true;
        openedSites += 1;
        int[][] neighs = getNeigh(row, col);
        for (int i = 0; i < neighs.length; i += 1) {
            int[] neigh = neighs[i];
            if (isOpen(neigh[0], neigh[1])) {
                // this neight is open
                system.union(twoDto1D(row, col), twoDto1D(neigh[0], neigh[1]));
                systemNoBottom.union(twoDto1D(row, col), twoDto1D(neigh[0], neigh[1]));
            }
        }

        // check for terminal nodes
        if (row == 0) {
            // connect it to the top terminal
            system.union(twoDto1D(row, col), dim * dim);
            systemNoBottom.union(twoDto1D(row, col), dim * dim);
        } else if (row == dim - 1) {
            // connect it to the bottom terminal
            system.union(twoDto1D(row, col), dim * dim + 1);
        }
    }

    public int twoDto1D(int row, int col) {
        if (row < 0 || row > dim -1 || col < 0 || col > dim -1) {
            System.err.println("The row and col indexes are wrong");
            System.exit(0);
        }
        return row * dim + col;
    }

    public int[] oneDto2D(int No) {
        if (No < 0 || No > dim * dim -1) {
            System.err.println("The 1 D index is wrong");
            System.exit(0);
        }
        int[] twoDindex = new int[2];
        twoDindex[0] = No / dim ;
        twoDindex[1] = No % dim;
        return twoDindex;
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row > dim -1 || col < 0 || col > dim -1) {
            System.err.println("The row and col indexes are wrong");
            System.exit(0);
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        return systemNoBottom.connected(twoDto1D(row, col), dim * dim);
    }

    public boolean percolates() {
        return system.connected(dim * dim, dim * dim + 1);
    }

    public int numberOfOpenSites() {
        return openedSites;
    }

    public int[][] getNeigh(int row, int col) {
        // we are assuming the row and col indexes are right
        if (row == 0) {
            if (col == 0) {
                int[][] neighs = new int[2][2];
                neighs[0][0] = row;
                neighs[0][1] = col + 1;
                neighs[1][0] = row + 1;
                neighs[1][1] = col;
                return neighs;
            } else if (col == dim -1) {
                // at the end
                int[][] neighs = new int[2][2];
                neighs[0][0] = row;
                neighs[0][1] = col -1;
                neighs[1][0] = row + 1;
                neighs[1][1] = col;
                return neighs;
            } else {
                int[][] neighs = new int[3][2];
                // first point
                neighs[0][0] = row;
                neighs[0][1] = col - 1;
                neighs[1][0] = row;
                neighs[1][1] = col + 1;
                neighs[2][0] = row + 1;
                neighs[2][1] = col;
                return neighs;
            }
        } else if (row == dim -1) {
            // we are at the last row
            if (col == 0) {
                int[][] neighs = new int[2][2];
                neighs[0][0] = row - 1;
                neighs[0][1] = col;
                neighs[1][0] = row;
                neighs[1][1] = col + 1;
                return neighs;
            } else if (col == dim -1) {
                // at the end
                int[][] neighs = new int[2][2];
                neighs[0][0] = row;
                neighs[0][1] = col -1;
                neighs[1][0] = row - 1;
                neighs[1][1] = col;
                return neighs;
            } else {
                int[][] neighs = new int[3][2];
                // first point
                neighs[0][0] = row;
                neighs[0][1] = col - 1;
                neighs[1][0] = row;
                neighs[1][1] = col + 1;
                neighs[2][0] = row - 1;
                neighs[2][1] = col;
                return neighs;
            }
        } else {
            // we are in the middle row
            if (col == 0) {
                int[][] neighs = new int[3][2];
                neighs[0][0] = row - 1;
                neighs[0][1] = col;
                neighs[1][0] = row;
                neighs[1][1] = col + 1;
                neighs[2][0] = row + 1;
                neighs[2][1] = col;
                return neighs;
            } else if (col == dim -1) {
                // at the end
                int[][] neighs = new int[3][2];
                neighs[0][0] = row;
                neighs[0][1] = col -1;
                neighs[1][0] = row - 1;
                neighs[1][1] = col;
                neighs[2][0] = row + 1;
                neighs[2][1] = col;
                return neighs;
            } else {
                int[][] neighs = new int[4][2];
                // first point
                neighs[0][0] = row;
                neighs[0][1] = col - 1;
                neighs[1][0] = row;
                neighs[1][1] = col + 1;
                neighs[2][0] = row - 1;
                neighs[2][1] = col;
                neighs[3][0] = row + 1;
                neighs[3][1] = col;
                return neighs;
            }
        }
    }


}                       
