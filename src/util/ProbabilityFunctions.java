package util;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thiago
 */
public class ProbabilityFunctions {

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed
    private double uniform;
    private double normal;
    private double exponencial;
    DialogPExponential de;
    DialogPNormal dn;
    DialogPUniform du;
    double a = 1, b = 2, aMean = 1, aVariance = 0.5, lambda = 0.9;
    int contador = 0;

    public ProbabilityFunctions(JFrame frame) {
        de = new DialogPExponential(frame, true);
        dn = new DialogPNormal(frame, true);
        du = new DialogPUniform(frame, true);

        try {
            dn.getBtnAceptar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (contador == 0) {
                        aMean = Double.parseDouble(dn.getTxtMean().getText());
                        aVariance = Double.parseDouble(dn.getTxtVariance().getText());
                        contador++;
                    }

                    dn.dispose();

                }
            });

            du.getBtnAceptar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (contador == 0) {
                        a = Double.parseDouble(du.getTxtMin().getText());
                        b = Double.parseDouble(du.getTxtMax().getText());
                        if (!(a < b)) {
                            JOptionPane.showMessageDialog(null, "Invalid range");
                        }
                    }

                    du.dispose();

                }
            });

            de.getBtnAceptar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (contador == 0) {
                        lambda = Double.parseDouble(de.getTxtLambda().getText());

                        if (!(lambda > 0.0)) {
                            JOptionPane.showMessageDialog(null, "Rate lambda must be positive");
                        }
                    }
                    de.dispose();

                }
            });

        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Data Error: " + e.getMessage());

        } finally {
            dn.dispose();
        }
    }

    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public double getUniform() {

        uniform = a + uniform() * (b - a);
        return uniform;
    }

    public double getNormal() {
        Random fRandom = new Random();
        normal = aMean + fRandom.nextGaussian() * aVariance;
        return normal;
    }

    public double getExponencial() {
        exponencial = -Math.log(1 - uniform()) / lambda;
        return exponencial;
    }

    public void setNormal() {
        dn.setVisible(true);


    }

    public void setUniform() {
        du.setVisible(true);
        try {
        } catch (NumberFormatException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Data Error: " + e.getMessage());
        } finally {
            du.dispose();
        }
    }

    public void setExponential() {
        de.setVisible(true);
        try {
        } catch (NumberFormatException | HeadlessException ed) {
            JOptionPane.showMessageDialog(null, "Data Error: " + ed.getMessage());
        } finally {
            de.dispose();
        }
    }
//

    //-----------------------------------------------------------
    //__________________________________________________________
    public ProbabilityFunctions() {
    }

    public static void setSeed(long s) {
        seed = s;
        random = new Random(seed);
    }

    public static long getSeed() {
        return seed;
    }

    public static double uniform() {

        return random.nextDouble();
    }

    public static int uniform(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Parameter N must be positive");
        }
        return random.nextInt(N);
    }

    public static double random() {
        return uniform();
    }

    public static double uniform(int a, int b) {
        if (b <= a) {
            throw new IllegalArgumentException("Invalid range");
        }
        if ((long) b - a >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Invalid range");
        }
        return a + uniform(b - a);
    }

    public boolean bernoulli(double p) {
        if (!(p >= 0.0 && p <= 1.0)) {
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        }
        return uniform() < p;
    }

    public boolean bernoulli() {
        return bernoulli(0.5);
    }

    public int geometric(double p) {
        if (!(p >= 0.0 && p <= 1.0)) {
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
        }

        return (int) Math.ceil(Math.log(uniform()) / Math.log(1.0 - p));
    }

    public int poisson(double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("Parameter lambda must be positive");
        }
        if (Double.isInfinite(lambda)) {
            throw new IllegalArgumentException("Parameter lambda must not be infinite");
        }

        int k = 0;
        double p = 1.0;
        double L = Math.exp(-lambda);
        do {
            k++;
            p *= uniform();
        } while (p >= L);
        return k - 1;
    }

    public double pareto(double alpha) {
        if (!(alpha > 0.0)) {
            throw new IllegalArgumentException("Shape parameter alpha must be positive");
        }
        return Math.pow(1 - uniform(), -1.0 / alpha) - 1.0;
    }

    public double cauchy() {
        return Math.tan(Math.PI * (uniform() - 0.5));
    }

    public int discrete(double[] a) {
        double EPSILON = 1E-14;
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            if (!(a[i] >= 0.0)) {
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: " + a[i]);
            }
            sum = sum + a[i];
        }
        if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON) {
            throw new IllegalArgumentException("sum of array entries does not approximately equal 1.0: " + sum);
        }

        // the for loop may not return a value when both r is (nearly) 1.0 and when the
        // cumulative sum is less than 1.0 (as a result of floating-point roundoff error)
        while (true) {
            double r = uniform();
            sum = 0.0;
            for (int i = 0; i < a.length; i++) {
                sum = sum + a[i];
                if (sum > r) {
                    return i;
                }
            }
        }
    }

    public void shuffle(Object[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N - i);     // between i and N-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public void shuffle(double[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N - i);     // between i and N-1
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public void shuffle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N - i);     // between i and N-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public void shuffle(Object[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi - i + 1);     // between i and hi
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public void shuffle(double[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi - i + 1);     // between i and hi
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public void shuffle(int[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("Illegal subarray range");
        }
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi - i + 1);     // between i and hi
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
}
