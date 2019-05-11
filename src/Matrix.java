import java.math.BigInteger;

public class Matrix {
    private Fraction[][] fractionMatrixA;
    private Fraction[] fractionMatrixB;
    private int numEquations;

    public Matrix(double[][] matrixA, double[] matrixB) {
        numEquations = matrixB.length;
        fractionMatrixA = new Fraction[numEquations][numEquations];
        fractionMatrixB = new Fraction[numEquations];
        populateMatrixA(matrixA);
        populateMatrixB(matrixB);
    }

    //Gaussian
    public void solve() {
        for (int i = 0; i < numEquations; i++) {
            //Find pivot
            Fraction pivot = fractionMatrixA[i][i];

            //Normalize the row
            for (int j = 0; j < numEquations; j++) {
                fractionMatrixA[i][j] = fractionMatrixA[i][j].divide(pivot);
            }

            //Set column to current value divided by pivot
            fractionMatrixB[i] = fractionMatrixB[i].divide(pivot);

            for (int k = 0; k < numEquations; k++) {
                if (k != i)
                {
                    Fraction factor = fractionMatrixA[k][i];
                    for (int z = 0; z < numEquations; z++) {
                        fractionMatrixA[k][z] = fractionMatrixA[k][z].subtract(factor.multiply(fractionMatrixA[i][z]));
                    }

                    //New column Value
                    fractionMatrixB[k] = fractionMatrixB[k].subtract(factor.multiply(fractionMatrixB[i]));
                }
            }
        }
    }


    public void populateMatrixA(double[][] matrixA) {
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixA[0].length; j++) {
                fractionMatrixA[i][j] = new Fraction((int)matrixA[i][j]);
            }
        }
    }

    public void populateMatrixB(double[] matrixB) {
        for (int i = 0; i < matrixB.length; i++) {
            fractionMatrixB[i] = new Fraction((int)matrixB[i]);
        }
    }

    public Fraction[] getResults() {
        return fractionMatrixB;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < fractionMatrixB.length; i++) {
            BigInteger numerator = fractionMatrixB[i].getNumerator().negate();
            result += "x" + (i+1) + " = " + numerator.toString() + "\n";
        }
        return result;
    }
}
