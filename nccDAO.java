/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import DTO.nccDTO;
import java.sql.*;
import java.util.ArrayList;
public class nccDAO {
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
    
    
    
    
        public ArrayList<nccDTO> getAllNHACUNGCAP(){
        ArrayList<nccDTO> arr = new ArrayList<nccDTO>();
        if(openConnection()){
            try{
                String sql="Select * from NHACUNGCAP";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    nccDTO n = new nccDTO();
                    n.setMancc(rs.getString("mancc"));
                    n.setTenncc(rs.getString("tenncc"));
                    n.setDiachincc(rs.getString("diachincc"));
                    n.setSdtncc(rs.getString("sdtncc"));
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
    
    
    public boolean addNHACUNGCAP(nccDTO emp){
        boolean result =false;
        if(openConnection()){
            try{
                String sql=" Insert into NHACUNGCAP values(?, ?, ?, ?) ";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, emp.getMancc());
                stmt.setString(2, emp.getTenncc());
                stmt.setString(3, emp.getDiachincc());
                stmt.setString(4, emp.getSdtncc());
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
    public boolean hasMancc(String ma){
        boolean result =false;
        if(openConnection()){
            try{
               String sql = " Select * from NHACUNGCAP where MANCC = " + ma;
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
