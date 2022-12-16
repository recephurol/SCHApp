package dataAccess;

import java.sql.SQLException;

public abstract class DbConnection {

    public DbConnection(){
    }

    public abstract void baglan();

    public abstract void baglantiyiKapat() throws SQLException;
}
