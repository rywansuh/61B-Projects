import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] b;
    private final WeightedQuickUnionUF a;
    private int top;
    private int bot;
    private int size;
    private int num;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        a = new WeightedQuickUnionUF(N * N + 2);
        b = new boolean[N][N];
        top = N * N + 1;
        bot = N * N;
        size = N;
        num = 0;
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        b[row][col] = true;
        if (row == 0) {
            a.union(row * size + col, top);
        }
        if (row == size - 1) {
            a.union(row * size + col, bot);
        }
        if (row > 0 && isOpen(row - 1, col)) {
            a.union(row * size + col, (row - 1) * size + col);
        }
        if (row < size - 1 && isOpen(row + 1, col)) {
            a.union(row * size + col, (row + 1) * size + col);
        }
        if (col > 0 && isOpen(row, col - 1)) {
            a.union(row * size + col, row * size + col - 1);
        }
        if (col < size - 1 && isOpen(row, col + 1)) {
            a.union(row * size + col, row * size + col + 1);
        }
        num++;
    }

    public boolean isOpen(int row, int col) {
        return b[row][col];
    }

    public boolean isFull(int row, int col) {
        return a.connected(top, row * size + col);
    }

    public int numberOfOpenSites() {
        return num;
    }

    public boolean percolates() {
        return a.connected(top, bot);
    }
}
