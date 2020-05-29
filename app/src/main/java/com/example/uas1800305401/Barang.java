package com.example.uas1800305401;

public class Barang {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getLokasi_gedung() {
        return lokasi_gedung;
    }

    public void setLokasi_gedung(String lokasi_gedung) {
        this.lokasi_gedung = lokasi_gedung;
    }

    public String getLokasi_ruang() {
        return lokasi_ruang;
    }

    public void setLokasi_ruang(String lokasi_ruang) {
        this.lokasi_ruang = lokasi_ruang;
    }
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    private int id;
    private String id_barang;
    private String nama;
    private String jumlah;
    private String lokasi_gedung;
    private String lokasi_ruang;
    private byte[] image;

    public Barang(int id, String id_barang, String nama, String jumlah, String lokasi_gedung, String lokasi_ruang, byte[] image) {
        this.id = id;
        this.id_barang = id_barang;
        this.nama = nama;
        this.jumlah = jumlah;
        this.lokasi_gedung = lokasi_gedung;
        this.lokasi_ruang = lokasi_ruang;
        this.image=image;
    }


}
