package GUI.Dialog;

import BUS.ProductBUS;
import DTO.Product;
import DTO.ProductType;
import Utilities.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class ProductDialog extends OptionDialog<Product> {
    //region GUI Field
    
    private JTextField nameTextField;
    private JComboBox<ProductType> typeComboBox;
    private JTextField priceTextField;
    private JTextField quantityTextField;
    private JTextArea desTextArea;
    private Vector<ProductType> productTypes;
    private JFileChooser imageChooser;
    private JPanel image;
    private String imagePath;

    //endregion

    private final ProductBUS productBUS;

    public ProductDialog(){
        super("Thêm Sản Phẩm", new Dimension(700, 700));
        productBUS = new ProductBUS();
    }

    public ProductDialog(Product current){
        super("Sửa sản phẩm", new Dimension(700, 700), current);
        productBUS = new ProductBUS();
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

    private JPanel createImagePanel(){
        JPanel panel = new JPanel();
        JLabel imageContainer = new JLabel();
        panel.add(imageContainer);

        return panel;
    }

    private ImageIcon createImage(String path){
        ImageIcon imageIcon = ImageUtils.loadImageAbsolute(path);
        imageIcon = ImageUtils.resizeImage(imageIcon, 120, 120);

        return imageIcon;
    }

    private RoundButton createAddImageButton(){
        RoundButton addImageButton = new RoundButton(10, "Chọn hình");
        addImageButton.setPreferredSize(new Dimension(80, 50));
        addImageButton.setForeground(Color.white);
        addImageButton.setBackground(new Color(255, 92, 95));

        addImageButton.addActionListener(e -> openImageChooser());

        return addImageButton;
    }

    @Override
    protected void setupComponent(){
        nameTextField = createTextField();
        priceTextField = createTextField();
        quantityTextField = createTextField();
        desTextArea = createTextArea();

        productTypes = new Vector<>(new ProductBUS().getListProductType());
        JPanel typeComboBoxPanel = createProductTypeComboBox(productTypes);

        image = createImagePanel();
        RoundButton addImageButton = createAddImageButton();

        addToContentPanel(createLabel("Tên sản phẩm:"), 0, 0, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Loại:"), 1, 0, 1, 1,
                new Insets(0, 0, 10, 0));
        addToContentPanel(nameTextField, 0, 1, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(typeComboBoxPanel, 1, 1, 1, 1,
                new Insets(0, 0, 10, 0));

        addToContentPanel(createLabel("Đơn giá:"), 0, 2, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Số lượng tồn kho:"), 1, 2, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(priceTextField, 0, 3, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(quantityTextField, 1, 3, 1, 1,
                new Insets(0, 0, 10, 0));

        addToContentPanel(createLabel("Mô tả"), 0, 4, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Hình ảnh: "), 1, 4, 1, 1,
                new Insets(0, 0, 10, 0));
        addToContentPanel(desTextArea, 0, 5, 1, 3,
                new Insets(0, 0, 10, 20));
        addToContentPanel(image, 1, 5, 1, 3,
                new Insets(0, 0, 10, 0));
        addToContentPanel(addImageButton, 1, 9, 1, 1,
                new Insets(0, 0, 10, 0));

    }

    private void openImageChooser(){
        imageChooser = new JFileChooser();
        imageChooser.setAcceptAllFileFilterUsed(false);
        imageChooser.addChoosableFileFilter(new FileNameExtensionFilter("Picture file (.png, .jpg)",
                "png", "jpg"));

        int isSave = imageChooser.showSaveDialog(null);

        if (isSave != JFileChooser.APPROVE_OPTION) return;

        JLabel label = (JLabel) image.getComponent(0);
        imagePath = imageChooser.getSelectedFile().getAbsolutePath();
        label.setIcon(createImage(imagePath));
    }

    @Override
    protected void writeData(){
        nameTextField.setText(current.getProductName());
        quantityTextField.setText(Integer.toString(current.getQuantity()));
        desTextArea.setText(current.getDescription());

        priceTextField.setText(Integer.toString((int) current.getPrice()));

        int curProductTypeIndex = getCurrentProductTypeIndex(current.getTypeCode());
        typeComboBox.setSelectedIndex(curProductTypeIndex);

        ImageIcon imageIcon = ImageUtils.loadImageAbsolute(current.getImage());

        if (imageIcon == null) return;

        imageIcon = ImageUtils.resizeImage(imageIcon, 150, 150);
        JLabel label = (JLabel) image.getComponent(0);
        label.setIcon(imageIcon);
    }

    private int getCurrentProductTypeIndex(String code){
        for (int i = 0; i < productTypes.size(); i++){
            if (Objects.equals(code, productTypes.elementAt(i).getTypeCode())){
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void setupButtonPanel(){
        super.setupButtonPanel();

        saveButton.addActionListener(e ->{
            if (!isAdjust) addProduct();
            else saveProductInfor();
        });
    }

    @Override
    protected Product getModel() {
        ProductType type = (ProductType) typeComboBox.getSelectedItem();

        if (type == null) throw new NullPointerException("type is null");
        String typeCode = type.getTypeCode();

        if (!productBUS.isValidNumberTextField(quantityTextField.getText(), "int",
                "Số lượng sai định dạng hoặc để trống")){
            return null;
        }

        if (!productBUS.isValidNumberTextField(priceTextField.getText(), "double",
                "Giá sai định dạng hoặc để trống")){
            return null;
        }

        int quantity = Integer.parseInt(quantityTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());

        String code = isAdjust ? current.getProductCode() : AppManager.generateRandomCode(10);
        return new Product(code, nameTextField.getText(), typeCode, quantity,
                desTextArea.getText(), imagePath, price);
    }

    private boolean checkValid(Product product){
        Pair<Boolean, String> res = productBUS.checkValidInput(product);

        if (!res.getFirst()){
            AppManager.showPopUpMessage(res.getLast());
            return false;
        }

        return true;
    }

    private void addProduct(){
        Product product = getModel();
        if (product == null) return;

        if (!checkValid(product)) return;

        Pair<Boolean, String> res = productBUS.addProductToDatabase(product);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }

    private void saveProductInfor(){
        Product product = getModel();
        if (product == null) return;

        if (!checkValid(product)) return;

        Pair<Boolean, String> res = productBUS.adjustProduct(product);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }
}
