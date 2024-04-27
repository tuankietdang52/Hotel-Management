package DAO;

import DTO.Sale;
import DTO.SaleDetail;
import SQL.SqlConnect;

import java.sql.*;
import java.util.ArrayList;

public class SaleDAO extends BaseDAO {
    private ArrayList<Sale> saleModels;

    public SaleDAO(){
        saleModels = new ArrayList<>();
        RetrieveData();
    }

    public void setSaleModels(ArrayList<Sale> saleModels) {
        this.saleModels = saleModels;
    }

    public ArrayList<Sale> getSaleModels() {
        return saleModels;
    }

    @Override
    protected void RetrieveData(){
        String query = "SELECT * FROM CHITIET_KM";

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = SqlConnect.getConnection();
            stmt = con.prepareStatement(query);

            rs = stmt.executeQuery();
            while (rs.next()){
                String code = rs.getString("MACTKM");
                String name = rs.getString("TEN");
                java.sql.Date dateStart = rs.getDate("NGAYBATDAU");
                java.sql.Date dateEnd = rs.getDate("NGAYKETTHUC");
                String imagePath = rs.getString("HINH");

                saleModels.add(new Sale(code, name, dateStart, dateEnd, imagePath));
            }
        }
        catch (ClassNotFoundException | SQLException ex){
            System.out.println("cannot connect loi~ r fen");
        }
        finally {
           ClosingConnection(con, stmt, rs);
        }
    }

    public ArrayList<SaleDetail> getSaleDetails(String saleCode){
        String query = """
                        SELECT MACTKM, MASANPHAM, PHANTRAMKM
                         FROM CHITIET_KMSP
                          WHERE MACTKM =""" + saleCode;

        ArrayList<SaleDetail> saleDetails = new ArrayList<>();

        try {
            var con = SqlConnect.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
               String salecode = rs.getString("MACTKM");
               String productCode = rs.getString("MASANPHAM");
               float percentDiscount = rs.getFloat("PHANTRAMKM");

               saleDetails.add(new SaleDetail(salecode, productCode, percentDiscount));
            }
        }
        catch (ClassNotFoundException | SQLException ex){
            System.out.println("cannot connect loi~ r fen");
        }

        return saleDetails;
    }
}
