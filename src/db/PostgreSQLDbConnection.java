package db;


import dataAccess.DbConnection;
import model.*;
import model.Kullanici;
import model.Urun;
import model.Yorum;
import urun.urunDetayDeneme.urunDetayFormDeneme;

import java.sql.*;


public class PostgreSQLDbConnection extends DbConnection {

    private final String url = "jdbc:postgresql://localhost:5432/schapp";

    private Connection conn = null;
    public PostgreSQLDbConnection(){
        super();
    }

    @Override
    public Connection baglan() {
        try {
            conn = DriverManager.getConnection(url,"postgres","1234");
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
            bulText= bulText == null ? "'%%'" : "'%"+bulText+"%'";
            String query = "select urun2.id, urunAdi,m.adi marka,k.adi kategori,r.adi renk,urun2.fiyat from (" +
                    "    select u.id,u.adi urunAdi,u.kategori_id,u.marka_id,u.renk_id,min(fiyat) fiyat from urun_fiyat " +
                    "        inner join urun u on urun_fiyat.urun_id=u.id " +
                    "    group by u.id,u.adi,u.kategori_id,u.marka_id,u.renk_id                         ) as  urun2 " +
                    "    inner join marka m on m.id=urun2.marka_id" +
                    "    inner join renk r on r.id=urun2.renk_id" +
                    "    inner join kategori k on k.id=urun2.kategori_id " +
                    " where ((UPPER(urunAdi) like %s) or  (UPPER(m.adi) like %s) or " +
                    "       (UPPER(k.adi) like %s) or  (UPPER(r.adi) like %s)) and " +
                    "                            (urun2.kategori_id= %d  or %d is NULL) and " +
                    "                            (urun2.marka_id= %d  or %d is NULL) and " +
                    "                            (urun2.renk_Id= %d or %d is NULL) ";

            String filteredQuery = String.format(query,bulText.toUpperCase(),bulText.toUpperCase(),bulText.toUpperCase(),bulText.toUpperCase(),kategoriId,kategoriId,markaId,markaId,renkId,renkId);
            Statement myStat =conn.createStatement();

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

    public ResultSet urunPuangetir(int urunId){

        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query ="select y.puan from yorum y " +
                        "where urun_id="+urunId;

                ResultSet urunPuan= myStat.executeQuery(query);
                return urunPuan;
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
                String query ="select uf.id,m.adi magaza,fiyat,stok from urun_fiyat uf " +
                        " inner join magaza m on uf.magaza_id = m.id " +
                        " where urun_id= " +urunId+
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
                String query ="select urun_id ,avg(puan)  from public.yorum"+ " where urun_id =" +urunId+" group by urun_id" ;

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
                ResultSet urunId=null;
                ResultSet result = urunGetir(urun.get_adi(),urun.get_kategoriId(),urun.get_markaId(),urun.get_renkId(),urun.get_magazaId());
                while(result.next()){

                    if(result==null){

                        String insertUrunQuery ="INSERT INTO urun (deleted, adi, kategori_id, marka_id,renk_id,aciklama) VALUES ( ?, ?, ?, ?, ?,?);";

                        PreparedStatement insertQuery = conn.prepareStatement(insertUrunQuery,Statement.RETURN_GENERATED_KEYS);
                        insertQuery.setBoolean(1,false);
                        insertQuery.setString(2,urun.get_adi());
                        insertQuery.setInt(3,urun.get_kategoriId());
                        insertQuery.setInt(4, urun.get_markaId());
                        insertQuery.setInt(5,urun.get_renkId());
                        insertQuery.setString(6,urun.get_aciklama());
                        int count = insertQuery.executeUpdate();

                        urunId = insertQuery.getGeneratedKeys();
                    } else {
                        ResultSet urunFiyatVarMi = urunFiyatGetir(result.getInt("urunId"),urun.get_magazaId());
                        if(urunFiyatVarMi==null){
                            while(urunId.next()){
                                urunFiyatEkle(urunId.getInt("id"),urun.get_magazaId(),urun.get_fiyat(),urun.get_stok());
                            }
                        }
                    }
                    if(urunId!=null){
                        while(urunId.next()){
                            urunFiyatEkle(urunId.getInt("id"),urun.get_magazaId(),urun.get_fiyat(),urun.get_stok());
                        }
                    }
                }



            } catch (SQLException e) {
            }

        }
    }

    public void urunFiyatEkle(Integer urunId,Integer magazaId, Double fiyat, Integer stok ) throws SQLException {
        if(conn!=null){
            try {

                String insertUrunFiyatQuery ="INSERT INTO urun_fiyat (deleted, urun_id, magaza_id,fiyat,stok) VALUES ( ?, ?, ?, ?, ?);";
                PreparedStatement insertFiyatQuery = conn.prepareStatement(insertUrunFiyatQuery, Statement.RETURN_GENERATED_KEYS);
                insertFiyatQuery.setBoolean(1,false);
                insertFiyatQuery.setInt(2,urunId);
                insertFiyatQuery.setInt(3,magazaId);
                insertFiyatQuery.setDouble(4,fiyat);
                insertFiyatQuery.setInt(5,stok);
                var count = insertFiyatQuery.executeUpdate();
                ResultSet result = insertFiyatQuery.getGeneratedKeys();
            } catch (SQLException e) {
            }

        }
    }

    public void yorumEkle(Yorum yorum) throws SQLException {
        if(conn!=null){
            try {
                String insertUrunQuery ="INSERT INTO yorum (deleted, urun_id, yorum, puan,ad_soyad) VALUES ( ?, ?, ?, ?, ?);";

                PreparedStatement insertQuery = conn.prepareStatement(insertUrunQuery);
                insertQuery.setBoolean(1,false);
                insertQuery.setInt(2,yorum.getUrunId());
                insertQuery.setString(3,yorum.getYorum());
                insertQuery.setDouble(4, yorum.getPuan());
                insertQuery.setString(5,yorum.getAdSoyad());
                insertQuery.executeUpdate();


            } catch (SQLException e) {
            }

        }
    }

    private ResultSet urunGetir(String urunAdi,Integer kategoriId, Integer markaId, Integer renkId, Integer magazaId ){
        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query ="select urun.id urunId,urun.adi urunAdi from urun " +
                        " inner join urun_fiyat uf on urun.id = uf.urun_id " +
                        " where urun.adi= '"+urunAdi+"' and " +
                        "      urun.kategori_id= "+kategoriId+" and " +
                        "      urun.marka_id= "+markaId+" and " +
                        "      urun.renk_id= "+renkId+" and " +
                        "      magaza_id= "+magazaId;

                ResultSet urunFiyatListesi= myStat.executeQuery(query);
                return urunFiyatListesi;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    private ResultSet urunFiyatGetir(int urunId, int magazaId ){
        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query =" select id, urun_Id,magaza_id  from urun_fiyat " +
                        " where magaza_id="+magazaId+" and urun_id="+urunId;

                ResultSet urunFiyat= myStat.executeQuery(query);
                return urunFiyat;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    public void kategoriEkle(Kategori kategori) {
        if(conn!=null){
            try {
                String insertUrunQuery ="INSERT INTO kategori (deleted, adi) VALUES ( ?, ?);";


                PreparedStatement insertQuery = conn.prepareStatement(insertUrunQuery);
                insertQuery.setBoolean(1,false);
                insertQuery.setString(2,kategori.getAdi());
                insertQuery.executeUpdate();


            } catch (SQLException e) {
            }

        }
    }
    public void renkEkleme(Renk renk) {
        if(conn!=null){
            try {
                String insertUrunQuery ="INSERT INTO renk (deleted, adi) VALUES ( ?, ?);";


                PreparedStatement insertQuery = conn.prepareStatement(insertUrunQuery);
                insertQuery.setBoolean(1,false);
                insertQuery.setString(2,renk.getAdi());
                insertQuery.executeUpdate();


            } catch (SQLException e) {
            }

        }
    }
    public void markaEkle(Marka marka) {
        if(conn!=null){
            try {
                String insertUrunQuery ="INSERT INTO marka (deleted, adi) VALUES ( ?, ?);";


                PreparedStatement insertQuery = conn.prepareStatement(insertUrunQuery);
                insertQuery.setBoolean(1,false);
                insertQuery.setString(2,marka.getAdi());
                insertQuery.executeUpdate();


            } catch (SQLException e) {
            }

        }
    }
    public void magazaEkle(Magaza magaza) {
        if(conn!=null){
            try {
                String insertUrunQuery ="INSERT INTO magaza (deleted, adi) VALUES ( ?, ?);";


                PreparedStatement insertQuery = conn.prepareStatement(insertUrunQuery);
                insertQuery.setBoolean(1,false);
                insertQuery.setString(2,magaza.getAdi());
                insertQuery.executeUpdate();


            } catch (SQLException e) {
            }

        }
    }

    public void kullaniciEkle(Kullanici kullanici) throws SQLException {
        if(conn!=null){
            try {
                String insertUrunQuery ="INSERT INTO kullanici (deleted, kullanici_adi, sifre,kullanici_tipi) VALUES ( ?, ?, ?,?);";

                PreparedStatement insertQuery = conn.prepareStatement(insertUrunQuery);
                insertQuery.setBoolean(1,false);
                insertQuery.setString(2,kullanici.getKullaniciAdi());
                insertQuery.setString(3,kullanici.getSifre());
                insertQuery.setString(4,kullanici.getKullaniciTipi().getValue());
                insertQuery.executeUpdate();


            } catch (SQLException e) {
            }

        }
    }
}