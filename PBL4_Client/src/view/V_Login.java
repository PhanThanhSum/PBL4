package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class V_Login extends JFrame {
    private static final long serialVersionUID = 1L;

    // Components
    private JLabel lb_UserName;
    private JLabel lb_Password;
    private JTextField tf_UserName;
    private JTextField tf_Password;
    private JButton btn_Login;

    // Constructor
    public V_Login() {
        init();
    }

    // Initialize components and layout
    private void init() {
        //
        // JFrame configuration
        //
        this.setTitle("Đăng nhập");
        this.setSize(853, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.decode("#CFE1B9"));

        //
        // Center panel for form layout
        //
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.decode("#CFE1B9"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets.set(10, 10, 10, 10); // Add padding between components

        // Username label and text field
        lb_UserName = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(lb_UserName, gbc);

        tf_UserName = new JTextField();
        tf_UserName.setPreferredSize(new Dimension(200, 30)); // Set size of the text field
        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(tf_UserName, gbc);

        // Password label and text field
        lb_Password = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(lb_Password, gbc);

        tf_Password = new JTextField();
        tf_Password.setPreferredSize(new Dimension(200, 30)); // Set size of the text field
        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(tf_Password, gbc);

        //
        // South panel for the login button
        //
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.decode("#CFE1B9"));

        btn_Login = new JButton("Đăng nhập");
        btn_Login.setPreferredSize(new Dimension(150, 40)); // Optional, for consistent button size
        southPanel.add(btn_Login);

        //
        // Add panels to frame
        //
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }

    // Main method for testing
    public static void main(String[] args) {
        V_Login loginFrame = new V_Login();
        loginFrame.setVisible(true);
    }
}
