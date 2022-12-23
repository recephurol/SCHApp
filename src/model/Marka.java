package model;

public class Marka {
    private int id ;
    private String adi;
    public Marka() {
    }
    public Marka (String adi){


        this.adi = adi;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }


}
