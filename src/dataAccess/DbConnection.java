package dataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DbConnection {

    public Connection conn=null;

    public DbConnection(){
    }

    public abstract Connection baglan() throws IOException;

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
