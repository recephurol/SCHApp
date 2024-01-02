package login.dto;

import enums.EnumKullaniciTipi;

public class KullaniciDto {
    private int id;
    private String kullaniciAdi;
    private String sifre;
    private EnumKullaniciTipi kullaniciTipi;
    public KullaniciDto(){

    }
    public KullaniciDto(String kullaniciAdi,String sifre,EnumKullaniciTipi kullaniciTipi){
        this.kullaniciAdi=kullaniciAdi;
        this.sifre=sifre;
        this.kullaniciTipi=kullaniciTipi;

    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

    public String getKullaniciAdi(){
        return this.kullaniciAdi;
    }
    public void setKullaniciAdi(String kullaniciAdi){
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre(){
        return sifre;
    }
    public void setSifre(String sifre){
        this.sifre = sifre;
    }

    public EnumKullaniciTipi getKullaniciTipi(){
        return this.kullaniciTipi;
    }
    public void setKullaniciTipi(EnumKullaniciTipi kullaniciTipi){
        this.kullaniciTipi = kullaniciTipi;
    }
}
