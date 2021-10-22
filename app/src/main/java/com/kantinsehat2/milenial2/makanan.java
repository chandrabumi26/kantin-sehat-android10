package com.kantinsehat2.milenial2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class makanan {

    @Expose
    @SerializedName("id") private int idMakanan;
    @Expose
    @SerializedName("nama_makanan") private String nama_makanan;
    @Expose
    @SerializedName("harga_makanan") private String harga_makanan;
    @Expose
    @SerializedName("gambar") private String gambar;
    @Expose
    @SerializedName("deskripsi_makanan") private String deskripsi_makanan;
    @Expose
    @SerializedName("status_makanan") private String status_makanan;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public int getIdMakanan() {
        return idMakanan;
    }

    public void setIdMakanan(int idMakanan) {
        this.idMakanan = idMakanan;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getHarga_makanan() {
        return harga_makanan;
    }

    public void setHarga_makanan(String harga_makanan) {
        this.harga_makanan = harga_makanan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
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

    public String getStatus_makanan() {
        return status_makanan;
    }

    public void setStatus_makanan(String status_makanan) {
        this.status_makanan = status_makanan;
    }

    public String getDeskripsi_makanan() {
        return deskripsi_makanan;
    }

    public void setDeskripsi_makanan(String deskripsi_makanan) {
        this.deskripsi_makanan = deskripsi_makanan;
    }
}
