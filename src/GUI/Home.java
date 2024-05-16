package GUI;

import BUS.ProductBUS;
import Interface.IView;
import Utilities.RoundBorder;
import Utilities.SelectListCellRenderer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Home implements IView{
    //region GUI Field

    private JPanel homePanel;
    private JPanel content;
    private JPanel productPanel;

    //endregion

    //region BUS Field

    private final ProductBUS productBUS;

    //endregion

    public Home(){
        productBUS = new ProductBUS();

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
        content.setPreferredSize(new Dimension(1200, 700));
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
        String[] list = {"San pham", "Moi ra mat"};
        addSelectList(list);

        productPanel = new JPanel();
        productPanel.setPreferredSize(new Dimension(1198, 200));
        productPanel.setBorder(new LineBorder(Color.BLACK, 1));
        productPanel.setBackground(new Color(255, 238, 225));

        content.add(productPanel);

        int length = productBUS.getListProduct().size();

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
