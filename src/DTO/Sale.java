package DTO;

import java.util.ArrayList;

public class Sale{
    private String saleCode;
    private String name;
    private java.sql.Date dateStart;
    private java.sql.Date dateEnd;
    private String imagePath;

    public Sale(){
    }

    public Sale(String saleCode, String name, java.sql.Date dateStart, java.sql.Date dateEnd, String imagePath){
        this.saleCode = saleCode;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.imagePath = imagePath;
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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return name;
    }
}
