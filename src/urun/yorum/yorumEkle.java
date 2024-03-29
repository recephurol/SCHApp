package urun.yorum;

import dataAccess.PostgreSQLDbConnection;
import model.Yorum;
import urun.urunDetayi.urunDetayFormDeneme;
import urun.urunListeleme.urunListelemeForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class yorumEkle extends JFrame {

    private Integer _urunId;

    private JLabel yorumLabel;
    private JTextArea yorumText;
    private JComboBox puan;
    private JLabel puanLabel;
    private JButton kaydetButton;
    private JLabel adSoyadLabel;
    private JTextField adSoyadText;
    private String kullaniciTuru;
    private Integer kullaniciId;

    public yorumEkle(Integer urunId,String kullaniciTuru, Integer kullaniciId){
        _urunId=urunId;
        this.kullaniciTuru=kullaniciTuru;
        this.kullaniciId=kullaniciId;
        initializeYorumEkleForm();
    }

    public Integer getUrunId() {
        return _urunId;
    }

    private void initializeYorumEkleForm() {
        setTitle("Yorum Ekleme İşlemleri");
        setBounds(600,400,350,300);
        setLayout(null);
        setVisible(true);

        getYorum();
        getPuan();
        getAdSoyad();

        kaydetButton = new JButton("Kaydet");
        kaydetButton.setBounds(120,160,100,20);
        kaydetButton.setVisible(true);
        kaydetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostgreSQLDbConnection db = null;
                try {
                    db = new PostgreSQLDbConnection();
                    db.baglan();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                Double puanCB =Double.parseDouble(puan.getSelectedItem().toString());
                final Integer urunIdfinal = getUrunId();
                Yorum yorum = new Yorum(urunIdfinal,adSoyadText.getText(),puanCB,yorumText.getText());
                try {
                    db.yorumEkle(yorum);
                    urunDetayFormDeneme detay = new urunDetayFormDeneme(urunIdfinal,kullaniciTuru,kullaniciId);
                    JOptionPane.showMessageDialog(null, "Yorum Başarıyla Eklendi");
                    detay.setVisible(true);
                    setVisible(false);
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        add(kaydetButton);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    urunDetayFormDeneme detay = new urunDetayFormDeneme(_urunId,kullaniciTuru,kullaniciId);
                    detay.setVisible(true);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getAdSoyad() {
        adSoyadLabel = new JLabel("Adı Soyadı : ");
        adSoyadLabel.setBounds(10,130,75,20);
        adSoyadLabel.setVisible(true);

        adSoyadText = new JTextField();
        adSoyadText.setBounds(100,130,75,20);
        adSoyadText.setVisible(true);

        add(adSoyadText);
        add(adSoyadLabel);
    }

    private void getPuan() {
        puanLabel = new JLabel("Puan : ");
        puanLabel.setBounds(10,100,75,20);
        puanLabel.setVisible(true);

        puan = new JComboBox();
        puan.setBounds(100,100,100,20);
        puan.setVisible(true);
        puan.addItem(1);
        puan.addItem(2);
        puan.addItem(3);
        puan.addItem(4);
        puan.addItem(5);
        add(puanLabel);
        add(puan);
    }

    private void getYorum() {
        yorumLabel = new JLabel("Yorum");
        yorumLabel.setBounds(10,10,75,20);
        yorumLabel.setVisible(true);

        yorumText = new JTextArea();
        yorumText.setFont(new Font(null,0,14));
        yorumText.setBounds(100,10,200,80);
        yorumText.setVisible(true);

        add(yorumLabel);
        add(yorumText);
    }
}
