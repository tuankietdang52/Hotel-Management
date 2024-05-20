package BUS;

import DAO.ProductDAO;
import DAO.SaleDAO;
import DTO.Product;
import DTO.Sale;
import DTO.SaleDetail;

import java.util.ArrayList;

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
            Product product = productDAO.findProductByCode(saleDetail.getProductCode());
            listProduct.add(product);
        }

        return listProduct;
    }
}
