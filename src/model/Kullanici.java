package model;

import enums.EnumKullaniciTipi;

public class Kullanici {
    private int id;
    private String kullaniciAdi;
    private String sifre;
    private EnumKullaniciTipi kullaniciTipi;

    public Kullanici(){

    }
    public Kullanici(String kullaniciAdi,String sifre,EnumKullaniciTipi kullaniciTipi){
        this.kullaniciAdi=kullaniciAdi;
        this.sifre=sifre;
        this.kullaniciTipi=kullaniciTipi;

    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public String getKullaniciAdi(){
        return kullaniciAdi;
    }
    public void setKullaniciAdi(String kullaniciAdi){
        kullaniciAdi = kullaniciAdi;
    }

    public String getSifre(){
        return sifre;
    }
    public void setSifre(String sifre){
        sifre = sifre;
    }

    public EnumKullaniciTipi getKullaniciTipi(){
        return kullaniciTipi;
    }
    public void setKullaniciTipi(EnumKullaniciTipi kullaniciTipi){
        kullaniciTipi = kullaniciTipi;
    }

}
