package GUI;

import BUS.ProductBUS;
import DTO.Product;
import DTO.ProductType;
import Utilities.*;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class ProductDialog extends JDialog {
    //region GUI Field

    private JPanel dialogPanel;
    private JPanel contentPanel;
    private JTextField codeTextField;
    private JTextField nameTextField;
    private JComboBox<ProductType> typeComboBox;
    private JTextField priceTextField;
    private JTextField quantityTextField;
    private JTextArea desTextArea;
    private Vector<ProductType> productTypes;

    //endregion

    private ProductBUS productBUS;
    private Product currentProduct;
    private boolean isAdjust = false;

    public ProductDialog(String title){
        setPreferredSize(new Dimension(700, 700));
        setSize(getPreferredSize());
        setModal(true);
        setLocationRelativeTo(null);

        setTitle(title);
        setup();

        setupContentPanel();
        setupButtonPanel();

        add(dialogPanel);
    }

    public ProductDialog(String title, Product currentProduct){
        setPreferredSize(new Dimension(700, 700));
        setSize(getPreferredSize());
        setModal(true);
        setLocationRelativeTo(null);

        this.currentProduct = currentProduct;
        isAdjust = true;

        setTitle(title);
        setup();

        setupContentPanel();
        writeData();
        setupButtonPanel();

        add(dialogPanel);
    }

    private void setup(){
        productBUS = new ProductBUS();

        dialogPanel = new JPanel();
        dialogPanel.setPreferredSize(getPreferredSize());
        dialogPanel.setSize(getSize());

        dialogPanel.setBackground(new Color(255, 238, 225));
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
    }

    private void setupContentPanel(){
        contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(700, 500));
        contentPanel.setBackground(new Color(255, 238, 225));

        contentPanel.setLayout(new GridBagLayout());

        setupComponent();
        dialogPanel.add(contentPanel);
    }

    private void addToContentPanel(Component component,
                                   int gridx,
                                   int gridy,
                                   int gridwidth,
                                   int gridheight,
                                   Insets insets){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.insets = insets;

        contentPanel.add(component, constraints);
    }

    private JPanel createLabel(String labelText){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 45));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());
        panel.setBackground(new Color(255, 238, 225));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(Font.PLAIN, 20));
        label.setPreferredSize(new Dimension(200, 40));
        label.setMaximumSize(label.getPreferredSize());
        label.setForeground(Color.black);
        label.setHorizontalAlignment(SwingConstants.LEFT);

        panel.add(label);
        return panel;
    }

    private JTextField createTextField(){
        JTextField textField = new JTextField();

        textField.setPreferredSize(new Dimension(300, 30));
        textField.setMaximumSize(textField.getPreferredSize());
        textField.setMinimumSize(textField.getPreferredSize());
        textField.setBackground(new Color(237, 221, 209));
        textField.setForeground(Color.black);
        textField.setFont(textField.getFont().deriveFont(Font.PLAIN, 15));
        textField.setBorder(null);

        return textField;
    }

    private JTextArea createTextArea(){
        JTextArea textArea = new JTextArea();

        textArea.setPreferredSize(new Dimension(300, 90));
        textArea.setMaximumSize(textArea.getPreferredSize());
        textArea.setMinimumSize(textArea.getPreferredSize());
        textArea.setBackground(new Color(237, 221, 209));
        textArea.setForeground(Color.black);
        textArea.setFont(textArea.getFont().deriveFont(Font.PLAIN, 15));
        textArea.setBorder(null);
        textArea.setLineWrap(true);

        return textArea;
    }

    private JPanel createProductTypeComboBox(Vector<ProductType> items){
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 60));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());
        panel.setBackground(new Color(255, 238, 225));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        typeComboBox = new JComboBox<>(items);
        typeComboBox.setRenderer(new ComboBoxRenderer(new Color(255, 238, 225)));
        typeComboBox.setPreferredSize(new Dimension(300, 60));
        typeComboBox.setMaximumSize(typeComboBox.getPreferredSize());
        typeComboBox.setMinimumSize(typeComboBox.getPreferredSize());
        typeComboBox.setBorder(new RoundBorder(Color.black, 10));

        typeComboBox.setFont(new Font("Times new Roman", Font.PLAIN, 20));

        panel.add(typeComboBox);
        return panel;
    }

    private void setupComponent(){
        codeTextField = createTextField();
        nameTextField = createTextField();
        priceTextField = createTextField();
        quantityTextField = createTextField();
        desTextArea = createTextArea();

        productTypes = new Vector<>(productBUS.getListProductType());
        JPanel typeComboBoxPanel = createProductTypeComboBox(productTypes);

        addToContentPanel(createLabel("Tên sản phẩm:"), 0, 0, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Loại:"), 1, 0, 1, 1,
                new Insets(0, 0, 10, 0));
        addToContentPanel(nameTextField, 0, 1, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(typeComboBoxPanel, 1, 1, 1, 1,
                new Insets(0, 0, 10, 0));

        addToContentPanel(createLabel("Đơn giá:"), 0, 2, 1, 1,
                new Insets(0, 0, 10, 0));
        addToContentPanel(createLabel("Số lượng tồn kho:"), 1, 2, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(priceTextField, 0, 3, 1, 1,
                new Insets(0, 0, 10, 0));
        addToContentPanel(quantityTextField, 1, 3, 1, 1,
                new Insets(0, 0, 10, 0));

        addToContentPanel(createLabel("Mô tả"), 0, 4, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(desTextArea, 0, 5, 1, 3,
                new Insets(0, 0, 10, 0));

    }

    private void writeData(){
        codeTextField.setText(currentProduct.getProductCode());
        nameTextField.setText(currentProduct.getProductName());
        quantityTextField.setText(Integer.toString(currentProduct.getQuantity()));
        desTextArea.setText(currentProduct.getDescription());

        priceTextField.setText(Integer.toString((int) currentProduct.getPrice()));

        int curProductTypeIndex = getCurrentProductTypeIndex(currentProduct.getTypeCode());
        typeComboBox.setSelectedIndex(curProductTypeIndex);
    }

    private int getCurrentProductTypeIndex(String code){
        for (int i = 0; i < productTypes.size(); i++){
            if (Objects.equals(code, productTypes.elementAt(i).getTypeCode())){
                return i;
            }
        }
        return -1;
    }

    private void setupButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(700, 200));
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        buttonPanel.setBackground(new Color(255, 238, 225));

        buttonPanel.setLayout(new GridBagLayout());

        RoundButton saveButton = new RoundButton(10, "Lưu");

        saveButton.setBackground(Color.green);
        saveButton.setForeground(Color.white);
        saveButton.setPreferredSize(new Dimension(100, 50));
        saveButton.setMaximumSize(saveButton.getPreferredSize());

        saveButton.addActionListener(e ->{
            if (!isAdjust) addProduct();
            else saveProductInfor();
        });

        buttonPanel.add(saveButton);
        dialogPanel.add(buttonPanel);
    }

    private ArrayList<String> gatherInput(){
        ProductType type = (ProductType) typeComboBox.getSelectedItem();

        if (type == null) throw new NullPointerException("type is null");

        String typeCode = type.getTypeCode();

        return new ArrayList<>(){{
            add(nameTextField.getText());
            add(typeCode);
            add(quantityTextField.getText());
            add(desTextArea.getText());
            add(priceTextField.getText());
        }};
    }

    private boolean checkValid(ArrayList<String> input){
        Pair<Boolean, String> res = productBUS.checkValidInput(input);

        if (!res.getFirst()){
            AppManager.showPopUpMessage(res.getLast());
            return false;
        }

        return true;
    }

    private void addProduct(){
        ArrayList<String> input = gatherInput();

        if (!checkValid(input)) return;

        Pair<Boolean, String> res = productBUS.addProductToDatabase(input);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }

    private void saveProductInfor(){
        ArrayList<String> input = gatherInput();

        if (!checkValid(input)) return;

        Pair<Boolean, String> res = productBUS.adjustProduct(input, currentProduct.getProductCode());
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }
}
