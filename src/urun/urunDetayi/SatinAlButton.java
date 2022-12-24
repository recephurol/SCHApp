package urun.urunDetayi;

import javax.swing.*;

public class SatinAlButton extends JButton {

    private Integer urunFiyatId;

    public SatinAlButton(Integer urunFiyatId, String title){
        super(title);
        urunFiyatId=urunFiyatId;
    }

    public Integer getUrunFiyatId(){
        return urunFiyatId;
    }
}
