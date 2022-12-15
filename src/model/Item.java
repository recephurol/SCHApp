package model;

public class Item {

    private int id;
    private String aciklama;

    public Item(int id, String aciklama) {
        this.id = id;
        this.aciklama = aciklama;
    }

    public int getId() {
        return id;
    }

    public String getAciklama() {
        return aciklama;
    }

    @Override
    public String toString() {
        return aciklama;
    }
}