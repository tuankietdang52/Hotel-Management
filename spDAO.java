/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import DTO.spDTO;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author ASUS
 */
public class spDAO {
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
    
    
    
    
        public ArrayList<spDTO> getAllSANPHAM(){
        ArrayList<spDTO> arr = new ArrayList<spDTO>();
        if(openConnection()){
            try{
                String sql="Select * from SANPHAM";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    spDTO n = new spDTO();
                    n.setMaSP(rs.getString("maSP"));
                    n.setTenSP(rs.getString("tenSP"));
                    n.setMaLoai(rs.getString("maLoai"));
                    n.setSoLuong(rs.getInt("soLuong"));
                    n.setMoTa(rs.getString("moTa"));
                    n.setDonGiaSP(rs.getFloat("donGia"));
                    
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
    
    
    public boolean addSANPHAM(spDTO sp){
        boolean result =false;
        if(openConnection()){
            try{
                String sql=" Insert into SANPHAM values(?, ?, ?, ?, ?, ?, ?) ";
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

