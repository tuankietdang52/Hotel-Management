package GUI;

import BUS.BillBUS;
import DTO.Bill;
import DTO.BillDetail;
import DTO.BillDetailTableModel;
import GUI.Dialog.BillDetailDialog;
import GUI.Dialog.BillDialog;
import Utilities.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.function.Consumer;

public class BillDetailPanel extends ViewWithTable {
    //region GUI Field

    private JPanel contentPanel;
    private JPanel inforPanel;
    private JPanel optionPanel;
    private RoundButton exitButton;
    private JTextField customerCode;
    private JTextField employeeCode;
    private JTextField dateCreated;
    private JPanel totalPanel;
    private RoundButton saveButton;

    //endregion

    private final BillBUS billBUS;
    private Bill currentBill;

    public BillDetailPanel(){
        billBUS = new BillBUS();
        setupView();
    }

    @Override
    public void setupView() {
        setupTableModel(new BillDetailTableModel(billBUS.getDetailDataTable(currentBill)), billBUS);
        setupPanel();

        setupInforPanel();
        setupOptionPanel();
        setupTable();
//        scrollTable.setPreferredSize(new Dimension(1000, 400));

        setupSearchPanel();

        if (contentPanel == null) throw new NullPointerException("Content Panel is null");

        contentPanel.add(searchPanel);
        contentPanel.add(scrollTable);
        setupButtonPanel();
    }

    @Override
    public void resetView() {
        resetTableData();
        customerCode.setText("");
        employeeCode.setText("");
        dateCreated.setText("");

        JLabel totalLabel = (JLabel) totalPanel.getComponent(0);
        totalLabel.setText("0 đ");
    }

    public void setCurrentBill(Bill bill){
        currentBill = bill;

        if (bill == null) resetTableModel();
        else setTableData(billBUS.getDetailDataTable(currentBill));
    }

    private void resetTableModel(){
        setTableData(new ArrayList<>());
    }

    private void setupPanel(){
        if (contentPanel == null) return;

        contentPanel.setBackground(new Color(255, 238, 225));
        contentPanel.setPreferredSize(new Dimension(1000, 900));
        contentPanel.setMaximumSize(contentPanel.getPreferredSize());
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.X_AXIS));
        optionPanel.setPreferredSize(new Dimension(1000, 200));
        optionPanel.setBackground(new Color(255, 238, 225));

    }

    private void setupInforPanel(){
        inforPanel = new JPanel();
        inforPanel.setPreferredSize(new Dimension(800, 300));
        inforPanel.setMaximumSize(inforPanel.getPreferredSize());
        inforPanel.setBackground(new Color(255, 238, 225));
        inforPanel.setLayout(new GridBagLayout());

        setupInforComponent();
        optionPanel.add(inforPanel);
    }

    //region create component function
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

    private JTextField createDateTextField(){
        JTextField formattedTextField = new JTextField(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(getBackground());

                if (!getText().isEmpty()) return;

                setFont(getFont().deriveFont(Font.ITALIC, 15));
                Graphics2D g2d = (Graphics2D) g;

                FontMetrics fontMetrics = g2d.getFontMetrics();
                int x = 10;
                int y = ((getHeight() - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();

                g.setColor(new Color(143, 143, 143));
                g.drawString("Nhập theo định dạng yyyy-mm-dd", x, y);
            }
        };

        formattedTextField.setPreferredSize(new Dimension(300, 30));
        formattedTextField.setMaximumSize(formattedTextField.getPreferredSize());
        formattedTextField.setMinimumSize(formattedTextField.getPreferredSize());
        formattedTextField.setBackground(new Color(237, 221, 209));
        formattedTextField.setForeground(Color.black);
        formattedTextField.setFont(formattedTextField.getFont().deriveFont(Font.PLAIN, 15));
        formattedTextField.setBorder(null);

        return formattedTextField;
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

        inforPanel.add(component, constraints);
    }

    //endregion

    private void setupInforComponent(){
        customerCode = createTextField();
        employeeCode = createTextField();
        dateCreated = createDateTextField();
        totalPanel = createLabel("0 đ");

        addToContentPanel(createLabel("Mã khách hàng:"), 0, 0,
                1, 1, new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Mã nhân viên"), 1, 0,
                1, 1, new Insets(0, 0, 10, 0));
        addToContentPanel(customerCode, 0, 1,
                1, 1, new Insets(0, 0, 10, 20));
        addToContentPanel(employeeCode, 1, 1,
                1, 1, new Insets(0, 0, 10, 0));

        addToContentPanel(createLabel("Ngày thêm:"), 0, 2,
                1, 1, new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Tổng tiền:"), 1, 2,
                1, 1, new Insets(0, 0, 10, 0));
        addToContentPanel(dateCreated, 0, 3,
                1, 1, new Insets(0, 0, 10, 20));
        addToContentPanel(totalPanel, 1, 3,
                1, 1, new Insets(0, 0, 10, 0));
    }

    private void setupOptionPanel(){
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setPreferredSize(new Dimension(200, 300));
        container.setMaximumSize(container.getPreferredSize());
        container.setBackground(new Color(255, 238, 225));

        RoundButton addButton = setupOptionButton("plus.png", new Color(0, 255, 0),
                e -> {
                    BillDetailDialog addDialog = new BillDetailDialog(currentBill.getBillCode());
                    addDialog.setVisible(true);
                    addEventDialog(addDialog);
                });
        RoundButton adjustButton = setupOptionButton("adjust.png", new Color(255, 127, 39),
                e -> showAdjustDialog());
        RoundButton removeButton = setupOptionButton("delete.png", new Color(255, 0, 0),
                e -> deleteBill());

        container.add(addButton);
        container.add(adjustButton);
        container.add(removeButton);

        addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        adjustButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        removeButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        optionPanel.add(container);
        contentPanel.add(optionPanel);
    }

    private void setupButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(1000, 100));
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        buttonPanel.setBackground(new Color(255, 238, 225));

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        saveButton = new RoundButton(10, "Lưu");
        saveButton.setBackground(Color.green);
        saveButton.setForeground(Color.white);
        saveButton.setPreferredSize(new Dimension(100, 50));
        saveButton.setMaximumSize(saveButton.getPreferredSize());
        saveButton.addActionListener(e -> saveBill());
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        exitButton = new RoundButton(10, "Thoát");
        exitButton.setBackground(Color.green.darker());
        exitButton.setForeground(Color.white);
        exitButton.setPreferredSize(new Dimension(100, 50));
        exitButton.setMaximumSize(saveButton.getPreferredSize());
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        contentPanel.add(buttonPanel);
    }

    private Bill getBillModel(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date;
        try{
            date = format.parse(dateCreated.getText());
        }
        catch (ParseException ex){
            AppManager.showPopUpMessage("Ngày tạo sai định dạng hoặc trống");
            return null;
        }
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String code = currentBill != null ?
                currentBill.getBillCode() : AppManager.generateRandomCode(10);

        return currentBill = new Bill(code, customerCode.getText(),
                employeeCode.getText(), sqlDate);
    }

    private void saveBill(){
        Bill bill = getBillModel();
        Pair<Boolean, String> res = billBUS.adjustBill(bill);

        if (res.getFirst()){
            AppManager.showPopUpMessage(res.getLast());
            exitButton.doClick();
        }
        else AppManager.showPopUpMessage("Không có khách hàng hoặc nhân viên trong cơ sở dữ liệu");
    }

    private void showAdjustDialog(){
        if (dataTable.getSelectedRow() == -1){
            AppManager.showPopUpMessage("Vui lòng chọn 1 chi tiết hóa đơn");
            return;
        }
        String productCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 0);
        String billCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 5);

        BillDetail cur = billBUS.getBillDetailByCode(billCode, productCode);

        BillDetailDialog adjustDialog = new BillDetailDialog(cur);
        adjustDialog.setVisible(true);
        addEventDialog(adjustDialog);
    }

    private void deleteBill(){
        if (dataTable.getSelectedRow() == -1){
            AppManager.showPopUpMessage("Vui lòng chọn 1 chi tiết hóa đơn");
            return;
        }

        if (!AppManager.showConfirmMessage("Bạn có chắc muốn xóa chi tiết hóa đơn này không ?")){
            return;
        }

        String productCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 0);
        String billCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 5);

        BillDetail billDetail = billBUS.getBillDetailByCode(billCode, productCode);

        Pair<Boolean, String> res = billBUS.removeBillDetail(billDetail);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            setTableData(billBUS.getDetailDataTable(currentBill));
            setTotal();
        }
    }

    private void addEventDialog(JDialog dialog){
        dialog.addWindowListener(new WindowCloseListener() {
            @Override
            public void windowClosed(WindowEvent e) {
                setTableData(billBUS.getDetailDataTable(currentBill));
                setTotal();
            }
        });
    }

    @Override
    protected void setupTableComponent(){
        TableColumnModel columnModel = dataTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(200);

        columnModel.removeColumn(columnModel.getColumn(5));

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

    private void setTotal(){
        DecimalFormat priceFormat = new DecimalFormat("#,###");

        JLabel totalLabel = (JLabel) totalPanel.getComponent(0);
        int total = (int)billBUS.calculateTotal(currentBill);
        totalLabel.setText(priceFormat.format(total) + " đ");
    }

    public void writeData(){
        if (currentBill == null) return;

        customerCode.setText(currentBill.getCustomerCode());
        employeeCode.setText(currentBill.getEmployeeCode());
        dateCreated.setText(currentBill.getDateCreated().toString());

        setTotal();
    }

    public void setChangeContentEvent(Consumer<String> callback){
        exitButton.addActionListener(e -> callback.accept("Bill"));
    }

    @Override
    public JPanel getPanel() {
        return contentPanel;
    }
}
