package BUS;

import DAO.ProductDAO;
import DAO.ProductTypeDAO;
import DTO.Product;
import DTO.ProductType;
import Utilities.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class ProductBUS extends BaseBUS<Product> {
    private final ProductDAO productDao;
    private final ProductTypeDAO productTypeDAO;

    public ProductBUS(){
        productDao = new ProductDAO();
        productTypeDAO = new ProductTypeDAO();
    }

    public ArrayList<Product> getListProduct(){
        return productDao.getProductData();
    }

    public ArrayList<Product> filtProductByTypeCode(String typeCode){
        ArrayList<Product> products = productDao.getProductData();
        ArrayList<Product> filtProduct = new ArrayList<>();

        for (Product product : products){
            if (!Objects.equals(product.getTypeCode(), typeCode)) continue;

            filtProduct.add(product);
        }

        return filtProduct;
    }

    public ArrayList<ArrayList<String>> filtProductTableByType(ProductType productType){
        ArrayList<Product> products = filtProductByTypeCode(productType.getTypeCode());
        return getDataTable(products);
    }

    public ArrayList<ProductType> getListProductType(){
        return productTypeDAO.getProductTypeData();
    }

    public Pair<Boolean, String> addProductToDatabase(Product product){
        boolean res = productDao.addProduct(product);
        return res ? new Pair<>(true, "Thêm thành công") : new Pair<>(false, "Thêm thất bại");
    }

    public Pair<Boolean, String> adjustProduct(Product product){
        boolean res = productDao.adjustProduct(product);
        return res ? new Pair<>(true, "Sửa thành công") : new Pair<>(false, "Sửa thất bại");
    }

    public Pair<Boolean, String> removeProduct(Product product){
        boolean res = productDao.deleteProduct(product);
        return res ? new Pair<>(true, "Xóa thành công") : new Pair<>(false, "Xóa thất bại");
    }

    @Override
    public ArrayList<ArrayList<String>> getDataTable(){
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

    public ArrayList<ArrayList<String>> getDataTable(ArrayList<Product> products){
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

    public Product getProductByCode(String code){
        return productDao.findProductByCode(code);
    }

    public ProductType getProductTypeByCode(String code){
        return productTypeDAO.findProductTypebyCode(code);
    }

    @Override
    public Pair<Boolean, String> checkValidInput(Product product){
        if (product.getProductName().isEmpty()){
            return new Pair<>(false, "Tên sản phẩm không được để trống");
        }

        return new Pair<>(true, "");
    }
}
