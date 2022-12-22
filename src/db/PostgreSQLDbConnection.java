package db;


import dataAccess.DbConnection;
import model.Urun;
import model.Yorum;
import urun.urunDetayDeneme.urunDetayFormDeneme;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;

import static java.sql.Types.NULL;

public class PostgreSQLDbConnection extends DbConnection {

    private final String url = "jdbc:postgresql://localhost:5432/schapp";

    private Connection conn = null;
    public PostgreSQLDbConnection(){
        super();
    }

    @Override
    public Connection baglan() {
        try {
            conn = DriverManager.getConnection(url,"postgres","postgres");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
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

    public ResultSet kullaniciListele() throws SQLException {
        if(conn!=null){
            Statement myStat =conn.createStatement();
            ResultSet kullanicilar= myStat.executeQuery("select * from kullanici");
            return kullanicilar;
        }
        return null;
    }

    public ResultSet urunListele(String bulText,Integer kategoriId,Integer markaId,Integer renkId) throws SQLException {
        if(conn!=null){
            bulText= bulText == null ? "" : bulText;
            String query = "select urun2.id, urunAdi,m.adi marka,k.adi kategori,r.adi renk,urun2.fiyat from (" +
                    "    select u.id,u.adi urunAdi,u.kategori_id,u.marka_id,u.renk_id,min(fiyat) fiyat from urun_fiyat " +
                    "        inner join urun u on urun_fiyat.urun_id=u.id " +
                    "    group by u.id,u.adi,u.kategori_id,u.marka_id,u.renk_id                         ) as  urun2 " +
                    "    inner join marka m on m.id=urun2.marka_id" +
                    "    inner join renk r on r.id=urun2.renk_id" +
                    "    inner join kategori k on k.id=urun2.kategori_id " +
                    " where ((urunAdi like '%"+bulText+"%') or  (m.adi like '%"+bulText+"%') or " +
                    "       (k.adi like '%"+bulText+"%') or  (r.adi like '%"+bulText+"%')) and " +
                    "                            (urun2.kategori_id= %d  or %d is NULL) and " +
                    "                            (urun2.marka_id= %d  or %d is NULL) and " +
                    "                            (urun2.renk_Id= %d or %d is NULL) ";

            String filteredQuery = String.format(query,kategoriId,kategoriId,markaId,markaId,renkId,renkId);
            Statement myStat =conn.createStatement();
//            if (kategoriId == null) {
//                myStat.setNull(1, NULL);
//                myStat.setNull(2,NULL);
//            } else {
//                myStat.setInt(1, kategoriId);
//                myStat.setInt(2,kategoriId);
//            }
//            if (markaId == null) {
//                myStat.setNull(3, NULL);
//                myStat.setNull(4,NULL);
//            } else {
//                myStat.setInt(3,markaId);
//                myStat.setInt(4,markaId);
//            }
//            if (renkId == null) {
//                myStat.setNull(5, NULL);
//                myStat.setNull(6,NULL);
//            } else {
//                myStat.setInt(5,renkId);
//                myStat.setInt(6,renkId);
//            }

            ResultSet kullanicilar= myStat.executeQuery(filteredQuery);
            return kullanicilar;
        }
        return null;
    }

    public boolean kullaniciKontrol(String kullaniciAdi, String sifre) throws SQLException {
        if(conn==null){
            baglan();
        }
        Statement myStat =conn.createStatement();
        String query = "select * from kullanici where kullanici_adi = '"+kullaniciAdi+"' and sifre='"+sifre+"';";
        //String.format(query,kullaniciAdi,sifre);
        var kullaniciSayisi= myStat.executeQuery(query);
        System.out.println(kullaniciSayisi);
        while(kullaniciSayisi.next()){
            if(kullaniciSayisi.getString("id")!=null){
                return true;
            }
        }
        return false;
    }

    public ResultSet urunDetayGetir(int id){

        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query ="select u.adi urunAdi, m.adi marka, k.adi kategori,r.adi renk,u.aciklama,u.fotograf  from urun u " +
                        "inner join kategori k on u.kategori_id = k.id " +
                        "inner join renk r on r.id=u.renk_id " +
                        "inner join marka m on m.id=u.marka_id " +
                        "where u.id="+id;
                ResultSet kullanicilar= myStat.executeQuery(query);
                return kullanicilar;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    public ResultSet urunYorumListesiGetir(int urunId){

        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query ="select y.yorum,y.ad_soyad adSoyad,y.puan from yorum y " +
                        "where urun_id="+urunId;

                ResultSet urunYorumListesi= myStat.executeQuery(query);
                return urunYorumListesi;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    public ResultSet urunFiyatListesiGetir(int urunId){

        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query ="select uf.id,m.adi magaza,fiyat from urun_fiyat uf " +
                        "inner join magaza m on uf.magaza_id = m.id " +
                        "where urun_id= " +urunId+
                        " order by fiyat asc";

                ResultSet urunFiyatListesi= myStat.executeQuery(query);
                return urunFiyatListesi;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    public ResultSet urunPuaniGetir(int urunId){

        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query ="select avg(puan) puan from public.yorum " +
                        "where urun_id=" +urunId;

                ResultSet puan= myStat.executeQuery(query);
                return puan;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    public ResultSet markaGetir(){
        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query ="select id,adi from marka ";
                ResultSet markalar= myStat.executeQuery(query);
                return markalar;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }
    public ResultSet kategoriGetir(){
        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query ="select id,adi from kategori ";
                ResultSet kategoriler= myStat.executeQuery(query);
                return kategoriler;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    public ResultSet renkGetir(){
        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query ="select id,adi from renk ";
                ResultSet renkler= myStat.executeQuery(query);
                return renkler;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }
    public ResultSet magazaGetir(){
        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query ="select id,adi from magaza ";
                ResultSet magazalar= myStat.executeQuery(query);
                return magazalar;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    public void urunEkle(Urun urun) throws SQLException {
        if(conn!=null){
            try {
                String insertUrunQuery ="INSERT INTO urun (deleted, adi, kategori_id, marka_id,renk_id,aciklama) VALUES ( ?, ?, ?, ?, ?,?);";

                PreparedStatement insertQuery = conn.prepareStatement(insertUrunQuery,Statement.RETURN_GENERATED_KEYS);
                insertQuery.setBoolean(1,false);
                insertQuery.setString(2,urun.get_adi());
                insertQuery.setInt(3,urun.get_kategoriId());
                insertQuery.setInt(4, urun.get_markaId());
                insertQuery.setInt(5,urun.get_renkId());
                insertQuery.setString(6,urun.get_aciklama());
                int count = insertQuery.executeUpdate();

                ResultSet urunId = insertQuery.getGeneratedKeys();
                String insertUrunFiyatQuery ="INSERT INTO urun_fiyat (deleted, urun_id, magaza_id,fiyat) VALUES ( ?, ?, ?, ?);";

                while(urunId.next()){
                    PreparedStatement insertFiyatQuery = conn.prepareStatement(insertUrunFiyatQuery);
                    insertFiyatQuery.setBoolean(1,false);
                    insertFiyatQuery.setInt(2,urunId.getInt("id"));
                    insertFiyatQuery.setInt(3,urun.get_magazaId());
                    insertFiyatQuery.setDouble(4,urun.get_fiyat());
                    insertFiyatQuery.executeUpdate();
                }

            } catch (SQLException e) {
                conn.rollback();
            }
            finally {
                conn.close();
            }

        }
    }

    public void yorumEkle(Yorum yorum) throws SQLException {
        if(conn!=null){
            try {
                String insertUrunQuery ="INSERT INTO yorum (deleted, urun_id, yorum, puan,ad_soyad) VALUES ( ?, ?, ?, ?, ?);";

                PreparedStatement insertQuery = conn.prepareStatement(insertUrunQuery,Statement.RETURN_GENERATED_KEYS);
                insertQuery.setBoolean(1,false);
                insertQuery.setInt(2,yorum.getUrunId());
                insertQuery.setString(3,yorum.getYorum());
                insertQuery.setDouble(4, yorum.getPuan());
                insertQuery.setString(5,yorum.getAdSoyad());
                insertQuery.executeUpdate();


            } catch (SQLException e) {
                conn.rollback();
            }
            finally {
                conn.close();
            }

        }
    }



}