public class SoftMaxEntropy {

    public double[] gradient(double[] yPred, double[] yTrue) {
        double[] grad = new double[yTrue.length];
        for (int i = 0; i < yTrue.length; i++) {
            grad[i] = yPred[i] - yTrue[i];
        }
        return grad;
    }

}
