package DTO;

public class Supplier {
    private String supplierCode;
    private String supplierName;
    private String address;
    private String phone;

    public Supplier(){

    }

    public Supplier(String supplierCode, String supplierName, String address, String phone){
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.address = address;
        this.phone = phone;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
