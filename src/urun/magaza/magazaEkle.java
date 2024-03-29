package urun.magaza;

import dataAccess.PostgreSQLDbConnection;
import model.Magaza;
import urun.urunListeleme.urunListelemeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;





    public class magazaEkle extends JFrame {



        private JLabel magazaEkleLabel;
        private JTextField magazaEkletex;
        private JButton magazaEkle;
        private String kullaniciTuru;
        private Integer kullaniciId;



        public magazaEkle(String kullaniciTuru, Integer kullaniciId){
            this.kullaniciTuru=kullaniciTuru;
            this.kullaniciId=kullaniciId;
            initializemagazaEkleForm();
        }



        private void initializemagazaEkleForm() {
            setTitle("magaza ekleme işlemleri");
            setBounds(600,400,350,130);
            setLayout(null);
            setVisible(true);
            magazaEkleLabel=new JLabel("Magaza");
            magazaEkleLabel.setBounds(15,15,100,20);
            magazaEkleLabel.setVisible(true);
            magazaEkletex = new JTextField();
            magazaEkletex.setBounds(115,15,180,20);
            magazaEkletex.setVisible(true);


            JButton magazaEkle = new JButton("Kaydet");
            magazaEkle.setBounds(125,60,100,20);
            magazaEkle .setVisible(true);
            magazaEkle .addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PostgreSQLDbConnection db = null;
                    try {
                        db = new PostgreSQLDbConnection();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        db.baglan();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    Magaza magaza = new Magaza(magazaEkletex.getText());
                    try {
                        db.magazaEkle(magaza);
                        urunListelemeForm urunlistele= new urunListelemeForm(kullaniciTuru,kullaniciId);


                        JOptionPane.showMessageDialog(null, "magaza başarıyla eklendi");
                        urunlistele.setVisible(true);
                        setVisible(false);
                    } catch (SQLException | IOException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

            this.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    try {
                        urunListelemeForm listeForm = new urunListelemeForm(kullaniciTuru,kullaniciId);
                        listeForm.setVisible(true);
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            add(magazaEkle);
            add(magazaEkleLabel);
            add(magazaEkletex);
        }




    }
