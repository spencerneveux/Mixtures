public class GaussianElimination {
    private static final double EPSILON = 1e-8;

    private final int N;      // N-by-N system
    private double[][] a;     // N-by-N+1 augmented matrix

    public GaussianElimination(double[][] A, double[] b) {
        N = b.length;

        // build augmented matrix
        a = new double[N][N+N+1];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = A[i][j];

        // only need if you want to find certificate of infeasibility (or compute inverse)
        for (int i = 0; i < N; i++)
            a[i][N+i] = 1.0;

        for (int i = 0; i < N; i++) a[i][N+N] = b[i];

        solve();
    }
    private void solve() {

        // Gauss-Jordan elimination
        for (int p = 0; p < N; p++) {
            // show();

            // find pivot row using partial pivoting
            int max = p;
            for (int i = p+1; i < N; i++) {
                if (Math.abs(a[i][p]) > Math.abs(a[max][p])) {
                    max = i;
                }
            }

            // exchange row p with row max
            swap(p, max);

            // singular or nearly singular
            if (Math.abs(a[p][p]) <= EPSILON) {
                continue;
                // throw new RuntimeException("Matrix is singular or nearly singular");
            }

            // pivot
            pivot(p, p);
        }
        // show();
    }

    // swap row1 and row2
    private void swap(int row1, int row2) {
        double[] temp = a[row1];
        a[row1] = a[row2];
        a[row2] = temp;
    }


    // pivot on entry (p, q) using Gauss-Jordan elimination
    private void pivot(int p, int q) {

        // everything but row p and column q
        for (int i = 0; i < N; i++) {
            double alpha = a[i][q] / a[p][q];
            for (int j = 0; j <= N+N; j++) {
                if (i != p && j != q) a[i][j] -= alpha * a[p][j];
            }
        }

        // zero out column q
        for (int i = 0; i < N; i++)
            if (i != p) a[i][q] = 0.0;

        // scale row p (ok to go from q+1 to N, but do this for consistency with simplex pivot)
        for (int j = 0; j <= N+N; j++)
            if (j != q) a[p][j] /= a[p][q];
        a[p][q] = 1.0;
    }

    // extract solution to Ax = b
    public double[] primal() {
        double[] x = new double[N];
        for (int i = 0; i < N; i++) {
            if (Math.abs(a[i][i]) > EPSILON)
                x[i] = a[i][N+N] / a[i][i];
            else if (Math.abs(a[i][N+N]) > EPSILON)
                return null;
        }
        return x;
    }

    // extract solution to yA = 0, yb != 0
    public double[] dual() {
        double[] y = new double[N];
        for (int i = 0; i < N; i++) {
            if ( (Math.abs(a[i][i]) <= EPSILON) && (Math.abs(a[i][N+N]) > EPSILON) ) {
                for (int j = 0; j < N; j++)
                    y[j] = a[i][N+j];
                return y;
            }
        }
        return null;
    }

    // does the system have a solution?
    public boolean isFeasible() {
        return primal() != null;
    }
}
