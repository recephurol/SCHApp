package model;

import db.PostgreSQLDbConnection;
import urun.urunListeleme.urunListelemeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;





    public class magazaEkle extends JFrame {



        private JLabel magazaEkleLabel;
        private JTextField magazaEkletex;
        private JButton magazaEkle;




        public magazaEkle(){

            initializemagazaEkleForm();
        }



        private void initializemagazaEkleForm() {
            setTitle("magaza ekleme işlemleri");
            setBounds(600,400,350,300);
            setLayout(null);
            setVisible(true);
            magazaEkleLabel=new JLabel("magaza");
            magazaEkleLabel.setBounds(15,15,100,20);
            magazaEkleLabel.setVisible(true);
            magazaEkletex = new JTextField();
            magazaEkletex.setBounds(115,15,100,20);
            magazaEkletex.setVisible(true);


            JButton magazaEkle = new JButton("Kaydet");
            magazaEkle.setBounds(120,160,100,20);
            magazaEkle .setVisible(true);
            magazaEkle .addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PostgreSQLDbConnection db = new PostgreSQLDbConnection();
                    db.baglan();

                    Magaza magaza = new Magaza(magazaEkletex.getText());
                    try {
                        db.magazaEkle(magaza);
                        urunListelemeForm urunlistele= new urunListelemeForm();


                        JOptionPane.showMessageDialog(null, "magaza başarıyla eklendi");
                        urunlistele.setVisible(true);
                        setVisible(false);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            add(magazaEkle);
            add(magazaEkleLabel);
            add(magazaEkletex);
        }




    }
