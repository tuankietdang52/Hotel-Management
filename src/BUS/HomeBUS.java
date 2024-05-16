package BUS;

import DAO.ProductDAO;
import DTO.Product;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

public class HomeBUS {
    private ProductDAO productDAO;

    public HomeBUS(){
        productDAO = new ProductDAO();
    }

    public ArrayList<Product> getListProduct(){
        return productDAO.getListProduct();
    }
}
