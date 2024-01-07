package login;

import dataAccess.PostgreSQLDbConnection;
import login.dto.KullaniciDto;
import urun.kullanici.kullaniciEkle;
import urun.urunListeleme.urunListelemeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class loginForm extends JFrame {
    private JTextField kullaniciAdiText;
    private JButton girisButton;
    private JPanel loginPanel;
    private JPasswordField parolaText;
    private JButton kayitOlButton;

    public loginForm(){
        add(loginPanel);
        setSize(400,200);
        setTitle("Kullanıcı Girişi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(750,400);
        setResizable(false);

        kayitOlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ee) {

                PostgreSQLDbConnection baglanti = null;

                kullaniciEkle kullaniciEkleForm = null;
                try {
                    String kullaniciTuru = null;
                    Integer kullaniciId = null;
                    kullaniciEkleForm = new kullaniciEkle(kullaniciTuru,kullaniciId);
                    kullaniciEkleForm.setVisible(true);
                    setVisible(false);

                } catch (SQLException e) {
                    e.printStackTrace();
                }



            }
        });


        girisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PostgreSQLDbConnection baglanti = null;
                try {
                    baglanti = new PostgreSQLDbConnection();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    baglanti.baglan();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                KullaniciDto sonuc = new KullaniciDto();
                try {
                    sonuc = baglanti.kullaniciKontrol(kullaniciAdiText.getText(),parolaText.getText());

                    if (sonuc!=null){
                        urunListelemeForm urunListele = null;
                        try {
                            urunListele = new urunListelemeForm(sonuc.getKullaniciTipi().toString(),sonuc.getId());
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        urunListele.setVisible(true);
                        setVisible(false);
                    }else{
                        JOptionPane.showMessageDialog(null,"Kullanıcı adı veya parola yanlış");
                    }
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

}
