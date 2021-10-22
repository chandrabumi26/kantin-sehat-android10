package com.kantinsehat2.milenial2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class jajanan {
    @Expose
    @SerializedName("id_jajan") private int id_jajan;
    @Expose
    @SerializedName("nama_jajan") private String nama_jajan;
    @Expose
    @SerializedName("harga_jajan") private String harga_jajan;
    @Expose
    @SerializedName("gambar_jajan") private String gambar_jajan;
    @Expose
    @SerializedName("deskripsi_jajan") private String deskripsi_jajan;
    @Expose
    @SerializedName("status_jajan") private String status_jajan;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public int getId_jajan() {
        return id_jajan;
    }

    public void setId_jajan(int id_jajan) {
        this.id_jajan = id_jajan;
    }

    public String getNama_jajan() {
        return nama_jajan;
    }

    public void setNama_jajan(String nama_jajan) {
        this.nama_jajan = nama_jajan;
    }

    public String getHarga_jajan() {
        return harga_jajan;
    }

    public void setHarga_jajan(String harga_jajan) {
        this.harga_jajan = harga_jajan;
    }

    public String getGambar_jajan() {
        return gambar_jajan;
    }

    public void setGambar_jajan(String gambar_jajan) {
        this.gambar_jajan = gambar_jajan;
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

    public String getStatus_jajan() {
        return status_jajan;
    }

    public void setStatus_jajan(String status_jajan) {
        this.status_jajan = status_jajan;
    }

    public String getDeskripsi_jajan() {
        return deskripsi_jajan;
    }

    public void setDeskripsi_jajan(String deskripsi_jajan) {
        this.deskripsi_jajan = deskripsi_jajan;
    }
}
