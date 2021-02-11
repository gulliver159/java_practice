package async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class Task1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final double[] x = new double[]{1, 14, 6, 12, 21, 4, 1, 2};
        final double[] y = new double[]{5, 4, 6, 11, 13, 2, 15, 7};

        CompletableFuture<double[]> cfX = CompletableFuture
                .supplyAsync(() -> {
                    double[] x_sub = new double[8];
                    int sum = 0;
                    for (double v : x) {
                        sum += v;
                    }
                    double x_ = sum / x.length;
                    for (int i = 0; i < x.length; i++) {
                        x_sub[i] = x[i] - x_;
                    }
                    return x_sub;
                });

        CompletableFuture<double[]> cfY = CompletableFuture
                .supplyAsync(() -> {
                    double[] y_sub = new double[8];
                    int sum = 0;
                    for (double v : y) {
                        sum += v;
                    }
                    double y_ = sum / y.length;
                    for (int i = 0; i < y.length; i++) {
                        y_sub[i] = y[i] - y_;
                    }
                    return y_sub;
                });

        CompletableFuture<Double> cfFrac  = cfX.thenCombine(cfY, (a, b) ->
        {
            double numerator = 0;
            for (int i = 0; i < a.length; i++) {
                numerator += a[i] * b[i];
            }
            double sum_x = 0, sum_y = 0;
            for (int i = 0; i < a.length; i++) {
                sum_x += a[i] * a[i];
                sum_y += b[i] * b[i];
            }
            double denominator = Math.sqrt(sum_x * sum_y);
            return numerator / denominator;
        });

        System.out.println("Result of correlation coefficient is " + cfFrac.get());
    }
}
