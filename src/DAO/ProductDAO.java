package DAO;

import DTO.Product;
import SQL.SqlConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO extends BaseDAO{

    public ProductDAO(){

    }

    private Product getModel(ResultSet rs) throws SQLException{
        String productCode = rs.getString("MASANPHAM");
        String productName = rs.getString("TENSANPHAM");
        int quantity = rs.getInt("SOLUONG");
        String typeCode = rs.getString("MALOAI");
        String description = rs.getString("MOTA");
        float price = rs.getFloat("DONGIA");
//        String imagePath = rs.getString("HINH");

        return new Product(productCode, productName, typeCode, quantity, description, "", price);
    }

    public Product findProductbyCode(String code){
        String query = String.format("SELECT * FROM SANPHAM WHERE MASANPHAM = '%s'", code);

        Connection cnt = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            rs = stmt.executeQuery();

            if (!rs.next()) return null;

            return getModel(rs);
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi");
            return null;
        }
        finally {
            closingConnection(cnt, stmt, rs);
        }
    }

    public ArrayList<Product> getProductData() {
        ArrayList<Product> listProduct = new ArrayList<>();
        String query = "SELECT * FROM SANPHAM";

        Connection cnt = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()){
                Product product = getModel(rs);
                listProduct.add(product);
            }
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi");
        }
        finally {
            closingConnection(cnt, stmt, rs);
        }

        return listProduct;
    }
}
