package urun.urunListeleme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class urunListelemeForm extends JFrame{

    private JTable table;
    private JPanel panel;
    private JScrollPane scrollPane;

    DefaultTableModel tableModel = new DefaultTableModel();
    Object[] kolonlar = {"Adı","Marka","Kategori","Puan","Fiyat","Mağaza"};
    Object[] satirlar = new Object[6];
    public urunListelemeForm(){

        setBounds(100,100,778,472);
        setTitle("Ürün Listesi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel=new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(panel);

        getContentPane().setLayout(null);

        scrollPane= new JScrollPane();
        scrollPane.setBounds(33,26,696,326);
        panel.add(scrollPane);

        table = new JTable();
        tableModel.setColumnIdentifiers(kolonlar);
        satirlar[0] = "Çikolatalı Gofret";
        satirlar[1] = "Eti";
        satirlar[2] = "Gıda";
        satirlar[3] = "4";
        satirlar[4] = "4";
        satirlar[5] = "Hepsiburada";
        tableModel.addRow(satirlar);


        satirlar = new Object[6];
        satirlar[0] = "Çikolatalı Gofret";
        satirlar[1] = "Ülker";
        satirlar[2] = "Gıda";
        satirlar[3] = "3";
        satirlar[4] = "2";
        satirlar[5] = "Trendyol";
        tableModel.addRow(satirlar);
        table.setModel(tableModel);
        table.setAutoCreateRowSorter(true);
        table.setVisible(true);
        scrollPane.setViewportView(table);
    }

}
