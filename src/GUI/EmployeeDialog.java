package GUI;

import BUS.EmployeeBUS;
import DTO.Employee;
import Utilities.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EmployeeDialog extends JDialog{
    //region GUI Field

    private JPanel dialogPanel;
    private JPanel contentPanel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JTextField addressTextField;
    private JTextField jobTextField;
    private JTextField salaryTextField;
    private JTextField dateTextField;

    //endregion

    private EmployeeBUS employeeBUS;
    private Employee currentEmployee;
    private boolean isAdjust = false;

    public EmployeeDialog(String title){
        setPreferredSize(new Dimension(700, 700));
        setSize(getPreferredSize());
        setModal(true);
        setLocationRelativeTo(null);

        setTitle(title);
        setup();

        setupContentPanel();
        setupButtonPanel();

        add(dialogPanel);
    }

    public EmployeeDialog(String title, Employee currentEmployee){
        setPreferredSize(new Dimension(700, 700));
        setSize(getPreferredSize());
        setModal(true);
        setLocationRelativeTo(null);

        this.currentEmployee = currentEmployee;
        isAdjust = true;

        setTitle(title);
        setup();

        setupContentPanel();
        writeData();
        setupButtonPanel();

        add(dialogPanel);
    }

    private void setup(){
        employeeBUS = new EmployeeBUS();

        dialogPanel = new JPanel();
        dialogPanel.setPreferredSize(getPreferredSize());
        dialogPanel.setSize(getSize());

        dialogPanel.setBackground(new Color(255, 238, 225));
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
    }

    private void setupContentPanel(){
        contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(700, 500));
        contentPanel.setBackground(new Color(255, 238, 225));

        contentPanel.setLayout(new GridBagLayout());

        setupComponent();
        dialogPanel.add(contentPanel);
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

        contentPanel.add(component, constraints);
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

    private JTextArea createTextArea(){
        JTextArea textArea = new JTextArea();

        textArea.setPreferredSize(new Dimension(300, 90));
        textArea.setMaximumSize(textArea.getPreferredSize());
        textArea.setMinimumSize(textArea.getPreferredSize());
        textArea.setBackground(new Color(237, 221, 209));
        textArea.setForeground(Color.black);
        textArea.setFont(textArea.getFont().deriveFont(Font.PLAIN, 15));
        textArea.setBorder(null);
        textArea.setLineWrap(true);

        return textArea;
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

    private void setupComponent(){
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

    private void writeData(){
        firstNameTextField.setText(currentEmployee.getFirstName());
        lastNameTextField.setText(currentEmployee.getLastName());
        usernameTextField.setText(currentEmployee.getUsername());
        passwordTextField.setText(currentEmployee.getPassword());
        addressTextField.setText(currentEmployee.getAddress());
        dateTextField.setText(currentEmployee.getBirthday().toString());
        jobTextField.setText(currentEmployee.getJob());
        salaryTextField.setText(Integer.toString((int)currentEmployee.getSalary()));
    }

    private void setupButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(700, 200));
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        buttonPanel.setBackground(new Color(255, 238, 225));

        buttonPanel.setLayout(new GridBagLayout());

        RoundButton saveButton = new RoundButton(10, "Lưu");

        saveButton.setBackground(Color.green);
        saveButton.setForeground(Color.white);
        saveButton.setPreferredSize(new Dimension(100, 50));
        saveButton.setMaximumSize(saveButton.getPreferredSize());

        saveButton.addActionListener(e ->{
            if (!isAdjust) addEmployee();
            else saveEmployeeInfor();
        });

        buttonPanel.add(saveButton);
        dialogPanel.add(buttonPanel);
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

    private boolean checkValid(ArrayList<String> input){
        Pair<Boolean, String> res = employeeBUS.checkValidInput(input);

        if (!res.getFirst()){
            AppManager.showPopUpMessage(res.getLast());
            return false;
        }

        return true;
    }

    private void addEmployee(){
        ArrayList<String> input = gatherInput();

        if (!checkValid(input)) return;

        Pair<Boolean, String> res = new Pair<>(false, "Something wrong");

        try{
            res = employeeBUS.addEmployeeToDatabase(input);
        }
        catch (ParseException ignore){}

        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }

    private void saveEmployeeInfor(){
        ArrayList<String> input = gatherInput();

        if (!checkValid(input)) return;

        Pair<Boolean, String> res = new Pair<>(false, "Something wrong");

        try{
            res = employeeBUS.adjustEmployee(input, currentEmployee.getCode());
        }
        catch (ParseException ignore){}

        AppManager.showPopUpMessage(res.getLast());

        if (res.getFirst()){
            dispose();
        }
    }
}
