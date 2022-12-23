package model;


import db.PostgreSQLDbConnection;
import urun.urunListeleme.urunListelemeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class markaEkle extends JFrame {



    private JLabel markaEkleLabel;
    private JTextField markaEkletex;
    private JButton MarkaEkle;




    public markaEkle(){

        initializemarkaEkleForm();
    }



    private void initializemarkaEkleForm() {
        setTitle("marka ekleme işlemleri");
        setBounds(600,400,350,300);
        setLayout(null);
        setVisible(true);
        markaEkleLabel=new JLabel("renk");
        markaEkleLabel.setBounds(15,15,100,20);
        markaEkleLabel.setVisible(true);
        markaEkletex = new JTextField();
        markaEkletex.setBounds(115,15,100,20);
        markaEkletex.setVisible(true);


        JButton markaEkle = new JButton("Kaydet");
        markaEkle.setBounds(120,160,100,20);
        markaEkle .setVisible(true);
        markaEkle .addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostgreSQLDbConnection db = new PostgreSQLDbConnection();
                db.baglan();

                Marka marka = new Marka(markaEkletex.getText());
                try {
                    db.markaEkle(marka);
                    urunListelemeForm urunlistele= new urunListelemeForm();


                    JOptionPane.showMessageDialog(null, "Marka başarıyla eklendi");
                    urunlistele.setVisible(true);
                    setVisible(false);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        add(markaEkle);
        add(markaEkleLabel);
        add(markaEkletex);
    }





}





