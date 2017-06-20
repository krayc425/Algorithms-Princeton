import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private double[] times;
    private int n;
    private int trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        times = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            times[i] = this.monteSimulation(p);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(times);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(times);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(this.trials);
    }

    private double monteSimulation(Percolation p) {
        while (!p.percolates()) {
            int i = StdRandom.uniform(this.n) + 1;
            int j = StdRandom.uniform(this.n) + 1;
            if (!p.isOpen(i, j)) {
                p.open(i, j);
            }
        }
        return (double) p.numberOfOpenSites() / (n * n);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(10, 10000);
        StdOut.println(ps.mean());
        StdOut.println(ps.stddev());
        StdOut.println(ps.confidenceLo());
        StdOut.println(ps.confidenceHi());
    }

}