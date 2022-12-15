package enums;

public enum EnumKullaniciTipi {
    MUSTERI("Müşteri"),
    MAZAGA("Mağaza");

    private final String dsc;

    EnumKullaniciTipi(String dsc) {
        this.dsc = dsc;
    }

    public String getLabel() {
        return this.dsc;
    }

    public String getValue() {
        return name();
    }
}
