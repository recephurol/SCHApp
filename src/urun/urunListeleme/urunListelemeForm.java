package urun.urunListeleme;

import db.PostgreSQLDbConnection;
import model.Item;
import urun.kullanici.kullaniciEkle;
import urun.urunDetayDeneme.urunDetayFormDeneme;
import urun.urunDetayi.urunDetayiForm;
import urun.urunEkle.urunEkle;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class urunListelemeForm extends JFrame{

    private JTable table;
    private JPanel mainPanel;
    private JPanel panelFiltre;
    private JScrollPane scrollPane;
    private JTextField bulText;
    private JLabel kategoriLabel;
    private JLabel markaLabel;
    private JLabel renkLabel;
    private JLabel bulLabel;
    private JComboBox markaCombobox;
    private JComboBox kategoriCombobox;
    private JComboBox renkCombobox;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem urunEkleMenuItem;
    private JMenuItem kategoriEkleMenuItem;
    private JMenuItem markaEkleMenuItem;
    private JMenuItem renkEkleMenuItem;
    private JMenuItem magazaEkleMenuItem;

    private JMenuItem kullaniciEkleMenuItem;

    private JButton filtreleButon;


    DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };
    Object[] kolonlar = {"id","Adı","Marka","Kategori","Renk","Fiyat"};
    Object[] satirlar = new Object[6];

    public urunListelemeForm() throws SQLException {
        setInitialFormValues();
    }

    private void setInitialFormValues() throws SQLException {
        setBounds(500,200,730,600);
        setTitle("Ürün Listesi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getMainPanel();
        getMenu();
    }

    private void getMainPanel() throws SQLException {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0,0,700,500);
        mainPanel.setVisible(true);

        getPanelFilter();
        getPanelUrunList();

        setContentPane(mainPanel);
        getContentPane().setLayout(null);
    }


    private void getUrunListeleTable() throws SQLException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        table = new JTable();
        table.setBounds(5,5,690,295);
        tableModel.setColumnIdentifiers(kolonlar);
        db.baglan();
        Item kategori = (Item)kategoriCombobox.getSelectedItem();
        Item marka = (Item)markaCombobox.getSelectedItem();
        Item renk = (Item)renkCombobox.getSelectedItem();
        var urunListesi = db.urunListele(bulText.getText(), kategori.getId(),marka.getId(),renk.getId());
        while(urunListesi.next()){
            satirlar[0] = urunListesi.getString("id");
            satirlar[1] = urunListesi.getString("urunadi");
            satirlar[2] = urunListesi.getString("marka");
            satirlar[3] = urunListesi.getString("kategori");
            satirlar[4] = urunListesi.getString("renk");
            satirlar[5] = urunListesi.getString("fiyat")+" TL";
            tableModel.addRow(satirlar);
        }
        table.setAutoCreateRowSorter(true);
        table.setModel(tableModel);
        table.removeColumn(table.getColumnModel().getColumn(0));
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(4).setMaxWidth(50);

        table.setVisible(true);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable target = (JTable)e.getSource();
                var urunId = target.getModel().getValueAt(target.getSelectedRow(),0);
                urunDetayFormDeneme urunDetay = null;
                try {
                    urunDetay = new urunDetayFormDeneme(Integer.valueOf(urunId.toString()));
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
                urunDetay.setVisible(true);
                setVisible(false);
            }
        });
    }

    private void getScrollPane() {
        scrollPane= new JScrollPane();
        scrollPane.setBounds(5,130,700,300);
        scrollPane.setViewportView(table);
    }

    private void getPanelFilter() throws SQLException {
        panelFiltre=new JPanel();
        panelFiltre.setLayout(null);
        panelFiltre.setBounds(5,5,700,100);
        Border borderFiltreleme = BorderFactory.createTitledBorder("Filtreleme");
        panelFiltre.setBorder(borderFiltreleme);
        getFilterComponents();
        mainPanel.add(panelFiltre);
    }

    private void getFilterComponents() throws SQLException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        ResultSet kategoriler = db.kategoriGetir();
        ResultSet renkler = db.renkGetir();
        ResultSet markalar = db.markaGetir();

        bulLabel=new JLabel("Bul :");
        bulLabel.setBounds(15,15,50,20);
        panelFiltre.add(bulLabel);

        bulText = new JTextField();
        bulText.setBounds(50,15,200,20);
        bulText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                urunListeYenile();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                urunListeYenile();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                urunListeYenile();
            }
        });
        panelFiltre.add(bulText);

        kategoriLabel = new JLabel("Kategori");
        kategoriLabel.setBounds(15,35,100,20);
        panelFiltre.add(kategoriLabel);

        markaLabel = new JLabel("Marka");
        markaLabel.setBounds(200,35,100,20);
        panelFiltre.add(markaLabel);

        renkLabel = new JLabel("Renk");
        renkLabel.setBounds(400,35,100,20);
        panelFiltre.add(renkLabel);

        kategoriCombobox= new JComboBox();
        kategoriCombobox.setBounds(15,55,150,20);
        kategoriCombobox.addItem(new Item(null,""));
        while(kategoriler.next()){
            kategoriCombobox.addItem(new Item(kategoriler.getInt("id"),kategoriler.getString("adi")));
        }
        panelFiltre.add(kategoriCombobox);
        markaCombobox= new JComboBox();
        markaCombobox.setBounds(200,55,150,20);
        markaCombobox.addItem(new Item(null,""));
        while(markalar.next()){
            markaCombobox.addItem(new Item(markalar.getInt("id"),markalar.getString("adi")));
        }
        panelFiltre.add(markaCombobox);

        renkCombobox= new JComboBox();
        renkCombobox.setBounds(400,55,150,20);
        renkCombobox.addItem(new Item(null,""));
        while(renkler.next()){
            renkCombobox.addItem(new Item(renkler.getInt("id"),renkler.getString("adi")));
        }
        panelFiltre.add(renkCombobox);

        filtreleButon = new JButton("Filtrele");
        filtreleButon.setBounds(570,55,100,20);
        filtreleButon.setVisible(true);
        filtreleButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                urunListeYenile();
            }
        });

        panelFiltre.add(filtreleButon);

        db.baglantiyiKapat();
    }

    private void urunListeYenile() {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        tableModel.setColumnIdentifiers(kolonlar);
        tableModel.setRowCount(0);
        db.baglan();
        try {
            Item kategori = (Item)kategoriCombobox.getSelectedItem();
            Item marka = (Item)markaCombobox.getSelectedItem();
            Item renk = (Item)renkCombobox.getSelectedItem();
            var urunListesi = db.urunListele(bulText.getText(), kategori.getId(),marka.getId(),renk.getId());

            while(urunListesi.next()){
                satirlar[0] = urunListesi.getString("id");
                satirlar[1] = urunListesi.getString("urunadi");
                satirlar[2] = urunListesi.getString("marka");
                satirlar[3] = urunListesi.getString("kategori");
                satirlar[4] = urunListesi.getString("renk");
                satirlar[5] = urunListesi.getString("fiyat")+" TL";
                tableModel.addRow(satirlar);
            }
            table.setAutoCreateRowSorter(true);
            table.setModel(tableModel);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void getMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("İşlemler");
        urunEkleMenuItem = new JMenuItem("Ürün İşlemleri");
        urunEkleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urunEkle urunEkleForm = null;
                try {
                    urunEkleForm = new urunEkle();
                    urunEkleForm.setVisible(true);
                    setVisible(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        kullaniciEkleMenuItem = new JMenuItem("Kullanici İŞlemleri");
        kullaniciEkleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kullaniciEkle kullaniciEkleForm = null;
                try {
                    kullaniciEkleForm = new kullaniciEkle();
                    kullaniciEkleForm.setVisible(true);
                    setVisible(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        kategoriEkleMenuItem = new JMenuItem("Kategori İşlemleri");
        markaEkleMenuItem = new JMenuItem("Marka İşlemleri");
        renkEkleMenuItem = new JMenuItem("Renk İşlemleri");
        magazaEkleMenuItem = new JMenuItem("Mağaza İŞlemleri");




        menu.add(urunEkleMenuItem);
        menu.add(kategoriEkleMenuItem);
        menu.add(markaEkleMenuItem);
        menu.add(renkEkleMenuItem);
        menu.add(magazaEkleMenuItem);
        menu.add(kullaniciEkleMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    private void getPanelUrunList() throws SQLException {
        getUrunListeleTable();
        getScrollPane();
        mainPanel.add(scrollPane);
    }

}
