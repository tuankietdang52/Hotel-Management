package BUS;

import DAO.*;
import DTO.Bill;
import DTO.BillDetail;
import DTO.Product;
import Utilities.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BillBUS extends BaseBUS<Bill> {
    private final BillDAO billDAO;
    private final BillDetailDAO billDetailDAO;
    private final EmployeeDAO employeeDAO;
    private final CustomerDAO customerDAO;
    private final ProductDAO productDAO;

    public BillBUS(){
        billDAO = new BillDAO();
        billDetailDAO = new BillDetailDAO();
        employeeDAO = new EmployeeDAO();
        customerDAO = new CustomerDAO();
        productDAO = new ProductDAO();
    }

    public ArrayList<Bill> getListBill(){
        return billDAO.getBillData();
    }

    public Pair<Boolean, String> addBillToDatabase(Bill bill){
        boolean res = billDAO.addBill(bill);
        return res ? new Pair<>(true, "Thêm thành công") : new Pair<>(false, "Thêm thất bại");
    }

    private boolean updateRemainProduct(BillDetail billDetail, int oldQuantity){
        Product product = productDAO.findProductByCode(billDetail.getProductCode());
        int quantity = billDetail.getQuantity() - oldQuantity;
        product.setQuantity(product.getQuantity() - quantity);

        return productDAO.adjustProduct(product);
    }

    public Pair<Boolean, String> addBillDetailToDatabase(BillDetail billDetail){
        boolean res = billDetailDAO.addBillDetail(billDetail) && updateRemainProduct(billDetail, 0);
        return res ? new Pair<>(true, "Thêm thành công") : new Pair<>(false, "Thêm thất bại");
    }

    private Pair<Boolean, BillDetail> checkBillDetail(BillDetail billDetail){
        BillDetail src = billDetailDAO.getBillDetailByBillAndProductCode(billDetail.getBillCode(), billDetail.getProductCode());
        if (src == null){
            return new Pair<>(false, billDetail);
        }

        src.setQuantity(src.getQuantity() + billDetail.getQuantity());
        return new Pair<>(true, src);
    }

    public Pair<Boolean, String> adjustBill(Bill bill){
        boolean res = billDAO.adjustBill(bill);
        return res ? new Pair<>(true, "Sửa thành công") : new Pair<>(false, "Sửa thất bại");
    }

    public Pair<Boolean, String> adjustBillDetail(BillDetail billDetail, int oldQuantity){
        boolean res = billDetailDAO.adjustBillDetail(billDetail) && updateRemainProduct(billDetail, oldQuantity);
        return res ? new Pair<>(true, "Sửa thành công") : new Pair<>(false, "Sửa thất bại");
    }

    private boolean returnAllProduct(Bill bill){
        ArrayList<BillDetail> billDetails = billDetailDAO.findBillDetailByBillCode(bill.getBillCode());

        for (BillDetail billDetail : billDetails){
            int quantity = billDetail.getQuantity() - billDetail.getQuantity() * 2;
            Product product = productDAO.findProductByCode(billDetail.getProductCode());

            if (product == null) return false;

            product.setQuantity(product.getQuantity() - quantity);
            if (!productDAO.adjustProduct(product)){
                return false;
            }
        }

        return true;
    }

    public Pair<Boolean, String> removeBill(Bill bill){
        boolean res = returnAllProduct(bill) && billDAO.deleteBill(bill);
        return res ? new Pair<>(true, "Xóa thành công") : new Pair<>(false, "Xóa thất bại");
    }

    public Pair<Boolean, String> removeBillDetail(BillDetail billDetail){
        boolean res = billDetailDAO.deleteBillDetail(billDetail) && updateRemainProduct(billDetail, billDetail.getQuantity() * 2);
        return res ? new Pair<>(true, "Xóa thành công") : new Pair<>(false, "Xóa thất bại");
    }

    public double calculateTotal(Bill bill){
        ArrayList<BillDetail> billDetails = billDetailDAO.findBillDetailByBillCode(bill.getBillCode());
        double total = 0;

        for (BillDetail billDetail : billDetails){
            double price = productDAO.findProductByCode(billDetail.getProductCode()).getPrice();
            total += billDetail.getQuantity() * price;
        }

        return total;
    }

    private ArrayList<String> gatherData(Bill bill){
        DecimalFormat priceFormat = new DecimalFormat("#,###");
        int total = (int) calculateTotal(bill);

        return new ArrayList<>(){{
            add(bill.getBillCode());
            add(customerDAO.findCustomerByCode(bill.getCustomerCode()).getFullName());
            add(employeeDAO.findEmployeeByCode(bill.getEmployeeCode()).getFullName());
            add(priceFormat.format(total) + " đ");
            add(bill.getDateCreated().toString());
            add(bill.getCustomerCode());
            add(bill.getEmployeeCode());
        }};
    }

    private ArrayList<String> gatherDetailData(BillDetail billDetail){
        Product product = productDAO.findProductByCode(billDetail.getProductCode());
        DecimalFormat priceFormat = new DecimalFormat("#,###");
        double total = product.getPrice() * billDetail.getQuantity();

        return new ArrayList<>(){{
            add(product.getProductCode());
            add(product.getProductName());
            add(priceFormat.format((int)product.getPrice()) + " đ");
            add(Integer.toString(billDetail.getQuantity()));
            add(priceFormat.format(total) + " đ");
            add(billDetail.getBillCode());
        }};
    }

    @Override
    public ArrayList<ArrayList<String>> getDataTable(){
        ArrayList<Bill> bills = billDAO.getBillData();
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (Bill bill : bills){
            result.add(gatherData(bill));
        }

        return result;
    }

    public ArrayList<ArrayList<String>> getDetailDataTable(Bill bill){
        if (bill == null) return new ArrayList<>();

        ArrayList<BillDetail> billDetails = billDetailDAO.findBillDetailByBillCode(bill.getBillCode());
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (BillDetail billDetail : billDetails){
            result.add(gatherDetailData(billDetail));
        }

        return result;
    }

    public ArrayList<ArrayList<String>> getDataTable(ArrayList<Bill> bills){
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (Bill bill : bills){
            result.add(gatherData(bill));
        }

        return result;
    }

    public Bill getBillByCode(String code){
        return billDAO.findBillByCode(code);
    }

    public BillDetail getBillDetailByCode(String billCode, String productCode){
        return billDetailDAO.getBillDetailByBillAndProductCode(billCode, productCode);
    }

    @Override
    public Pair<Boolean, String> checkValidInput(Bill bill){
        if (customerDAO.findCustomerByCode(bill.getCustomerCode()) == null){
            return new Pair<>(false, "Không có khách hàng trong cơ sở dữ liệu");
        }

        if (employeeDAO.findEmployeeByCode(bill.getEmployeeCode()) == null){
            return new Pair<>(false, "Không có nhân viên trong cơ sở dữ liệu");
        }

        return new Pair<>(true, "");
    }

    public Pair<Boolean, String> checkDetailValidInput(BillDetail billDetail){
        if (billDetail.getProductCode().isEmpty()){
            return new Pair<>(false, "Mã sản phẩm không được để trống");
        }
        else if (!checkQuantity(billDetail.getProductCode(), billDetail.getQuantity())){
            return new Pair<>(false, "Không đủ số lượng trong kho");
        }

        return new Pair<>(true, "");
    }

    private boolean checkQuantity(String code, int quantity){
        int remainQuantity = productDAO.findProductByCode(code).getQuantity();

        return quantity <= remainQuantity;
    }
}
