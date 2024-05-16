package DAO;

import DTO.Product;
import DTO.ProductType;
import SQL.SqlConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductTypeDAO extends BaseDAO {
    private final ArrayList<ProductType> listProductType;

    public ProductTypeDAO(){
        listProductType = new ArrayList<>();
    }

    public ArrayList<ProductType> getListProductType() {
        return listProductType;
    }

    @Override
    protected void RetrieveData() {
        String query = "SELECT * FROM LOAI";

        Connection cnt;
        PreparedStatement stmt;
        ResultSet rs;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()){
                String typeCode = rs.getString("MALOAI");
                String typeName = rs.getString("TENLOAI");

                listProductType.add(new ProductType(typeCode, typeName));
            }
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi");
        }
    }
}
