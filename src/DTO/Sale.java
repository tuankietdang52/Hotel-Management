package DTO;

import Interface.IList;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sale implements IList<SaleDetail> {
    private String saleCode;
    private String name;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;

    private ArrayList<SaleDetail> saleDetails;

    public Sale(){

    }

    public Sale(String saleCode, String name, LocalDateTime dateStart, LocalDateTime dateEnd){
        this.saleCode = saleCode;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
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

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
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
