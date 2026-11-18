package qlsv;

import qlsv.ui.LoginForm;

import javax.swing.*;
import java.awt.Font;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
        } catch (Exception ignored) { }
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
