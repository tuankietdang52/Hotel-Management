package GUI;

import BUS.ProductBUS;
import BUS.SaleBUS;
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
    private final SaleBUS saleBUS;

    //endregion

    public Home(){
        productBUS = new ProductBUS();
        saleBUS = new SaleBUS();

        homePanel.setBackground(new Color(255, 238, 225));
        homePanel.setPreferredSize(new Dimension(1000, 800));
        homePanel.setMaximumSize(homePanel.getPreferredSize());
        homePanel.setLayout(new GridBagLayout());
    }

    @Override
    public JPanel getPanel() {
        return homePanel;
    }
}
