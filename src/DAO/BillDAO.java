package DAO;

import DTO.Bill;
import SQL.SqlConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillDAO extends BaseDAO<Bill> {
    @Override
    protected Bill getModel(ResultSet rs) throws SQLException {
        String billCode = rs.getString("MAHOADON");
        String employeeCode = rs.getString("MAKH");
        String customerCode = rs.getString("MANV");
        java.sql.Date dateCreated = rs.getDate("NGAYLAP");

        return new Bill(billCode, employeeCode, customerCode, dateCreated);
    }

    public Bill findBillByCode(String code){
        String query = String.format("SELECT * FROM HOADON WHERE MAHOADON = '%s'", code);

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

    public ArrayList<Bill> getBillData() {
        ArrayList<Bill> listBill = new ArrayList<>();
        String query = "SELECT * FROM HOADON";

        Connection cnt = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()){
                Bill bill = getModel(rs);
                listBill.add(bill);
            }
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi" + ex);
        }
        finally {
            closingConnection(cnt, stmt, rs);
        }

        return listBill;
    }

    public boolean addBill(Bill bill){
        String query = String.format("INSERT INTO HOADON VALUES ('%s', '%s', '%s', ?)",
                bill.getBillCode(), bill.getCustomerCode(), bill.getEmployeeCode());

        Connection cnt = null;
        PreparedStatement stmt = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            stmt.setDate(1, bill.getDateCreated());

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

    public boolean adjustBill(Bill bill){
        String query = String.format("UPDATE HOADON SET MAKH = '%s', MANV = '%s', NGAYLAP = ? WHERE MAHOADON = '%s'",
                bill.getCustomerCode(), bill.getEmployeeCode(), bill.getBillCode());

        Connection cnt = null;
        PreparedStatement stmt = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            stmt.setDate(1, bill.getDateCreated());

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
    public boolean deleteBill(Bill bill){
        String query = String.format("DELETE FROM HOADON WHERE MAHOADON = '%s'",
                bill.getBillCode());

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
