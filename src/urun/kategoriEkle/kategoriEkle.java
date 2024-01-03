package urun.kategoriEkle;

import dataAccess.PostgreSQLDbConnection;
import model.Kategori;
import urun.urunListeleme.urunListelemeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class kategoriEkle extends JFrame {

    private JLabel kategoriLabel;
    private JTextField kategoritex;
    private JButton kategoriEkle;
    private String kullaniciTuru;
    private Integer kullaniciId;

    public kategoriEkle(String kullaniciTuru,Integer kullaniciId){
        this.kullaniciTuru=kullaniciTuru;
        this.kullaniciId=kullaniciId;
        initializeKategoriEkleForm();
    }

    private void initializeKategoriEkleForm() {
        setTitle("kategori ekleme işlemleri");
        setBounds(600,400,350,130);
        setLayout(null);
        setVisible(true);
        kategoriLabel=new JLabel("Kategori");
        kategoriLabel.setBounds(15,15,100,20);
        kategoriLabel.setVisible(true);
        kategoritex = new JTextField();
        kategoritex.setBounds(115,15,180,20);
        kategoritex.setVisible(true);



        kategoriEkle = new JButton("Kaydet");
        kategoriEkle.setBounds(120,60,100,20);
        kategoriEkle.setVisible(true);
        kategoriEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostgreSQLDbConnection db = null;
                try {
                    db = new PostgreSQLDbConnection();
                    db.baglan();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


                Kategori kategori = new Kategori(kategoritex.getText());
                try {
                    db.kategoriEkle(kategori);
                    urunListelemeForm urunlistele= new urunListelemeForm(kullaniciTuru,kullaniciId);


                    JOptionPane.showMessageDialog(null, "kategori başarıyla eklendi");

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
        add(kategoriEkle);
        add(kategoriLabel);
        add(kategoritex);
    }





}
