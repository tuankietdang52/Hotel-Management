package GUI;

import BUS.SupplierBUS;
import DTO.Supplier;
import DTO.SupplierTableModel;
import GUI.Dialog.SupplierDialog;
import Utilities.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class SupplierGUI extends ViewWithTable {
    //region GUI Field

    private JPanel contentPanel;
    private JPanel optionPanel;

    //endregion

    private SupplierBUS supplierBUS;

    public SupplierGUI(){
        super(new SupplierTableModel(), new SupplierBUS());
        setupView();
    }

    @Override
    public void setupView() {
        supplierBUS = (SupplierBUS) viewBUS;

        setupPanel();

        setupOptionPanel();

        setupTable();
        setupSearchPanel();

        contentPanel.add(searchPanel);
        contentPanel.add(scrollTable);
    }

    @Override
    public void resetView() {
        resetTableData();
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
                    SupplierDialog addDialog = new SupplierDialog();
                    addDialog.setVisible(true);
                    addEventDialog(addDialog);
                });
        RoundButton adjustButton = setupOptionButton("adjust.png", new Color(255, 127, 39),
                e -> showAdjustDialog());
        RoundButton removeButton = setupOptionButton("delete.png", new Color(255, 0, 0),
                e -> deleteSupplier());

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
        String supplierCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 0);
        Supplier cur = supplierBUS.getSupplierByCode(supplierCode);

        SupplierDialog adjustDialog = new SupplierDialog(cur);
        adjustDialog.setVisible(true);
        addEventDialog(adjustDialog);
    }

    private void deleteSupplier(){
        if (dataTable.getSelectedRow() == -1){
            AppManager.showPopUpMessage("Vui long chon 1 san pham truoc");
            return;
        }

        if (!AppManager.showConfirmMessage("Bạn có chắc muốn xóa sản phẩm này không ?")){
            return;
        }

        String supplierCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 0);
        Supplier supplier = supplierBUS.getSupplierByCode(supplierCode);

        Pair<Boolean, String> res = supplierBUS.removeSupplier(supplier);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()) setTableData(supplierBUS.getDataTable());
    }

    private void addEventDialog(JDialog dialog){
        dialog.addWindowListener(new WindowCloseListener() {
            @Override
            public void windowClosed(WindowEvent e) {
                setTableData(supplierBUS.getDataTable());
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

    protected void setupTableComponent(){
        TableColumnModel columnModel = dataTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(300);
        columnModel.getColumn(3).setPreferredWidth(200);

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
