import java.util.Random;

public class MatrixMultiplication{
    public static void main(String[] args) throws InterruptedException{
        // number of rows and cols of matrixes
        int n = 3;
        int[][] A = randomSquareMatrix(n);
        int[][] B = randomSquareMatrix(n);

        MatrixMonitor monitor = new MatrixMonitor(n);
        MatrixThread[][] threads= new MatrixThread[n][n];

        printMatrix(A);
        System.out.print("\n");
        printMatrix(B);

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                threads[i][j] = new MatrixThread(monitor, A, B, i, j);
                threads[i][j].start();
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                threads[i][j].join();
            }
        }

        System.out.print("\n");
        printMatrix(A);
    }

    public static int[][] randomSquareMatrix(int size){
        int[][] matrix = new int[size][size];
        Random rand = new Random();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                matrix[i][j] = rand.nextInt(4)+1;
            }
        }
        return matrix;
    }

    public static void printMatrix(int[][] matrix){
        for(int i = 0; i < matrix.length; i++){
            System.out.print("|");
            for(int j = 0; j < matrix[0].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.print("\b|\n");
        }
    }
}

class MatrixMonitor{
    // number of rows and cols of matrix
    private int n;
    // number of readings remaining for each row of matrix
    private int[] timesToRead;

    public MatrixMonitor(int n){
        this.n = n;
        this.timesToRead = new int[n];
        for(int i = 0; i < n; i++){
            this.timesToRead[i] = n;
        }
    }

    public synchronized void read(int i){
        timesToRead[i]--;

        if(timesToRead[i] == 0){
            notifyAll();
        }
    }

    public synchronized void write(int[][] matrix, int value, int i, int j){
        try{
            while(this.timesToRead[i] != 0){
                wait();
            }
        }
        catch(InterruptedException exc){
            exc.printStackTrace();   
        }

        matrix[i][j] = value;
    }
}

class MatrixThread extends Thread{
    private MatrixMonitor monitor;
    private int[][] A;
    private int[][] B;
    private int row;
    private int col;

    public MatrixThread(MatrixMonitor monitor, int[][] A, int[][] B, int row, int col){
        this.monitor = monitor;
        this.A = A;
        this.B = B;
        this.row = row;
        this.col = col;

        // System.out.println("row " + this.row + " col " + this.col);
        // MatrixMultiplication.printMatrix(A);
        // MatrixMultiplication.printMatrix(B);
    }

    public void run(){
        int newValue = 0;
        monitor.read(row);
        //MatrixMultiplication.printMatrix(A);
        //MatrixMultiplication.printMatrix(B);
        for(int i = 0; i < A.length; i++){
            newValue += A[row][i] * B[i][col];
        }
        //System.out.println(newValue);
        monitor.write(A, newValue, row, col);
    }
}