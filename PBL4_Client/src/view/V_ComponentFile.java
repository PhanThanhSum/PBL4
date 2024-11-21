package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class V_ComponentFile extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;

    // Attributes
    private String fileName;
    private JLabel lbFileName;
    private boolean isSelected;
    private JPopupMenu popupMenu;
    private V_TrangChu parent; // Tham chiếu đến JFrame chứa component này

    // Constructor
    public V_ComponentFile(String fileName, V_TrangChu parent) {
        this.fileName = fileName;
        this.parent = parent;
        this.isSelected = false;
        init();
    }

    // Initialize components
    private void init() {
        // JPanel setup
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        this.setMinimumSize(new Dimension(0, 40));
        this.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        this.setBackground(Color.decode("#F2DDDC"));
        this.setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // JLabel for file name
        String displayText = fileName;
        if (fileName.length() > 70) { // Giới hạn ký tự hiển thị
            displayText = fileName.substring(0, 70) + "...";
        }
        lbFileName = new JLabel(displayText);
        this.add(lbFileName, BorderLayout.CENTER);
        this.setToolTipText(this.fileName);

        // Context menu (popup menu)
        createPopupMenu();

        // Add mouse listener
        this.addMouseListener(this);
    }

    // Create context menu
    private void createPopupMenu() {
        popupMenu = new JPopupMenu();

        // Add menu items
        JMenuItem menuItem1 = new JMenuItem("DownLoad");
        JMenuItem menuItem2 = new JMenuItem("Delete");
        JMenuItem menuItem3 = new JMenuItem("Copy");
        menuItem1.addActionListener(parent);
        menuItem2.addActionListener(parent);
        menuItem3.addActionListener(parent);

        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);
        popupMenu.add(menuItem3);
    }

    // Getter and setter for fileName
    public String getFileName() {
        return fileName;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        setBackground(isSelected ? Color.decode("#F6BCBA") : Color.decode("#F2DDDC")); // Cập nhật màu nền
    }

    public boolean isSelected() {
        return isSelected;
    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) { // Right-click to show menu
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        } else if (e.isControlDown()) { // Nhấn giữ ctrl và click
            this.isSelected = !this.isSelected;
        } else { // Click bình thường
            parent.clearSelections();
            this.isSelected = true;
        }
        parent.updateSelections(this); // Cập nhật danh sách
        this.setSelected(this.isSelected); // Hiển thị trạng thái
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    	
    }

    @Override
    public void mouseExited(MouseEvent e) {
    	
    }
}
