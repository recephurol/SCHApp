package urun.urunDetayi;

import dataAccess.PostgreSQLDbConnection;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class SatinAlButton extends JButton {

    private Integer urunFiyatId;

    public SatinAlButton(  String title){
        super(title);

    }

    public void setUrunFiyatId(Integer urunFiyatId) {
        this.urunFiyatId = urunFiyatId;
    }

    public void stokDus() throws SQLException, IOException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        db.stokDus(urunFiyatId);
        db.baglantiyiKapat();
    }

    public Integer getUrunFiyatId(){
        return urunFiyatId;
    }
}
