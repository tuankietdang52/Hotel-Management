package DAO;

import DTO.Customer;
import SQL.SqlConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO extends BaseDAO<Customer>{
    public CustomerDAO(){

    }

    @Override
    protected Customer getModel(ResultSet rs) throws SQLException {
        String customerCode = rs.getString("MAKH");
        String customerFirstName = rs.getString("Ho");
        String customerLastName = rs.getString("TEN");
        String username = rs.getString("TENDANGNHAP");
        String password = rs.getString("MATKHAU");
        String address = rs.getString("DIACHI");
        String phone = rs.getString("SDT");
        java.sql.Date birthday = rs.getDate("NGAYSINH");

        return new Customer(customerCode, customerFirstName, customerLastName,
                username, password, phone, address, birthday);
    }

    public Customer findCustomerByCode(String code){
        String query = String.format("SELECT * FROM KHACHHANG WHERE MAKH = '%s'", code);

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

    public ArrayList<Customer> getCustomerData() {
        ArrayList<Customer> listCustomer = new ArrayList<>();
        String query = "SELECT * FROM KHACHHANG";

        Connection cnt = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()){
                Customer customer = getModel(rs);
                listCustomer.add(customer);
            }
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi" + ex);
        }
        finally {
            closingConnection(cnt, stmt, rs);
        }

        return listCustomer;
    }

    public boolean addCustomer(Customer customer){
        String query = String.format("INSERT INTO KHACHHANG VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', ?)",
                customer.getCode(), customer.getUsername(), customer.getPassword(), customer.getFirstName(),
                customer.getLastName(), customer.getAddress(), customer.getPhone());

        Connection cnt = null;
        PreparedStatement stmt = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            stmt.setDate(1, customer.getBirthday());

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

    public boolean adjustCustomer(Customer customer){
        String query = String.format("UPDATE KHACHHANG SET TENDANGNHAP = '%s', MATKHAU = '%s', HO = '%s', TEN = '%s', DIACHI = '%s', SDT = '%s', NGAYSINH = ? WHERE MAKH = '%s'",
                customer.getUsername(),  customer.getPassword(), customer.getFirstName(), customer.getLastName(),
                customer.getAddress(), customer.getPhone(), customer.getCode());


        Connection cnt = null;
        PreparedStatement stmt = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            stmt.setDate(1, customer.getBirthday());

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
    public boolean deleteCustomer(Customer customer){
        String query = String.format("DELETE FROM KHACHHANG WHERE MAKH = '%s'",
                customer.getCode());

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
