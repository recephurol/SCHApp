package urun.urunDetayi;

import db.PostgreSQLDbConnection;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;


public class urunDetayiForm extends JFrame{
    private JTextField markaJTextField;
    private JTextField katagoriJTextField;
    private JTextField urunIsimiJTextField;
    private JPanel mainpanel;
    private JPanel sag;
    private JPanel resimpanel;
    private JTextField renktextField1;
    private JTextArea aciklamaTextArea;
    private JPanel fiyatPanel;
    private JPanel yorumPanel;
    private JTextField fiyatText;
    private JTextField yorumText;

    private BufferedImage image;
    private Integer urunId;

    public urunDetayiForm(Integer urunId) throws SQLException {
        urunId=urunId;
        getInitialForm(urunId);
    }

    public Integer getUrunId(){
        return urunId;
    }

    public urunDetayiForm() throws SQLException {

        getInitialForm(null);

    }

    private void getInitialForm(Integer urunId) throws SQLException {
        add(mainpanel);
        setSize(1000,800);
        setTitle("Urun Detay Ekrani");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        PostgreSQLDbConnection veriTabani=new PostgreSQLDbConnection();
        veriTabani.baglan();
        ResultSet urunDetay=veriTabani.urunDetayGetir(urunId);//******
        while (urunDetay.next()){

            //yorumJTextField.setText(urunDetay.getString("urun_yorum"));
            //magzaJTextField.setText(urunDetay.getString("urun_magza"));
            markaJTextField.setText(urunDetay.getString("marka"));
            //fiyatJTextField.setText("adsdasdsad");
            katagoriJTextField.setText(urunDetay.getString("kategori"));
            aciklamaTextArea.setText(urunDetay.getString("aciklama"));
            urunIsimiJTextField.setText(urunDetay.getString("urunAdi"));
            renktextField1.setText(urunDetay.getString("renk"));
        }

        ResultSet urunYorumlari = veriTabani.urunYorumGetir(urunId);
        Integer sayac =0;
        yorumPanel = new JPanel();
        while(urunYorumlari.next()){

            //yorumları cektik. nasıl doldurulacagina karar verilecek
            JLabel yorum = new JLabel("Ad Soyad: "+urunYorumlari.getString("adsoyad")+"\n Yorum :"+urunYorumlari.getString("yorum")+"\n Puan : "+urunYorumlari.getString("puan"));
            yorum.setBounds(20,400+sayac,200,20);
            yorum.setVisible(true);
            yorumPanel.add(yorum);
            sayac+=15;
        }
        add(yorumPanel);

    }


}
