package login;

import dataAccess.DbConnection;
import model.Item;
import urunListeleme.urunListelemeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

                DbConnection baglanti = new DbConnection();
                baglanti.baglan();

                String kullaniciAdi="admin", parola="1234";
                if (kullaniciAdi.equalsIgnoreCase(kullaniciAdiText.getText() ) && parola.equalsIgnoreCase(parolaText.getText())){
                    urunListelemeForm urunListele = new urunListelemeForm();
                    urunListele.setVisible(true);
                    setVisible(false);
                }

            }
        });
    }

}
