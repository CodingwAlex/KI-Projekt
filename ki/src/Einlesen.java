import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Einlesen {

    //for training of user data(ranges of training data)
    private static final List<Double> ranges = new ArrayList<>();
    private static final List<Double> mins = new ArrayList<>();

    public static double[][] einlesenXY(File datei) throws IOException {
        List<double[]> datenListe = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(datei))) {
            String zeile;

            while ((zeile = br.readLine()) != null) {
                String[] werte = zeile.split(",");

                if (werte.length != 31) {
                    System.err.println("Warnung: Zeile hat nicht 31 Werte: " + werte.length);
                    continue;
                }

                double[] zahlenArray = new double[31];
                for (int i = 0; i < 31; i++) {
                    try {
                        zahlenArray[i] = Double.parseDouble(werte[i].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Fehler beim Parsen von Wert: " + werte[i]);
                        zahlenArray[i] = 0.0;
                    }
                }

                datenListe.add(zahlenArray);
            }
        }

        return datenListe.toArray(new double[0][]);
    }

    public static double[][] getLabels(double[][] daten, int numClasses) {
        if (daten.length == 0)
            return null;

        double[][] labels = new double[daten.length][numClasses];

        for (int i = 0; i < daten.length; i++) {
            int label = (int) daten[i][daten[i].length - 1];
            if (label < 0 ||  label > numClasses) {
                throw new IllegalArgumentException("Label " + label + " ist nicht zwischen 1 und 10");
            }
            labels[i][label-1] = 1.0;
        }

        return labels;
    }

    public static double[][] getFeatures(double[][] daten){
        if (daten.length == 0)
            return null;
        int zeilen = daten.length;
        int spalten = daten[0].length - 1; // letzte Spalte = Label
        double[][] features = new double[zeilen][spalten];
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                features[i][j] = daten[i][j];
            }
        }
        minMaxScaler(features);
        return features;
    }

    public static void minMaxScaler(double[][] features) {
        int zeilen = features.length;
        int spalten = features[0].length;

        if(zeilen == 1){
            for (int i = 0; i < spalten; i++) {
                features[0][i] = (features[0][i]-mins.get(i)) / ranges.get(i);
            }
            return;
        }

        for (int j = 0; j < spalten; j++) {
            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;

            for (int i = 0; i < zeilen; i++) {
                if (features[i][j] < min) min = features[i][j];
                if (features[i][j] > max) max = features[i][j];
            }
            double range = max - min;
            if (range == 0) range = 1;
            ranges.add(range);
            mins.add(min);
            for (int i = 0; i < zeilen; i++) {
                features[i][j] = (features[i][j] - min) / range;
            }
        }
    }

    public static void answers(double [][]data){
        Scanner in = new Scanner(System.in);
        Path path = Paths.get(  "data", "questions.txt");
        System.out.println("For the following questions, answer from 1 to 10, where 1 means \"that's not me\" and 10 means \"that's me\".");
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            List<String> questions = new ArrayList<>();
            reader.lines().forEach(questions::add);
            for(int i=0;i<questions.size();){
                System.out.println(questions.get(i));
                try{
                    double answer=in.nextDouble();
                    if(answer>10 || answer<1)
                        throw new InputMismatchException();
                    data[0][i]=answer;
                    i++;
                } catch (InputMismatchException e) {
                    System.out.println("Enter value from 1 to 10");
                    in.nextLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
