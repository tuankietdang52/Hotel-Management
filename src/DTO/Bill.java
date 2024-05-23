package DTO;

public class Bill {
    private String billCode;
    private String customerCode;
    private String employeeCode;
    private java.sql.Date dateCreated;

    public Bill() {
    }

    public Bill(String billCode, String customerCode, String employeeCode,
                java.sql.Date dateCreated){
        this.billCode = billCode;
        this.customerCode = customerCode;
        this.employeeCode = employeeCode;
        this.dateCreated = dateCreated;
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

    public void setDateCreated(java.sql.Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public java.sql.Date getDateCreated() {
        return dateCreated;
    }
}