package GUI;

import BUS.ProductBUS;
import DTO.Product;
import DTO.ProductTableModel;
import Interface.IView;
import Utilities.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ProductGUI implements IView {
    //region GUI Field

    private JPanel contentPanel;
    private JPanel optionPanel;
    private JPanel searchPanel;
    private JScrollPane scrollTable;
    private JTable productTable;
    private JTextField searchArea;

    //endregion

    private final ProductBUS productBUS;

    public ProductGUI(){
        productBUS = new ProductBUS();

        setupPanel();
        setupOptionPanel();
        setupTable();
        setupSearchAndFilter();

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
        optionPanel.setLayout(new GridBagLayout());
        optionPanel.setPreferredSize(new Dimension(1000, 150));
        optionPanel.setBackground(new Color(255, 238, 225));

        JPanel container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        container.setBackground(new Color(255, 238, 225));

        RoundButton addButton = setupOptionButton("plus.png", new Color(0, 255, 0),
                e -> {
                    ProductDialog addDialog = new ProductDialog("Thêm Sản Phẩm");
                    addDialog.setVisible(true);
                    addEventDialog(addDialog);
                });
        RoundButton adjustButton = setupOptionButton("adjust.png", new Color(255, 127, 39),
                e -> {
                    showAdjustDialog();
                });
        RoundButton removeButton = setupOptionButton("delete.png", new Color(255, 0, 0),
                e -> {
                    deleteProduct();
                });

        container.add(addButton);
        container.add(adjustButton);
        container.add(removeButton);

        optionPanel.add(container);
        contentPanel.add(optionPanel);
    }

    private void showAdjustDialog(){
        if (productTable.getSelectedRow() == -1){
            AppManager.showPopUpMessage("Vui long chon 1 san pham truoc");
            return;
        }
        String productCode = (String) productTable.getModel().getValueAt(productTable.getSelectedRow(), 0);
        Product cur = productBUS.getProductByCode(productCode);

        ProductDialog adjustDialog = new ProductDialog("Sửa sản phẩm", cur);
        adjustDialog.setVisible(true);
        addEventDialog(adjustDialog);
    }

    private void deleteProduct(){
        if (productTable.getSelectedRow() == -1){
            AppManager.showPopUpMessage("Vui long chon 1 san pham truoc");
            return;
        }

        if (!AppManager.showConfirmMessage("Ban co chac muon xoa san pham nay khong ?")){
            return;
        }

        String productCode = (String) productTable.getModel().getValueAt(productTable.getSelectedRow(), 0);

        Pair<Boolean, String> res = productBUS.removeProduct(productCode);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()) resetTable();
    }

    private void addEventDialog(JDialog dialog){
        dialog.addWindowListener(new WindowCloseListener() {
            @Override
            public void windowClosed(WindowEvent e) {
                resetTable();
            }
        });
    }

    private RoundButton setupOptionButton(String imageName, Color color, ActionListener callback){
        ImageIcon image = ImageUtils.loadImageResource(getClass(), "/Assets/" + imageName);
        image = ImageUtils.resizeImage(image, 80, 30);
        RoundButton button = new RoundButton(20, image);

        button.setPreferredSize(new Dimension(110, 60));
        button.setBackground(color);
        button.setBorder(new EmptyBorder(0, 0, 0, 100));

        button.addActionListener(callback);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void setupSearchAndFilter(){
        searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setPreferredSize(new Dimension(1000, 50));
        searchPanel.setBackground(new Color(255, 238, 225));

        setupSearch();
        setupFilter();
        contentPanel.add(searchPanel);
    }

    private void setupSearch(){
        searchArea = new SearchField("Search...");

        searchArea.setPreferredSize(new Dimension(600, 50));
        searchArea.setFont(searchArea.getFont().deriveFont(Font.PLAIN, 20));
        searchArea.setBorder(null);
        searchArea.setOpaque(false);
        searchArea.setBackground(new Color(237, 221, 209));

        setRowSowrter();

        searchPanel.add(searchArea);
    }

    private void setRowSowrter(){
        ProductTableModel model = (ProductTableModel) productTable.getModel();
        productTable.setRowSorter(model.createTableFilter(searchArea));
    }

    private void setupFilter(){
        JComboBox<String> a = new JComboBox<>();
        a.setPreferredSize(new Dimension(200, 50));
        a.setBorder(new RoundBorder(Color.black, 10));
        a.setBackground(new Color(255, 228, 203));

        a.setUI(new BasicComboBoxUI());

        searchPanel.add(a);
    }

    private void setupTable(){
        ArrayList<ArrayList<String>> data = productBUS.getProductToDataTable();

        productTable = new JTable(new ProductTableModel(data));

        productTable.setSize(1000, 600);

        setupTableComponent();

        scrollTable = new JScrollPane(productTable);
        scrollTable.setBackground(new Color(255, 238, 225));
        scrollTable.getViewport().setBackground(new Color(255, 238, 225));
        scrollTable.setPreferredSize(new Dimension(1000, 600));
    }

    public void resetTable(){
        ArrayList<ArrayList<String>> data = productBUS.getProductToDataTable();
        ProductTableModel model = (ProductTableModel) productTable.getModel();
        model.setData(data);
    }

    private void setupTableComponent(){
        TableColumnModel columnModel = productTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(200);

        productTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setRowHeight(50);

        JTableHeader header = productTable.getTableHeader();
        header.setPreferredSize(new Dimension(10, 50));
        header.setFont(header.getFont().deriveFont(Font.BOLD, 15));
        header.setBackground(new Color(255, 92, 95));

        productTable.setColumnModel(columnModel);
        productTable.setBackground(new Color(255, 228, 203));
        productTable.setSelectionBackground(new Color(255, 0, 0, 100));
        productTable.getTableHeader().setResizingAllowed(false);
        productTable.getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public JPanel getPanel() {
        return contentPanel;
    }
}
