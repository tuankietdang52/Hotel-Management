import SQL.SqlConnect;

import java.io.Console;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void blah() throws Exception{
        PreparedStatement st = null;
        try{
            st = SqlConnect.getConnection().prepareStatement("SELECT * FROM MyTable");

            if (st == null) throw new NullPointerException();

            ResultSet rs = st.executeQuery();
            while (rs.next()){
                System.out.println(rs.getInt(1));
                System.out.println(rs.getString(2));
            }
        }
        catch (Exception ex){
            System.out.println("Cant connect");
            System.out.println(ex);
        }
        finally {
            if (st != null) st.close();
        }

    }
    public static void main(String[] args) {
        try{
            blah();
        }
        catch (Exception ex){
            System.out.println("NULL");
        }
    }
}