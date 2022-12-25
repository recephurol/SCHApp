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
        setBounds(600,400,350,130);
        setLayout(null);
        setVisible(true);
        markaEkleLabel=new JLabel("Marka");
        markaEkleLabel.setBounds(15,15,100,20);
        markaEkleLabel.setVisible(true);
        markaEkletex = new JTextField();
        markaEkletex.setBounds(115,15,180,20);
        markaEkletex.setVisible(true);


        JButton markaEkle = new JButton("Kaydet");
        markaEkle.setBounds(120,60,100,20);
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

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    urunListelemeForm listeForm = new urunListelemeForm();
                    listeForm.setVisible(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        add(markaEkle);
        add(markaEkleLabel);
        add(markaEkletex);
    }





}






