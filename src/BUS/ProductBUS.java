package BUS;

import DAO.ProductDAO;
import DAO.ProductTypeDAO;
import DTO.Product;
import DTO.ProductType;

import javax.swing.*;
import java.text.DecimalFormat;
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

    public ArrayList<ArrayList<String>> getProductToDataTable(){
        ArrayList<Product> products = productDao.getProductData();
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        DecimalFormat priceFormat = new DecimalFormat("#,###");

        for (Product product : products){
            result.add(new ArrayList<>(){{
                add(product.getProductCode());
                add(product.getProductName());
                add(getProductTypeByCode(product.getTypeCode()).getTypeName());
                add(priceFormat.format((int)product.getPrice()) + "đ");
                add(Integer.toString(product.getQuantity()));
            }});
        }

        return result;
    }

    private ProductType getProductTypeByCode(String code){
        return productTypeDAO.findProductTypebyCode(code);
    }
}
