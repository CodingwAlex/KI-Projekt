import java.util.Random;


public class FFN {

    private final int numLayers;
    private final int[] layerSizes;

    private final double[][][] W; // Gewichte: W[l][j][i]
    private final double[][] b; // Bias-Vektoren: b[l][j]
    private final double[][] a; // Aktivierungen: a[l][i]
    private final double[][] z; // Nettoeing�nge: z[l][i]
    private final double[][] delta;

    private final String hiddenActivation;
    private final String outputActivation;
    private final String taskType;
    private final Random rand;

    public FFN(int[] layerSizes, String hiddenActivation, String outputActivation, String taskType, long seed) {

        this.layerSizes = layerSizes;
        this.numLayers = layerSizes.length;

        this.hiddenActivation = hiddenActivation;
        this.outputActivation = outputActivation;
        this.taskType = taskType;
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
                a[l][j] = NNMath.activate(z[l][j]);
            }
        }
        return a[numLayers - 1];
    }

    // ============================================================
    // BACKWARD PASS
    // ============================================================
    public void backward(double[] yTrue, BinaryCrossEntropy lossFunction) {
        int L = numLayers - 1;

        double[] gradOut = lossFunction.gradient(a[L], yTrue); // dL/da

        for (int j = 0; j < a[L].length; j++) {
            delta[L][j] = gradOut[j];
        }

        // Delta f�r Hidden-Schichten
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
    private void sumForBatch(double[][][]gradientW, double[][]gradientB, int featuresSize) {
        for(int i=0;i<featuresSize;i++) {
            for(int l = 1; l < numLayers; l++) {
                for(int j = 0; j < layerSizes[l]; j++) {
                    gradientB[l][j]+=delta[l][j];
                    for(int k=0;k<layerSizes[l - 1];k++){
                        gradientW[l][j][k]+=delta[l][j]*a[l-1][k];
                    }
                }
            }
        }
    }

    public void updateWeightsBatchGradient(double learningRate, double [][][]gradientW, double[][]gradientB, int dataSize) {
        for (int l = 1; l < numLayers; l++) {
            for (int j = 0; j < layerSizes[l]; j++) {
                b[l][j] -= learningRate * (gradientB[l][j]/dataSize);
                for (int i = 0; i < layerSizes[l - 1]; i++) {
                    W[l][j][i]-=learningRate * (gradientW[l][j][i]/dataSize);
                }
            }
        }
    }



    public void train(double[][] features, double[][] labels, int anzEpochen, double learningRate,
                      BinaryCrossEntropy lossFunction) {

        double alpha = learningRate;
        double deltaAlpha = alpha / anzEpochen;

        System.out.println("Start Alpha: " + alpha);
        System.out.println("AnzEpochen : " + anzEpochen);

        int epoche = 1;
        int batchSize=2;
        while (epoche <= anzEpochen) {
            int[] reihenfolge = NNMath.generatePermutation(features.length, rand);
            double[][][]gradientW = new double[numLayers][][];
            double[][]gradientB = new double[numLayers][];
            for (int l = 1; l < numLayers; l++) {
                gradientW[l] = new double[layerSizes[l]][layerSizes[l - 1]];
                gradientB[l] = new double[layerSizes[l]];
            }
            //Batch Gradient
			/*for (int idx = 0; idx < features.length; idx++) {
				int i = reihenfolge[idx];
				forward(features[i]);
				backward(labels[i], lossFunction);
				sumForBatch(gradientW, gradientB, features.length);
				updateWeightsStochastic(alpha);
			}
			updateWeightsBatchGradient(alpha, gradientW, gradientB, features.length);*/

            //MiniBatch
            for (int idx = 0; idx < features.length; idx+=batchSize) {
                for (int l = 1; l < numLayers; l++) {
                    gradientW[l] = new double[layerSizes[l]][layerSizes[l - 1]];
                    gradientB[l] = new double[layerSizes[l]];
                }

                int actualBatchSize = Math.min(batchSize, features.length - batchSize);
                for(int b=0; b<actualBatchSize; b++) {
                    int i=reihenfolge[idx+b];
                    forward(features[i]);
                    backward(labels[i], lossFunction);
                    sumForBatch(gradientW, gradientB, actualBatchSize);
                }
                updateWeightsBatchGradient(learningRate, gradientW, gradientB, actualBatchSize);
                //updateWeightsStochastic(alpha);
            }
            alpha = alpha - deltaAlpha;
            epoche++;

            validate(features, labels);
        }
        System.out.println("Training abgeschlossen");
    }


    public void validate(double[][] features, double[][] labels) {
        int anzKorrektSamples = 0;
        for (int i = 0; i < features.length; i++) {
            int[] pred = predictClass(features[i]); // Bei Multi-Label evtl. mehrere Outputs
            if (pred.length == 1 && ((int) Math.round(labels[i][0])) == pred[0])
                anzKorrektSamples++;
        }
        System.out.println("Testgenauigkeit: " + anzKorrektSamples / (double) features.length);
    }

    public int[] predictClass(double[] features) {
        forward(features);
        return new int[] { (a[numLayers - 1][0] >= 0.5) ? 1 : 0 };
    }
}
