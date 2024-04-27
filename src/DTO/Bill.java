package DTO;

import Interface.IList;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bill implements IList<BillDetail> {
    private String billCode;
    private String customerCode;
    private String employeeCode;
    private double total;
    private java.sql.Date dateCreated;

    private ArrayList<BillDetail> billDetails;

    public Bill(){
        billDetails = new ArrayList<>();
    }

    public Bill(String billCode, String customerCode, String employeeCode){
        this.billCode = billCode;
        this.customerCode = customerCode;
        this.employeeCode = employeeCode;

        billDetails = new ArrayList<>();
    }

    public void setBillCode(String receiptCode) {
        this.billCode = receiptCode;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setDateCreated(java.sql.Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public java.sql.Date getDateCreated() {
        return dateCreated;
    }

    public void setReceiptDetails(ArrayList<BillDetail> receiptDetails) {
        this.billDetails = receiptDetails;
    }

    public ArrayList<BillDetail> getReceiptDetails() {
        return billDetails;
    }

    public void add(BillDetail detail){
        this.billDetails.add(detail);
        this.total += detail.getTotal();
    }

    public void remove(BillDetail detail){
        this.billDetails.remove(detail);
        this.total -= detail.getTotal();
    }

    public void calculateTotal(){
        this.total = 0;
        for (var detail : this.billDetails){
            this.total += detail.getTotal();
        }
    }
}