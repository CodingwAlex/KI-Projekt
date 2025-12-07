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

    public static void main(String[] args) {
        try {
            double[][] daten = einlesenXY(new File("data\\linear.csv"));

            System.out.println("Anzahl Zeilen: " + daten.length);
            if (daten.length > 0) {
                System.out.println("Anzahl Spalten: " + daten[0].length);

                System.out.println("\nErste Zeile:");
                for (int i = 0; i < daten[0].length; i++) {
                    System.out.print(daten[0][i] + " ");
                }
                System.out.println();
            }

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }
    }
}
