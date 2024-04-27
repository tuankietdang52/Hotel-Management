package DTO;

import Interface.IList;

import java.util.ArrayList;

public class Sale implements IList<SaleDetail> {
    private String saleCode;
    private String name;
    private java.sql.Date dateStart;
    private java.sql.Date dateEnd;
    private String imagePath;

    private ArrayList<SaleDetail> saleDetails;

    public Sale(){
        saleDetails = new ArrayList<>();
    }

    public Sale(String saleCode, String name, java.sql.Date dateStart, java.sql.Date dateEnd, String imagePath){
        this.saleCode = saleCode;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.imagePath = imagePath;

        saleDetails = new ArrayList<>();
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDateStart(java.sql.Date dateStart) {
        this.dateStart = dateStart;
    }

    public java.sql.Date getDateStart() {
        return dateStart;
    }

    public void setDateEnd(java.sql.Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public java.sql.Date getDateEnd() {
        return dateEnd;
    }

    public void setSaleDetails(ArrayList<SaleDetail> saleDetails) {
        this.saleDetails = saleDetails;
    }

    public ArrayList<SaleDetail> getSaleDetails() {
        return saleDetails;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void add(SaleDetail item) {
        this.saleDetails.add(item);
    }

    @Override
    public void remove(SaleDetail item) {
        this.saleDetails.remove(item);
    }

    @Override
    public void calculateTotal() {

    }
}
