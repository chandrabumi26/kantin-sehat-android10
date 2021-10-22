package com.kantinsehat2.milenial2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class keranjang implements java.io.Serializable{
    @Expose
    @SerializedName("id_item") private int id_item;
    @Expose
    @SerializedName("id_keranjang") private int id_keranjang;
    @Expose
    @SerializedName("nama") private String nama;
    @Expose
    @SerializedName("harga") private int harga;
    @Expose
    @SerializedName("jumlah") private int jumlah;
    @Expose
    @SerializedName("notes") private String notes;
    @Expose
    @SerializedName("total_harga") private int total_harga;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;



    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public int getId_keranjang() {
        return id_keranjang;
    }

    public void setId_keranjang(int id_keranjang) {
        this.id_keranjang = id_keranjang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
