package com.kantinsehat2.milenial2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class users {

    @Expose
    @SerializedName("nis") private String nis;
    @Expose
    @SerializedName("nama") private String nama;
    @Expose
    @SerializedName("kelas") private String kelas;
    @Expose
    @SerializedName("saldo") private String saldo;
    @Expose
    @SerializedName("tipe") private String tipe;
    @Expose
    @SerializedName("limit_pembelian") private String limit_pembelian;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

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

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getLimit_pembelian() {
        return limit_pembelian;
    }

    public void setLimit_pembelian(String limit_pembelian) {
        this.limit_pembelian = limit_pembelian;
    }
}
