package DAO;

import java.sql.*;
import java.util.ArrayList;

public abstract class BaseDAO<T> {
    protected abstract T getModel(ResultSet rs) throws SQLException;

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
