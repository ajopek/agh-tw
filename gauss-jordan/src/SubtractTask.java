public class SubtractTask implements Runnable {
    private int i,j, size;
    private double[] coefs;
    private double[][] matrix;
    private double[][] rhs;

    public SubtractTask(int i, int j, double[] coefs, double[][] matrix, int size, double[][] rhs) {
        this.i = i;
        this.j = j;
        this.coefs = coefs;
        this.matrix = matrix;
        this.size = size;
        this.rhs = rhs;
    }

    @Override
    public void run() {
        for(int col = 0; col < size; col++){
            matrix[j][col] -= coefs[j] * matrix[i][col];
        }
        rhs[j][0] -= coefs[j] * rhs[i][0];
    }
}
