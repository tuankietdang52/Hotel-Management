package BUS;

import DAO.ProductDAO;
import DAO.ProductTypeDAO;
import DTO.Product;
import DTO.ProductType;
import Utilities.AppManager;
import Utilities.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

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
        return getProductDataTable(products);
    }

    public ArrayList<ProductType> getListProductType(){
        return productTypeDAO.getProductTypeData();
    }

    public Pair<Boolean, String> addProductToDatabase(ArrayList<String> input){
        int quantity = Integer.parseInt(input.get(2));
        double price = Double.parseDouble(input.getLast());

        Product product = new Product(AppManager.generateRandomCode(10), input.getFirst(), input.get(1),
                quantity, input.get(3), "", price);

        boolean res = productDao.addProduct(product);

        return res ? new Pair<>(true, "Thêm thành công") : new Pair<>(false, "Thêm thất bại");
    }

    public Pair<Boolean, String> adjustProduct(ArrayList<String> input, String productCode){
        int quantity = Integer.parseInt(input.get(2));
        double price = Double.parseDouble(input.getLast());

        Product product = new Product(productCode, input.getFirst(), input.get(1),
                quantity, input.get(3), "", price);

        boolean res = productDao.adjustProduct(product);

        return res ? new Pair<>(true, "Sửa thành công") : new Pair<>(false, "Sửa thất bại");
    }

    public Pair<Boolean, String> removeProduct(String productCode){
        boolean res = productDao.deleteProduct(productCode);
        return res ? new Pair<>(true, "Xóa thành công") : new Pair<>(false, "Xóa thất bại");
    }

    public ArrayList<ArrayList<String>> getProductDataTable(){
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

    public ArrayList<ArrayList<String>> getProductDataTable(ArrayList<Product> products){
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

    public Pair<Boolean, String> checkValidInput(ArrayList<String> dataInput){
        if (dataInput.getFirst().isEmpty()){
            return new Pair<>(false, "Ten san pham khong duoc de trong");
        }
        else if (!isValidNumberTextField(dataInput.get(2))){
            return new Pair<>(false, "So luong sai dinh dang hoac de trong");
        }
        else if (!isValidNumberTextField(dataInput.getLast())){
            return new Pair<>(false, "Gia sai dinh dang hoac de trong");
        }

        return new Pair<>(true, "");
    }

    public boolean isValidNumberTextField(String text){
        if (text.isEmpty()) return false;
        for (int i = 0; i < text.length(); i++){
            if (text.charAt(i) < 48 || text.charAt(i) > 57) return false;
        }

        return true;
    }
}
