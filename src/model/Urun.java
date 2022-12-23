package model;

public class Urun {

    private Double _fiyat;
    private String _adi;
    private String _aciklama;
    private Integer _kategoriId;
    private Integer _markaId;
    private Integer _renkId;
    private Integer _magazaId;
    private String _fotograf;
    private Integer _stok;


    public Urun(Double fiyat, String adi, String aciklama, Integer kategoriId, Integer markaId, Integer renkId, Integer magazaId,Integer stok, String fotograf){
        _fiyat = fiyat;
        _adi = adi;
        _aciklama=aciklama;
        _kategoriId = kategoriId;
        _markaId = markaId;
        _renkId=renkId;
        _magazaId = magazaId;
        _stok = stok;
        _fotograf = fotograf;
    }

    public Urun(){

    }

    public Integer get_stok() {
        return _stok;
    }

    public void set_stok(Integer _stok) {
        this._stok = _stok;
    }

    public Double get_fiyat() {
        return _fiyat;
    }

    public void set_fiyat(Double _fiyat) {
        this._fiyat = _fiyat;
    }

    public String get_adi() {
        return _adi;
    }

    public void set_adi(String _adi) {
        this._adi = _adi;
    }

    public String get_aciklama() {
        return _aciklama;
    }

    public void set_aciklama(String _aciklama) {
        this._aciklama = _aciklama;
    }

    public Integer get_kategoriId() {
        return _kategoriId;
    }

    public void set_kategoriId(Integer _kategoriId) {
        this._kategoriId = _kategoriId;
    }

    public Integer get_markaId() {
        return _markaId;
    }

    public void set_markaId(Integer _markaId) {
        this._markaId = _markaId;
    }

    public Integer get_renkId() {
        return _renkId;
    }

    public void set_renkId(Integer _renkId) {
        this._renkId = _renkId;
    }

    public Integer get_magazaId() {
        return _magazaId;
    }

    public void set_magazaId(Integer _magazaId) {
        this._magazaId = _magazaId;
    }

    public String get_fotograf() {
        return _fotograf;
    }

    public void set_fotograf(String _fotograf) {
        this._fotograf = _fotograf;
    }
}
