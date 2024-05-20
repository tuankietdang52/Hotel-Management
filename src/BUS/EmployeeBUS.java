package BUS;

import DAO.EmployeeDAO;
import DTO.Employee;
import Utilities.AppManager;
import Utilities.Pair;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EmployeeBUS {
    private final EmployeeDAO employeeDAO;

    public EmployeeBUS(){
        employeeDAO = new EmployeeDAO();
    }

    public Employee getEmployeeByCode(String code){
        return employeeDAO.findEmployeeByCode(code);
    }

    public ArrayList<ArrayList<String>> getEmployeeDataTable(){
        ArrayList<Employee> employees = employeeDAO.getEmployeeData();
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        DecimalFormat priceFormat = new DecimalFormat("#,###");

        for (Employee employee : employees){
            result.add(new ArrayList<>(){{
                add(employee.getCode());
                add(String.format("%s %s", employee.getFirstName(), employee.getLastName()));
                add(employee.getUsername());
                add(employee.getPassword());
                add(employee.getAddress());
                add(employee.getBirthday().toString());
                add(employee.getJob());
                add(priceFormat.format((int)employee.getSalary()) + "đ");
            }});
        }

        return result;
    }

    public ArrayList<ArrayList<String>> getEmployeeDataTable(ArrayList<Employee> employees){
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        DecimalFormat priceFormat = new DecimalFormat("#,###");

        for (Employee employee : employees){
            result.add(new ArrayList<>(){{
                add(employee.getCode());
                add(String.format("%s %s", employee.getFirstName(), employee.getLastName()));
                add(employee.getUsername());
                add(employee.getPassword());
                add(employee.getAddress());
                add(employee.getBirthday().toString());
                add(employee.getJob());
                add(priceFormat.format((int)employee.getSalary()) + "đ");
            }});
        }

        return result;
    }

    public Pair<Boolean, String> addEmployeeToDatabase(ArrayList<String> input) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = format.parse(input.get(5));
        java.sql.Date birthday = new java.sql.Date(date.getTime());

        double salary = Double.parseDouble(input.getLast());

        Employee employee = new Employee(AppManager.generateRandomCode(10),
                input.getFirst(), input.get(1), input.get(2), input.get(3), "", input.get(4),
                birthday, input.get(6), salary);

        boolean res = employeeDAO.addEmployee(employee);
        return res ? new Pair<>(true, "Thêm thành công") : new Pair<>(false, "Thêm thất bại");
    }

    public Pair<Boolean, String> adjustEmployee(ArrayList<String> input, String employeeCode) throws ParseException{
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = format.parse(input.get(5));
        java.sql.Date birthday = new java.sql.Date(date.getTime());

        double salary = Double.parseDouble(input.getLast());

        Employee employee = new Employee(employeeCode,
                input.getFirst(), input.get(1), input.get(2), input.get(3), "", input.get(4),
                birthday, input.get(6), salary);

        boolean res = employeeDAO.adjustEmployee(employee);
        return res ? new Pair<>(true, "Sửa thành công") : new Pair<>(false, "Sửa thất bại");
    }

    public Pair<Boolean, String> removeEmployee(String employeeCode){
        boolean res = employeeDAO.deleteEmployee(employeeCode);
        return res ? new Pair<>(true, "Xóa thành công") : new Pair<>(false, "Xóa thất bại");
    }

    public Pair<Boolean, String> checkValidInput(ArrayList<String> dataInput){
        for (int i = 0; i < dataInput.size(); i++){
            String input = dataInput.get(i);
            switch (i){
                case 0, 1 -> {
                    if (input.isEmpty()){
                        return new Pair<>(false, "Họ tên nhân viên không được để trống");
                    }
                }
                case 2, 3 -> {
                    if (input.isEmpty()){
                        return new Pair<>(false, "Tài khoản, mật khẩu không được để trống");
                    }
                }
                case 4 -> {
                    if (input.isEmpty()){
                        return new Pair<>(false, "Địa chỉ không được để trống");
                    }
                }
                case 5 -> {
                    if (!isValidDate(input) || input.isEmpty()){
                        return new Pair<>(false, "Ngày sinh sai định dạng hoặc trống");
                    }
                }
                case 6 -> {
                    if (input.isEmpty()){
                        return new Pair<>(false, "Tên công việc không được trống");
                    }
                }
                case 7 -> {
                    if (!isValidNumberTextField(input) || input.isEmpty()){
                        return new Pair<>(false, "Lương sai định dạng hoặc để trống");
                    }
                }
            }
        }

        return new Pair<>(true, "");
    }

    public boolean isValidDate(String date){
        // idk what the fac this regex is
        return date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
    }

    public boolean isValidNumberTextField(String text){
        if (text.isEmpty()) return false;
        for (int i = 0; i < text.length(); i++){
            if (text.charAt(i) < 48 || text.charAt(i) > 57) return false;
        }

        return true;
    }
}
