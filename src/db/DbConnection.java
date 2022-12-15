package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnection {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection myConn = (Connection) DriverManager.getConnection("jdbc:postgresql://localhost:5432/schappdatabases","postgres","1234");
            Statement myStat = (Statement)myConn.createStatement();
            ResultSet kullanicilar= myStat.executeQuery("select * from kullanicilar");
            while (kullanicilar.next()){
                System.out.println(kullanicilar.getString("id")+kullanicilar.getString("kullanici_ismi") +kullanicilar.getString("sifre"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}