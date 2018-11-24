import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        File fil = new File(args[0]);
        FileReader inputFil = new FileReader(fil);
        BufferedReader in = new BufferedReader(inputFil);

        String s = in.readLine();

        int size = Integer.parseInt(s);
        double[][] lhs = new double[size][size];
        double[][] rhs = new double[size][1];

        for (int i = 0; i < size; i++) {
            s = in.readLine();
            String[] sp = s.split(" ");
            for (int j = 0; j < size; j++) {
                lhs[i][j] = Double.parseDouble(sp[j]);
            }
        }
        s = in.readLine();
        String[] sp = s.split(" ");
        for (int j = 0; j < size; j++) {
            rhs[j][0] = Double.parseDouble(sp[j]);
        }

        for (int i = 0; i < size; i++) {
            double[] coefs = new double[size];
            Thread[] coefThreads = new Thread[size];

            for(int j = 0; j < size && j != i; j++) {
                coefThreads[j] = new Thread(new CoefTask(i,j,lhs, coefs));
            }

            for(int j = 0; j < size && j != i; j++) {
                coefThreads[j].start();
            }

            for(int j = 0; j < size && j != i; j++) {
                coefThreads[j].join();
            }

            Thread[] subtractThreads = new Thread[size];

            for(int j = 0; j < size && j != i; j++) {
                subtractThreads[j] = new Thread(new SubtractTask(i, j, coefs, lhs, size, rhs));
            }

            for(int j = 0; j < size && j != i; j++) {
                subtractThreads[j].start();
            }

            for(int j = 0; j < size && j != i; j++) {
                subtractThreads[j].join();
            }

        }


        Thread[] normalizeThreads = new Thread[size];
        for(int j = 0; j < size; j++) {
            normalizeThreads[j] = new Thread(new NormalizeTask(j, lhs, rhs));
        }

        for(int j = 0; j < size; j++) {
            normalizeThreads[j].start();
        }

        for(int j = 0; j < size; j++) {
            normalizeThreads[j].join();
        }

        File file = new File("./out" + size + ".txt");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setOut(ps);
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(lhs[i][j] + " ");
            }
            System.out.println();
        }
        for (int j = 0; j < size; j++) {
            System.out.print(rhs[j][0] + " ");
        }
        System.out.println();
    }
}
