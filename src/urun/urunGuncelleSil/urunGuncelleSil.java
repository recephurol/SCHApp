package urun.urunGuncelleSil;

import araclar.FileExtension;
import dataAccess.PostgreSQLDbConnection;
import model.Item;
import model.Urun;
import urun.urunListeleme.urunListelemeForm;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;

public class urunGuncelleSil extends JFrame {

    private JPanel mainPanel;
    private JPanel urunListesiPanel;
    private JPanel urunBilgisiPanel;
    private JTable table;
    private JScrollPane urunListesiScrollPane;
    private JTextField urunAdiText;
    private JComboBox markaCombobox;
    private JComboBox kategoriCombobox;
    private JComboBox renkCombobox;
    private JComboBox magazaCombobox;
    private JFormattedTextField fiyatText;
    private JTextArea aciklamaText;
    private JLabel urunAdiLabel;
    private JLabel markaLabel;
    private JLabel kategoriLabel;
    private JLabel renkLabel;
    private JLabel magazaLabel;
    private JLabel fiyatLabel;
    private JLabel aciklamaLabel;
    private JLabel stokLabel;
    private JFormattedTextField stokText;
    private JButton kaydetButon;
    private JButton silButon;
    private JButton iptalButon;
    private JButton fotografSec;
    private Image urunFoto;
    private String kullaniciTuru;
    private Integer kullaniciId;

    private Integer urunId;
    private Integer urunFiyatId;
    private String fotoFileName;


    DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };
    Object[] kolonlar = {"id","Ürün Id","markaId","kategoriId","renkId","magazaId","Adı","Marka","Kategori","Renk","Mağaza","Stok","Fiyat","Açıklama"};
    Object[] satirlar = new Object[14];

    public urunGuncelleSil(String kullaniciTuru,Integer kullaniciId) throws SQLException, IOException {
        this.kullaniciTuru=kullaniciTuru;
        this.kullaniciId=kullaniciId;
        initializeUrunGuncelleSil();

        getMainPanel();
        getUrunListesiPanel();
        getUrunBilgisiPanel();

    }

    private void getUrunBilgisiPanel() throws SQLException, IOException {
        urunBilgisiPanel = new JPanel();
        urunBilgisiPanel.setLayout(null);
        urunBilgisiPanel.setBounds(640,0,400,420);
        urunBilgisiPanel.setVisible(true);
        Border borderYorum = BorderFactory.createTitledBorder("Ürün Fiyat Bilgileri");
        urunBilgisiPanel.setBorder(borderYorum);

        getUrunAdi();
        getUrunMarkasi();
        getUrunKategorisi();
        getUrunRenk();
        getMagaza();
        getFiyat();
        getStok();
        getAciklama();
        getButtons();
        kapatButonu();
//        getFotografSecButon();
        mainPanel.add(urunBilgisiPanel);
    }


    private void getFotografSecButon() {
        fotografSec = new JButton("Fotoğraf Seç");
        fotografSec.setBounds(250,195,100,20);
        fotografSec.setVisible(true);


        fotografSec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser dosyaSec = new JFileChooser();
                dosyaSec.showOpenDialog(null);
                File f = dosyaSec.getSelectedFile();
                String url="src/images/";


                String fotoPath = f.getAbsolutePath();
                File directory = new File(url);
                if(!directory.exists()){
                    directory.mkdirs();
                }
                File sourceFile = null;
                File destinationFile = null;
                String uzanti = FileExtension.getFileExtension(f);
                fotoFileName = f.getName();

                sourceFile = new File(fotoPath);
                destinationFile = new File(url+fotoFileName);
                try {
                    Files.copy(sourceFile.toPath(),destinationFile.toPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    BufferedImage img = ImageIO.read(destinationFile);
                    ImageIcon icon = new ImageIcon(img);

                    JLabel imgLabel = new JLabel(icon);

                    imgLabel.setBounds(100,200,120,250);
                    imgLabel.setVisible(true);
                    urunBilgisiPanel.add(imgLabel);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        urunBilgisiPanel.add(fotografSec);
    }

    public void kapatButonu(){
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    urunListelemeForm listeForm = new urunListelemeForm(kullaniciTuru,kullaniciId);
                    listeForm.setVisible(true);
                    setVisible(false);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getButtons() {
        kaydetButon = new JButton("Kaydet");
        kaydetButon.setBounds(20,350,100,20);
        kaydetButon.setVisible(true);
        kaydetButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostgreSQLDbConnection db = null;
                try {
                    db = new PostgreSQLDbConnection();
                    db.baglan();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                Item kategori= (Item)kategoriCombobox.getSelectedItem();
                Item marka= (Item)markaCombobox.getSelectedItem();
                Item renk= (Item)renkCombobox.getSelectedItem();
                Item magaza= (Item)magazaCombobox.getSelectedItem();
                Boolean sonuc = db.urunGuncelle(new Urun(
                        urunId,
                        Double.parseDouble(fiyatText.getText()),
                        urunAdiText.getText(),
                        aciklamaText.getText(),
                        kategori.getId(),
                        marka.getId(),
                        renk.getId(),
                        magaza.getId(),
                        Integer.valueOf(stokText.getText()),
                        null
                ));
                if(sonuc){
                    db.urunFiyatGuncelle(urunFiyatId,Double.parseDouble(fiyatText.getText()),Integer.valueOf(stokText.getText()));
                }

                String mesaj = sonuc ? "Ürün Fiyat Başarıyla Güncellenmiştir" : "Ürün Güncellerken bir hata oluştu";
                JOptionPane.showMessageDialog(null,mesaj);
                try {
                    urunListeYenile();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });


        silButon = new JButton("Sil");
        silButon.setBounds(150,350,100,20);
        silButon.setVisible(true);
        silButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostgreSQLDbConnection db = null;
                try {
                    db = new PostgreSQLDbConnection();
                    db.baglan();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            Boolean sonuc = db.urunFiyatSil(urunFiyatId);

            String mesaj = sonuc ? "Ürün Fiyat Başarıyla Silinmiştir" : "Ürün Silerken bir hata oluştu";
            JOptionPane.showMessageDialog(null,mesaj);
                try {
                    urunListeYenile();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        iptalButon = new JButton("İptal");
        iptalButon.setBounds(280,350,100,20);
        iptalButon.setVisible(true);
        iptalButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                urunListelemeForm listeForm = null;
                try {
                    listeForm = new urunListelemeForm(kullaniciTuru,kullaniciId);
                    listeForm.setVisible(true);
                    setVisible(false);
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        urunBilgisiPanel.add(kaydetButon);
        urunBilgisiPanel.add(silButon);
        urunBilgisiPanel.add(iptalButon);
    }


    private void getAciklama() {
        aciklamaLabel = new JLabel("Açıklama: ");
        aciklamaLabel.setBounds(10,225,50,20);
        aciklamaLabel.setVisible(true);

        aciklamaText = new JTextArea();
        aciklamaText.setBounds(65,225,250,80);
        aciklamaText.setFont(new Font(null,0,14));
        aciklamaText.setVisible(true);

        urunBilgisiPanel.add(aciklamaLabel);
        urunBilgisiPanel.add(aciklamaText);
    }

    private void urunListeYenile() throws IOException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        tableModel.setColumnIdentifiers(kolonlar);
        tableModel.setRowCount(0);
        db.baglan();
        try {
            ResultSet urunFiyatListesi = db.urunFiyatListele();

            while(urunFiyatListesi.next()){
                satirlar[0] = urunFiyatListesi.getInt("id");
                satirlar[1] = urunFiyatListesi.getInt("urunId");
                satirlar[2] = urunFiyatListesi.getInt("markaId");
                satirlar[3] = urunFiyatListesi.getInt("kategoriId");
                satirlar[4] = urunFiyatListesi.getInt("renkId");
                satirlar[5] = urunFiyatListesi.getInt("magazaId");
                satirlar[6] = urunFiyatListesi.getString("urunAdi");
                satirlar[7] = urunFiyatListesi.getString("marka");
                satirlar[8] = urunFiyatListesi.getString("kategori");
                satirlar[9] = urunFiyatListesi.getString("renk");
                satirlar[10] = urunFiyatListesi.getString("magaza");
                satirlar[11] = urunFiyatListesi.getInt("stok");
                satirlar[12] = urunFiyatListesi.getDouble("fiyat")+" TL";
                satirlar[13] = urunFiyatListesi.getString("aciklama");
                tableModel.addRow(satirlar);
            }
            table.setAutoCreateRowSorter(true);
            table.setModel(tableModel);
            table.removeColumn(table.getColumnModel().getColumn(0));
            table.removeColumn(table.getColumnModel().getColumn(0));
            table.removeColumn(table.getColumnModel().getColumn(0));
            table.removeColumn(table.getColumnModel().getColumn(0));
            table.removeColumn(table.getColumnModel().getColumn(0));
            table.removeColumn(table.getColumnModel().getColumn(0));
            table.removeColumn(table.getColumnModel().getColumn(7));

            table.setModel(tableModel);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void getStok() {
        stokLabel = new JLabel("Stok: ");
        stokLabel.setBounds(10,170,50,20);
        stokLabel.setVisible(true);

        stokText = new JFormattedTextField();
        stokText.setBounds(65,170,150,20);
        stokText.setVisible(true);

        urunBilgisiPanel.add(stokLabel);
        urunBilgisiPanel.add(stokText);
    }

    private void getFiyat() {
        fiyatLabel = new JLabel("Fiyat: ");
        fiyatLabel.setBounds(10,145,50,20);
        fiyatLabel.setVisible(true);

        fiyatText = new JFormattedTextField();
        fiyatText.setBounds(65,145,150,20);
        fiyatText.setVisible(true);

        urunBilgisiPanel.add(fiyatLabel);
        urunBilgisiPanel.add(fiyatText);

    }

    private void getMagaza() throws SQLException, IOException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        ResultSet magazalar  = db.magazaGetir();
        magazaLabel = new JLabel("Mağaza: ");
        magazaLabel.setBounds(10,120,50,20);
        magazaLabel.setVisible(true);

        magazaCombobox = new JComboBox();
        while(magazalar.next()){
            magazaCombobox.addItem(new Item(magazalar.getInt("id"),magazalar.getString("adi")));
        }
        db.baglantiyiKapat();
        magazaCombobox.setBounds(65,120,150,20);
        magazaCombobox.setVisible(true);

        urunBilgisiPanel.add(magazaLabel);
        urunBilgisiPanel.add(magazaCombobox);
    }

    private void getUrunRenk() throws SQLException, IOException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        ResultSet renkler  = db.renkGetir();
        renkLabel = new JLabel("renk: ");
        renkLabel.setBounds(10,95,50,20);
        renkLabel.setVisible(true);

        renkCombobox = new JComboBox();
        while(renkler.next()){
            renkCombobox.addItem(new Item(renkler.getInt("id"),renkler.getString("adi")));
        }
        db.baglantiyiKapat();
        renkCombobox.setBounds(65,95,150,20);
        renkCombobox.setVisible(true);

        urunBilgisiPanel.add(renkLabel);
        urunBilgisiPanel.add(renkCombobox);
    }

    private void getUrunKategorisi() throws SQLException, IOException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        ResultSet kategoriler  = db.kategoriGetir();
        kategoriLabel = new JLabel("Kategori: ");
        kategoriLabel.setBounds(10,70,50,20);
        kategoriLabel.setVisible(true);

        kategoriCombobox = new JComboBox();
        while(kategoriler.next()){
            kategoriCombobox.addItem(new Item(kategoriler.getInt("id"),kategoriler.getString("adi")));
        }
        db.baglantiyiKapat();
        kategoriCombobox.setBounds(65,70,150,20);
        kategoriCombobox.setVisible(true);

        urunBilgisiPanel.add(kategoriLabel);
        urunBilgisiPanel.add(kategoriCombobox);
    }

    private void getUrunMarkasi() throws SQLException, IOException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        ResultSet markalar  = db.markaGetir();
        markaLabel = new JLabel("Marka: ");
        markaLabel.setBounds(10,45,50,20);
        markaLabel.setVisible(true);

        markaCombobox = new JComboBox();
        while(markalar.next()){
            markaCombobox.addItem(new Item(markalar.getInt("id"),markalar.getString("adi")));
        }
        db.baglantiyiKapat();
        markaCombobox.setBounds(65,45,150,20);
        markaCombobox.setVisible(true);

        urunBilgisiPanel.add(markaLabel);
        urunBilgisiPanel.add(markaCombobox);
    }

    private void getUrunAdi() {
        urunAdiLabel = new JLabel("Ürün Adı: ");
        urunAdiLabel.setBounds(10,20,50,20);
        urunAdiLabel.setVisible(true);

        urunAdiText = new JTextField();
        urunAdiText.setBounds(65,20,150,20);
        urunAdiText.setVisible(true);
        urunBilgisiPanel.add(urunAdiLabel);
        urunBilgisiPanel.add(urunAdiText);
    }

    private void getUrunListesiPanel() throws SQLException, IOException {
        urunListesiPanel = new JPanel();
        urunListesiPanel.setLayout(null);
        urunListesiPanel.setBounds(0,0,620,420);
        urunListesiPanel.setVisible(true);
        Border borderYorum = BorderFactory.createTitledBorder("Ürün Fiyat Listesi");
        urunListesiPanel.setBorder(borderYorum);

        getUrunListeleTable();
        getUrunListesiScroll();
        mainPanel.add(urunListesiPanel);
    }

    private void getUrunListesiScroll() {
        urunListesiScrollPane= new JScrollPane(urunListesiPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        urunListesiScrollPane.setBounds(10,15,590,390);
        urunListesiScrollPane.setViewportView(table);
        mainPanel.add(urunListesiScrollPane);
    }

    private void getUrunListeleTable() throws SQLException, IOException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        table = new JTable();
        table.setBounds(0,0,590,390);
        tableModel.setColumnIdentifiers(kolonlar);
        db.baglan();

        ResultSet urunFiyatListesi = db.urunFiyatListele();
        while(urunFiyatListesi.next()){
            satirlar[0] = urunFiyatListesi.getInt("id");
            satirlar[1] = urunFiyatListesi.getInt("urunId");
            satirlar[2] = urunFiyatListesi.getInt("markaId");
            satirlar[3] = urunFiyatListesi.getInt("kategoriId");
            satirlar[4] = urunFiyatListesi.getInt("renkId");
            satirlar[5] = urunFiyatListesi.getInt("magazaId");
            satirlar[6] = urunFiyatListesi.getString("urunAdi");
            satirlar[7] = urunFiyatListesi.getString("marka");
            satirlar[8] = urunFiyatListesi.getString("kategori");
            satirlar[9] = urunFiyatListesi.getString("renk");
            satirlar[10] = urunFiyatListesi.getString("magaza");
            satirlar[11] = urunFiyatListesi.getInt("stok");
            satirlar[12] = urunFiyatListesi.getDouble("fiyat")+" TL";
            satirlar[13] = urunFiyatListesi.getString("aciklama");
            tableModel.addRow(satirlar);
        }
        table.setAutoCreateRowSorter(true);
        table.setModel(tableModel);
        table.removeColumn(table.getColumnModel().getColumn(0));
        table.removeColumn(table.getColumnModel().getColumn(0));
        table.removeColumn(table.getColumnModel().getColumn(0));
        table.removeColumn(table.getColumnModel().getColumn(0));
        table.removeColumn(table.getColumnModel().getColumn(0));
        table.removeColumn(table.getColumnModel().getColumn(0));
        table.removeColumn(table.getColumnModel().getColumn(7));

        table.setVisible(true);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable target = (JTable)e.getSource();
                urunFiyatId = (Integer)target.getModel().getValueAt(target.getSelectedRow(),0);
                urunId = (Integer)target.getModel().getValueAt(target.getSelectedRow(),1);

                urunAdiText.setText(target.getModel().getValueAt(target.getSelectedRow(),6).toString());
                markaCombobox.getModel().setSelectedItem(
                        new Item((Integer)target.getModel().getValueAt(target.getSelectedRow(),2),
                                (String)target.getModel().getValueAt(target.getSelectedRow(),7)));
                kategoriCombobox.getModel().setSelectedItem(
                        new Item((Integer)target.getModel().getValueAt(target.getSelectedRow(),3),
                        (String)target.getModel().getValueAt(target.getSelectedRow(),8)));
                renkCombobox.getModel().setSelectedItem(
                        new Item((Integer)target.getModel().getValueAt(target.getSelectedRow(),4),
                                (String)target.getModel().getValueAt(target.getSelectedRow(),9)));
                magazaCombobox.getModel().setSelectedItem(
                        new Item((Integer)target.getModel().getValueAt(target.getSelectedRow(),5),
                                (String)target.getModel().getValueAt(target.getSelectedRow(),10))
                );
                stokText.setText(target.getModel().getValueAt(target.getSelectedRow(),11).toString());
                fiyatText.setText(target.getModel().getValueAt(target.getSelectedRow(),12).toString().replace(" TL",""));
                aciklamaText.setText(target.getModel().getValueAt(target.getSelectedRow(),13).toString());
            }
        });

    }

    private void getMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(5,5,1200,490);
        mainPanel.setVisible(true);
        add(mainPanel);
    }

    private void initializeUrunGuncelleSil() {
        setTitle("Ürün Güncelle / Sil İşlemleri");
        setLayout(null);
        setBounds(350,200,1200,500);
        setVisible(true);
    }
}
