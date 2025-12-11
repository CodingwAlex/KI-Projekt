import javax.swing.*;
import java.awt.*;

public class FragenGUI extends JFrame {

    private final String[] fragen = {
            // Agrar- & Forstwissenschaften
            "Ich arbeite gern draußen in der Natur.",
            "Nachhaltigkeit, Ökosysteme und Umwelt interessieren mich sehr.",
            "Ich kann mir Labor- oder Feldarbeit vorstellen.",

            // Gesellschafts- & Sozialwissenschaften
            "Ich analysiere gern menschliches Verhalten und soziale Strukturen.",
            "Politik, Gesellschaft und Kultur interessieren mich stark.",
            "Ich arbeite gern mit Menschen zusammen.",

            // Mathematik & Naturwissenschaften
            "Ich denke gern logisch und strukturiert.",
            "Mathe, Biologie, Chemie oder Physik interessieren mich sehr.",
            "Ich experimentiere und forsche gern."
            // … weitere Kategorien analog aufteilen
    };

    int[] ergebnisse = new int[fragen.length];
    private int aktuelleFrage = 0;

    private JTextArea frageText;
    private JSpinner bewertungSpinner;
    private JButton weiterButton;

    public FragenGUI() {
        setTitle("Selbsteinschätzung");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Frage-Textfeld
        frageText = new JTextArea();
        frageText.setEditable(false);
        frageText.setLineWrap(true);
        frageText.setWrapStyleWord(true);
        frageText.setFont(new Font("Arial", Font.PLAIN, 16));
        frageText.setBackground(new Color(250, 250, 250));
        frageText.setMargin(new Insets(20, 20, 20, 20));

        add(frageText, BorderLayout.CENTER);

        // Bewertung unten
        JPanel untenPanel = new JPanel();
        untenPanel.add(new JLabel("Bewertung (1–10):"));

        bewertungSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        untenPanel.add(bewertungSpinner);

        weiterButton = new JButton("Weiter");
        weiterButton.addActionListener(e -> weiter());
        untenPanel.add(weiterButton);

        add(untenPanel, BorderLayout.SOUTH);

        frageAktualisieren();
        setVisible(true);
    }

    private void frageAktualisieren() {
        frageText.setText((aktuelleFrage + 1) + ". Frage:\n\n" + fragen[aktuelleFrage]);
        bewertungSpinner.setValue(ergebnisse[aktuelleFrage] == 0 ? 5 : ergebnisse[aktuelleFrage]);

        if (aktuelleFrage == fragen.length - 1) {
            weiterButton.setText("Fertig");
        }
    }

    private void weiter() {
        ergebnisse[aktuelleFrage] = (int) bewertungSpinner.getValue();

        if (aktuelleFrage < fragen.length - 1) {
            aktuelleFrage++;
            frageAktualisieren();
        } else {
            speichern();
        }
    }

    private void speichern() {
        System.out.println("Ergebnisse:");
        for (int wert : ergebnisse) {
            System.out.print(wert + " ");
        }
        System.out.println();

        JOptionPane.showMessageDialog(this, "Alle Antworten wurden gespeichert!");
        dispose(); // Fenster schließen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FragenGUI::new);
    }
}
