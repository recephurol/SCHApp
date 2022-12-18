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
    Object[] satirlar = {"Çikolatalı Gofret","Eti","Gıda","4","4","Hepsiburada"};
    public urunListelemeForm(){

        setBounds(100,100,778,472);
        setTitle("Ürün Listesi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(400,200);
        panel=new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(panel);

        getContentPane().setLayout(null);

        scrollPane= new JScrollPane();
        scrollPane.setBounds(33,26,696,326);
        panel.add(scrollPane);

        table = new JTable();
        tableModel.setColumnIdentifiers(kolonlar);
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        tableModel.addRow(satirlar);
        table.setModel(tableModel);
        table.setVisible(true);
        scrollPane.setViewportView(table);
    }

}
