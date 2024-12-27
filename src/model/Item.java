package model;

public class Item {

    private Integer id;
    private String aciklama;

    public Item(Integer id, String aciklama) {
        this.id = id;
        this.aciklama = aciklama;
    }

    public Integer getId() {
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