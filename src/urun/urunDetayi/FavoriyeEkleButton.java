package urun.urunDetayi;

import dataAccess.PostgreSQLDbConnection;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class FavoriyeEkleButton  extends JButton {

        private Integer urunId;
        private Integer kullaniciId;

        public FavoriyeEkleButton(String title, Integer urunId, Integer kullaniciId){
            super(title);
            this.urunId=urunId;
            this.kullaniciId=kullaniciId;
        }

        public void setUrunId(Integer urunId) {
            this.urunId = urunId;
        }

    public void setKullaniciId(Integer kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

        public void favoriyeEkle() throws SQLException, IOException {
            PostgreSQLDbConnection db = new PostgreSQLDbConnection();
            db.baglan();
            db.favoriyeEkle(this.urunId,this.kullaniciId);
            JOptionPane.showMessageDialog(null,"Ürün favorilere basarili bir sekilde eklendi");
            db.baglantiyiKapat();
        }

        public Integer getUrunId(){
            return this.urunId;
        }
    public Integer getKullaniciId(){
        return this.kullaniciId;
    }
    }
