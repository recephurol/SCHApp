package urun.kullanici;


        import dataAccess.PostgreSQLDbConnection;
        import enums.EnumKullaniciTipi;
        import model.Kullanici;
        import urun.urunListeleme.urunListelemeForm;

        import javax.swing.*;
        import javax.swing.border.Border;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.io.IOException;
        import java.sql.SQLException;

public class kullaniciEkle extends JFrame {
    private JLabel kullaniciAdiLabel;
    private JTextField kullaniciAdiText;

    private JLabel sifreLabel;
    private JTextField sifreText;

    private JPanel kullaniciBilgileriPanel;

    private JLabel kullaniciTuruLabel;
    private JComboBox kullaniciTuruCombobox;

    private JButton kaydetButon;

    public kullaniciEkle() throws SQLException {

        initialKullaniciEkle();

    }

    private void initialKullaniciEkle() throws SQLException {
        setTitle("Kullanici Ekleme İşlemleri");
        setBounds(400, 400, 310, 220);
        setLayout(null);

        getKullaniciBilgileriPanel();
        getkullaniciAdi();
        getKullaniciSifre();
        getKullaniciTipi();
        getKaydetButon();

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    urunListelemeForm listeForm = new urunListelemeForm();
                    listeForm.setVisible(true);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void getKullaniciBilgileriPanel() {
        kullaniciBilgileriPanel = new JPanel();
        kullaniciBilgileriPanel.setLayout(null);
        kullaniciBilgileriPanel.setBounds(5, 5, 290, 170);
        kullaniciBilgileriPanel.setVisible(true);
        Border borderFoto = BorderFactory.createTitledBorder("Kullanici Bilgileri");
        kullaniciBilgileriPanel.setBorder(borderFoto);
        add(kullaniciBilgileriPanel);
    }


    private void getkullaniciAdi() {
        kullaniciAdiLabel = new JLabel("Adı: ");
        kullaniciAdiLabel.setBounds(10, 25, 150, 20);
        kullaniciAdiLabel.setFont(new Font(null, 1, 14));
        kullaniciAdiLabel.setVisible(true);

        kullaniciAdiText = new JTextField();
        kullaniciAdiText.setBounds(100, 25, 150, 20);
        kullaniciAdiText.setVisible(true);
        kullaniciBilgileriPanel.add(kullaniciAdiLabel);
        kullaniciBilgileriPanel.add(kullaniciAdiText);
    }

    private void getKullaniciSifre() {
        sifreLabel = new JLabel("Sifre: ");
        sifreLabel.setBounds(10, 50, 150, 20);
        sifreLabel.setFont(new Font(null, 1, 14));
        sifreLabel.setVisible(true);

        sifreText = new JTextField();
        sifreText.setBounds(100, 50, 150, 20);
        sifreText.setVisible(true);
        kullaniciBilgileriPanel.add(sifreLabel);
        kullaniciBilgileriPanel.add(sifreText);
    }

    private void getKullaniciTipi() throws SQLException {

        kullaniciTuruLabel = new JLabel("Kullanici Türü: ");
        kullaniciTuruLabel.setBounds(10, 90, 250, 20);
        kullaniciTuruLabel.setFont(new Font(null, 1, 12));
        //kullaniciTuruLabel.setFont(null,1,14);
        kullaniciTuruLabel.setVisible(true);

        kullaniciTuruCombobox = new JComboBox();
        kullaniciTuruCombobox.setBounds(100, 90, 150, 20);
        kullaniciTuruCombobox.setFont(new Font(null, 1, 14));
        kullaniciTuruCombobox.setVisible(true);
        kullaniciTuruCombobox.addItem("Bireysel");
        kullaniciTuruCombobox.addItem("Mağaza");
        kullaniciTuruCombobox.addItem("Admin");

        kullaniciBilgileriPanel.add(kullaniciTuruLabel);
        kullaniciBilgileriPanel.add(kullaniciTuruCombobox);

    }

    private void getKaydetButon() {
        kaydetButon = new JButton("Kayit Ol");
        kaydetButon.setBounds(85, 140, 100, 20);
        kaydetButon.setVisible(true);
        kaydetButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PostgreSQLDbConnection db = null;
                try {
                    db = new PostgreSQLDbConnection();
                    db.baglan();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                String kullaniciTipi = (String) kullaniciTuruCombobox.getSelectedItem();
                try {
                    enums.EnumKullaniciTipi tip = null;
                    if (kullaniciTipi.equals("Bireysel") ) {
                        tip=enums.EnumKullaniciTipi.MUSTERI;
                    } else if (kullaniciTipi.equals("Mağaza")) {
                        tip= EnumKullaniciTipi.MAGAZA;
                    } else if (kullaniciTipi.equals("Admin")) {
                        tip= EnumKullaniciTipi.ADMIN;
                    }

                    db.kullaniciEkle(
                            new Kullanici(
                                    kullaniciAdiText.getText(),
                                    sifreText.getText(),
                                    tip
                            )
                    );
                    JOptionPane.showMessageDialog(null,"Kullanici basarili bir sekilde eklendi");
                    kullaniciAdiText.setText(" ");
                    sifreText.setText(" ");


                } catch (Exception ex) {

                }

            }
        });
        kullaniciBilgileriPanel.add(kaydetButon);

    }

}