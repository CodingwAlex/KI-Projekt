import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            double[][] data = Einlesen.einlesenXY(new File("KI-Projekt\\ki\\data\\data.csv"));
            double[][] trainFeatures=Einlesen.getFeatures(data);
            double[][] trainLabels=Einlesen.getLabels(data);
            int[] layerSizes={trainFeatures[0].length,100,trainLabels[0].length};
            FFN netz=new FFN(layerSizes,886L);
            BinaryCrossEntropy entropy=new BinaryCrossEntropy();
            netz.train(trainFeatures,trainLabels,300,0.5,entropy);

            System.out.println("\n=== Ergebnisse für alle Samples ===");

            for (int i = 0; i < trainFeatures.length; i++) {

                // Vorhersage holen
                double[] prediction = netz.forward(trainFeatures[i]);
                int predictedClass = prediction[0] >= 0.5 ? 1 : 0;

                // Antworten (Input-Werte) ausgeben
                System.out.print("Antworten Sample " + i + ": [");
                for (int j = 0; j < trainFeatures[i].length; j++) {
                    System.out.print((int)(trainFeatures[i][j]*10));
                    if (j < trainFeatures[i].length - 1) System.out.print(", ");
                }
                System.out.print("]");

                // Prediction + Label
                System.out.printf(
                        "  --> Vorhersage: %.4f (%d) | Label: %d%n",
                        prediction[0],
                        predictedClass,
                        (int) trainLabels[i][0]
                );
            }


        }catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}