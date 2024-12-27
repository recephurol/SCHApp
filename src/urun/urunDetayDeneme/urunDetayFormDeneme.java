package urun.urunDetayDeneme;

import db.PostgreSQLDbConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class urunDetayFormDeneme extends JFrame {
    private JPanel mainPanel;
    private JPanel fotoPanel;
    private JPanel detayPanel;
    private JPanel fiyatPanel;
    private JPanel yorumPanel;

    private JLabel urunAdiLabel;
    private JLabel markaLabel;
    private JLabel kategoriLabel;
    private JLabel renkLabel;
    private JLabel aciklamaLabel;


    public urunDetayFormDeneme(Integer urunId) throws SQLException, IOException {

        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        setBounds(400,200,690,750);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(25,25,745,745);
        mainPanel.setVisible(true);



        fotoPanel = new JPanel(){};
        fotoPanel.setLayout(null);
        fotoPanel.setBounds(10,40,150,200);
        fotoPanel.setVisible(true);


        Border borderFoto = BorderFactory.createTitledBorder("Fotoğraf");
        fotoPanel.setBorder(borderFoto);

        detayPanel = new JPanel(new SpringLayout ());
        detayPanel.setLayout(null);
        detayPanel.setBounds(200,40,450,200);
        detayPanel.setVisible(true);
        Border borderDetay = BorderFactory.createTitledBorder("Ürün Detayı");
        detayPanel.setBorder(borderDetay);

        ResultSet urunDetay = db.urunDetayGetir(urunId);
        while(urunDetay.next()){
            BufferedImage img = ImageIO.read(new File(urunDetay.getString("fotograf")));
            Image scaledImage = img.getScaledInstance(fotoPanel.getWidth(),fotoPanel.getHeight(),BufferedImage.SCALE_DEFAULT);
            ImageIcon icon = new ImageIcon(scaledImage);
            JLabel imgLabel = new JLabel(icon);
            imgLabel.setVisible(true);
            imgLabel.setBounds(0,0,150,200);
            fotoPanel.add(imgLabel);

            urunAdiLabel = new JLabel(urunDetay.getString("urunAdi"));
            urunAdiLabel.setFont(new Font(null,1,18));
            urunAdiLabel.setBounds(5,10,400,30);
            urunAdiLabel.setVisible(true);

            markaLabel = new JLabel("Marka:  "+urunDetay.getString("marka"));
            markaLabel.setBounds(5,45,400,30);
            markaLabel.setFont(new Font(null,0,16));
            markaLabel.setVisible(true);

            kategoriLabel = new JLabel("Kategori:  "+urunDetay.getString("kategori"));
            kategoriLabel.setBounds(5,80,400,30);
            kategoriLabel.setFont(new Font(null,0,16));
            kategoriLabel.setVisible(true);

            renkLabel = new JLabel("Renk:  "+urunDetay.getString("renk"));
            renkLabel.setBounds(5,115,400,30);
            renkLabel.setFont(new Font(null,0,16));
            renkLabel.setVisible(true);

            aciklamaLabel = new JLabel("<html>Açıklama:  "+urunDetay.getString("aciklama")+"</html>");
            aciklamaLabel.setBounds(5,150,400,50);
            aciklamaLabel.setFont(new Font(null,0,16));
            aciklamaLabel.setVisible(true);

            detayPanel.add(urunAdiLabel);
            detayPanel.add(markaLabel);
            detayPanel.add(kategoriLabel);
            detayPanel.add(renkLabel);
            detayPanel.add(aciklamaLabel);
        }




        fiyatPanel = new JPanel(new BorderLayout ());
        fiyatPanel.setLayout(null);
        fiyatPanel.setBounds(10,260,645,200);
        fiyatPanel.setVisible(true);
        Border borderFiyat = BorderFactory.createTitledBorder("Mağaza Fiyat Bilgisi");

        fiyatPanel.setBorder(borderFiyat);

        ResultSet urunFiyat= db.urunFiyatListesiGetir(urunId);

        Integer j=0;
        while(urunFiyat.next()){
            JLabel magazaFiyat = new JLabel(urunFiyat.getString("magaza") + "            Fiyat : "+urunFiyat.getString("fiyat")+" TL");
            magazaFiyat.setBounds(15,25+j,500,20);
            magazaFiyat.setFont(new Font(null,1,14));
            magazaFiyat.setVisible(true);

            SatinAlButton satinAl = new SatinAlButton(Integer.valueOf(urunFiyat.getString("id")),"Satın Al");
            satinAl.setBounds(400,25+j,100,20);

            fiyatPanel.add(magazaFiyat,BorderLayout.LINE_START);
            fiyatPanel.add(satinAl,BorderLayout.LINE_END);
            j+=30;
        }


        yorumPanel = new JPanel(new SpringLayout ());
        yorumPanel.setLayout(null);
        yorumPanel.setBounds(10,480,645,200);
        yorumPanel.setVisible(true);
        Border borderYorum = BorderFactory.createTitledBorder("Ürün Yorumları");
        yorumPanel.setBorder(borderYorum);

        ResultSet urunYorum= db.urunYorumListesiGetir(urunId);

        Integer i=0;
        while(urunYorum.next()){
            JLabel adSoyad = new JLabel(urunYorum.getString("adSoyad") + "     Puan : "+urunYorum.getString("puan"));
            adSoyad.setBounds(15,15+i,700,20);
            adSoyad.setFont(new Font(null,1,14));
            adSoyad.setVisible(true);

            JLabel yorum = new JLabel(urunYorum.getString("yorum"));
            yorum.setBounds(15,40+i,700,20);
            yorum.setFont(new Font(null,0,12));
            yorum.setVisible(true);

            JLabel ayirici = new JLabel("----------------------------------------------------------------------------------------------------------------");
            ayirici.setBounds(15,50+i,700,20);
            ayirici.setFont(new Font(null,0,12));
            ayirici.setVisible(true);

            yorumPanel.add(adSoyad);
            yorumPanel.add(yorum);
            yorumPanel.add(ayirici);
            i+=53;
        }

        mainPanel.add(fotoPanel);
        mainPanel.add(detayPanel);
        mainPanel.add(fiyatPanel);
        mainPanel.add(yorumPanel);
        setContentPane(mainPanel);
    }


}
