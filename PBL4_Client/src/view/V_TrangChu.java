package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;

import controller.C_MyClient;

public class V_TrangChu extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private C_MyClient client;
	
	private JPanel menuPanel;
    private JFileChooser fileChooser;
    
    private JPanel fileListPanel; // Panel chứa các FileView
    private JScrollPane scrollPane;
    
    private List<V_ComponentFile> selectedComponents; // Danh sách các component được chọn
    
    
	
	public V_TrangChu() {
		init();
		client = new C_MyClient("localhost", 8888);
		selectedComponents = new ArrayList<>();
		
		String[] listFilesName = client.load();
        this.showListFiles(listFilesName);
	}
	
	public void init() {
//    	try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		} catch (ClassNotFoundException e) {
//			
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e) {
//			
//			e.printStackTrace();
//		}
		
		try {
		    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
    	//
    	//this JFrame
    	//
        this.setTitle("Trang chủ");
        this.setSize(853, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        //
        //fileChooser
        //
        this.fileChooser = new JFileChooser();
        //
        //fileListPanel
        //
        fileListPanel = new JPanel();
        fileListPanel.setLayout(new FlowLayout());
        fileListPanel.setLayout(new BoxLayout(fileListPanel, BoxLayout.Y_AXIS));
        fileListPanel.setBackground(Color.decode("#F4E7F8"));
        //
        //scrollPanel
        //
        scrollPane = new JScrollPane(fileListPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER); // Thêm scroll pane vào giữa
        //
        // menuPanel
        //
        this.menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS)); // Sắp xếp theo chiều dọc
        menuPanel.setBackground(Color.decode("#E3AADD"));
        this.add(menuPanel, BorderLayout.WEST);
        //
        // menuItems
        //
        String[] menuItems = {"UpLoad", "Load", "DownLoad"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa theo chiều ngang
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Chiều ngang tự động, chiều cao cố định
            button.addActionListener(this);
            menuPanel.add(button);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách giữa các nút
        }
        
        //
        // setVisible
        //
        this.setVisible(true);
    }
	
	public void showListFiles(String[] listFilesName) {
        fileListPanel.removeAll(); // Xóa các thành phần cũ nếu có
        this.selectedComponents.clear();
        if (listFilesName != null) {
            for (String fileName : listFilesName) {
                V_ComponentFile componentFile = new V_ComponentFile(fileName, this);
                fileListPanel.add(componentFile);
            }
            fileListPanel.revalidate();
            fileListPanel.repaint();
        }
    }

    public void updateSelections(V_ComponentFile component) {
        if (component.isSelected() && !selectedComponents.contains(component)) {
            selectedComponents.add(component); // Thêm vào danh sách chọn
        } else if (!component.isSelected()) {
            selectedComponents.remove(component); // Bỏ khỏi danh sách chọn
        }
    }

    public void clearSelections() {
        for (V_ComponentFile component : selectedComponents) {
            component.setSelected(false); // Bỏ chọn
        }
        selectedComponents.clear(); // Xóa danh sách chọn
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		switch (s) {
	    case "UpLoad":
	        int result = fileChooser.showOpenDialog(this); // Hiển thị JFileChooser
	        if (result == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            String fileName = selectedFile.getAbsolutePath();
	            client.upLoad(fileName);
//	            String[] listFilesName = client.load();
//		        this.showListFiles(listFilesName);
	        }
	        break;

	    case "Load":
	        String[] listFilesName = client.load();
	        this.showListFiles(listFilesName);
	        break;
	        
	    case "DownLoad":
	    	if(!this.selectedComponents.isEmpty()) {
	    		List<String> selectedFiles = new ArrayList<String>();
	    		for(V_ComponentFile component : this.selectedComponents) {
	    			selectedFiles.add(component.getFileName());
	    		}
	    	client.downLoadFile(selectedFiles);
	    	} else {
	    		JOptionPane.showMessageDialog(null, "Bạn chưa chọn File muốn xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
	    	}
	        break;
	        
	    case "Delete":
	    	if(!this.selectedComponents.isEmpty()) {
	    		List<String> selectedFiles = new ArrayList<String>();
	    		for(V_ComponentFile component : this.selectedComponents) {
	    			selectedFiles.add(component.getFileName());
	    		}
	    	client.deleteFile(selectedFiles);
	    	
//	    	String[] listFilesName2 = client.load();
//	        this.showListFiles(listFilesName2);
	    	} else {
	    		JOptionPane.showMessageDialog(null, "Bạn chưa chọn File muốn tải xuống", "Lỗi", JOptionPane.ERROR_MESSAGE);
	    	}
	        break;
	        

	    default:
	        System.out.println("Lựa chọn không hợp lệ: " + s);
	        break;
		}
	}

    
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String s = e.getActionCommand();
//        switch (s) {
//            case "UpLoad":
//                int result = fileChooser.showOpenDialog(this);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File selectedFile = fileChooser.getSelectedFile();
//                    String fileName = selectedFile.getAbsolutePath();
//                    // Sử dụng SwingWorker để thực hiện thao tác upload
//                    new SwingWorker<Void, Void>() {
//                        @Override
//                        protected Void doInBackground() throws Exception {
//                            client.upLoad(fileName);
//                            return null;
//                        }
//
//                        @Override
//                        protected void done() {
//                            String[] listFilesName = client.load();
//                            showListFiles(listFilesName);
//                        }
//                    }.execute();
//                }
//                break;
//
//            case "Load":
//                new SwingWorker<String[], Void>() {
//                    @Override
//                    protected String[] doInBackground() throws Exception {
//                        return client.load();
//                    }
//
//                    @Override
//                    protected void done() {
//                        try {
//                            showListFiles(get());
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }.execute();
//                break;
//
//            case "DownLoad":
//                if (!this.selectedComponents.isEmpty()) {
//                    List<String> selectedFiles = new ArrayList<>();
//                    for (V_ComponentFile component : this.selectedComponents) {
//                        selectedFiles.add(component.getFileName());
//                    }
//                    new SwingWorker<Void, Void>() {
//                        @Override
//                        protected Void doInBackground() throws Exception {
//                            client.downLoadFile(selectedFiles);
//                            return null;
//                        }
//                    }.execute();
//                } else {
//                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn File muốn tải xuống", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                }
//                break;
//
//            case "Delete":
//                if (!this.selectedComponents.isEmpty()) {
//                    List<String> selectedFiles = new ArrayList<>();
//                    for (V_ComponentFile component : this.selectedComponents) {
//                        selectedFiles.add(component.getFileName());
//                    }
//                    new SwingWorker<Void, Void>() {
//                        @Override
//                        protected Void doInBackground() throws Exception {
//                            client.deleteFile(selectedFiles);
//                            return null;
//                        }
//
//                        @Override
//                        protected void done() {
//                            String[] listFilesName2 = client.load();
//                            showListFiles(listFilesName2);
//                        }
//                    }.execute();
//                } else {
//                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn File muốn xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                }
//                break;
//
//            default:
//                System.out.println("Lựa chọn không hợp lệ: " + s);
//                break;
//        }
//    }
}
