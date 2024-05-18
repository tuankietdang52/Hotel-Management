/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import DTO.khDTO;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author ASUS
 */
public class khDAO {
    private Connection con;
    //ket noi database
    public boolean openConnection(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Shiba;trustServerCertificate=true";
            String USERNAME = "shiba";
            String PASSWORD = "bruh";
            con = DriverManager.getConnection(url, USERNAME, PASSWORD);
            return true;
        }
        catch(Exception ex){
            System.out.println(ex);
            return false;
        }
    }
    //dong ket noi connection
    public void closeConnection() {
        try{
            if(con != null){
                con.close();
            }
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
    }
    //ket thuc ket noi
    
    
    
    
        public ArrayList<khDTO> getAllKHACHHANG(){
        ArrayList<khDTO> arr = new ArrayList<khDTO>();
        if(openConnection()){
            try{
                String sql="Select * from KHACHHANG";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    khDTO n = new khDTO();
                    n.setMaKH(rs.getString("maKH"));
                    n.setTenDangNhapKH(rs.getString("tenDangNhapKH"));
                    n.setMatKhauKH(rs.getString("matKhauKH"));
                    n.setHoKH(rs.getString("hoKH"));
                    n.setTenKH(rs.getString("tenKH"));
                    n.setDiaChiKH(rs.getString("diaChiKH"));
                    n.setSdtKH(rs.getString("sdtKH"));
                    n.setDobKH(rs.getDate("ngaySinhKH"));
                    
                }
            }
            catch(SQLException ex){
                System.out.println(ex);
            }
            finally{
                closeConnection();
            }
        }
        return arr;
    }
    
    
    public boolean addKHACHHANG(khDTO kh){
        boolean result =false;
        if(openConnection()){
            try{
                String sql=" Insert into KHACHHANG values(?, ?, ?, ?, ?, ?, ?, ?) ";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, kh.getMaKH());
                stmt.setString(2, kh.getTenDangNhapKH());
                stmt.setString(3, kh.getMatKhauKH());
                stmt.setString(4, kh.getHoKH());
                stmt.setString(5, kh.getTenKH());
                stmt.setString(6, kh.getDiaChiKH());
                stmt.setString(7, kh.getSdtKH());
                stmt.setDate(8, kh.getDobKH());

                
                if(stmt.executeUpdate() >=1){
                    result = true;
                }
            }
            catch(SQLException ex){
                System.out.println(ex);
            }
            finally{
                closeConnection();
            }
        }
        return result;
    }
    
    //kiem tra ma nha cung cap da co chua
    public boolean hasMaKH(String ma){
        boolean result =false;
        if(openConnection()){
            try{
               String sql = " Select * from KHACHHANG where MAKH = " + ma;
               Statement stmt = con.createStatement();
               ResultSet rs = stmt.executeQuery(sql);
               result = rs.next();
            }
            catch(SQLException ex){
                System.out.println(ex);
            }
            finally{
                closeConnection();
            }
        }
        return result;
    }
}

