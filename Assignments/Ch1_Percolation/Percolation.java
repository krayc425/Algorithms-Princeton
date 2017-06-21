import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private int openCount;
    
    // false as not open, true as open
    private boolean[] ids;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF anotherUf;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;

        // last 2 as virtual nodes
        uf = new WeightedQuickUnionUF(n * n + 2); 
        anotherUf = new WeightedQuickUnionUF(n * n + 1);
        ids = new boolean[n * n];
        for (int i = 0; i < n * n; i++) {
            ids[i] = false;
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOutOfBounds(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (!ids[posInUF(row, col)]) {
            ids[posInUF(row, col)] = true;
            openCount++;
        } else {
            return;
        }
        if (row - 1 == 0) {
            uf.union(n * n, posInUF(row, col));
            anotherUf.union(n * n, posInUF(row, col));
        }
        if (row - 1 == n - 1) {
            uf.union(n * n + 1, posInUF(row, col));
        }
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                uf.union(posInUF(row - 1, col), posInUF(row, col));
                anotherUf.union(posInUF(row - 1, col), posInUF(row, col));
            }
        }
        if (row < n) {
            if (isOpen(row + 1, col)) {
                uf.union(posInUF(row + 1, col), posInUF(row, col));
                anotherUf.union(posInUF(row + 1, col), posInUF(row, col));
            }
        }
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                uf.union(posInUF(row, col - 1), posInUF(row, col));
                anotherUf.union(posInUF(row, col - 1), posInUF(row, col));
            }
        }
        if (col < n) {
            if (isOpen(row, col + 1)) {
                uf.union(posInUF(row, col + 1), posInUF(row, col));
                anotherUf.union(posInUF(row, col + 1), posInUF(row, col));
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isOutOfBounds(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return ids[posInUF(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOutOfBounds(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return uf.connected(n * n, posInUF(row, col)) 
            && anotherUf.connected(n * n, posInUF(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(n * n, n * n + 1);
    }

    // calculate the position in uf
    private int posInUF(int row, int col) {
        return (row - 1) * this.n + col - 1;
    }
    
    // whether row, col is out of bounds
    private boolean isOutOfBounds(int row, int col) {
        return row < 1 || row > n || col < 1 || col > n;
    }
}