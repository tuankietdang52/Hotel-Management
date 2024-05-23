package DTO;

public class BillDetail {
    private String billCode;
    private String productCode;
    private int quantity;

    public BillDetail(){

    }

    public BillDetail(String billCode, String productCode, int quantity){
        this.billCode = billCode;
        this.productCode = productCode;
        this.quantity = quantity;
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
}
