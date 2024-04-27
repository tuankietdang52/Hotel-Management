package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnect {
    private final static String username = "shiba";
    private final static String password = "bruh";

    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String path = "jdbc:sqlserver://localhost:1433;databaseName=Shiba;trustServerCertificate=true";

        return DriverManager.getConnection(path, username, password);
    }
}
