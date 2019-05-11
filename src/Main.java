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
            numRows = desiredRatio.length;
            while(input.hasNextLine()) {
                input.nextLine();
                numCols++;
            }

            System.out.println("NumRows: " + numRows + " NumCols " + numCols);

            //Create matrixA/matrixB
            double[][] matrixA = new double[numRows][numCols];
            double[] matrixB = new double[numRows];

            //Populate matrixB
            int count = 0;
            for(String value : desiredRatio) {
                matrixB[count] = Double.parseDouble(value);
                count++;
            }

            try(Scanner input2 = new Scanner(file)) {
                //Consume first line
                input2.nextLine();

                //Populate Matrix A
                int col = 0;
                while(input2.hasNextLine()) {
                    int row = 0;
                    String[] values = input2.nextLine().split(":");
                    for (String value : values) {
                        matrixA[row][col] = Double.parseDouble(value);
                        row++;
                    }
                    col++;
                }
            }catch (FileNotFoundException e) { e.getMessage(); }

            //Print out matrix A
            printMatrix(matrixA);
            System.out.println();

            // Matrix to solve using gaussian elimination
            Matrix solution = new Matrix(matrixA, matrixB);
            solution.solve();
            System.out.println(solution.toString());


        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
