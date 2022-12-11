package login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginForm extends JFrame {
    private JTextField kullaniciAdiText;
    private JTextField parolaText;
    private JButton girisButton;
    private JPanel loginPanel;
    private JComboBox comboBox1;

    public loginForm(){
        add(loginPanel);
        setSize(400,200);
        setTitle("Kullanıcı Girişi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        girisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String kullaniciAdi="admin", parola="1234";
                if (kullaniciAdi== kullaniciAdiText.getText() && parola==parolaText.getText()){

                }

            }
        });
    }
}
