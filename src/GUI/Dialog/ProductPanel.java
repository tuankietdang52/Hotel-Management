package GUI.Dialog;

import DTO.Product;
import Utilities.RoundBorder;

import javax.swing.*;
import java.awt.*;

public class ProductPanel extends JPanel {
    private final Product product;
    private JPanel componentPanel;

    public ProductPanel(Product product){
        this.product = product;

        setupPanel();
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void setupPanel(){
        setBorder(new RoundBorder(Color.BLACK, 10));
        setPreferredSize(new Dimension(200, 250));
        setMaximumSize(getPreferredSize());

        setLayout(new GridBagLayout());

        setupComponent();
    }

    private void setupComponent(){
        componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));

        setupImage();
        setupNameandPrice();

        add(componentPanel);
    }

    private void setupImage(){
        JLabel image = new JLabel();

        image.setBorder(new RoundBorder(Color.black, 10));
        image.setPreferredSize(new Dimension(100, 100));
        image.setMaximumSize(image.getPreferredSize());
        image.setMinimumSize(image.getPreferredSize());

        componentPanel.add(image);
    }

    private void setupNameandPrice(){
        JLabel name = new JLabel(product.getProductName());
        name.setFont(name.getFont().deriveFont(Font.BOLD, 20));

        JLabel price = new JLabel(String.valueOf(product.getPrice()));
        price.setFont(price.getFont().deriveFont(Font.BOLD, 15));
        price.setForeground(Color.red);

        componentPanel.add(name);
        componentPanel.add(price);
    }
}
