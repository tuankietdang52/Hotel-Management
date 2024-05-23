package GUI;

import BUS.BillBUS;
import DTO.Bill;
import DTO.BillTableModel;
import GUI.Dialog.BillDialog;
import Utilities.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

public class BillPanel extends ViewWithTable {
    //region GUI Field

    private JPanel contentPanel;
    private JPanel optionPanel;
    private JButton addButton;
    private JButton adjustButton;
    private BillDialog billDialog;

    //endregion

    private BillBUS billBUS;
    private Bill current;
    private boolean forceChange = false;

    public BillPanel(){
        super(new BillTableModel(), new BillBUS());
        setupView();
    }

    @Override
    public void setupView() {
        billBUS = (BillBUS) viewBUS;
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

        addButton = setupOptionButton("plus.png", new Color(0, 255, 0),
                e -> addBill());
        adjustButton = setupOptionButton("adjust.png", new Color(255, 127, 39), null);
        RoundButton removeButton = setupOptionButton("delete.png", new Color(255, 0, 0),
                e -> deleteBill());

        container.add(addButton);
        container.add(adjustButton);
        container.add(removeButton);

        addButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        adjustButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        removeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        optionPanel.add(container);
        contentPanel.add(optionPanel);
    }

    private void addBill(){
        billDialog = new BillDialog();
        billDialog.setVisible(true);
        billDialog.addWindowListener(new WindowCloseListener() {
            @Override
            public void windowClosed(WindowEvent e) {
                addEvent();
            }
        });
    }

    private void addEvent(){
        setCurrentBill(billDialog.getBillAdded());
        forceChange = true;
        adjustButton.doClick();
    }

    private void deleteBill(){
        if (dataTable.getSelectedRow() == -1){
            AppManager.showPopUpMessage("Vui lòng chọn 1 hóa đơn");
            return;
        }

        if (!AppManager.showConfirmMessage("Bạn có chắc muốn xóa hóa đơn này không ?")){
            return;
        }

        String billCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 0);
        Bill bill = billBUS.getBillByCode(billCode);

        Pair<Boolean, String> res = billBUS.removeBill(bill);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()) setTableData(billBUS.getDataTable());
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

        if (callback != null) button.addActionListener(callback);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
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

    public void setChangeContentEvent(Consumer<String> callback){
        adjustButton.addActionListener(e -> {
            if (!forceChange){
                if (dataTable.getSelectedRow() == -1) {
                    AppManager.showPopUpMessage("Vui lòng chọn 1 hóa đơn");
                    return;
                }
                else setCurrentBill();
            }
            callback.accept("BillDetail");
            forceChange = false;
        });
    }

    public void setCurrentBill(){
        try{
            String billCode = (String) dataTable.getModel().getValueAt(dataTable.getSelectedRow(), 0);
            current = billBUS.getBillByCode(billCode);
        }
        catch (Exception ignore){}
    }

    public void setCurrentBill(Bill bill){
        current = bill;
    }

    public Bill getCurrentBill(){
        return current;
    }

    @Override
    public JPanel getPanel() {
        return contentPanel;
    }
}
