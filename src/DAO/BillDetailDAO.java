package DAO;

import DTO.BillDetail;
import SQL.SqlConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillDetailDAO extends BaseDAO<BillDetail> {
    @Override
    protected BillDetail getModel(ResultSet rs) throws SQLException {
        String billCode = rs.getString("MAHOADON");
        String productCode = rs.getString("MASANPHAM");
        int amount = rs.getInt("SOLUONG");

        return new BillDetail(billCode, productCode, amount);
    }

    public ArrayList<BillDetail> findBillDetailByBillCode(String code){
        String query = String.format("SELECT * FROM CHITIETHOADON WHERE MAHOADON = '%s'", code);

        ArrayList<BillDetail> listBillDetail = new ArrayList<>();

        Connection cnt = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()){
                listBillDetail.add(getModel(rs));
            }
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi" + ex);
            return listBillDetail;
        }
        finally {
            closingConnection(cnt, stmt, rs);
        }

        return listBillDetail;
    }

    public BillDetail getBillDetailByBillAndProductCode(String billCode, String productCode){
        String query = String.format("SELECT * FROM CHITIETHOADON WHERE MAHOADON = '%s' AND MASANPHAM = '%s'", billCode, productCode);

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
            System.out.println("Khong the ket noi" + ex);
            return null;
        }
        finally {
            closingConnection(cnt, stmt, rs);
        }
    }

    public ArrayList<BillDetail> getBillDetailData() {
        ArrayList<BillDetail> listBillDetail = new ArrayList<>();
        String query = "SELECT * FROM CHITIETHOADON";

        Connection cnt = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()){
                BillDetail billDetail = getModel(rs);
                listBillDetail.add(billDetail);
            }
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi" + ex);
        }
        finally {
            closingConnection(cnt, stmt, rs);
        }

        return listBillDetail;
    }

    public boolean addBillDetail(BillDetail billDetail){
        String query = String.format("INSERT INTO CHITIETHOADON VALUES ('%s', '%s', %s)",
                billDetail.getBillCode(), billDetail.getProductCode(), billDetail.getQuantity());

        Connection cnt = null;
        PreparedStatement stmt = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);

            stmt.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi" + ex);
            return false;
        }
        finally {
            closingConnection(cnt, stmt, null);
        }

        return true;
    }

    public boolean adjustBillDetail(BillDetail billDetail){
        String query = String.format("UPDATE CHITIETHOADON SET SOLUONG = %s WHERE MAHOADON = '%s' AND MASANPHAM = '%s'",
                billDetail.getQuantity(),
                billDetail.getBillCode(), billDetail.getProductCode());

        Connection cnt = null;
        PreparedStatement stmt = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);

            stmt.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi" + ex);
            return false;
        }
        finally {
            closingConnection(cnt, stmt, null);
        }

        return true;
    }

    public boolean deleteBillDetail(BillDetail billDetail){
        String query = String.format("DELETE FROM CHITIETHOADON WHERE MAHOADON = '%s' AND MASANPHAM = '%s'",
                billDetail.getBillCode(), billDetail.getProductCode());

        Connection cnt = null;
        PreparedStatement stmt = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            stmt.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi" + ex);
            return false;
        }
        finally {
            closingConnection(cnt, stmt, null);
        }

        return true;
    }
}
