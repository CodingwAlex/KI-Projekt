import javax.swing.*;
import java.awt.*;

public class FragenGUI extends JFrame {

    private final String[] fragen = {
            "🌿 Agrar- & Forstwissenschaften\n" +
                    "Ich arbeite gern draußen in der Natur.\n" +
                    "Nachhaltigkeit, Ökosysteme und Umwelt interessieren mich sehr.\n" +
                    "Ich kann mir Labor- oder Feldarbeit vorstellen.",

            "🧑‍🤝‍🧑 Gesellschafts- & Sozialwissenschaften\n" +
                    "Ich analysiere gern menschliches Verhalten und soziale Strukturen.\n" +
                    "Politik, Gesellschaft und Kultur interessieren mich stark.\n" +
                    "Ich arbeite gern mit Menschen zusammen.",

            "🛠️ Ingenieurwissenschaften\n" +
                    "Ich löse gern technische oder praktische Probleme.\n" +
                    "Ich mag Mathe, Physik oder technisches Verständnis.\n" +
                    "Ich baue, tüftle oder verbessere gern Dinge.",

            "🎨 Kunst, Musik, Design\n" +
                    "Kreatives Gestalten liegt mir.\n" +
                    "Ich habe ein gutes Gespür für Ästhetik.\n" +
                    "Ich möchte eigene Werke oder Ideen erschaffen.",

            "🔬 Mathematik & Naturwissenschaften\n" +
                    "Ich denke gern logisch und strukturiert.\n" +
                    "Mathe, Biologie, Chemie oder Physik interessieren mich sehr.\n" +
                    "Ich experimentiere und forsche gern.",

            "🩺 Medizin & Gesundheitswissenschaften\n" +
                    "Ich möchte Menschen gesundheitlich helfen.\n" +
                    "Ich kann Verantwortung und Stress gut tragen.\n" +
                    "Biologie und Körperfunktionen interessieren mich.",

            "📚 Sprach- & Kulturwissenschaften\n" +
                    "Ich lese, schreibe oder analysiere gern Texte.\n" +
                    "Ich interessiere mich für Sprachen, Literatur oder Geschichte.\n" +
                    "Ich beschäftige mich gern mit kulturellen Themen.",

            "💼 Wirtschafts- & Rechtswissenschaften\n" +
                    "Ich denke wirtschaftlich oder juristisch-strukturiert.\n" +
                    "Märkte, Unternehmen oder Recht interessieren mich.\n" +
                    "Ich argumentiere gern logisch und finde Regeln spannend.",

            "🍎 Lehramt\n" +
                    "Ich arbeite gern mit Kindern oder Jugendlichen.\n" +
                    "Ich erkläre gern Dinge und vermittle Wissen.\n" +
                    "Ich habe Geduld und Einfühlungsvermögen.",

            "🏛️ Öffentliche Verwaltung\n" +
                    "Ich arbeite gern strukturiert, organisiert und regelorientiert.\n" +
                    "Politik und staatliche Abläufe interessieren mich.\n" +
                    "Sicherheit und Stabilität sind mir wichtig."
    };

    int[] ergebnisse = new int[fragen.length];
    private int aktuelleFrage = 0;

    private JTextArea frageText;
    private JSpinner bewertungSpinner;
    private JButton weiterButton;

    public FragenGUI() {
        setTitle("Selbsteinschätzung");
        setSize(600, 400);
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
