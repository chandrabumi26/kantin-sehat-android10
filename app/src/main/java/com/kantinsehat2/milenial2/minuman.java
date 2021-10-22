package com.kantinsehat2.milenial2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class minuman {

    @Expose
    @SerializedName("id_minuman") private int idMinuman;
    @Expose
    @SerializedName("nama_minuman") private String nama_minuman;
    @Expose
    @SerializedName("harga_minuman") private String harga_minuman;
    @Expose
    @SerializedName("gambar_minuman") private String gambar_minuman;
    @Expose
    @SerializedName("deskripsi_minuman") private String deskripsi_minuman;
    @Expose
    @SerializedName("status_minuman") private String status_minuman;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public int getIdMinuman() {
        return idMinuman;
    }

    public void setIdMinuman(int idMinuman) {
        this.idMinuman = idMinuman;
    }

    public String getNama_minuman() {
        return nama_minuman;
    }

    public void setNama_minuman(String nama_minuman) {
        this.nama_minuman = nama_minuman;
    }

    public String getHarga_minuman() {
        return harga_minuman;
    }

    public void setHarga_minuman(String harga_minuman) {
        this.harga_minuman = harga_minuman;
    }

    public String getGambar_minuman() {
        return gambar_minuman;
    }

    public void setGambar_minuman(String gambar_minuman) {
        this.gambar_minuman = gambar_minuman;
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

    public String getStatus_minuman() {
        return status_minuman;
    }

    public void setStatus_minuman(String status_minuman) {
        this.status_minuman = status_minuman;
    }

    public String getDeskripsi_minuman() {
        return deskripsi_minuman;
    }

    public void setDeskripsi_minuman(String deskripsi_minuman) {
        this.deskripsi_minuman = deskripsi_minuman;
    }
}
