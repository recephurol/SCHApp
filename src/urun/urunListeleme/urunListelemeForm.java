package urun.urunListeleme;

import db.PostgreSQLDbConnection;
import urun.urunDetayDeneme.urunDetayFormDeneme;
import urun.urunDetayi.urunDetayiForm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

public class urunListelemeForm extends JFrame{

    private JTable table;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JTextField bulText;
    private JLabel kategoriLabel;
    private JLabel markaLabel;
    private JLabel renkLabel;
    private JLabel bulLabel;
    private JComboBox markaCombobox;
    private JComboBox kategoriCombobox;
    private JComboBox renkCombobox;

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

        getUrunListeleTable();
        getScrollPane();
        getPanel();


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                  // to detect doble click events
                    JTable target = (JTable)e.getSource();
                    var urunId = target.getModel().getValueAt(target.getSelectedRow(),0);// select a row
                urunDetayFormDeneme urunDetay = null;
                try {
                    urunDetay = new urunDetayFormDeneme(Integer.valueOf(urunId.toString()));
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
                urunDetay.setVisible(true);
            }
        });
    }


    private void getUrunListeleTable() throws SQLException {
        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        table = new JTable();
        tableModel.setColumnIdentifiers(kolonlar);
        db.baglan();
        var urunListesi = db.urunListele();
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
    }

    private void getScrollPane() {
        scrollPane= new JScrollPane();
        scrollPane.setBounds(5,100,696,326);
        scrollPane.setViewportView(table);
    }

    private void getPanel() {
        panel=new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBorder(new EmptyBorder(5,5,5,5));
        bulLabel=new JLabel("Bul :");
        bulLabel.setBounds(5,5,50,20);
        panel.add(bulLabel);

        bulText = new JTextField();
        bulText.setBounds(50,5,200,20);
        panel.add(bulText);

        kategoriLabel = new JLabel("Kategori");
        kategoriLabel.setBounds(5,35,100,20);
        panel.add(kategoriLabel);

        markaLabel = new JLabel("Marka");
        markaLabel.setBounds(200,35,100,20);
        panel.add(markaLabel);

        renkLabel = new JLabel("Renk");
        renkLabel.setBounds(400,35,100,20);
        panel.add(renkLabel);

        kategoriCombobox= new JComboBox();
        kategoriCombobox.setBounds(5,55,150,20);
        panel.add(kategoriCombobox);

        markaCombobox= new JComboBox();
        markaCombobox.setBounds(200,55,150,20);
        panel.add(markaCombobox);

        renkCombobox= new JComboBox();
        renkCombobox.setBounds(400,55,150,20);
        panel.add(renkCombobox);

        panel.add(scrollPane);
        setContentPane(panel);
        getContentPane().setLayout(null);
    }

    private void setInitialFormValues() {
        setBounds(100,100,720,472);
        setTitle("Ürün Listesi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(400,200);
    }

}
