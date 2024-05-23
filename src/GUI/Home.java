package GUI;

import BUS.ProductBUS;
import Interface.IView;

import javax.swing.*;
import java.awt.*;

public class Home implements IView{
    //region GUI Field

    private JPanel homePanel;
    private JPanel content;
    private JPanel salePanel;

    //endregion

    //region BUS Field

    private final ProductBUS productBUS;

    //endregion

    public Home(){
        productBUS = new ProductBUS();
        setupView();
    }

    @Override
    public void setupView() {
        if (homePanel == null) throw new NullPointerException("Home Panel is null");

        homePanel.setBackground(new Color(255, 238, 225));
        homePanel.setPreferredSize(new Dimension(1000, 800));
        homePanel.setMaximumSize(homePanel.getPreferredSize());
        homePanel.setLayout(new GridBagLayout());
    }

    @Override
    public void resetView() {

    }

    @Override
    public JPanel getPanel() {
        return homePanel;
    }
}
