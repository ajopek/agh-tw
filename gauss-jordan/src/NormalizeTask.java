public class NormalizeTask implements Runnable{
    private int i;
    private double[][] matrix;
    private double[][] rhs;

    public NormalizeTask(int i, double[][] matrix, double[][] rhs){
        this.i = i;
        this.matrix = matrix;
        this.rhs = rhs;
    }

    @Override
    public void run() {
        rhs[i][0] = rhs[i][0] / matrix[i][i];
        matrix[i][i] = 1;
    }
}