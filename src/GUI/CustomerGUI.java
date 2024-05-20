package GUI;

import BUS.CustomerBUS;
import DTO.Customer;
import DTO.CustomerTableModel;
import Utilities.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class CustomerGUI extends ViewWithTable {
    //region GUI Field

    private JPanel contentPanel;
    private JPanel optionPanel;

    //endregion

    private final CustomerBUS customerBUS;

    public CustomerGUI(){
        super(new CustomerTableModel(new CustomerBUS().getCustomerDataTable()));

        customerBUS = new CustomerBUS();

        setupPanel();
        setupOptionPanel();
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
        optionPanel.setLayout(new GridBagLayout());
        optionPanel.setPreferredSize(new Dimension(1000, 150));
        optionPanel.setBackground(new Color(255, 238, 225));

        JPanel container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        container.setBackground(new Color(255, 238, 225));

        RoundButton addButton = setupOptionButton("plus.png", new Color(0, 255, 0),
                e -> {
                    CustomerDialog addDialog = new CustomerDialog("Thêm khách hàng");
                    addDialog.setVisible(true);
                    addEventDialog(addDialog);
                });
        RoundButton adjustButton = setupOptionButton("adjust.png", new Color(255, 127, 39),
                e -> showAdjustDialog());
        RoundButton removeButton = setupOptionButton("delete.png", new Color(255, 0, 0),
                e -> deleteCustomer());

        container.add(addButton);
        container.add(adjustButton);
        container.add(removeButton);

        optionPanel.add(container);
        contentPanel.add(optionPanel);
    }

    private void showAdjustDialog(){
        if (dataTable.getSelectedRow() == -1){
            AppManager.showPopUpMessage("Vui lòng chọn 1 khách hàng");
            return;
        }
        String customerCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 0);
        Customer cur = customerBUS.getCustomerByCode(customerCode);

        CustomerDialog adjustDialog = new CustomerDialog("Sửa thông tin khách hàng", cur);
        adjustDialog.setVisible(true);
        addEventDialog(adjustDialog);
    }

    private void deleteCustomer(){
        if (dataTable.getSelectedRow() == -1){
            AppManager.showPopUpMessage("Vui lòng chọn 1 khách hàng");
            return;
        }

        if (!AppManager.showConfirmMessage("Bạn có chắc muốn xóa khách hàng này không ?")){
            return;
        }

        String customerCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 0);

        Pair<Boolean, String> res = customerBUS.removeCustomer(customerCode);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()) setTableData(customerBUS.getCustomerDataTable());
    }

    private void addEventDialog(JDialog dialog){
        dialog.addWindowListener(new WindowCloseListener() {
            @Override
            public void windowClosed(WindowEvent e) {
                setTableData(customerBUS.getCustomerDataTable());
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

    @Override
    protected void setupTableComponent() {
        TableColumnModel columnModel = dataTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(200);
        columnModel.getColumn(5).setPreferredWidth(150);
        columnModel.getColumn(6).setPreferredWidth(150);


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
