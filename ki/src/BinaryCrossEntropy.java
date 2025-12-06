public class BinaryCrossEntropy {

    public double[] gradient(double[] predictions, double[] labels) {
        double[] grad = new double[labels.length];
        for (int i = 0; i < labels.length; i++) {
            // Gradient: (p - y) / (p*(1-p)) kann numerisch instabil sein
            // vereinfachte Form: grad = p - y (funktioniert mit Sigmoid-Output)
            grad[i] = predictions[i] - labels[i];
        }
        return grad;
    }

    /**
     * Berechnet den Binary Cross-Entropy Loss f�r ein einzelnes Sample
     */
    public double loss(double[] predictions, double[] labels) {
        double loss = 0.0;
        for (int i = 0; i < labels.length; i++) {
            double p = predictions[i];
            double y = labels[i];
            // epsilon gegen log(0)
            loss += - (y * Math.log(p + 1e-15) + (1 - y) * Math.log(1 - p + 1e-15));
        }
        return loss / labels.length;
    }
}
