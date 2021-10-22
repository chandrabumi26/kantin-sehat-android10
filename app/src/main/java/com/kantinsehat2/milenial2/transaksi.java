package com.kantinsehat2.milenial2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class transaksi {
    @Expose
    @SerializedName("nis_transaksi") private String nis_transaksi;
    @Expose
    @SerializedName("nama_customer") private String nama_customer;
    @Expose
    @SerializedName("kelas_customer") private String kelas_customer;
    @Expose
    @SerializedName("pesanan_customer") private String pesanan_customer;
    @Expose
    @SerializedName("note_pesanan") private String note_pesanan;
    @Expose
    @SerializedName("jumlah_pesanan") private int jumlah_pesanan;
    @Expose
    @SerializedName("total_harga") private int total_harga;
    @Expose
    @SerializedName("total_pembayaran") private int total_pembayaran;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public String getNis_transaksi() {
        return nis_transaksi;
    }

    public void setNis_transaksi(String nis_transaksi) {
        this.nis_transaksi = nis_transaksi;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getKelas_customer() {
        return kelas_customer;
    }

    public void setKelas_customer(String kelas_customer) {
        this.kelas_customer = kelas_customer;
    }

    public String getPesanan_customer() {
        return pesanan_customer;
    }

    public void setPesanan_customer(String pesanan_customer) {
        this.pesanan_customer = pesanan_customer;
    }

    public String getNote_pesanan() {
        return note_pesanan;
    }

    public void setNote_pesanan(String note_pesanan) {
        this.note_pesanan = note_pesanan;
    }

    public int getJumlah_pesanan() {
        return jumlah_pesanan;
    }

    public void setJumlah_pesanan(int jumlah_pesanan) {
        this.jumlah_pesanan = jumlah_pesanan;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }


    public int getTotal_pembayaran() {
        return total_pembayaran;
    }

    public void setTotal_pembayaran(int total_pembayaran) {
        this.total_pembayaran = total_pembayaran;
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
