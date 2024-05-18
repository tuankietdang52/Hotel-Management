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
    public ProductTypeDAO(){

    }

    private ProductType getModel(ResultSet rs) throws SQLException{
        String typeCode = rs.getString("MALOAI");
        String typeName = rs.getString("TENLOAI");

        return new ProductType(typeCode, typeName);
    }

    public ProductType findProductTypebyCode(String code){
        String query = String.format("SELECT * FROM LOAI WHERE MALOAI = '%s'", code);

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

    public ArrayList<ProductType> getProductTypeData() {
        ArrayList<ProductType> listProductType = new ArrayList<>();

        String query = "SELECT * FROM LOAI";

        Connection cnt = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()){
                ProductType productType = getModel(rs);
                listProductType.add(productType);
            }
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi");
        }
        finally {
            closingConnection(cnt, stmt, rs);
        }

        return listProductType;
    }
}
