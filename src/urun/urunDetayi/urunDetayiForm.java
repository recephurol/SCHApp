package urun.urunDetayi;

import urun.fotografGosterme.fotoGoster;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


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
    private JLabel resimlabel;

    private BufferedImage image;


    public urunDetayiForm(){
        add(mainpanel);
        setSize(1000,500);
        setTitle("Urun Detay Ekrani");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        resimpanel=new fotoGoster();
        add(resimpanel);




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
