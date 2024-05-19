package DTO;

import Utilities.TableModelWithFilter;

import java.util.ArrayList;

public class ProductTableModel extends TableModelWithFilter {
    public ProductTableModel(ArrayList<ArrayList<String>> dataProduct){
        String[] cols = {"Mã", "Tên", "Loại", "Đơn giá", "Số lượng tồn kho"};
        setCols(cols);
        setData(dataProduct);
    }
}
