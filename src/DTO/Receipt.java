package DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Receipt {
    private String receiptCode;
    private String employeeCode;
    private String supplierCode;
    private LocalDateTime dateCreated;
    private double total;

    public Receipt(){

    }

    public Receipt(String receiptCode, String employeeCode, String supplierCode, double total){
        this.receiptCode = receiptCode;
        this.employeeCode = employeeCode;
        this.supplierCode = supplierCode;

        this.total = total;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }
}
