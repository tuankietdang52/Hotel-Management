package DAO;

import DTO.Sale;
import DTO.SaleDetail;
import SQL.SqlConnect;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;

public class SaleDAO extends BaseDAO {

    public SaleDAO(){}

    private Sale getSaleModel(ResultSet rs) throws SQLException{
        String code = rs.getString("MACTKM");
        String name = rs.getString("TEN");
        java.sql.Date dateStart = rs.getDate("NGAYBATDAU");
        java.sql.Date dateEnd = rs.getDate("NGAYKETTHUC");
//        String imagePath = rs.getString("HINH");

        return new Sale(code, name, dateStart, dateEnd, "");
    }

    private SaleDetail getSaleDetailModel(ResultSet rs) throws SQLException{
        String salecode = rs.getString("MACTKM");
        String productCode = rs.getString("MASANPHAM");
        float percentDiscount = rs.getFloat("PHANTRAMKM");

        return new SaleDetail(salecode, productCode, percentDiscount);
    }

    public ArrayList<Sale> getSaleData(){
        ArrayList<Sale> listSale = new ArrayList<>();
        String query = "SELECT * FROM CHITIET_KM";

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = SqlConnect.getConnection();
            stmt = con.prepareStatement(query);

            rs = stmt.executeQuery();
            while (rs.next()){
                Sale sale = getSaleModel(rs);

                sale.setSaleDetails(getSaleDetailsByCode(sale.getSaleCode(), con));

                listSale.add(sale);
            }
        }
        catch (ClassNotFoundException | SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally {
           closingConnection(con, stmt, rs);
        }

        return listSale;
    }

    public ArrayList<SaleDetail> getSaleDetailsByCode(String saleCode){
        String query = String.format("SELECT * FROM CHITIET_KMSP WHERE MACTKM = '%s'", saleCode);

        ArrayList<SaleDetail> saleDetails = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            con = SqlConnect.getConnection();
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                SaleDetail saleDetail = getSaleDetailModel(rs);
                saleDetails.add(saleDetail);
            }
        }
        catch (ClassNotFoundException | SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally {
            closingConnection(con, stmt, rs);
        }

        return saleDetails;
    }

    private ArrayList<SaleDetail> getSaleDetailsByCode(String saleCode, Connection con) throws SQLException{
        String query = String.format("SELECT * FROM CHITIET_KMSP WHERE MACTKM = '%s'", saleCode);

        ArrayList<SaleDetail> saleDetails = new ArrayList<>();

        PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            SaleDetail saleDetail = getSaleDetailModel(rs);
            saleDetails.add(saleDetail);
        }

        stmt.close();
        return saleDetails;
    }
}
