package login;

import db.PostgreSQLDbConnection;
import urun.urunListeleme.urunListelemeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class loginForm extends JFrame {
    private JTextField kullaniciAdiText;
    private JButton girisButton;
    private JPanel loginPanel;
    private JComboBox kullanici_tipi_cb;
    private JPasswordField parolaText;

    public loginForm(){
        add(loginPanel);
        setSize(400,200);
        setTitle("Kullanıcı Girişi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(750,400);
        setResizable(false);
//        kullanici_tipi_cb = new JComboBox();
//        kullanici_tipi_cb.addItem(new Item(1,"Müşteri"));
//        kullanici_tipi_cb.addItem(new Item(2,"Mağaza"));
//        kullanici_tipi_cb.setVisible(true);
//        kullanici_tipi_cb.setLocation(parolaText.getBounds().getLocation());
//        add(kullanici_tipi_cb);


        girisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*PostgreSQLDbConnection baglanti = new PostgreSQLDbConnection();
                baglanti.baglan();
                try {
                    var kullanicilar=baglanti.kullaniciListele();
                    while (kullanicilar.next()){
                        System.out.println(kullanicilar.getString("id")+kullanicilar.getString("kullanici_adi") +kullanicilar.getString("sifre"));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                String kullaniciAdi="admin", parola="1234";
                boolean sonuc = true;
                /*try {
                    sonuc = baglanti.kullaniciKontrol(kullaniciAdiText.getText(),parolaText.getText());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }*/
                boolean sonuc = true;
                if (sonuc){
                    urunListelemeForm urunListele = new urunListelemeForm();
                    urunListele.setVisible(true);
                    setVisible(false);
                }

            }
        });
    }

}
