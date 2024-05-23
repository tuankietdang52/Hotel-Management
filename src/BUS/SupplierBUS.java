package BUS;

import DAO.SupplierDAO;
import DTO.Supplier;
import Utilities.Pair;

import java.util.ArrayList;

public class SupplierBUS extends BaseBUS<Supplier> {
    private final SupplierDAO supplierDAO;

    public SupplierBUS(){
        supplierDAO = new SupplierDAO();
    }

    public ArrayList<Supplier> getListSupplier(){
        return supplierDAO.getSupplierData();
    }

    public Pair<Boolean, String> addSupplierToDatabase(Supplier supplier){;
        boolean res = supplierDAO.addSupplier(supplier);
        return res ? new Pair<>(true, "Thêm thành công") : new Pair<>(false, "Thêm thất bại");
    }

    public Pair<Boolean, String> adjustSupplier(Supplier supplier){
        boolean res = supplierDAO.adjustSupplier(supplier);
        return res ? new Pair<>(true, "Sửa thành công") : new Pair<>(false, "Sửa thất bại");
    }

    public Pair<Boolean, String> removeSupplier(Supplier supplier){
        boolean res = supplierDAO.deleteSupplier(supplier);
        return res ? new Pair<>(true, "Xóa thành công") : new Pair<>(false, "Xóa thất bại");
    }

    @Override
    public ArrayList<ArrayList<String>> getDataTable(){
        ArrayList<Supplier> suppliers = supplierDAO.getSupplierData();
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (Supplier supplier : suppliers){
            result.add(new ArrayList<>(){{
                add(supplier.getSupplierCode());
                add(supplier.getSupplierName());
                add(supplier.getAddress());
                add(supplier.getPhone());
            }});
        }

        return result;
    }

    public ArrayList<ArrayList<String>> getDataTable(ArrayList<Supplier> suppliers){
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (Supplier supplier : suppliers){
            result.add(new ArrayList<>(){{
                add(supplier.getSupplierCode());
                add(supplier.getSupplierName());
                add(supplier.getAddress());
                add(supplier.getPhone());
            }});
        }

        return result;
    }

    public Supplier getSupplierByCode(String code){
        return supplierDAO.findSupplierByCode(code);
    }

    public Pair<Boolean, String> checkValidInput(Supplier supplier){
        if (supplier.getSupplierName().isEmpty()){
            return new Pair<>(false, "Tên nhà cung cấp không được để trống");
        }
        else if (supplier.getAddress().isEmpty()){
            return new Pair<>(false, "Địa chỉ không được để trống");
        }
        else if (!isValidNumberTextField(supplier.getPhone(), "int") || !isValidPhone(supplier.getPhone())){
            return new Pair<>(false, "SDT sai định dạng hoặc trống");
        }

        return new Pair<>(true, "");
    }
}
