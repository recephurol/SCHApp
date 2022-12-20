package db;


import dataAccess.DbConnection;
import model.Urun;

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

    public ResultSet urunListele() throws SQLException {
        if(conn!=null){
            Statement myStat =conn.createStatement();
            ResultSet kullanicilar= myStat.executeQuery("select urun2.id, urunAdi,m.adi marka,k.adi kategori,r.adi renk,urun2.fiyat from ( " +
                    "    select u.id,u.adi urunAdi,u.kategori_id,u.marka_id,u.renk_id,min(fiyat) fiyat from urun_fiyat " +
                    "    inner join urun u on urun_fiyat.urun_id = u.id " +
                    "    group by u.id,u.adi,u.kategori_id,u.marka_id,u.renk_id " +
                    "    ) as  urun2 " +
                    "inner join marka m on m.id=urun2.marka_id " +
                    "inner join renk r on r.id=urun2.renk_id " +
                    "inner join kategori k on k.id=urun2.kategori_id ");
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

    public void urunEkle(Urun urun){
        if(conn!=null){
            Statement myStat = null;
            try {
                myStat = conn.createStatement();
                String query ="INSERT INTO urun (id, deleted, adi, kategori_id, marka_id,renk_id,aciklama) VALUES (3, false, 'Baldo Pirinç', 3, 3, 6,'2.5 Kg Baldo Pirinç');";
                myStat.executeUpdate(query);
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }



}