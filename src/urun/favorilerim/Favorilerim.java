package urun.favorilerim;

import dataAccess.PostgreSQLDbConnection;
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

public class Favorilerim extends JFrame {
    private JPanel mainPanel;
    private JPanel favoriListesiPanel;
    private JTable favorilerTable;
    private JScrollPane favoriListesiScrollPane;

    private String kullaniciTuru;
    private Integer kullaniciId;

    private Integer urunId;


    DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };
    Object[] kolonlar = {"id","urun_id","adi","fiyat"};
    Object[] satirlar = new Object[5];


    public Favorilerim(String kullaniciTuru, Integer kullaniciId) throws SQLException, IOException {
        this.kullaniciId=kullaniciId;
        this.kullaniciTuru=kullaniciTuru;
        initializeBildirimler();

        getMainPanel();
        getFavoriListesiPanel();
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
        setTitle("Favorilerim");
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

    private void getFavoriListesiPanel() throws SQLException, IOException {
        favoriListesiPanel = new JPanel();
        favoriListesiPanel.setLayout(null);
        favoriListesiPanel.setBounds(0,0,760,420);
        favoriListesiPanel.setVisible(true);
        Border borderYorum = BorderFactory.createTitledBorder("Favori Ürün Listesi");
        favoriListesiPanel.setBorder(borderYorum);

        getFavoriListeleTable();
        getFavoriListesiScroll();
        mainPanel.add(favoriListesiPanel);
    }

    private void getFavoriListesiScroll() {
        favoriListesiScrollPane= new JScrollPane(favoriListesiPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        favoriListesiScrollPane.setBounds(10,15,740,390);
        favoriListesiScrollPane.setViewportView(favorilerTable);
        mainPanel.add(favoriListesiScrollPane);
    }

    private void getFavoriListeleTable() throws SQLException, IOException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        favorilerTable = new JTable();
        favorilerTable.setBounds(0,0,750,390);
        tableModel.setColumnIdentifiers(kolonlar);
        db.baglan();

        ResultSet favoriListesi = db.favorilerimiListele(kullaniciId);
        while(favoriListesi.next()){
            satirlar[0] = favoriListesi.getString("id");
            satirlar[1] = favoriListesi.getString("urun_id");
            satirlar[2] = favoriListesi.getString("urun_adi");
            satirlar[3] = favoriListesi.getString("fiyat");
            tableModel.addRow(satirlar);
        }
        favorilerTable.setAutoCreateRowSorter(true);
        favorilerTable.setModel(tableModel);
        favorilerTable.removeColumn(favorilerTable.getColumnModel().getColumn(0));
        favorilerTable.removeColumn(favorilerTable.getColumnModel().getColumn(0));
        favorilerTable.setVisible(true);

        favorilerTable.addMouseListener(new MouseAdapter() {
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
