import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScriptDisplay extends JFrame {
    private JTextArea textArea;
    private Script script;
    private LineUp submissions;
    private int[] timingParams;
    private ScheduleCleaner cleaning;
    private TimelinePanel timelinePanel; 
    private boolean isNeat = true; 


    public ScriptDisplay(Script script, LineUp submissions, int[] timingParams) {
        this.script = script;
        this.submissions = submissions;
        this.timingParams = timingParams;
        this.cleaning = new ScheduleCleaner();

        setTitle("Event Script Manager");
        setSize(750, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setText(cleaning.makeNeat(script.toString()));
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(750, 400));

        // --- Timeline Setup ---
        timelinePanel = new TimelinePanel();
        
        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        JButton btnContestant = new JButton("Add Contestant");
        JButton btnPause = new JButton("Add Pause");
        JButton btnPrep = new JButton("Add Stageprep");
        JButton btnRemContestant = new JButton("Remove Contestant");
        JButton btnRemPause = new JButton("Remove Pause");
        JButton btnRemPrep = new JButton("Remove Prep");
        JButton btnToggle = new JButton("Toggle Neat View");
        btnToggle.addActionListener(e -> {
            isNeat = !isNeat; // Flip the state
            refreshScript();  // Refresh the display
        });

        // Action Listeners
        btnContestant.addActionListener(e -> showContestantDialog());
        btnPause.addActionListener(e -> showPauseDialog());
        btnPrep.addActionListener(e -> showPrepDialog());
        btnRemContestant.addActionListener(e -> showRemoveContestantDialog());
        btnRemPause.addActionListener(e -> showRemovePauseDialog());
        btnRemPrep.addActionListener(e -> showRemovePrepDialog());

        buttonPanel.add(btnContestant);
        buttonPanel.add(btnPause);
        buttonPanel.add(btnPrep);
        buttonPanel.add(btnRemContestant);
        buttonPanel.add(btnRemPause);
        buttonPanel.add(btnRemPrep);
        buttonPanel.add(btnToggle);

        // --- Combined Bottom Layout ---
        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.add(timelinePanel, BorderLayout.NORTH); // Timeline above buttons
        bottomContainer.add(buttonPanel, BorderLayout.CENTER);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomContainer, BorderLayout.SOUTH);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    // Custom Panel to draw the timeline
    private class TimelinePanel extends JPanel {
        public TimelinePanel() {
            setPreferredSize(new Dimension(700, 40)); // Fixed height for the bar
            setBorder(BorderFactory.createTitledBorder("Visual Schedule"));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Get the list of segments from your Script object
            // Assuming script.getSegments() returns a List of objects representing the rows
            List<?> segments = script.getSegments(); 
            if (segments == null || segments.isEmpty()) return;

            int w = getWidth() - 20;
            int h = getHeight() - 25;
            int x = 10;
            int y = 20;

            double segmentWidth = (double) w / segments.size();

            for (int i = 0; i < segments.size(); i++) {
                String desc = segments.get(i).toString();
                
                // Color Logic
                if (desc.contains("Empty Slot") || desc.contains("Other preparation for concert")) {
                    g.setColor(new Color(255, 100, 100)); // Light Red
                } else {
                    g.setColor(new Color(100, 255, 100)); // Light Green
                }

                int drawX = x + (int)(i * segmentWidth);
                int drawW = (int)segmentWidth;
                
                g.fillRect(drawX, y, drawW, h);
                g.setColor(Color.BLACK);
                g.drawRect(drawX, y, drawW, h); // Outline
            }
        }
    }

    private void refreshScript() {
        this.script = new Script(timingParams[0], timingParams[1], timingParams[2], 
                                timingParams[3], timingParams[4], timingParams[5], 
                                timingParams[6], submissions);
        
        // Toggle logic
        String content = isNeat ? cleaning.makeNeat(script.toString()) : script.toString();
        textArea.setText(content);
        
        timelinePanel.repaint();
    }


    // ... Keep all your existing showDialog methods here ...

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
