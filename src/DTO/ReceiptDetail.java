package DTO;

public class ReceiptDetail {
    private String receiptCode;
    private String productCode;
    private int quantity;
    private double price;
    private double total;

    public ReceiptDetail(){

    }

    public ReceiptDetail(String receiptCode, String productCode, int quantity, double price){
        this.receiptCode = receiptCode;
        this.productCode = productCode;
        this.quantity = quantity;
        this.price = price;

        this.total = price * quantity;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public String getReceiptCode() {
        return receiptCode;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }
}