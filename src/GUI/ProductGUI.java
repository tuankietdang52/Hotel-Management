package GUI;

import BUS.ProductBUS;
import Interface.IView;

import javax.swing.*;
import java.awt.*;

public class ProductGUI implements IView {
    //region GUI Field

    private JPanel contentPanel;

    //endregion

    private ProductBUS productBUS;

    public ProductGUI(){
        productBUS = new ProductBUS();

        setupPanel();
    }

    private void setupPanel(){
        if (contentPanel == null) return;

        contentPanel.setPreferredSize(new Dimension(1000, 900));
        contentPanel.setMaximumSize(contentPanel.getPreferredSize());
    }

    @Override
    public JPanel getPanel() {
        return contentPanel;
    }
}
