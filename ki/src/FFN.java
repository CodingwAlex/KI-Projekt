import java.util.Arrays;
import java.util.Random;


public class FFN {

    private int numLayers;
    private final int[] layerSizes;

    private final double[][][] W; // Gewichte: W[l][j][i]
    private final double[][] b; // Bias-Vektoren: b[l][j]
    private final double[][] a; // Aktivierungen: a[l][i]
    private final double[][] z; // Nettoeing�nge: z[l][i]
    private final double[][] delta;
    private final Random rand;

    public FFN(int[] layerSizes, long seed) {

        this.layerSizes = layerSizes;
        this.numLayers = layerSizes.length;

        this.rand = new Random(seed);

        W = new double[numLayers][][];
        b = new double[numLayers][];
        a = new double[numLayers][];
        z = new double[numLayers][];
        delta = new double[numLayers][];

        // Initialisierung
        for (int l = 1; l < numLayers; l++) {
            int nIn = layerSizes[l - 1];
            int nOut = layerSizes[l];

            W[l] = new double[nOut][nIn];
            b[l] = new double[nOut];
            a[l] = new double[nOut];
            z[l] = new double[nOut];
            delta[l] = new double[nOut];

        }

        W[0] = null;
        b[0] = null;
        a[0] = new double[layerSizes[0]];
        z[0] = null;
        delta[0] = null;

        initWeights();

    }


    private void initWeights() {
        for (int l = 1; l < numLayers; l++) {
            int nIn = layerSizes[l - 1];
            int nOut = layerSizes[l];
            for (int j = 0; j < nOut; j++) {
                b[l][j] = 0.0;
                for (int i = 0; i < nIn; i++) {
                    W[l][j][i] = (Math.random() - 0.5) * 0.1;
                }
            }
        }
        System.out.println("Gewichte init " + rand.nextDouble());
    }

    // ============================================================
    // FORWARD PASS (EINZELINPUT)
    // ============================================================
    public double[] forward(double[] input) {
        a[0] = input; // a[0] darf nicht ueberschrieben werden, da sonst Seiteneffekt auf input!
        // Aufpassen ;-) sonst lieber clonen

        for (int l = 1; l < numLayers; l++) {
            for (int j = 0; j < layerSizes[l]; j++) {
                double sum = b[l][j];
                for (int i = 0; i < layerSizes[l - 1]; i++) {
                    sum += W[l][j][i] * a[l - 1][i];
                }
                z[l][j] = sum;
                if(l<numLayers-1)
                    a[l][j] = NNMath.hiddenActivation(z[l][j]);
            }
        }

        int L = numLayers - 1;

        double[] activated = NNMath.outputActivation(z[L]);
        System.arraycopy(activated, 0, a[L], 0, activated.length);

        return a[L];
    }

    // ============================================================
    // BACKWARD PASS
    // ============================================================
    public void backward(double[] labels, SoftMaxEntropy lossFunction) {
        int L = numLayers - 1;

        double[] gradOut = lossFunction.gradient(a[L], labels); // dL/da

        for (int j = 0; j < a[L].length; j++) {
            delta[L][j] = gradOut[j];
        }

        // Delta for Hidden-Schichten
        for (int l = L - 1; l > 0; l--) {
            for (int i = 0; i < layerSizes[l]; i++) {
                double sum = 0.0;
                for (int j = 0; j < layerSizes[l + 1]; j++) {
                    sum += W[l + 1][j][i] * delta[l + 1][j];
                }
                delta[l][i] = sum * NNMath.activateDerivative(z[l][i]);
            }
        }
    }


    public void updateWeightsStochastic(double learningRate) {
        // Gewichte und Bias updaten
        for (int l = 1; l < numLayers; l++) {
            for (int j = 0; j < layerSizes[l]; j++) {
                b[l][j] -= learningRate * delta[l][j];
                for (int i = 0; i < layerSizes[l - 1]; i++) {
                    W[l][j][i] -= learningRate * delta[l][j] * a[l - 1][i];
                }
            }
        }
    }

    public void train(double[][] features, double[][] labels, int anzEpochen, double learningRate,
                      SoftMaxEntropy lossFunction) {

        System.out.println("Start Alpha: " + learningRate);
        System.out.println("AnzEpochen : " + anzEpochen);

        int epoche = 1;
        while (epoche < anzEpochen) {
            int[]reihenFolge=NNMath.generatePermutation(features.length, rand);
            for(int i=0; i<reihenFolge.length; i++){
                int idx=reihenFolge[i];
                forward(features[idx]);
                backward(labels[idx], lossFunction);
                updateWeightsStochastic(learningRate);
            }
            epoche++;
            validate(features, labels);
        }
        System.out.println("Training abgeschlossen");
    }


    public void validate(double[][] features, double[][] labels) {
        int anzKorrektSamples = 0;
        for (int i = 0; i < features.length; i++) {
            int pred = predictClass(features[i]);

            int trueLabel = 0;
            for (int j = 0; j < labels[i].length; j++) {
                if (labels[i][j] == 1.0) {
                    trueLabel = j;
                    break;
                }
            }

            if (pred == trueLabel) anzKorrektSamples++;
        }
        System.out.println("Testgenauigkeit: " + anzKorrektSamples / (double) features.length);
    }

    public int predictClass(double[] features) {
        double[] out = forward(features);

        int best = 0;
        for (int i = 1; i < out.length; i++) {
            if (out[i] > out[best]) best = i;
        }
        return best;
    }

}
