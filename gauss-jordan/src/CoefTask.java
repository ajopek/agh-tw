public class CoefTask implements Runnable{
    private int i,j;
    private double[][] matrix;
    private double[] coefs;

    public CoefTask(int i, int j, double[][] matrix, double[] coefs){
        this.i = i;
        this.j = j;
        this.matrix = matrix;
        this.coefs = coefs;
    }

    @Override
    public void run() {
        coefs[i] = matrix[j][i]/matrix[i][i];
    }
}
