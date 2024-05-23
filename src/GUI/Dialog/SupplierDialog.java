package GUI.Dialog;

import BUS.SupplierBUS;
import DTO.Supplier;
import Utilities.AppManager;
import Utilities.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SupplierDialog extends OptionDialog<Supplier> {
    //region GUI Field

    private JTextField nameTextField;
    private JTextField addressTextField;
    private JTextField phoneTextField;

    //endregion

    private final SupplierBUS supplierBUS;

    public SupplierDialog(){
        super("Thêm nhà cung cấp", new Dimension(700, 700));
        supplierBUS = new SupplierBUS();
    }

    public SupplierDialog(Supplier current){
        super("Sửa nhà cung cấp", new Dimension(700, 700), current);
        supplierBUS = new SupplierBUS();
    }

    @Override
    protected void setupComponent(){
        nameTextField = createTextField();
        addressTextField = createTextField();
        phoneTextField = createTextField();

        addToContentPanel(createLabel("Ten nha cung cap:"), 0, 0, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Dia chi"), 1, 0, 1, 1,
                new Insets(0, 0, 10, 0));
        addToContentPanel(nameTextField, 0, 1, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(addressTextField, 1, 1, 1, 1,
                new Insets(0, 0, 10, 0));

        addToContentPanel(createLabel("SDT:"), 0, 2, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(phoneTextField, 0, 3, 1, 1,
                new Insets(0, 0, 10, 20));

    }

    @Override
    protected void writeData(){
        nameTextField.setText(current.getSupplierName());
        addressTextField.setText(current.getAddress());
        phoneTextField.setText(current.getPhone());
    }

    @Override
    protected void setupButtonPanel(){
        super.setupButtonPanel();
        saveButton.addActionListener(e ->{
            if (!isAdjust) addSupplier();
            else saveSupplierInfor();
        });
    }

    private ArrayList<String> gatherInput(){
        return new ArrayList<>(){{
            add(nameTextField.getText());
            add(addressTextField.getText());
            add(phoneTextField.getText());
        }};
    }

    @Override
    protected Supplier getModel() {
        String code = isAdjust ? current.getSupplierCode() : AppManager.generateRandomNumberCode(10);

        return new Supplier(code, nameTextField.getText(),
                addressTextField.getText(), phoneTextField.getText());
    }

    private boolean checkValid(Supplier supplier){
        Pair<Boolean, String> res = supplierBUS.checkValidInput(supplier);

        if (!res.getFirst()){
            AppManager.showPopUpMessage(res.getLast());
            return false;
        }

        return true;
    }

    private void addSupplier(){
        Supplier supplier = getModel();

        if (supplier == null) return;
        if (!checkValid(supplier)) return;

        Pair<Boolean, String> res = supplierBUS.addSupplierToDatabase(supplier);

        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }

    private void saveSupplierInfor(){
        Supplier supplier = getModel();

        if (supplier == null) return;
        if (!checkValid(supplier)) return;

        Pair<Boolean, String> res = supplierBUS.adjustSupplier(supplier);

        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }
}
