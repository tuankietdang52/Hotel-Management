package GUI.Dialog;

import BUS.BillBUS;
import BUS.ProductBUS;
import DTO.BillDetail;
import DTO.Product;
import DTO.ProductTableModel;
import Utilities.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class BillDetailDialog extends OptionDialog<BillDetail> {
    //region GUI Field

    private JTextField quantityTextField;
    private JPanel nameProduct;
    private JPanel remainAmount;
    private JTable table;
    private JScrollPane tablePanel;

    //endregion

    private String billCode;
    private final ProductBUS productBUS;
    private final BillBUS billBUS;

    public BillDetailDialog(String billCode){
        super("Thêm sản phẩm vào hóa đơn", new Dimension(1000, 800));

        contentPanel.setPreferredSize(new Dimension(1000, 200));
        productBUS = new ProductBUS();
        billBUS = new BillBUS();

        this.billCode = billCode;

        setupProductTable();
        setupSearchPanel();
    }

    public BillDetailDialog(BillDetail current){
        super("Sửa số lượng sản phẩm", new Dimension(800, 400), current);

        contentPanel.setPreferredSize(new Dimension(800, 400));
        productBUS = new ProductBUS();
        billBUS = new BillBUS();

        writeData();
    }

    @Override
    protected void setupComponent(){
        if (!isAdjust) setupQuantityTextField(0, 0);
        else{
            setupQuantityTextField(0, 2);
            setupAdjustInfor();
        }
    }

    private void setupQuantityTextField(int gridx, int gridy){
        quantityTextField = createTextField();
        int right = gridx == 0 ? 20 : 0;

        addToContentPanel(createLabel("Số lượng:"), gridx, gridy,
                1, 1, new Insets(0, 0, 10, right));
        addToContentPanel(quantityTextField, gridx, gridy + 1, 1, 1,
                new Insets(0, 0, 10, right));
    }

    private void setupAdjustInfor(){
        Product product = new ProductBUS().getProductByCode(current.getProductCode());
        nameProduct = createLabel(product.getProductName());
        remainAmount = createLabel(Integer.toString(product.getQuantity()));

        addToContentPanel(createLabel("Tên sản phẩm:"), 0, 0,
                1, 1, new Insets(0, 0, 10, 20));
        addToContentPanel(nameProduct, 0, 1, 1, 1,
                new Insets(0, 0, 10, 20));

        addToContentPanel(createLabel("Số lượng trong kho:"), 1, 0,
                1, 1, new Insets(0, 0, 10, 0));
        addToContentPanel(remainAmount, 1, 1, 1, 1,
                new Insets(0, 0, 10, 0));
    }

    private void setupSearchPanel(){
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setPreferredSize(new Dimension(1000, 50));
        searchPanel.setBackground(new Color(255, 238, 225));

        searchPanel.add(createSearchField());
        dialogPanel.add(searchPanel, 1);
    }

    private SearchField createSearchField(){
        SearchField searchArea = new SearchField("Search...");

        searchArea.setPreferredSize(new Dimension(600, 50));
        searchArea.setFont(searchArea.getFont().deriveFont(Font.PLAIN, 20));
        searchArea.setBorder(null);
        searchArea.setOpaque(false);
        searchArea.setBackground(new Color(237, 221, 209));
        searchArea.setForeground(Color.black);

        setRowSowrter(searchArea);
        return searchArea;
    }

    private void setRowSowrter(JTextField searchArea){
        TableModelWithFilter model = (TableModelWithFilter) table.getModel();
        table.setRowSorter(model.createTableFilter(searchArea));
    }

    private void setupProductTable(){
        table = new JTable(new ProductTableModel(productBUS.getDataTable()));
        table.setSize(new Dimension(1000, 500));

        setupTableComponent();

        tablePanel = new JScrollPane(table);
        tablePanel.setBackground(new Color(255, 238, 225));
        tablePanel.getViewport().setBackground(new Color(255, 238, 225));
        tablePanel.setPreferredSize(new Dimension(1000, 500));

        dialogPanel.add(tablePanel, 1);
    }

    @Override
    protected void writeData(){
        Product product = new ProductBUS().getProductByCode(current.getProductCode());
        JLabel label = (JLabel) nameProduct.getComponent(0);
        label.setText(product.getProductName());

        label = (JLabel) remainAmount.getComponent(0);
        label.setText(Integer.toString(product.getQuantity()));

        quantityTextField.setText(Integer.toString(current.getQuantity()));
    }

    @Override
    protected void setupButtonPanel(){
        super.setupButtonPanel();

        saveButton.addActionListener(e ->{
            if (!isAdjust) addDetailBill();
            else saveDetailBillInfor();
        });
    }

    private void setupTableComponent(){
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);

        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(50);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(10, 50));
        header.setFont(header.getFont().deriveFont(Font.BOLD, 15));
        header.setBackground(new Color(255, 92, 95));
        header.setForeground(Color.black);

        table.setColumnModel(columnModel);
        table.setForeground(Color.black);
        table.setBackground(new Color(255, 228, 203));
        table.setSelectionBackground(new Color(255, 0, 0, 100));
        table.setSelectionForeground(Color.black);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
    }

    @Override
    protected BillDetail getModel() {
        int quantity = Integer.parseInt(quantityTextField.getText());
        return new BillDetail(current.getBillCode(), current.getProductCode(), quantity);
    }

    private BillDetail getNewModel(){
        if (table.getSelectedRow() == -1){
            AppManager.showPopUpMessage("Vui lòng chọn 1 sản phẩm");
            return null;
        }

        String productCode = (String) table.getValueAt(table.getSelectedRow(), 0);
        int quantity;

        try{
            quantity = Integer.parseInt(quantityTextField.getText());
        }
        catch (NumberFormatException ex){
            AppManager.showPopUpMessage("Số lượng sai hoặc chứa kí tự");
            return null;
        }

        return new BillDetail(billCode, productCode, quantity);
    }

    private boolean checkValid(BillDetail input){
        Pair<Boolean, String> res = billBUS.checkDetailValidInput(input);

        if (!res.getFirst()){
            AppManager.showPopUpMessage(res.getLast());
            return false;
        }

        return true;
    }

    private boolean isAlreadyAdded(String productCode){
        BillDetail temp = billBUS.getBillDetailByCode(billCode, productCode);
        return temp != null;
    }

    private void addDetailBill(){
        BillDetail billDetail = getNewModel();
        if (billDetail == null) return;
        if (isAlreadyAdded(billDetail.getProductCode())){
            AppManager.showPopUpMessage("Sản phẩm đã có trong hóa đơn");
            return;
        }
        if (!checkValid(billDetail)){
            return;
        }

        var res = billBUS.addBillDetailToDatabase(billDetail);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }

    private void saveDetailBillInfor(){
        BillDetail billDetail = getModel();
        if (billDetail == null) return;

        if (!checkValid(billDetail)){
            return;
        }

        var res = billBUS.adjustBillDetail(billDetail, current.getQuantity());
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }
}
