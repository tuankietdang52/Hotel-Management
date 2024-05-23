package GUI.Dialog;

import BUS.BillBUS;
import DTO.Bill;
import Utilities.*;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BillDialog extends OptionDialog<Bill>{
    //region GUI Field

    private JTextField customerCode;
    private JTextField employeeCode;
    private JTextField dateTextField;

    //endregion

    private final BillBUS billBUS;
    private Bill addedBill;

    public BillDialog(){
        super("Thêm nhân viên", new Dimension(700, 400));
        billBUS = new BillBUS();
    }

    @Override
    protected void setupComponent(){
        customerCode = createTextField();
        employeeCode = createTextField();
        dateTextField = createDateTextField();

        addToContentPanel(createLabel("Mã khách hàng:"), 0, 0, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Mã nhân viên:"), 1, 0, 1, 1,
                new Insets(0, 0, 10, 0));
        addToContentPanel(customerCode, 0, 1, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(employeeCode, 1, 1, 1, 1,
                new Insets(0, 0, 10, 0));

        addToContentPanel(createLabel("Ngày thêm:"), 0, 2, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(dateTextField, 0, 3, 1, 1,
                new Insets(0, 0, 10, 20));
    }

    @Override
    protected void writeData(){

    }

    @Override
    protected void setupButtonPanel(){
        super.setupButtonPanel();
        saveButton.addActionListener(e -> addBill());
    }

    @Override
    protected Bill getModel(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date;
        try{
            date = format.parse(dateTextField.getText());
        }
        catch (ParseException ex){
            AppManager.showPopUpMessage("Ngày tạo sai định dạng hoặc trống");
            return null;
        }
        java.sql.Date dateCreated = new java.sql.Date(date.getTime());

        String code = AppManager.generateRandomCode(10);
        return new Bill(code, customerCode.getText(), employeeCode.getText(), dateCreated);
    }

    private boolean checkValid(Bill bill){
        Pair<Boolean, String> res = billBUS.checkValidInput(bill);

        if (!res.getFirst()){
            AppManager.showPopUpMessage(res.getLast());
            return false;
        }

        return true;
    }

    private void addBill(){
        Bill bill = getModel();
        if (!checkValid(bill)) return;

        if (bill == null){
            AppManager.showPopUpMessage("Cant Parse");
            return;
        }

        Pair<Boolean, String> res = billBUS.addBillToDatabase(bill);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
            addedBill = bill;
        }
    }

    public Bill getBillAdded(){
        return addedBill;
    }
}
