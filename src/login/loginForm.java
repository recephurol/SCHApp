package login;

import dataAccess.PostgreSQLDbConnection;
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
