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

    public ResultSet urunListele() throws SQLException {
        if(conn!=null){
            Statement myStat =conn.createStatement();
            ResultSet kullanicilar= myStat.executeQuery("select * from kullanici");
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
                ResultSet kullanicilar= myStat.executeQuery("SELECT urun.adi urun_adi,k.adi katagori_adi,r.adi renk_adi,m.adi marka_adi,urun.aciklama urun_aciklama,y.yorum urun_yorum FROM public.urun\n" +
                        "        join kategori k on k.id = urun.kategori_id\n" +
                        "        join marka m on m.id = urun.marka_id\n" +
                        "        join renk r on r.id = urun.renk_id\n"+
                        "        join yorum y on y.id = urun.yorum_id");
                return kullanicilar;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;




    }
}