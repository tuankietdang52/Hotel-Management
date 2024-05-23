package BUS;

import DAO.EmployeeDAO;
import DTO.Employee;
import Utilities.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EmployeeBUS extends BaseBUS<Employee>{
    private final EmployeeDAO employeeDAO;

    public EmployeeBUS(){
        employeeDAO = new EmployeeDAO();
    }

    public Employee getEmployeeByCode(String code){
        return employeeDAO.findEmployeeByCode(code);
    }

    @Override
    public ArrayList<ArrayList<String>> getDataTable(){
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

    public ArrayList<ArrayList<String>> getDataTable(ArrayList<Employee> employees){
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

    public Pair<Boolean, String> addEmployeeToDatabase(Employee employee){
        boolean res = employeeDAO.addEmployee(employee);
        return res ? new Pair<>(true, "Thêm thành công") : new Pair<>(false, "Thêm thất bại");
    }

    public Pair<Boolean, String> adjustEmployee(Employee employee){
        boolean res = employeeDAO.adjustEmployee(employee);
        return res ? new Pair<>(true, "Sửa thành công") : new Pair<>(false, "Sửa thất bại");
    }

    public Pair<Boolean, String> removeEmployee(Employee employee){
        boolean res = employeeDAO.deleteEmployee(employee);
        return res ? new Pair<>(true, "Xóa thành công") : new Pair<>(false, "Xóa thất bại");
    }

    public Pair<Boolean, String> checkValidInput(Employee employee){
        if (employee.getFirstName().isEmpty() || employee.getLastName().isEmpty()){
            return new Pair<>(false, "Họ tên khách hàng không được để trống");
        }

        else if (employee.getUsername().isEmpty() || employee.getPassword().isEmpty()){
            return new Pair<>(false, "Tài khoản, mật khẩu không được để trống");
        }

        else if (employee.getAddress().isEmpty()){
            return new Pair<>(false, "Địa chỉ không được để trống");
        }

        else if (employee.getJob().isEmpty()){
            return new Pair<>(false, "Tên công việc không được trống");
        }

        return new Pair<>(true, "");
    }
}
