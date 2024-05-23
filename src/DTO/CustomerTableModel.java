package DTO;

import Utilities.TableModelWithFilter;

import java.util.ArrayList;

public class CustomerTableModel extends TableModelWithFilter {
    public CustomerTableModel(){
        String[] cols = {"Mã", "Họ và tên", "Tên đăng nhập", "Mật khẩu", "Địa chỉ", "SDT", "Ngày sinh"};
        setCols(cols);
    }

    public CustomerTableModel(ArrayList<ArrayList<String>> dataEmployee){
        String[] cols = {"Mã", "Họ và tên", "Tên đăng nhập", "Mật khẩu", "Địa chỉ", "SDT", "Ngày sinh"};
        setCols(cols);
        setData(dataEmployee);
    }
}
