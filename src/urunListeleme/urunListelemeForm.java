package urunListeleme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class urunListelemeForm extends JFrame{

    private JScrollPane scrollPane;
    private JTable table;

    public urunListelemeForm(){

        setSize(750,400);
        setTitle("Ürün Listesi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(400,200);
        setResizable(false);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(49,29,271,153);
        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{
                        {null,null,null},
                        {null,null,null},
                        {null,null,null},
                        {null,null,null},
                        {null,null,null},
                        {null,null,null},
                        {null,null,null},
                        {null,null,null},
                        {null,null,null},
                        {null,null,null},
                        {null,null,null},
                        {null,null,null},
                        {null,null,null}
                },
                new String[] {
                        "Adı","Kategori","Mağaza"
                }
        ));
        table.setBounds(293,214,264,128);
        add(scrollPane);
        add(table);
    }

}
