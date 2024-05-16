package DAO;

import DTO.Product;
import SQL.SqlConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO extends BaseDAO{
    private final ArrayList<Product> listProduct;

    public ProductDAO(){
        listProduct = new ArrayList<>();
    }

    public ArrayList<Product> getListProduct(){
        return listProduct;
    }

    @Override
    protected void RetrieveData() {
        String query = "SELECT * FROM SANPHAM";

        Connection cnt;
        PreparedStatement stmt;
        ResultSet rs;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()){
                String productCode = rs.getString("MASANPHAM");
                String productName = rs.getString("TENSANPHAM");
                int quantity = rs.getInt("SOLUONG");
                String description = rs.getString("MOTA");
                float price = rs.getFloat("DONGIA");
                String imagePath = rs.getString("HINH");

                listProduct.add(new Product(productCode, productName, "d", quantity, description, imagePath, price));
            }
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi");
        }
    }
}
