package db;


import dataAccess.DbConnection;

import java.sql.*;

public class PostgreSQLDbConnection extends DbConnection {

    private final String url = "jdbc:postgresql://localhost:5432/schapp";

    private Connection conn = null;
    public PostgreSQLDbConnection(){
        super();
    }

    @Override
    public void baglan() {
        try {
            conn = DriverManager.getConnection(url,"postgres","postgres");
            System.out.println("Bağlantı gerçekleşti");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void baglantiyiKapat() throws SQLException {
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }

        }
    }
}