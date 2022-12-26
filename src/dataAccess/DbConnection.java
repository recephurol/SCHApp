package dataAccess;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DbConnection {

    public Connection conn=null;

    public DbConnection(){
    }

    public abstract Connection baglan();

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
