package urun.renkEkleme;


import db.PostgreSQLDbConnection;
import model.Renk;
import urun.urunListeleme.urunListelemeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class renkEkle extends JFrame {



    private JLabel renkEklemeLabel;
    private JTextField renkEklemetex;
    private JButton renkEkleme;




    public renkEkle(){

        initializeRenkEklemeForm();
    }



    private void initializeRenkEklemeForm() {
        setTitle("renk ekeleme işlemleri");
        setBounds(600,400,350,300);
        setLayout(null);
        setVisible(true);
        renkEklemeLabel=new JLabel("renk");
        renkEklemeLabel.setBounds(15,15,100,20);
        renkEklemeLabel.setVisible(true);
        renkEklemetex = new JTextField();
        renkEklemetex.setBounds(115,15,100,20);
        renkEklemetex.setVisible(true);



        renkEkleme = new JButton("Kaydet");
        renkEkleme.setBounds(120,160,100,20);
        renkEkleme .setVisible(true);
        renkEkleme .addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostgreSQLDbConnection db = new PostgreSQLDbConnection();
                db.baglan();

                Renk renk = new Renk(renkEklemetex.getText());
                try {
                    db.renkEkleme(renk);
                    urunListelemeForm urunlistele= new urunListelemeForm();


                    JOptionPane.showMessageDialog(null, "Renk başarıyla eklendi");
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
        add(renkEkleme);
        add(renkEklemeLabel);
        add(renkEklemetex);
    }





}
