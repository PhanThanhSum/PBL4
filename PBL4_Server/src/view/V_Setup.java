package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.C_MyServer;

public class V_Setup extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;

    private JTextField tf_HomeDirectoryPath;
    private JButton btn_ChooseDirectory, btn_Start;

    public V_Setup() {
        init();
    }

    public void init() {
        //
        // JFrame
        //
        this.setTitle("Server Setup");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        Color backgroundColor = Color.decode("#CFE1B9");
        this.getContentPane().setBackground(backgroundColor);

        //
        // Center Panel (JTextField và Button)
        //
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(backgroundColor);
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // JTextField
        tf_HomeDirectoryPath = new JTextField("D:\\OneDrive - The University of Technology\\Desktop\\PBL4_RemoveFolder\\PBL4_Server\\home");
        tf_HomeDirectoryPath.setEditable(false); // Không cho chỉnh sửa
        tf_HomeDirectoryPath.setBackground(Color.WHITE);
        tf_HomeDirectoryPath.setPreferredSize(new Dimension(300, 30));
        centerPanel.add(tf_HomeDirectoryPath, BorderLayout.CENTER);

        // Button Choose Directory
        btn_ChooseDirectory = new JButton("Choose Directory");
        centerPanel.add(btn_ChooseDirectory, BorderLayout.SOUTH);
        btn_ChooseDirectory.addActionListener(this);
        this.add(centerPanel, BorderLayout.CENTER);

        //
        // Bottom Panel (Button Start)
        //
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        btn_Start = new JButton("Start");
        btn_Start.addActionListener(this);
        bottomPanel.add(btn_Start);
        this.add(bottomPanel, BorderLayout.SOUTH);
        //
        // Set JFrame Visible
        //
        this.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		switch (s) {
	    case "Choose Directory":
	    	JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Chỉ chọn thư mục
            int result = fileChooser.showOpenDialog(V_Setup.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                tf_HomeDirectoryPath.setText(selectedDirectory.getAbsolutePath());
            }
	        break;
	        
	    case "Start":
	    	C_MyServer server = new C_MyServer(8888);
	        server.startServer(tf_HomeDirectoryPath.getText());
	        break;
	        
	    default:
	        System.out.println("Lựa chọn không hợp lệ: " + s);
	        break;
		}
	}
	public static void main(String[] args) {
        new V_Setup();
    }
}
