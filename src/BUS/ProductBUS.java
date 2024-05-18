package BUS;

import DAO.ProductDAO;
import DAO.ProductTypeDAO;
import DTO.Product;
import DTO.ProductType;

import javax.swing.*;
import java.util.ArrayList;

public class ProductBUS {
    private final ProductDAO productDao;
    private final ProductTypeDAO productTypeDAO;

    public ProductBUS(){
        productDao = new ProductDAO();
        productTypeDAO = new ProductTypeDAO();
    }

    public ArrayList<Product> getListProduct(){
        return productDao.getProductData();
    }

    public ArrayList<ProductType> getListProductType(){
        return productTypeDAO.getProductTypeData();
    }
}
