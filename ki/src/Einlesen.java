import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Einlesen {

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

    public static double[][] getLabels(double[][] daten) {
        if (daten.length == 0)
            return null;

        double[][] labels = new double[daten.length][1];

        for (int i = 0; i < daten.length; i++) {
            int label = (int) daten[i][daten[i].length - 1];
            if (label != 0 && label != 1) {
                throw new IllegalArgumentException("Label " + label + " ist nicht 0 oder 1");
            }
            labels[i][0] = label;
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

    private static void minMaxScaler(double[][] features) {
        int zeilen = features.length;
        int spalten = features[0].length;

        for (int j = 0; j < spalten; j++) {
            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;

            for (int i = 0; i < zeilen; i++) {
                if (features[i][j] < min) min = features[i][j];
                if (features[i][j] > max) max = features[i][j];
            }

            double range = max - min;
            if (range == 0) range = 1;

            for (int i = 0; i < zeilen; i++) {
                features[i][j] = (features[i][j] - min) / range;
            }
        }
    }

}
