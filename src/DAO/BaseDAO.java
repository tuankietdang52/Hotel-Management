package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class BaseDAO {
    protected abstract void RetrieveData();

    protected void ClosingConnection(Connection con, PreparedStatement stmt, ResultSet rs){
        try {
            if (con != null) con.close();
            if (stmt != null) stmt.close();
            if (stmt != null) rs.close();
        }
        catch (Exception ignored){}
    }
}
