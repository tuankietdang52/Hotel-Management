package DTO;

import Utilities.TableModelWithFilter;

import java.util.ArrayList;

public class SupplierTableModel extends TableModelWithFilter {
    public SupplierTableModel(){
        String[] cols = {"Mã", "Tên", "Địa chỉ", "SDT"};
        setCols(cols);
    }

    public SupplierTableModel(ArrayList<ArrayList<String>> dataSupplier){
        String[] cols = {"Mã", "Tên", "Địa chỉ", "SDT"};
        setCols(cols);
        setData(dataSupplier);
    }
}
