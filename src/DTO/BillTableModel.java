package DTO;

import Utilities.TableModelWithFilter;

import java.util.ArrayList;

public class BillTableModel extends TableModelWithFilter {
    public BillTableModel(){
        String[] cols = {"Mã hóa đơn", "Tên khách hàng", "Tên nhân viên", "Tổng tiền", "Ngày lập", "Mã khách hàng", "Mã nhân viên"};
        setCols(cols);
    }

    public BillTableModel(ArrayList<ArrayList<String>> dataBill){
        String[] cols = {"Mã hóa đơn", "Tên khách hàng", "Tên nhân viên", "Tổng tiền", "Ngày lập", "Mã khách hàng", "Mã nhân viên"};
        setCols(cols);
        setData(dataBill);
    }
}
