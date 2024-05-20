package DAO;

import DTO.Employee;
import SQL.SqlConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAO extends BaseDAO{
     public EmployeeDAO(){

     }

    private Employee getModel(ResultSet rs) throws SQLException {
        String employeeCode = rs.getString("MANV");
        String employeeFirstName = rs.getString("Ho");
        String employeeLastName = rs.getString("TEN");
        String username = rs.getString("TENDANGNHAP");
        String password = rs.getString("MATKHAU");
        String address = rs.getString("DIACHI");
        java.sql.Date birthday = rs.getDate("NGAYSINH");
        String job = rs.getString("TENCONGVIEC");
        double salary = rs.getDouble("LUONG");


        return new Employee(employeeCode, employeeFirstName, employeeLastName,
                username, password, "", address, birthday, job, salary);
    }

    public Employee findEmployeeByCode(String code){
        String query = String.format("SELECT * FROM NHANVIEN WHERE MANV = '%s'", code);

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

    public ArrayList<Employee> getEmployeeData() {
        ArrayList<Employee> listEmployee = new ArrayList<>();
        String query = "SELECT * FROM NHANVIEN";

        Connection cnt = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()){
                Employee employee = getModel(rs);
                listEmployee.add(employee);
            }
        }
        catch (SQLException | ClassNotFoundException ex){
            System.out.println("Khong the ket noi");
        }
        finally {
            closingConnection(cnt, stmt, rs);
        }

        return listEmployee;
    }

    public boolean addEmployee(Employee employee){
        String query = String.format("INSERT INTO NHANVIEN VALUES ('%s', '%s', '%s', '%s', '%s', '%s', ?, '%s', %s)",
                employee.getCode(), employee.getUsername(), employee.getPassword(), employee.getFirstName(),
                employee.getLastName(), employee.getAddress(), employee.getJob(), employee.getSalary());

        Connection cnt = null;
        PreparedStatement stmt = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            stmt.setDate(1, employee.getBirthday());

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

    public boolean adjustEmployee(Employee employee){
        String query = String.format("UPDATE NHANVIEN SET TENDANGNHAP = '%s', MATKHAU = '%s', HO = '%s', TEN = '%s', DIACHI = '%s', NGAYSINH = ?, TENCONGVIEC = '%s', LUONG = %s WHERE MANV = '%s'",
                employee.getUsername(),  employee.getPassword(), employee.getFirstName(), employee.getLastName(),
                employee.getAddress(), employee.getJob(), employee.getSalary(), employee.getCode());


        Connection cnt = null;
        PreparedStatement stmt = null;

        try{
            cnt = SqlConnect.getConnection();
            stmt = cnt.prepareStatement(query);
            stmt.setDate(1, employee.getBirthday());

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
    public boolean deleteEmployee(String employeeCode){
        String query = String.format("DELETE FROM NHANVIEN WHERE MANV = '%s'",
                employeeCode);

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
