package DTO;

public class SaleDetail {
    private String saleCode;
    private String productCode;
    private double discountPercent;

    public SaleDetail(){

    }

    public SaleDetail(String saleCode, String productCode, double discountPercent){
        this.saleCode = saleCode;
        this.productCode = productCode;
        this.discountPercent = discountPercent;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }
}
