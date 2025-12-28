import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            double[][] data = Einlesen.einlesenXY(new File("KI-Projekt\\ki\\data\\data.csv"));
            double[][] trainFeatures=Einlesen.getFeatures(data);
            double[][] trainLabels=Einlesen.getLabels(data, 10);
            int[] layerSizes={trainFeatures[0].length,50,trainLabels[0].length};
            FFN netz=new FFN(layerSizes,886L);
            SoftMaxEntropy entropy =new SoftMaxEntropy();
            netz.train(trainFeatures,trainLabels,200,0.01,entropy);

            double[][]answers=new double[1][31];
            Einlesen.answers(answers);
            answers[0][30]=0;
            trainFeatures=Einlesen.getFeatures(answers);
            String[]stud={"Agrar- und Forshwissenschaften",
                "Gesellschafts- und Sozialwissenschaften",
                "Ingenieurwissenschaften",
                "Kunst, Musik, Design",
                "Mathematik, Naturwissenschaften",
                "Medizin, Gesundheitswissenschaften",
                "Sprach- und Kulturwissenschaften",
                "Wirtschafts- und Rechtswissenschaften","Lehramt",
                "Öffentliche Verwaltung"};
            for (int i = 0; i < trainFeatures.length; i++) {

                double[] prediction = netz.forward(trainFeatures[i]);

                int predictedClass = 0;
                double maxProb = prediction[0];
                for (int j = 1; j < prediction.length; j++) {
                    if (prediction[j] > maxProb) {
                        maxProb = prediction[j];
                        predictedClass = j;
                    }
                }
                System.out.println("Predicted class: " + stud[predictedClass] + " (prob: " + maxProb + ")");

            }


        }catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}