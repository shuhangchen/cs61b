package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
public class PercolationStats {

    double[] experRes;
    int runTimes;
    double mean;
    double stddev;
    public PercolationStats(int N, int T) {
        runTimes = T;
        experRes = new double[T];
        for (int i=0; i< T; i += 1) {
            experRes[i] = doPercolateExper(N);
        }
    }

    public double doPercolateExper(int N) {
        Percolation exper = new Percolation(N);

        while (!exper.percolates()) {
            int row = StdRandom.uniform(N);
            int col = StdRandom.uniform(N);
            exper.open(row, col);
        }

        return (double) exper.numberOfOpenSites() / N / N;
    }

    public double mean() {
        mean = StdStats.mean(experRes);
        return mean;
    }

    public double stddev() {
        stddev = StdStats.stddev(experRes);
        return stddev;
    }

    public double confidenceLow() {
        return mean - 1.96 * stddev / Math.sqrt(runTimes);
    }

    public double confidenceHigh() {
        return mean + 1.96 * stddev / Math.sqrt(runTimes);
    }

    public static void main(String[] args) {
        PercolationStats percolations = new PercolationStats(20, 30);
        System.out.println(percolations.mean());
    }
}                       
