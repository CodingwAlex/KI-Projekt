import java.util.Random;

public class NNMath {
    public static int[] generatePermutation(int n, Random randG) {
        int[] result = new int[n];
        int[] pool = new int[n];

        for (int i = 0; i < n; i++) {
            pool[i] = i;
        }

        for (int i = n - 1; i >= 0; i--) {
            int index = randG.nextInt(i + 1);
            result[i] = pool[index];
            pool[index] = pool[i];
        }
        return result;
    }

    public static double hiddenActivation(double x){
        return Math.max(0.0,x);
    }

    public static double[] outputActivation(double[] z) {
        double max = z[0];
        for (int i = 1; i < z.length; i++) {
            if (z[i] > max) max = z[i];
        }

        double sum = 0.0;
        double[] result = new double[z.length];
        for (int i = 0; i < z.length; i++) {
            result[i] = Math.exp(z[i] - max); // numerisch stabil
            sum += result[i];
        }
        for (int i = 0; i < z.length; i++) {
            result[i] /= sum;
        }
        return result;
    }

    public static double activateDerivative(double x) {
        return x > 0 ? 1.0 : 0.0;
    }
}
