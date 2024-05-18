/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import DTO.nvDTO;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author ASUS
 */
public class nvDAO {
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
    
    
    
    
        public ArrayList<nvDTO> getAllNHANVIEN(){
        ArrayList<nvDTO> arr = new ArrayList<nvDTO>();
        if(openConnection()){
            try{
                String sql="Select * from NHANVIEN";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    nvDTO n = new nvDTO();
                    n.setMaNV(rs.getString("maNV"));
                    n.setTenDangNhap(rs.getString("tenDangNhap"));
                    n.setMatKhau(rs.getString("matKhau"));
                    n.setHo(rs.getString("ho"));
                    n.setTen(rs.getString("ten"));
                    n.setDiaChi(rs.getString("diaChi"));
                    n.setNgaySinh(rs.getDate("ngaySinh"));
                    n.setTenCongViec(rs.getString("tenCongViec"));
                    n.setLuong(rs.getInt("luong"));
                    
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
    
    
    public boolean addNHANVIEN(nvDTO nv){
        boolean result =false;
        if(openConnection()){
            try{
                String sql=" Insert into NHANVIEN values(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, nv.getMaNV());
                stmt.setString(2, nv.getTenDangNhap());
                stmt.setString(3, nv.getMatKhau());
                stmt.setString(4, nv.getHo());
                stmt.setString(5, nv.getTen());
                stmt.setString(6, nv.getDiaChi());
                stmt.setDate(7, nv.getNgaySinh());
                stmt.setString(8, nv.getTenCongViec());
                stmt.setInt(9,  nv.getLuong());
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
    public boolean hasMaNV(String ma){
        boolean result =false;
        if(openConnection()){
            try{
               String sql = " Select * from NHANVIEN where MANV = " + ma;
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

