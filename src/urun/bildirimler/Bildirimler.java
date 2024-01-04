package urun.bildirimler;

import dataAccess.PostgreSQLDbConnection;
import model.Item;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bildirimler extends JFrame {
    private JPanel mainPanel;
    private JPanel bildirimListesiPanel;
    private JTable bildirimlerTable;
    private JScrollPane bildirimListesiScrollPane;

    private String kullaniciTuru;
    private Integer kullaniciId;

    private Integer urunId;
    private Integer urunFiyatId;


    DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };
    Object[] kolonlar = {"id","aciklama"};
    Object[] satirlar = new Object[5];


    public Bildirimler(Integer kullaniciId) throws SQLException, IOException {
        this.kullaniciId=kullaniciId;

        initializeBildirimler();

        getMainPanel();
        getBildirimListesiPanel();
    }

    private void initializeBildirimler() {
        setTitle("Bildirimler");
        setLayout(null);
        setBounds(350,200,1200,500);
        setVisible(true);
    }

    private void getMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(5,5,1200,490);
        mainPanel.setVisible(true);
        add(mainPanel);
    }

    private void getBildirimListesiPanel() throws SQLException, IOException {
        bildirimListesiPanel = new JPanel();
        bildirimListesiPanel.setLayout(null);
        bildirimListesiPanel.setBounds(0,0,620,420);
        bildirimListesiPanel.setVisible(true);
        Border borderYorum = BorderFactory.createTitledBorder("Bildirim Listesi");
        bildirimListesiPanel.setBorder(borderYorum);

        getBildirimListeleTable();
        getBildirimListesiScroll();
        mainPanel.add(bildirimListesiPanel);
    }

    private void getBildirimListesiScroll() {
        bildirimListesiScrollPane= new JScrollPane(bildirimListesiPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bildirimListesiScrollPane.setBounds(10,15,590,390);
        bildirimListesiScrollPane.setViewportView(bildirimlerTable);
        mainPanel.add(bildirimListesiScrollPane);
    }

    private void getBildirimListeleTable() throws SQLException, IOException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        bildirimlerTable = new JTable();
        bildirimlerTable.setBounds(0,0,590,390);
        tableModel.setColumnIdentifiers(kolonlar);
        db.baglan();

        ResultSet bildirimListesi = db.bildirimListele(kullaniciId);
        while(bildirimListesi.next()){
            satirlar[0] = bildirimListesi.getInt("id");
            satirlar[1] = bildirimListesi.getString("aciklama");

            tableModel.addRow(satirlar);
        }
        bildirimlerTable.setAutoCreateRowSorter(true);
        bildirimlerTable.setModel(tableModel);

        bildirimlerTable.setVisible(true);
    }


}
