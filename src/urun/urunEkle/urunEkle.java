package urun.urunEkle;

import db.PostgreSQLDbConnection;
import model.Item;
import model.Urun;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.spec.ECGenParameterSpec;
import java.sql.ResultSet;
import java.sql.SQLException;

public class urunEkle extends JFrame {

    private JLabel urunAdiLabel;
    private JTextField urunAdiText;
    private JLabel markaLabel;
    private JComboBox markaCombobox;
    private JLabel kategoriLabel;
    private JComboBox kategoriCombobox;
    private JLabel renkLabel;
    private JComboBox renkCombobox;
    private JLabel aciklamaLabel;
    private JTextArea aciklamaText;
    private JLabel fiyatLabel;
    private  JTextField fiyatText;
    private JLabel magazaLabel;
    private JComboBox magazaCombobox;
    private JPanel urunBilgileriPanel;
    private JPanel fotoPanel;
    private JButton fotografSec;
    private JButton kaydetButon;

    private Font baslik = new Font(null,1,14);

    public urunEkle() throws SQLException {
        initializeUrunEkle();
    }

    private void initializeUrunEkle() throws SQLException {
        setTitle("Ürün Ekleme İşlemleri");
        setBounds(400,400,600,600);
        setLayout(null);

        getUrunBilgileriPanel();
        getFotoPanel();

        getUrunAdi();
        getMarka();
        getKategori();
        getRenk();
        getMagaza();
        getAciklama();
        getFiyat();

        getKaydetButon();
        getFotografSecButon();
    }

    private void getFotografSecButon() {
        fotografSec = new JButton("Fotoğraf Seç");
        fotografSec.setBounds(420,370,100,20);
        fotografSec.setVisible(true);


        fotografSec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser dosyaSec = new JFileChooser();
                dosyaSec.showOpenDialog(null);
                File f = dosyaSec.getSelectedFile();
                String filename = f.getAbsolutePath();
                try {
                    BufferedImage img = ImageIO.read(f);
                    Image scaledImage = img.getScaledInstance(fotoPanel.getWidth()-25,fotoPanel.getHeight()-25,BufferedImage.SCALE_DEFAULT);
                    ImageIcon icon = new ImageIcon(scaledImage);
                    JLabel imgLabel = new JLabel(icon);
                    imgLabel.setBounds(0,0,120,250);
                    imgLabel.setVisible(true);
                    fotoPanel.add(imgLabel);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        add(fotografSec);
    }

    private void getKaydetButon() {
        kaydetButon = new JButton("Kaydet");
        kaydetButon.setBounds(120,370,100,20);
        kaydetButon.setVisible(true);
        kaydetButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostgreSQLDbConnection db = new PostgreSQLDbConnection();
                db.baglan();
                try {
                    db.urunEkle(new Urun());
                } catch (Exception ex){

                }

            }
        });
        add(kaydetButon);
    }

    private void getFotoPanel() {
        fotoPanel = new JPanel(new SpringLayout ());
        fotoPanel.setBounds(370,5,200,350);
        fotoPanel.setVisible(true);
        Border borderFoto = BorderFactory.createTitledBorder("Ürün Fotoğrafı");
        fotoPanel.setBorder(borderFoto);
        add(fotoPanel);
    }

    private void getUrunBilgileriPanel() {
        urunBilgileriPanel = new JPanel();
        urunBilgileriPanel.setLayout(null);
        urunBilgileriPanel.setBounds(5,5,350,350);
        urunBilgileriPanel.setVisible(true);
        Border borderFoto = BorderFactory.createTitledBorder("Ürün Bilgileri");
        urunBilgileriPanel.setBorder(borderFoto);
        add(urunBilgileriPanel);
    }

    private void getFiyat() {
        fiyatLabel = new JLabel("Fiyat: ");
        fiyatLabel.setBounds(10,35,100,20);
        fiyatLabel.setFont(new Font(null,1,14));
        fiyatLabel.setVisible(true);

        fiyatText = new JTextField();
        fiyatText.setBounds(110,35,200,20);
        fiyatText.setVisible(true);
        urunBilgileriPanel.add(fiyatLabel);
        urunBilgileriPanel.add(fiyatText);

    }

    private void getAciklama() {
        aciklamaLabel = new JLabel("Açıklama: ");
        aciklamaLabel.setBounds(10,160,100,20);
        aciklamaLabel.setFont(baslik);
        aciklamaLabel.setVisible(true);

        aciklamaText = new JTextArea();
        aciklamaText.setBounds(110,160,200,80);
        aciklamaText.setFont(new Font(null,0,14));
        aciklamaText.setVisible(true);
        urunBilgileriPanel.add(aciklamaLabel);
        urunBilgileriPanel.add(aciklamaText);
    }

    private void getMagaza() throws SQLException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        ResultSet magazalar  = db.magazaGetir();
        magazaLabel = new JLabel("Mağaza: ");
        magazaLabel.setBounds(10,135,100,20);
        magazaLabel.setFont(baslik);
        magazaLabel.setVisible(true);

        magazaCombobox = new JComboBox();
        magazaCombobox.setBounds(110,135,200,20);
        magazaCombobox.setVisible(true);
        while(magazalar.next()){
            magazaCombobox.addItem(new Item(magazalar.getInt("id"),magazalar.getString("adi")));
        }
        urunBilgileriPanel.add(magazaLabel);
        urunBilgileriPanel.add(magazaCombobox);
        db.baglantiyiKapat();
    }

    private void getRenk() throws SQLException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        ResultSet renkler  = db.renkGetir();
        renkLabel = new JLabel("Renk: ");
        renkLabel.setBounds(10,110,100,20);
        renkLabel.setFont(baslik);
        renkLabel.setVisible(true);

        renkCombobox = new JComboBox();
        renkCombobox.setBounds(110,110,200,20);
        renkCombobox.setVisible(true);
        while(renkler.next()){
            renkCombobox.addItem(new Item(renkler.getInt("id"),renkler.getString("adi")));
        }
        urunBilgileriPanel.add(renkLabel);
        urunBilgileriPanel.add(renkCombobox);
        db.baglantiyiKapat();
    }

    private void getKategori() throws SQLException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        ResultSet kategoriler  = db.kategoriGetir();
        kategoriLabel = new JLabel("Kategori: ");
        kategoriLabel.setBounds(10,85,100,20);
        kategoriLabel.setFont(new Font(null,1,14));
        kategoriLabel.setVisible(true);

        kategoriCombobox = new JComboBox();
        kategoriCombobox.setBounds(110,85,200,20);
        kategoriCombobox.setVisible(true);
        while(kategoriler.next()){
            kategoriCombobox.addItem(new Item(kategoriler.getInt("id"),kategoriler.getString("adi")));
        }
        urunBilgileriPanel.add(kategoriLabel);
        urunBilgileriPanel.add(kategoriCombobox);
        db.baglantiyiKapat();
    }

    private void getMarka() throws SQLException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        ResultSet markalar  = db.markaGetir();
        markaLabel = new JLabel("Marka: ");
        markaLabel.setBounds(10,60,100,20);
        markaLabel.setFont(new Font(null,1,14));
        markaLabel.setVisible(true);

        markaCombobox = new JComboBox();
        markaCombobox.setBounds(110,60,200,20);
        markaCombobox.setVisible(true);
        while(markalar.next()){
            markaCombobox.addItem(new Item(markalar.getInt("id"),markalar.getString("adi")));
        }
        urunBilgileriPanel.add(markaLabel);
        urunBilgileriPanel.add(markaCombobox);
        db.baglantiyiKapat();
    }

    private void getUrunAdi() {
        urunAdiLabel = new JLabel("Adı: ");
        urunAdiLabel.setBounds(10,10,100,20);
        urunAdiLabel.setFont(new Font(null,1,14));
        urunAdiLabel.setVisible(true);

        urunAdiText = new JTextField();
        urunAdiText.setBounds(110,10,200,20);
        urunAdiText.setVisible(true);
        urunBilgileriPanel.add(urunAdiLabel);
        urunBilgileriPanel.add(urunAdiText);
    }
}
