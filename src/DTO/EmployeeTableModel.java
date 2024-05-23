package DTO;

import Utilities.TableModelWithFilter;

import java.util.ArrayList;

public class EmployeeTableModel extends TableModelWithFilter {
    public EmployeeTableModel(){
        String[] cols = {"Mã", "Họ và tên", "Tên đăng nhập", "Mật khẩu", "Địa chỉ", "Ngày sinh", "Tên công việc", "Lương"};
        setCols(cols);
    }

    public EmployeeTableModel(ArrayList<ArrayList<String>> dataEmployee){
        String[] cols = {"Mã", "Họ và tên", "Tên đăng nhập", "Mật khẩu", "Địa chỉ", "Ngày sinh", "Tên công việc", "Lương"};
        setCols(cols);
        setData(dataEmployee);
    }
}
