package dataAccess;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DbConnection {

    public DbConnection(){
    }

    public abstract Connection baglan();

    public abstract void baglantiyiKapat() throws SQLException;

}
