package dba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class DBConnection {
    public static Connection farmacieConnection() {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Farmacie;user=sa;Password=parola";
            con = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException ex){};
    
    
    return con;
    }
}
