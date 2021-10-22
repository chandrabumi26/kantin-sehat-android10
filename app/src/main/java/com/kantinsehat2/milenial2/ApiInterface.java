package com.kantinsehat2.milenial2;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("getUsers.php")
    Call<Value> view();
    @GET("getMakanan.php")
    Call<Value> viewMakanan();
    @GET("getMinuman.php")
    Call<Value> viewMinuman();
    @GET("getJajanan.php")
    Call<Value> viewJajanan();

    @FormUrlEncoded
    @POST("pesansave.php")
    Call<transaksi> savePesanan(@Field("nis_transaksi") String nis_transaksi,
                                @Field("nama_customer") String nama_customer,
                                @Field("kelas_customer") String kelas_customer,
                                @Field("pesanan_customer") String nama_pesanan,
                                @Field("note_pesanan") String note_pesanan,
                                @Field("jumlah_pesanan") int jumlah_pesanan,
                                @Field("total_harga") int total_harga,
                                @Field("update_saldo") int total_pembayaran,
                                @Field("tanggal_pemesanan") String tanggal_pemesanan,
                                @Field("update_limit") int limit_kurangin);

}
