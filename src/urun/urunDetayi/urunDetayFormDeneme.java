package urun.urunDetayi;

import dataAccess.PostgreSQLDbConnection;
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
import java.text.DecimalFormat;

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
        setBounds(500,200,690,750);
        setResizable(false);

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
            setTitle(urunDetay.getString("urunAdi")+ " Ürün Detayı");
            String fotoUrl = urunDetay.getString("fotograf");
            if(fotoUrl!=null && fotoUrl!=""){
                try{
                    BufferedImage img = ImageIO.read(new File(fotoUrl));
                    Image scaledImage = img.getScaledInstance(fotoPanel.getWidth()-25,fotoPanel.getHeight()-25,BufferedImage.SCALE_DEFAULT);
                    ImageIcon icon = new ImageIcon(scaledImage);
                    JLabel imgLabel = new JLabel(icon);
                    imgLabel.setVisible(true);
                    imgLabel.setBounds(0,0,150,200);
                    fotoPanel.add(imgLabel);
                }catch (Exception e){

                }
            }

            urunAdiLabel = new JLabel(urunDetay.getString("urunAdi"));
            urunAdiLabel.setFont(new Font(null,1,18));
            urunAdiLabel.setBounds(5,10,300,30);
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
            if(urunPuani.next()){
                ortalamaPuanLabel = new JLabel("Puan :  " + new DecimalFormat("##.##").format(urunPuani.getDouble("avg")));

                ortalamaPuanLabel.setBounds(320,10,150,30);
                ortalamaPuanLabel.setFont(new Font(null,0,16));
                ortalamaPuanLabel.setVisible(true);
                Double ortPuan = urunPuani.getDouble("avg");
                String yildizUrl ="";
                if(ortPuan<1.5){
                    yildizUrl = "src/images/bir.png";
                }else if(ortPuan<2.0){
                    yildizUrl = "src/images/birbucuk.png";
                }else if(ortPuan<2.5){
                    yildizUrl = "src/images/ikiyildiz.png";
                }else if(ortPuan<3.0){
                    yildizUrl = "src/images/ikibucukyildiz.png";
                }else if(ortPuan<3.5){
                    yildizUrl = "src/images/ucyildiz.png";
                }else if(ortPuan<4.0){
                    yildizUrl = "src/images/ucbucukyildiz.png";
                }else if(ortPuan<4.5){
                    yildizUrl = "src/images/dortyildiz.png";
                }else if(ortPuan<5.0){
                    yildizUrl = "src/images/dortbucuk.png";
                } else {
                    yildizUrl = "src/images/besyildiz.png";
                }

            BufferedImage img = ImageIO.read(new File(yildizUrl));
            ImageIcon icon = new ImageIcon(img);
            JLabel imgLabel = new JLabel(icon);
            imgLabel.setVisible(true);
            imgLabel.setBounds(270,30,162,30);
            detayPanel.add(imgLabel);
            detayPanel.add(ortalamaPuanLabel);
            }


            detayPanel.add(urunAdiLabel);
            detayPanel.add(markaLabel);
            detayPanel.add(kategoriLabel);
            detayPanel.add(renkLabel);
            detayPanel.add(aciklamaLabel);

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

            SatinAlButton satinAl = new SatinAlButton("satın al");
            satinAl.setUrunFiyatId(urunFiyat.getInt("id"));

            satinAl.setBounds(400,25+j,100,20);
            satinAl.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        satinAl.stokDus();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

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
            Integer puan = urunYorum.getInt("puan");
            JLabel adSoyad = new JLabel(urunYorum.getString("adSoyad"));
            adSoyad.setBounds(15,15+i,450,20);
            adSoyad.setFont(new Font(null,Font.BOLD,14));
            adSoyad.setVisible(true);

            JLabel yorum = new JLabel(urunYorum.getString("yorum"));
            yorum.setBounds(15,40+i,450,20);
            yorum.setFont(new Font(null,Font.PLAIN,12));
            yorum.setVisible(true);

            String yildizUrl ="";
            if(puan<2){
                yildizUrl = "src/images/bir.png";
            }else if(puan<3){
                yildizUrl = "src/images/ikiyildiz.png";
            }else if(puan<4){
                yildizUrl = "src/images/ucyildiz.png";
            }else if(puan<5){
                yildizUrl = "src/images/dortyildiz.png";
            } else {
                yildizUrl = "src/images/besyildiz.png";
            }

            BufferedImage img = ImageIO.read(new File(yildizUrl));
            ImageIcon icon = new ImageIcon(img);
            JLabel imgLabel = new JLabel(icon);
            imgLabel.setText("   Puan : "+puan);
            imgLabel.setVisible(true);
            imgLabel.setBounds(475,15+i,162,20);
            yorumPanel.add(imgLabel);

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
