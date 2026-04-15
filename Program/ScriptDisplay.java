import javax.swing.*;
import java.awt.*;

public class ScriptDisplay extends JFrame {

    public ScriptDisplay(Script script) {
        // Set up the window (JFrame)
        setTitle("Event Script - Detailed Agenda");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Create a text area to hold the script
        JTextArea textArea = new JTextArea();
        textArea.setText(script.toString());
        textArea.setEditable(false); // Make it read-only
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Good for alignment
        textArea.setMargin(new Insets(10, 10, 10, 10));

        // Wrap the text area in a scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add to frame
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void showScript(Script script) {
        // Run UI on the Event Dispatch Thread (standard practice for Swing)
        SwingUtilities.invokeLater(() -> {
            new ScriptDisplay(script).setVisible(true);
        });
    }
}
