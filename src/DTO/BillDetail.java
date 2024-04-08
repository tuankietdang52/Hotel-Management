package DTO;

public class BillDetail {
    private String billCode;
    private String productCode;
    private int quantity;
    private double total;

    public BillDetail(){

    }

    public BillDetail(String billCode, String productCode, int quantity, double total){
        this.billCode = billCode;
        this.productCode = productCode;
        this.quantity = quantity;
        this.total = total;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }
}
