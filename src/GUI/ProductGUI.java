package GUI;

import BUS.ProductBUS;
import DTO.Product;
import DTO.ProductTableModel;
import DTO.ProductType;
import Utilities.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class ProductGUI extends ViewWithTable {
    //region GUI Field

    private JPanel contentPanel;
    private JPanel optionPanel;

    //endregion

    private final ProductBUS productBUS;

    public ProductGUI(){
        super(new ProductTableModel(new ProductBUS().getProductDataTable()));

        productBUS = new ProductBUS();

        setupPanel();

        setupOptionPanel();
        optionPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        setupFilter();

        setupTable();
        setupSearchPanel();

        contentPanel.add(searchPanel);
        contentPanel.add(scrollTable);
    }

    private void setupPanel(){
        if (contentPanel == null) return;

        contentPanel.setBackground(new Color(255, 238, 225));
        contentPanel.setPreferredSize(new Dimension(1000, 900));
        contentPanel.setMaximumSize(contentPanel.getPreferredSize());
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    }

    private void setupOptionPanel(){
        optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.X_AXIS));
        optionPanel.setPreferredSize(new Dimension(1000, 200));
        optionPanel.setBackground(new Color(255, 238, 225));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setPreferredSize(new Dimension(490, 200));
        container.setMaximumSize(container.getPreferredSize());
        container.setBackground(new Color(255, 238, 225));

        RoundButton addButton = setupOptionButton("plus.png", new Color(0, 255, 0),
                e -> {
                    ProductDialog addDialog = new ProductDialog("Thêm Sản Phẩm");
                    addDialog.setVisible(true);
                    addEventDialog(addDialog);
                });
        RoundButton adjustButton = setupOptionButton("adjust.png", new Color(255, 127, 39),
                e -> showAdjustDialog());
        RoundButton removeButton = setupOptionButton("delete.png", new Color(255, 0, 0),
                e -> deleteProduct());

        container.add(addButton);
        container.add(adjustButton);
        container.add(removeButton);

        addButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        adjustButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        removeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        optionPanel.add(container);
        contentPanel.add(optionPanel);
    }

    private void showAdjustDialog(){
        if (dataTable.getSelectedRow() == -1){
            AppManager.showPopUpMessage("Vui long chon 1 san pham truoc");
            return;
        }
        String productCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 0);
        Product cur = productBUS.getProductByCode(productCode);

        ProductDialog adjustDialog = new ProductDialog("Sửa sản phẩm", cur);
        adjustDialog.setVisible(true);
        addEventDialog(adjustDialog);
    }

    private void deleteProduct(){
        if (dataTable.getSelectedRow() == -1){
            AppManager.showPopUpMessage("Vui long chon 1 san pham truoc");
            return;
        }

        if (!AppManager.showConfirmMessage("Ban co chac muon xoa san pham nay khong ?")){
            return;
        }

        String productCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 0);

        Pair<Boolean, String> res = productBUS.removeProduct(productCode);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()) setTableData(productBUS.getProductDataTable());
    }

    private void addEventDialog(JDialog dialog){
        dialog.addWindowListener(new WindowCloseListener() {
            @Override
            public void windowClosed(WindowEvent e) {
                setTableData(productBUS.getProductDataTable());
            }
        });
    }

    private RoundButton setupOptionButton(String imageName, Color color, ActionListener callback){
        ImageIcon image = ImageUtils.loadImageResource(getClass(), "/Assets/" + imageName);
        image = ImageUtils.resizeImage(image, 80, 30);
        RoundButton button = new RoundButton(20, image);

        button.setPreferredSize(new Dimension(110, 60));
        button.setMaximumSize(button.getPreferredSize());
        button.setMinimumSize(button.getPreferredSize());

        button.setBackground(color);
        button.setBorder(new EmptyBorder(0, 0, 0, 100));

        button.addActionListener(callback);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }


    private void setupFilter(){
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setPreferredSize(new Dimension(490, 200));
        container.setMaximumSize(container.getPreferredSize());
        container.setBackground(new Color(255, 238, 225));

        JComboBox<ProductType> companyFilter = createCompanyFilter();
        companyFilter.setAlignmentX(Component.LEFT_ALIGNMENT);


        container.add(Box.createRigidArea(new Dimension(0, 70)));
        container.add(companyFilter);
        optionPanel.add(container);
    }

    private JComboBox<ProductType> createCompanyFilter(){
        JComboBox<ProductType> companyFilter = new JComboBox<>();
        companyFilter.addItem(new ProductType("all", "Tất cả"));

        for (ProductType productType : productBUS.getListProductType()){
            companyFilter.addItem(productType);
        }

        companyFilter.setPreferredSize(new Dimension(120, 50));
        companyFilter.setMaximumSize(new Dimension(120, 50));
        companyFilter.setBorder(new RoundBorder(Color.black, 10));
        companyFilter.setBackground(new Color(255, 228, 203));

        companyFilter.setRenderer(new ComboBoxRenderer(new Color(255, 238, 225)));
        companyFilter.addActionListener(e -> filtProductType(companyFilter));

        return companyFilter;
    }

    private void filtProductType(JComboBox<ProductType> companyFilter){
        var type = (ProductType) companyFilter.getModel().getSelectedItem();

        if (Objects.equals(type.getTypeCode(), "all")){
            setTableData(productBUS.getProductDataTable());
            return;
        }

        var filtData = productBUS.filtProductTableByType(type);
        setTableData(filtData);
    }

    protected void setupTableComponent(){
        TableColumnModel columnModel = dataTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(200);

        dataTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.setRowHeight(50);

        JTableHeader header = dataTable.getTableHeader();
        header.setPreferredSize(new Dimension(10, 50));
        header.setFont(header.getFont().deriveFont(Font.BOLD, 15));
        header.setBackground(new Color(255, 92, 95));

        dataTable.setColumnModel(columnModel);
        dataTable.setBackground(new Color(255, 228, 203));
        dataTable.setSelectionBackground(new Color(255, 0, 0, 100));
        dataTable.getTableHeader().setResizingAllowed(false);
        dataTable.getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public JPanel getPanel() {
        return contentPanel;
    }
}
