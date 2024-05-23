package GUI;

import Interface.IView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BillGUI implements IView {
    //region GUI Field

    private JPanel contentPanel;

    //endregion
    //region Content Panel

    private BillPanel billPanel;
    private BillDetailPanel billDetailPanel;

    //endregion

    public BillGUI(){
        setupView();
    }


    @Override
    public void setupView() {
        if (contentPanel == null) throw new NullPointerException("Content Panel is null");

        contentPanel.setPreferredSize(new Dimension(1000, 900));
        contentPanel.setLayout(new CardLayout());

        setupContentPanel();
    }

    private void setupContentPanel(){
        billPanel = new BillPanel();
        billDetailPanel = new BillDetailPanel();

        contentPanel.add(billPanel.getPanel(), "Bill");
        contentPanel.add(billDetailPanel.getPanel(), "BillDetail");
        setEventChangePanel();
    }

    @Override
    public void resetView() {
        changeContent("Bill");
    }

    private void setEventChangePanel(){
        billPanel.setChangeContentEvent(this::changeContent);
        billDetailPanel.setChangeContentEvent(this::changeContent);
    }

    private void setupBillDetailPanel(){
        billDetailPanel.resetView();
        billDetailPanel.setCurrentBill(billPanel.getCurrentBill());
        billDetailPanel.writeData();
    }

    private void changeContent(String name){
        if (Objects.equals(name, "BillDetail")){
            setupBillDetailPanel();
        }
        else billPanel.resetView();

        CardLayout layout = (CardLayout) contentPanel.getLayout();
        layout.show(contentPanel, name);
    }

    @Override
    public JPanel getPanel() {
        return contentPanel;
    }
}
