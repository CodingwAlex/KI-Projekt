import javax.swing.*;
import java.awt.*;

public class FragenGUI extends JFrame {

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

    private final int[] ergebnisse = new int[fragen.length];
    private int aktuelleFrage = 0;

    private JTextArea frageText;
    private JPanel radioPanel;
    private ButtonGroup buttonGroup;
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

        // Unten-Panel
        JPanel untenPanel = new JPanel(new BorderLayout());

        // Radio Buttons 1-10
        radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonGroup = new ButtonGroup();
        for (int i = 1; i <= 10; i++) {
            JRadioButton rb = new JRadioButton(String.valueOf(i));
            rb.setActionCommand(String.valueOf(i));
            buttonGroup.add(rb);
            radioPanel.add(rb);
        }
        untenPanel.add(radioPanel, BorderLayout.WEST);

        // Weiter-Button
        weiterButton = new JButton("Weiter");
        weiterButton.addActionListener(e -> weiter());
        untenPanel.add(weiterButton, BorderLayout.EAST);

        // Fortschrittsanzeige
        fortschrittLabel = new JLabel("", SwingConstants.CENTER);
        fortschrittLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        untenPanel.add(fortschrittLabel, BorderLayout.CENTER);

        add(untenPanel, BorderLayout.SOUTH);

        frageAktualisieren();
        setVisible(true);
    }

    private void frageAktualisieren() {
        frageText.setText((aktuelleFrage + 1) + ". Frage:\n\n" + fragen[aktuelleFrage]);
        fortschrittLabel.setText("Frage " + (aktuelleFrage + 1) + " von " + fragen.length);

        // ButtonGroup zurücksetzen
        buttonGroup.clearSelection();
        if (ergebnisse[aktuelleFrage] != 0) {
            // vorherige Auswahl setzen
            for (AbstractButton button : java.util.Collections.list(buttonGroup.getElements())) {
                if (Integer.parseInt(button.getActionCommand()) == ergebnisse[aktuelleFrage]) {
                    button.setSelected(true);
                    break;
                }
            }
        }

        if (aktuelleFrage == fragen.length - 1) {
            weiterButton.setText("Fertig");
        } else {
            weiterButton.setText("Weiter");
        }
    }

    private void weiter() {
        // Ausgewählten Wert speichern
        ButtonModel selected = buttonGroup.getSelection();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Bewertung von 1 bis 10 aus!");
            return;
        }
        ergebnisse[aktuelleFrage] = Integer.parseInt(selected.getActionCommand());

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
