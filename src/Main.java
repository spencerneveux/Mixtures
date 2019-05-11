import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File file = new File("solutionsTest.txt");
        //Size of row/col
        int numRows = 0;
        int numCols = 0;

        // Read in File
        try (Scanner input = new Scanner(file)) {
            // Get desired ratio
            String[] desiredRatio = input.nextLine().split(":");

            //Determine size of matrix
            int size = desiredRatio.length;
            System.out.println("NumRows: " + size + " NumCols " + size);

            //Create matrixA/matrixB
            double[][] matrixA = new double[size][size];
            double[] matrixB = new double[size];

            //Populate matrixB
            int count = 0;
            for(String value : desiredRatio) {
                matrixB[count] = Double.parseDouble(value);
                count++;
            }

            //Populate Matrix A
            int col = 0;
            while(input.hasNextLine()) {
                int row = 0;
                String[] values = input.nextLine().split(":");
                for (String value : values) {
                    matrixA[row][col] = Double.parseDouble(value);
                    row++;
                }
                col++;
            }

            // Matrix to solve using gaussian elimination
            Matrix solution = new Matrix(matrixA, matrixB);
            solution.solve();
            System.out.println(solution.toString());


        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
