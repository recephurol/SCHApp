package urun.urunDetayi;

import db.PostgreSQLDbConnection;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;


public class urunDetayiForm extends JFrame{
    private JTextField yorumJTextField;
    private JTextField magzaJTextField;
    private JTextField markaJTextField;
    private JTextField fiyatJTextField;
    private JTextField katagoriJTextField;
    private JTextField aciklamaJTextField;
    private JTextField urunIsimiJTextField;
    private JPanel mainpanel;
    private JPanel sag;
    private JPanel resimpanel;
    private JTextField renktextField1;

    private BufferedImage image;


    public urunDetayiForm() throws SQLException {
        add(mainpanel);
        setSize(1000,500);
        setTitle("Urun Detay Ekrani");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        PostgreSQLDbConnection veriTabani=new PostgreSQLDbConnection();
        veriTabani.baglan();
        ResultSet urunDetay=veriTabani.urunDetayGetir(1);//******
        while (urunDetay.next()){

            yorumJTextField.setText(urunDetay.getString("urun_yorum"));
            magzaJTextField.setText("urunDetay[0]");
            markaJTextField.setText(urunDetay.getString("marka_adi"));
            fiyatJTextField.setText("adsdasdsad");
            katagoriJTextField.setText(urunDetay.getString("katagori_adi"));
            aciklamaJTextField.setText(urunDetay.getString("urun_aciklama"));
            urunIsimiJTextField.setText(urunDetay.getString("urun_adi"));
            renktextField1.setText(urunDetay.getString("renk_adi"));
        }






    }





}
