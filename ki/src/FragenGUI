import javax.swing.*;
import java.awt.*;

public class FragenGUI extends JFrame {

    private final JSpinner[] spinnerFelder = new JSpinner[30];
    private final int[] ergebnisse = new int[30];

    public FragenGUI() {
        setTitle("Fragebogen – 30 Fragen (1–10)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 800);
        setLayout(new BorderLayout());

        // Scrollbare Fläche
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(30, 2, 10, 10));

        // 30 Fragen + Spinner erzeugen
        for (int i = 0; i < 30; i++) {
            panel.add(new JLabel("Frage " + (i + 1) + ":"));

            spinnerFelder[i] = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
            panel.add(spinnerFelder[i]);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        // Speichern-Button
        JButton speichernButton = new JButton("Ergebnisse speichern");
        speichernButton.addActionListener(e -> speichern());
        add(speichernButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void speichern() {
        for (int i = 0; i < 30; i++) {
            ergebnisse[i] = (int) spinnerFelder[i].getValue();
        }

        // Ausgabe
        System.out.println("Ergebnisse:");
        for (int wert : ergebnisse) {
            System.out.print(wert + " ");
        }
        System.out.println();

        JOptionPane.showMessageDialog(this, "Ergebnisse wurden gespeichert!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FragenGUI::new);
    }
}
