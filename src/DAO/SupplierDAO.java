package DAO;

import DTO.Supplier;
import SQL.SqlConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAO extends BaseDAO<Supplier> {
    @Override
    protected Supplier getModel(ResultSet rs) throws SQLException {
        String supplierCode = rs.getString("MANCC");
        String supplierName = rs.getString("TEN_NCC");
        String address = rs.getString("DIACHI");
        String phone = rs.getString("SDT");

        return new Supplier(supplierCode, supplierName, address, phone);
    }

    public Supplier findSupplierByCode(String code){
        String query = String.format("SELECT * FROM NHACUNGCAP WHERE MANCC = '%s'", code);

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

    public ArrayList<Supplier> getSupplierData() {
        ArrayList<Supplier> listSupplier = new ArrayList<>();
        String query = "SELECT * FROM NHACUNGCAP";

        Connection cnt = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()){
                Supplier supplier = getModel(rs);
                listSupplier.add(supplier);
            }
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi" + ex);
        }
        finally {
            closingConnection(cnt, stmt, rs);
        }

        return listSupplier;
    }

    public boolean addSupplier(Supplier supplier){
        String query = String.format("INSERT INTO NHACUNGCAP VALUES ('%s', '%s', '%s', '%s')",
                supplier.getSupplierCode(), supplier.getSupplierName(), supplier.getAddress(),
                supplier.getPhone());

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

    public boolean adjustSupplier(Supplier supplier){
        String query = String.format("UPDATE NHACUNGCAP SET TEN_NCC = '%s', DIACHI = '%s', SDT = '%s' WHERE MANCC = '%s'",
                supplier.getSupplierName(), supplier.getAddress(), supplier.getPhone(), supplier.getSupplierCode());


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

    public boolean deleteSupplier(Supplier supplier){
        String query = String.format("DELETE FROM NHACUNGCAP WHERE MANCC = '%s'",
                supplier.getSupplierCode());

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
