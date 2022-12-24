package urun.kategoriEkle;

import db.PostgreSQLDbConnection;
import model.Kategori;
import urun.urunListeleme.urunListelemeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class kategoriEkle extends JFrame {



    private JLabel kategoriLabel;
    private JTextField kategoritex;
    private JButton kategoriEkle;




    public kategoriEkle(){

        initializeKategoriEkleForm();
    }



    private void initializeKategoriEkleForm() {
        setTitle("kategori ekleme işlemleri");
        setBounds(600,400,350,300);
        setLayout(null);
        setVisible(true);
        kategoriLabel=new JLabel("kategori");
        kategoriLabel.setBounds(15,15,100,20);
        kategoriLabel.setVisible(true);
        kategoritex = new JTextField();
        kategoritex.setBounds(115,15,100,20);
        kategoritex.setVisible(true);



        kategoriEkle = new JButton("Kaydet");
        kategoriEkle.setBounds(120,160,100,20);
        kategoriEkle.setVisible(true);
        kategoriEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostgreSQLDbConnection db = new PostgreSQLDbConnection();
                db.baglan();

                Kategori kategori = new Kategori(kategoritex.getText());
                try {
                    db.kategoriEkle(kategori);
                    urunListelemeForm urunlistele= new urunListelemeForm();


                    JOptionPane.showMessageDialog(null, "kategori başarıyla eklendi");
                    urunlistele.setVisible(true);
                    setVisible(false);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        add(kategoriEkle);
        add(kategoriLabel);
        add(kategoritex);
    }





}
