package DTO;

import Utilities.TableModelWithFilter;

import java.util.ArrayList;

public class BillDetailTableModel extends TableModelWithFilter {
    public BillDetailTableModel(){
        String[] cols = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền", "Mã hóa đơn"};
        setData(new ArrayList<>());
        setCols(cols);
    }

    public BillDetailTableModel(ArrayList<ArrayList<String>> dataBill){
        String[] cols = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền", "Mã hóa đơn"};
        setCols(cols);
        setData(dataBill);
    }
}
