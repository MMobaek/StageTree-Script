import javax.swing.*;
import java.awt.*;

public class ScriptDisplay extends JFrame {
    private JTextArea textArea;
    private Script script;
    private LineUp submissions;
    // Store timing parameters to recreate the script object
    private int[] timingParams; 
    private ScheduleCleaner cleaning;

    public ScriptDisplay(Script script, LineUp submissions, int[] timingParams) {
        this.script = script;
        this.submissions = submissions;
        this.timingParams = timingParams;
        this.cleaning = new ScheduleCleaner();

        setTitle("Event Script Manager");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Script Display Area ---
        textArea = new JTextArea();
        textArea.setText(cleaning.makeNeat(script.toString()));
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);

                // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5)); 

        JButton btnContestant = new JButton("Add Contestant");
        JButton btnPause = new JButton("Add Pause");
        JButton btnPrep = new JButton("Add Stageprep");
        JButton btnRemContestant = new JButton("Remove Contestant");
        JButton btnRemPause = new JButton("Remove Pause");
        JButton btnRemPrep = new JButton("Remove Prep");

        // ADD THESE ACTION LISTENERS:
        btnContestant.addActionListener(e -> showContestantDialog());
        btnPause.addActionListener(e -> showPauseDialog());
        btnPrep.addActionListener(e -> showPrepDialog());
        btnRemContestant.addActionListener(e -> showRemoveContestantDialog());
        btnRemPause.addActionListener(e -> showRemovePauseDialog());
        btnRemPrep.addActionListener(e -> showRemovePrepDialog());

        // Add them to the grid
        buttonPanel.add(btnContestant);
        buttonPanel.add(btnPause);
        buttonPanel.add(btnPrep);
        buttonPanel.add(btnRemContestant);
        buttonPanel.add(btnRemPause);
        buttonPanel.add(btnRemPrep);


        // Layout the main frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 1. Force the scroll pane to a specific size (Width, Height)
        // This keeps the window height under control while allowing scrolling
        scrollPane.setPreferredSize(new Dimension(750, 600));

        // 2. pack() now respects the scrollPane's preferred size 
        // and the button panel's width
        this.pack(); 

        // 3. Center it
        this.setLocationRelativeTo(null);
    }


    private void refreshScript() {
        // Re-create the script with updated submissions
        this.script = new Script(timingParams[0], timingParams[1], timingParams[2], 
                                 timingParams[3], timingParams[4], timingParams[5], 
                                 timingParams[6], submissions);
        textArea.setText(cleaning.makeNeat(script.toString()));
    }

    private void showContestantDialog() {
        JTextField name = new JTextField();
        JTextField song = new JTextField();
        JTextField cat = new JTextField();
        JTextField dur = new JTextField();
        JTextField imp = new JTextField();

        Object[] message = {
            "Name:", name, "Song:", song, "Category:", cat,
            "Duration (min):", dur, "Importance (-100 to 100):", imp
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Contestant", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            submissions.addSubmission(new Contestant(name.getText(), song.getText(), 
                cat.getText(), Integer.parseInt(dur.getText()), Integer.parseInt(imp.getText())));
            refreshScript();
        }
    }

    private void showPauseDialog() {
        JTextField dur = new JTextField();
        JTextField desc = new JTextField();

        Object[] message = { "Duration (min):", dur, "Description:", desc };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Pause", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            submissions.addPause(new Pause(Integer.parseInt(dur.getText()), desc.getText()));
            refreshScript();
        }
    }

    private void showPrepDialog() {
        JTextField dur = new JTextField();
        JTextField hour = new JTextField();
        JTextField min = new JTextField();
        JTextField desc = new JTextField();

        Object[] message = { "Duration:", dur, "Hour:", hour, "Minute:", min, "Description:", desc };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Stageprep", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            submissions.addPreparation(new Stageprep(Integer.parseInt(dur.getText()), 
                Integer.parseInt(hour.getText()), Integer.parseInt(min.getText()), desc.getText()));
            refreshScript();
        }
    }

    private void showRemoveContestantDialog() {
    String name = JOptionPane.showInputDialog(this, "Enter Contestant Name to remove:");
    if (name != null && !name.isEmpty()) {
        submissions.removeSubmission(name);
        refreshScript();
    }
}

private void showRemovePauseDialog() {
    String[] options = {"Index", "Description"};
    JComboBox<String> combo = new JComboBox<>(options);
    JTextField input = new JTextField();

    Object[] message = { "Remove by:", combo, "Value:", input };

    int option = JOptionPane.showConfirmDialog(this, message, "Remove Pause", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        if (combo.getSelectedIndex() == 0) { // Index
            try {
                submissions.popPause(Integer.parseInt(input.getText()));
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Index");
            }
        } else { // Description
            submissions.removePause(input.getText());
        }
        refreshScript();
    }
}

private void showRemovePrepDialog() {
    String[] options = {"Index", "Description"};
    JComboBox<String> combo = new JComboBox<>(options);
    JTextField input = new JTextField();

    Object[] message = { "Remove by:", combo, "Value:", input };

    int option = JOptionPane.showConfirmDialog(this, message, "Remove Preparation", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        if (combo.getSelectedIndex() == 0) { // Index
            try {
                submissions.popPreparation(Integer.parseInt(input.getText()));
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Index");
            }
        } else { // Description
            submissions.removePreparation(input.getText());
        }
        refreshScript();
    }
}

    public static void showScript(Script script, LineUp submissions, int[] params) {
        SwingUtilities.invokeLater(() -> new ScriptDisplay(script, submissions, params).setVisible(true));
    }
}
