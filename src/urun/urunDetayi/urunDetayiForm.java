package urun.urunDetayi;

import javax.swing.*;


public class urunDetayiForm extends JFrame{
    private JTextField yorumJTextField;
    private JTextField magzaJTextField;
    private JTextField markaJTextField;
    private JTextField fiyatJTextField;
    private JTextField katagoriJTextField;
    private JTextField aciklamaJTextField;
    private JTextField urunIsimiJTextField;
    private JPanel mainpanel;
    private JPanel sag;
    private JPanel resimpanel;


    public urunDetayiForm(){
        add(mainpanel);
        setSize(1000,500);
        setTitle("Urun Detay Ekrani");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        String yorum="aaaaaa";
        yorumJTextField.setText(yorum);
        magzaJTextField.setText("bbbbbb");
        markaJTextField.setText("aadasdsd");
        fiyatJTextField.setText("adsdasdsad");
        katagoriJTextField.setText("asdasdasdas");
        aciklamaJTextField.setText("asdasdsadas");
        urunIsimiJTextField.setText("asdasdasdasd");


    }





}
