package model;


    public class Renk {

        private int  id ;
        private String adi;
        public Renk() {
        }
        public Renk (String adi){


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

