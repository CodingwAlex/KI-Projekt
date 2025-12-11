import java.awt.*;
import javax.swing.*;

public class FragenGUI extends JFrame {

    private final JSpinner[] spinnerFelder;
    int[] ergebnisse;

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

    public FragenGUI() {
        setTitle("Selbsteinschätzung – 1 bis 10");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLayout(new BorderLayout());

        spinnerFelder = new JSpinner[fragen.length];
        ergebnisse = new int[fragen.length];

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panels für jede Frage
        for (int i = 0; i < fragen.length; i++) {
            JPanel fragePanel = new JPanel(new BorderLayout());
            fragePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
            fragePanel.setBackground(new Color(245, 245, 245));
            fragePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

            JTextArea text = new JTextArea(fragen[i]);
            text.setFont(new Font("Arial", Font.PLAIN, 14));
            text.setEditable(false);
            text.setLineWrap(true);
            text.setWrapStyleWord(true);
            text.setBackground(new Color(245, 245, 245));
            text.setBorder(null);

            JPanel bewertungPanel = new JPanel();
            bewertungPanel.add(new JLabel("Bewertung (1–10): "));
            spinnerFelder[i] = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
            bewertungPanel.add(spinnerFelder[i]);

            fragePanel.add(text, BorderLayout.CENTER);
            fragePanel.add(bewertungPanel, BorderLayout.SOUTH);
            panel.add(fragePanel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnSpeichern = new JButton("Ergebnisse speichern");
        btnSpeichern.setFont(new Font("Arial", Font.BOLD, 16));
        btnSpeichern.addActionListener(e -> speichern());
        add(btnSpeichern, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void speichern() {
        for (int i = 0; i < spinnerFelder.length; i++) {
            ergebnisse[i] = (int) spinnerFelder[i].getValue();
        }

        System.out.println("Ergebnisse:");
        for (int wert : ergebnisse) {
            System.out.print(wert + " ");
        }
        System.out.println();

        JOptionPane.showMessageDialog(this, "Ergebnisse wurden gespeichert!");
    }

    public int[] getErgebnisse() {
        return ergebnisse;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FragenGUI::new);
    }
}
