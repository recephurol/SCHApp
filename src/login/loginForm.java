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
    private JPasswordField parolaText;

    public loginForm(){
        add(loginPanel);
        setSize(400,200);
        setTitle("Kullanıcı Girişi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(750,400);
        setResizable(false);


        girisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PostgreSQLDbConnection baglanti = new PostgreSQLDbConnection();
                baglanti.baglan();
                boolean sonuc = true;
                try {
                    sonuc = baglanti.kullaniciKontrol(kullaniciAdiText.getText(),parolaText.getText());

                    if (sonuc){
                        urunListelemeForm urunListele = null;
                        try {
                            urunListele = new urunListelemeForm();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        urunListele.setVisible(true);
                        setVisible(false);
                    }
                {

                }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });
    }

}
