package model;

public class Yorum {
    private Integer _urunId;
    private String _adSoyad;
    private Double _puan;
    private String _yorum;

    public Yorum(){

    }

    public Yorum(Integer urunId,String adSoyad,Double puan,String yorum){
        _urunId=urunId;
        _adSoyad=adSoyad;
        _puan = puan;
        _yorum = yorum;
    }

    public Integer getUrunId() {
        return _urunId;
    }

    public void setUrunId(Integer urunId) {
        this._urunId = urunId;
    }

    public String getAdSoyad() {
        return _adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this._adSoyad = adSoyad;
    }

    public Double getPuan() {
        return _puan;
    }

    public void setPuan(Double puan) {
        this._puan = puan;
    }

    public String getYorum() {
        return _yorum;
    }

    public void setYorum(String yorum) {
        this._yorum = yorum;
    }
}
