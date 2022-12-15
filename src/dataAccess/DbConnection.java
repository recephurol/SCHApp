package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    static String url = "jdbc:postgresql://localhost:5432/schapp";
    static Connection conn = null;
    public static void baglan(){
        try {
            conn = DriverManager.getConnection(url,"postgres","postgres");
            System.out.println("Bağlantı gerçekleşti");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
