package urun.urunDetayDeneme;

import db.PostgreSQLDbConnection;
import urun.urunListeleme.urunListelemeForm;
import urun.yorum.yorumEkle;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private JLabel ortalamaPuanLabel;

    private JButton yorumYapButon;

    private Integer urunId;

    private JScrollPane yorumScroll;

    public urunDetayFormDeneme(Integer urunId) throws SQLException, IOException {
        urunId=urunId;

        PostgreSQLDbConnection db = new PostgreSQLDbConnection();
        db.baglan();
        setBounds(400,200,690,750);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(25,25,745,745);
        mainPanel.setVisible(true);



        fotoPanel = new JPanel();
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
            String fotoUrl = urunDetay.getString("fotograf");
            if(fotoUrl!=null && fotoUrl!=""){
                BufferedImage img = ImageIO.read(new File(fotoUrl));
                Image scaledImage = img.getScaledInstance(fotoPanel.getWidth()-25,fotoPanel.getHeight()-25,BufferedImage.SCALE_DEFAULT);
                ImageIcon icon = new ImageIcon(scaledImage);
                JLabel imgLabel = new JLabel(icon);
                imgLabel.setVisible(true);
                imgLabel.setBounds(0,0,150,200);
                fotoPanel.add(imgLabel);
            }

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


            ResultSet urunPuani= db.urunPuaniGetir(urunId);
            urunPuani.next();
            ortalamaPuanLabel = new JLabel("Puan :  " +urunPuani.getDouble("avg"));
            ortalamaPuanLabel.setBounds(300,10,400,30);
            ortalamaPuanLabel.setFont(new Font(null,0,16));
            ortalamaPuanLabel.setVisible(true);

            detayPanel.add(urunAdiLabel);
            detayPanel.add(markaLabel);
            detayPanel.add(kategoriLabel);
            detayPanel.add(renkLabel);
            detayPanel.add(aciklamaLabel);
            detayPanel.add(ortalamaPuanLabel);
        }





        fiyatPanel = new JPanel();
        fiyatPanel.setLayout(null);
        fiyatPanel.setBounds(10,250,645,200);
        fiyatPanel.setVisible(true);
        Border borderFiyat = BorderFactory.createTitledBorder("Mağaza Fiyat Bilgisi");
        fiyatPanel.setBorder(borderFiyat);



        ResultSet urunFiyat= db.urunFiyatListesiGetir(urunId);

        int j=0;
        while(urunFiyat.next()){
            JLabel magazaFiyat = new JLabel(urunFiyat.getString("magaza") + "    Stok: "+urunFiyat.getInt("stok")+"        Fiyat : "+urunFiyat.getString("fiyat")+" TL");
            magazaFiyat.setBounds(15,25+j,500,20);
            magazaFiyat.setFont(new Font(null,Font.BOLD,14));
            magazaFiyat.setVisible(true);

            SatinAlButton satinAl = new SatinAlButton(Integer.valueOf(urunFiyat.getString("id")),"Satın Al");
            satinAl.setBounds(400,25+j,100,20);

            fiyatPanel.add(magazaFiyat);
            fiyatPanel.add(satinAl);
            j+=30;
        }


        yorumPanel = new JPanel();
        yorumPanel.setLayout(new BoxLayout(yorumPanel,BoxLayout.Y_AXIS));
        yorumPanel.setBounds(10,485,645,200);
        yorumPanel.setVisible(true);
        Border borderYorum = BorderFactory.createTitledBorder("Ürün Yorumları");
        yorumPanel.setBorder(borderYorum);


        yorumScroll = new JScrollPane(yorumPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        yorumScroll.setBounds(10,485,645,200);
        yorumScroll.setPreferredSize(new Dimension(645, 200));
        mainPanel.add(yorumScroll);

        yorumYapButon= new JButton("Yorum Yap");
        yorumYapButon.setBounds(545,460,100,20);
        yorumYapButon.setVisible(true);
        final Integer urunIdFinal = urunId;
        yorumYapButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yorumEkle yorumEkle = new yorumEkle(urunIdFinal);
                yorumEkle.setVisible(true);
                setVisible(false);
            }
        });
        add(yorumYapButon);

        ResultSet urunYorum= db.urunYorumListesiGetir(urunId);

        int i=0;
        while(urunYorum.next()){
            JLabel adSoyad = new JLabel(urunYorum.getString("adSoyad") + "     Puan : "+urunYorum.getString("puan"));
            adSoyad.setBounds(15,15+i,450,20);
            adSoyad.setFont(new Font(null,Font.BOLD,14));
            adSoyad.setVisible(true);

            JLabel yorum = new JLabel(urunYorum.getString("yorum"));
            yorum.setBounds(15,40+i,450,20);
            yorum.setFont(new Font(null,Font.PLAIN,12));
            yorum.setVisible(true);

            JLabel ayirici = new JLabel("----------------------------------------------------------------------------------------------------------------");
            ayirici.setBounds(15,50+i,450,20);
            ayirici.setFont(new Font(null,Font.PLAIN,12));
            ayirici.setVisible(true);

            yorumPanel.add(adSoyad);
            yorumPanel.add(yorum);
            yorumPanel.add(ayirici);
            yorumScroll.setViewportView(yorumPanel);
            i+=53;
        }

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    urunListelemeForm listeForm = new urunListelemeForm();
                    listeForm.setVisible(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        mainPanel.add(fotoPanel);
        mainPanel.add(detayPanel);
        mainPanel.add(fiyatPanel);
        mainPanel.add(yorumYapButon);

        setContentPane(mainPanel);
    }


}
