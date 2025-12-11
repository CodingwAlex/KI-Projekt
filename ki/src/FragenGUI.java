import java.awt.*;
import javax.swing.*;

public class FragenGUI extends JFrame {

<<<<<<< HEAD
    private final JSpinner[] spinnerFelder;
    int[] ergebnisse;

=======
>>>>>>> bbc851ee1c7ad18f45fca8b2f882360122483540
    private final String[] fragen = {
            // 🌿 Agrar- & Forstwissenschaften
            "Ich arbeite gern draußen in der Natur.",
            "Nachhaltigkeit, Ökosysteme und Umwelt interessieren mich sehr.",
            "Ich kann mir Labor- oder Feldarbeit vorstellen.",

            // 🧑‍🤝‍🧑 Gesellschafts- & Sozialwissenschaften
            "Ich analysiere gern menschliches Verhalten und soziale Strukturen.",
            "Politik, Gesellschaft und Kultur interessieren mich stark.",
            "Ich arbeite gern mit Menschen zusammen.",

            // 🛠️ Ingenieurwissenschaften
            "Ich löse gern technische oder praktische Probleme.",
            "Ich mag Mathe, Physik oder technisches Verständnis.",
            "Ich baue, tüftle oder verbessere gern Dinge.",

            // 🎨 Kunst, Musik, Design
            "Kreatives Gestalten liegt mir.",
            "Ich habe ein gutes Gespür für Ästhetik.",
            "Ich möchte eigene Werke oder Ideen erschaffen.",

            // 🔬 Mathematik & Naturwissenschaften
            "Ich denke gern logisch und strukturiert.",
            "Mathe, Biologie, Chemie oder Physik interessieren mich sehr.",
            "Ich experimentiere und forsche gern.",

            // 🩺 Medizin & Gesundheitswissenschaften
            "Ich möchte Menschen gesundheitlich helfen.",
            "Ich kann Verantwortung und Stress gut tragen.",
            "Biologie und Körperfunktionen interessieren mich.",

            // 📚 Sprach- & Kulturwissenschaften
            "Ich lese, schreibe oder analysiere gern Texte.",
            "Ich interessiere mich für Sprachen, Literatur oder Geschichte.",
            "Ich beschäftige mich gern mit kulturellen Themen.",

            // 💼 Wirtschafts- & Rechtswissenschaften
            "Ich denke wirtschaftlich oder juristisch-strukturiert.",
            "Märkte, Unternehmen oder Recht interessieren mich.",
            "Ich argumentiere gern logisch und finde Regeln spannend.",

            // 🍎 Lehramt
            "Ich arbeite gern mit Kindern oder Jugendlichen.",
            "Ich erkläre gern Dinge und vermittle Wissen.",
            "Ich habe Geduld und Einfühlungsvermögen.",

            // 🏛️ Öffentliche Verwaltung
            "Ich arbeite gern strukturiert, organisiert und regelorientiert.",
            "Politik und staatliche Abläufe interessieren mich.",
            "Sicherheit und Stabilität sind mir wichtig."
    };

     int[] ergebnisse = new int[fragen.length];
    private int aktuelleFrage = 0;

    private JTextArea frageText;
    private JSpinner bewertungSpinner;
    private JButton weiterButton;
    private JLabel fortschrittLabel;

    public FragenGUI() {
        setTitle("Selbsteinschätzung – 1 bis 10");
        setSize(600, 350);
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

        // Unten-Panel für Bewertung + Button + Fortschritt
        JPanel untenPanel = new JPanel(new BorderLayout());

        JPanel bewertungPanel = new JPanel();
        bewertungPanel.add(new JLabel("Bewertung (1–10): "));
        bewertungSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        bewertungPanel.add(bewertungSpinner);

        untenPanel.add(bewertungPanel, BorderLayout.WEST);

        weiterButton = new JButton("Weiter");
        weiterButton.addActionListener(e -> weiter());
        untenPanel.add(weiterButton, BorderLayout.EAST);

        fortschrittLabel = new JLabel("", SwingConstants.CENTER);
        fortschrittLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        untenPanel.add(fortschrittLabel, BorderLayout.CENTER);

        add(untenPanel, BorderLayout.SOUTH);

        frageAktualisieren();
        setVisible(true);
    }

    private void frageAktualisieren() {
        frageText.setText((aktuelleFrage + 1) + ". Frage:\n\n" + fragen[aktuelleFrage]);
        bewertungSpinner.setValue(ergebnisse[aktuelleFrage] == 0 ? 5 : ergebnisse[aktuelleFrage]);
        fortschrittLabel.setText("Frage " + (aktuelleFrage + 1) + " von " + fragen.length);

        if (aktuelleFrage == fragen.length - 1) {
            weiterButton.setText("Fertig");
        } else {
            weiterButton.setText("Weiter");
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

    public int[] getErgebnisse() {
        return ergebnisse;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FragenGUI::new);
    }
}
