package GUI.Dialog;

import BUS.CustomerBUS;
import DTO.Customer;
import Utilities.AppManager;
import Utilities.Pair;
import Utilities.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomerDialog extends OptionDialog<Customer> {
    //region GUI Field
    
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JTextField addressTextField;
    private JTextField phoneTextField;
    private JTextField dateTextField;

    //endregion

    private CustomerBUS customerBUS;

    public CustomerDialog(){
        super("Thêm khách hàng", new Dimension(700, 700));
        customerBUS = new CustomerBUS();
    }

    public CustomerDialog(Customer current){
        super("Sửa thông tin khách hàng", new Dimension(700, 700), current);
        customerBUS = new CustomerBUS();
    }

    @Override
    protected void setupComponent(){
        firstNameTextField = createTextField();
        lastNameTextField = createTextField();
        usernameTextField = createTextField();
        passwordTextField = createTextField();
        addressTextField = createTextField();
        phoneTextField = createTextField();
        dateTextField = createDateTextField();

        addToContentPanel(createLabel("Họ khách hàng:"), 0, 0, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Tên khách hàng:"), 1, 0, 1, 1,
                new Insets(0, 0, 10, 0));
        addToContentPanel(firstNameTextField, 0, 1, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(lastNameTextField, 1, 1, 1, 1,
                new Insets(0, 0, 10, 0));

        addToContentPanel(createLabel("Tài khoản:"), 0, 2, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Mật khẩu:"), 1, 2, 1, 1,
                new Insets(0, 0, 10, 0));
        addToContentPanel(usernameTextField, 0, 3, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(passwordTextField, 1, 3, 1, 1,
                new Insets(0, 0, 10, 0));

        addToContentPanel(createLabel("Địa chỉ:"), 0, 4, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("SDT:"), 1, 4, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(addressTextField, 0, 5, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(phoneTextField, 1, 5, 1, 1,
                new Insets(0, 0, 10, 0));

        addToContentPanel(createLabel("Ngày sinh:"), 0, 6, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(dateTextField, 0, 7, 1, 1,
                new Insets(0, 0, 10, 20));

    }

    @Override
    protected void writeData(){
        firstNameTextField.setText(current.getFirstName());
        lastNameTextField.setText(current.getLastName());
        usernameTextField.setText(current.getUsername());
        passwordTextField.setText(current.getPassword());
        addressTextField.setText(current.getAddress());
        phoneTextField.setText(current.getPhone());
        dateTextField.setText(current.getBirthday().toString());
    }
    
    @Override
    protected void setupButtonPanel(){
        super.setupButtonPanel();

        saveButton.addActionListener(e ->{
            if (!isAdjust) addCustomer();
            else saveCustomerInfor();
        });
    }

    private boolean checkValid(Customer customer){
        Pair<Boolean, String> res = customerBUS.checkValidInput(customer);

        if (!res.getFirst()){
            AppManager.showPopUpMessage(res.getLast());
            return false;
        }

        return true;
    }

    @Override
    protected Customer getModel(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date;
        try{
            date = format.parse(dateTextField.getText());
        }
        catch (ParseException ignore){
            AppManager.showPopUpMessage("Ngày sinh sai định dạng hoặc trống");
            return null;
        }

        java.sql.Date birthday = new java.sql.Date(date.getTime());
        String code = isAdjust ? current.getCode() : AppManager.generateRandomCode(10);

        return new Customer(code,
                firstNameTextField.getText(), lastNameTextField.getText(),
                usernameTextField.getText(), passwordTextField.getText(), phoneTextField.getText(),
                addressTextField.getText(), birthday);
    }

    private void addCustomer(){
        Customer customer = getModel();

        if (customer == null) return;
        if (!checkValid(customer)) return;

        Pair<Boolean, String> res = customerBUS.addCustomerToDatabase(customer);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }

    private void saveCustomerInfor(){
        Customer customer = getModel();

        if (customer == null) return;
        if (!checkValid(customer)) return;

        Pair<Boolean, String> res = customerBUS.adjustCustomer(customer);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }
}
