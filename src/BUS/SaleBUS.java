package BUS;

import DAO.ProductDAO;
import DAO.SaleDAO;
import DTO.Product;
import DTO.Sale;
import DTO.SaleDetail;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import java.util.Objects;

public class SaleBUS {
    private final SaleDAO saleDAO;
    private final ProductDAO productDAO;

    public SaleBUS(){
        saleDAO = new SaleDAO();
        productDAO = new ProductDAO();
    }

    public ArrayList<Sale> getListSale(){
        return saleDAO.getSaleData();
    }

    public ArrayList<Product> getSaleProduct(String saleCode){
        ArrayList<Product> listProduct = new ArrayList<>();
        ArrayList<SaleDetail> listSaleDetail = saleDAO.getSaleDetailsByCode(saleCode);

        for (SaleDetail saleDetail : listSaleDetail){
            Product product = productDAO.findProductbyCode(saleDetail.getProductCode());
            listProduct.add(product);
        }

        return listProduct;
    }
}
