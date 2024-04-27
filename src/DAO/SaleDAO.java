package DAO;

import DTO.Sale;
import DTO.SaleDetail;
import SQL.SqlConnect;
import org.jetbrains.annotations.NotNull;

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
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = SqlConnect.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery(query);
            while (rs.next()){
                String code = rs.getString("MACTKM");
                String name = rs.getString("TEN");
                java.sql.Date dateStart = rs.getDate("NGAYBATDAU");
                java.sql.Date dateEnd = rs.getDate("NGAYKETTHUC");
                String imagePath = rs.getString("HINH");

                Sale model = new Sale(code, name, dateStart, dateEnd, imagePath);
                model.setSaleDetails(getSaleDetails(code, stmt));

                saleModels.add(model);
            }
        }
        catch (ClassNotFoundException | SQLException ex){
            System.out.println("cannot connect loi~ r fen");
        }
        finally {
           ClosingConnection(con, stmt, rs);
        }
    }

    private @NotNull ArrayList<SaleDetail> getSaleDetails(String saleCode, @NotNull Statement stmt) throws SQLException{
        String query = """
                        SELECT *
                         FROM CHITIET_KMSP
                          WHERE MACTKM =""" + saleCode;

        ArrayList<SaleDetail> saleDetails = new ArrayList<>();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String salecode = rs.getString("MACTKM");
            String productCode = rs.getString("MASANPHAM");
            float percentDiscount = rs.getFloat("PHANTRAMKM");

            saleDetails.add(new SaleDetail(salecode, productCode, percentDiscount));
        }

        return saleDetails;
    }
}
