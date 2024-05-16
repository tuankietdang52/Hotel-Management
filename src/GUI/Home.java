package GUI;

import BUS.HomeBUS;
import DTO.Product;
import Interface.IView;
import Utilities.SelectListCellRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Home implements IView {
    //region GUI Field

    private JPanel homePanel;
    private JPanel content;
    private JPanel productPanel;

    //endregion

    private HomeBUS homeBUS;

    public Home(){
        homeBUS = new HomeBUS();

        homePanel.setBackground(new Color(255, 238, 225));
        homePanel.setLayout(new GridLayout(1, 1));
        setup();
    }

    public void setup(){
        setupContentPanel();

        JScrollPane scroll = new JScrollPane(content,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.setPreferredSize(new Dimension(1200, 800));
        scroll.getVerticalScrollBar().setUnitIncrement(10);

        homePanel.add(scroll);
    }

    private void setupContentPanel(){
        content = new JPanel();
        content.setPreferredSize(new Dimension(1200, 800));
        content.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        content.setBackground(new Color(255, 238, 225));

        setupProductPanel();

        productPanel.setVisible(true);
        content.setVisible(true);
    }

    private void addSelectList(String[] list){
        JList<String> selectList = new JList<>(list);

        SelectListCellRenderer renderer = new SelectListCellRenderer(Color.RED);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        renderer.setVerticalAlignment(SwingConstants.CENTER);
        selectList.setCellRenderer(renderer);

        selectList.setPreferredSize(new Dimension(500, 30));
        selectList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        selectList.setVisibleRowCount(1);
        selectList.setFixedCellHeight(30);
        selectList.setFixedCellWidth(100);
        selectList.setBackground(new Color(255, 238, 225));
        selectList.setForeground(Color.white);
        selectList.setSelectionForeground(Color.white);
        selectList.setSelectionBackground(Color.red);
        selectList.setSelectedIndex(0);

        content.add(selectList);
    }

    private void setupProductPanel(){
        String[] list = {"San pham"};
        addSelectList(list);

        productPanel = new JPanel();
        productPanel.setPreferredSize(new Dimension(1200, 200));
        productPanel.setBorder(new LineBorder(Color.BLACK, 1, true));
        productPanel.setBackground(new Color(255, 238, 225));

        content.add(productPanel);

        int length = homeBUS.getListProduct().size();

        if (length == 0){
            productPanel.setLayout(new BorderLayout());
            addNoneProductText(productPanel);
            return;
        }

        int col = 3;
        int row = (length / col) + 1;

        productPanel.setLayout(new GridLayout(row, col));
    }

    private void addProduct(){

    }

    private void addNoneProductText(JPanel container){
        JLabel textLabel = new JLabel("", JLabel.CENTER);
        textLabel.setText("Khong co san pham");
        textLabel.setForeground(Color.red);
        textLabel.setPreferredSize(new Dimension(1200, 30));

        container.add(textLabel, BorderLayout.CENTER);
    }

    @Override
    public JPanel getPanel() {
        return homePanel;
    }
}
