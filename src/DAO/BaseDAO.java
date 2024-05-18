package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public abstract class BaseDAO {
    protected void closingConnection(Connection con, Statement stmt, ResultSet rs){
        try {
            if (con != null) con.close();
            if (stmt != null) stmt.close();
            if (stmt != null) rs.close();
        }
        catch (Exception ignored){}
    }

    protected void closingConnection(Connection con, PreparedStatement stmt, ResultSet rs){
        try {
            if (con != null) con.close();
            if (stmt != null) stmt.close();
            if (stmt != null) rs.close();
        }
        catch (Exception ignored){}
    }
}
