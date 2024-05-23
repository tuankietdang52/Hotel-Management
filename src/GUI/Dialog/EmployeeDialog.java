package GUI.Dialog;

import BUS.EmployeeBUS;
import DTO.Customer;
import DTO.Employee;
import Utilities.*;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EmployeeDialog extends OptionDialog<Employee>{
    //region GUI Field
    
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JTextField addressTextField;
    private JTextField jobTextField;
    private JTextField salaryTextField;
    private JTextField dateTextField;

    //endregion

    private final EmployeeBUS employeeBUS;

    public EmployeeDialog(){
        super("Thêm nhân viên", new Dimension(700, 700));
        employeeBUS = new EmployeeBUS();
    }

    public EmployeeDialog(Employee current){
        super("Sửa thông tin nhân viên", new Dimension(700, 700), current);
        employeeBUS = new EmployeeBUS();
    }

    @Override
    protected void setupComponent(){
        firstNameTextField = createTextField();
        lastNameTextField = createTextField();
        usernameTextField = createTextField();
        passwordTextField = createTextField();
        addressTextField = createTextField();
        jobTextField = createTextField();
        salaryTextField = createTextField();
        dateTextField = createDateTextField();

        addToContentPanel(createLabel("Họ nhân viên:"), 0, 0, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Tên nhân viên:"), 1, 0, 1, 1,
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

        addToContentPanel(createLabel("Công việc:"), 0, 4, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Lương:"), 1, 4, 1, 1,
                new Insets(0, 0, 10, 0));
        addToContentPanel(jobTextField, 0, 5, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(salaryTextField, 1, 5, 1, 1,
                new Insets(0, 0, 10, 0));

        addToContentPanel(createLabel("Địa chỉ:"), 0, 6, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(createLabel("Ngày sinh:"), 1, 6, 1, 1,
                new Insets(0, 0, 10, 0));
        addToContentPanel(addressTextField, 0, 7, 1, 1,
                new Insets(0, 0, 10, 20));
        addToContentPanel(dateTextField, 1, 7, 1, 1,
                new Insets(0, 0, 10, 0));

    }

    @Override
    protected void writeData(){
        firstNameTextField.setText(current.getFirstName());
        lastNameTextField.setText(current.getLastName());
        usernameTextField.setText(current.getUsername());
        passwordTextField.setText(current.getPassword());
        addressTextField.setText(current.getAddress());
        dateTextField.setText(current.getBirthday().toString());
        jobTextField.setText(current.getJob());
        salaryTextField.setText(Integer.toString((int)current.getSalary()));
    }

    @Override
    protected void setupButtonPanel(){
        super.setupButtonPanel();

        saveButton.addActionListener(e ->{
            if (!isAdjust) addEmployee();
            else saveEmployeeInfor();
        });
    }

    private ArrayList<String> gatherInput(){
        return new ArrayList<>(){{
            add(firstNameTextField.getText());
            add(lastNameTextField.getText());
            add(usernameTextField.getText());
            add(passwordTextField.getText());
            add(addressTextField.getText());
            add(dateTextField.getText());
            add(jobTextField.getText());
            add(salaryTextField.getText());
        }};
    }

    @Override
    protected Employee getModel(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date;
        try{
            date = format.parse(dateTextField.getText());
        }
        catch (ParseException ex){
            AppManager.showPopUpMessage("Ngày sinh sai định dạng hoặc trống");
            return null;
        }
        java.sql.Date birthday = new java.sql.Date(date.getTime());

        if (!employeeBUS.isValidNumberTextField(salaryTextField.getText(), "double",
                "Lương sai định dạng hoặc để trống")){
            return null;
        }

        double salary = Double.parseDouble(salaryTextField.getText());
        String code = isAdjust ? current.getCode() : AppManager.generateRandomCode(10);

        return new Employee(code, firstNameTextField.getText(),
                lastNameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(),
                "", addressTextField.getText(), birthday, jobTextField.getText(), salary);
    }

    private boolean checkValid(Employee employee){
        Pair<Boolean, String> res = employeeBUS.checkValidInput(employee);

        if (!res.getFirst()){
            AppManager.showPopUpMessage(res.getLast());
            return false;
        }

        return true;
    }

    private void addEmployee(){
        Employee employee = getModel();
        if (employee == null) return;

        if (!checkValid(employee)) return;

        Pair<Boolean, String> res = employeeBUS.addEmployeeToDatabase(employee);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }

    private void saveEmployeeInfor(){
        Employee employee = getModel();
        if (employee == null) return;

        if (!checkValid(employee)) return;

        Pair<Boolean, String> res = employeeBUS.adjustEmployee(employee);
        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }
}
