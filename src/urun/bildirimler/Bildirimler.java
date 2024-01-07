package urun.bildirimler;

import dataAccess.PostgreSQLDbConnection;
import model.Item;
import urun.urunDetayi.urunDetayFormDeneme;
import urun.urunListeleme.urunListelemeForm;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    Object[] kolonlar = {"id","urun_id","aciklama"};
    Object[] satirlar = new Object[5];


    public Bildirimler(String kullaniciTuru, Integer kullaniciId) throws SQLException, IOException {
        this.kullaniciId=kullaniciId;
        this.kullaniciTuru=kullaniciTuru;
        initializeBildirimler();

        getMainPanel();
        getBildirimListesiPanel();
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
    }

    private void initializeBildirimler() {
        setTitle("Bildirimler");
        setLayout(null);
        setBounds(350,200,800,500);
        setVisible(true);
    }

    private void getMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(5,5,800,490);
        mainPanel.setVisible(true);
        add(mainPanel);
    }

    private void getBildirimListesiPanel() throws SQLException, IOException {
        bildirimListesiPanel = new JPanel();
        bildirimListesiPanel.setLayout(null);
        bildirimListesiPanel.setBounds(0,0,760,420);
        bildirimListesiPanel.setVisible(true);
        Border borderYorum = BorderFactory.createTitledBorder("Bildirim Listesi");
        bildirimListesiPanel.setBorder(borderYorum);

        getBildirimListeleTable();
        getBildirimListesiScroll();
        mainPanel.add(bildirimListesiPanel);
    }

    private void getBildirimListesiScroll() {
        bildirimListesiScrollPane= new JScrollPane(bildirimListesiPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bildirimListesiScrollPane.setBounds(10,15,740,390);
        bildirimListesiScrollPane.setViewportView(bildirimlerTable);
        mainPanel.add(bildirimListesiScrollPane);
    }

    private void getBildirimListeleTable() throws SQLException, IOException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        bildirimlerTable = new JTable();
        bildirimlerTable.setBounds(0,0,750,390);
        tableModel.setColumnIdentifiers(kolonlar);
        db.baglan();

        ResultSet bildirimListesi = db.bildirimListele(kullaniciId);
        while(bildirimListesi.next()){
            satirlar[0] = bildirimListesi.getString("id");
            satirlar[1] = bildirimListesi.getString("urun_id");
            satirlar[2] = bildirimListesi.getString("aciklama");
            tableModel.addRow(satirlar);
        }
        bildirimlerTable.setAutoCreateRowSorter(true);
        bildirimlerTable.setModel(tableModel);
        bildirimlerTable.removeColumn(bildirimlerTable.getColumnModel().getColumn(0));
        bildirimlerTable.removeColumn(bildirimlerTable.getColumnModel().getColumn(0));
        bildirimlerTable.setVisible(true);

        bildirimlerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable target = (JTable)e.getSource();
                String urun_id = (String)target.getModel().getValueAt(target.getSelectedRow(),1);
                urunId=Integer.valueOf(urun_id);
                urunDetayFormDeneme urunDetay = null;
                try {
                    urunDetay = new urunDetayFormDeneme(urunId,kullaniciTuru,kullaniciId);
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
                urunDetay.setVisible(true);
                setVisible(false);
            }
        });
    }
}
