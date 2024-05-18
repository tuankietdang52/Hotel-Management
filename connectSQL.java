/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
class connectSQL {
    private Connection con;
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
}
