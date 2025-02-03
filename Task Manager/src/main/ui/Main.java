package ui;

import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JWindow splashScreen = new JWindow();
        JLabel splashLabel = new JLabel("Welcome to Task Manager!", JLabel.CENTER);
        splashLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
        splashLabel.setOpaque(true);
        splashLabel.setBackground(Color.BLACK);
        splashLabel.setForeground(Color.WHITE);

        splashScreen.getContentPane().add(splashLabel, BorderLayout.CENTER);
        splashScreen.setSize(400, 200);
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setVisible(true);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splashScreen.dispose();
        SwingUtilities.invokeLater(TaskManagerGUI::new);
    }
}