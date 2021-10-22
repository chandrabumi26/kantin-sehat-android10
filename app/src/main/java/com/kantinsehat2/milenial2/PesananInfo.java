package com.kantinsehat2.milenial2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananInfo extends AppCompatActivity {
    private RecyclerView pesananInfoRv;
    private PesananInfoAdapter pesananInfoAdapter;
    private ProgressDialog progressDialog;
    private TextView tvNamaPemesan,tvKelasPemesan,tvTotalPembayaran,tvLimitPembelian,tvNis,tvSaldo;
    private Button btProses,btTanggal;
    private List<users> users = new ArrayList<>();
    private List<transaksi> transaksis = new ArrayList<>();
    private ApiInterface apiInterface;
    private String buatNis, tipe;
    private List<keranjang> keranjangs = new ArrayList<>();
    private DatePickerDialog.OnDateSetListener setListener;
    private int saldo_customer;
    private DataHelper mydb=null;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_info);
        progressDialog = new ProgressDialog(PesananInfo.this);
        progressDialog.setMessage("Tunggu...");
        keranjangs = (ArrayList<keranjang>) getIntent().getSerializableExtra("arraylist");
        tvNamaPemesan = findViewById(R.id.namaPemesan);
        tvKelasPemesan = findViewById(R.id.kelasPemesan);
        tvNis = findViewById(R.id.nisPemesan);
        tvTotalPembayaran = findViewById(R.id.totalPembayaran);
        tvLimitPembelian = findViewById(R.id.pesananInfoLimit);
        btProses = findViewById(R.id.buttonProses);
        btTanggal = findViewById(R.id.buttonTanggal);
        tvSaldo = findViewById(R.id.pesananInfoSaldo);
        /*Tanggal*/
        Calendar kalender = Calendar.getInstance();
        final int year = kalender.get(Calendar.YEAR);
        final int month = kalender.get(Calendar.MONTH);
        final int day = kalender.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
        btTanggal.setText(date);

        btTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PesananInfo.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        setListener,year,month,day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day +"/" +month+ "/"+year;
                btTanggal.setText(date);
            }
        };

        /*/Tanggal*/
        int hargaItem =0;
        int [] item = new int [keranjangs.size()];
        for(int i=0; i<keranjangs.size(); i++){
            item [i] = keranjangs.get(i).getTotal_harga();
            hargaItem += item[i];
        }
        tvTotalPembayaran.setText(String.valueOf(hargaItem));
        buatNis = (String) getIntent().getStringExtra("nis");
        pesananInfoRv = findViewById(R.id.rvPesananInfo);
        pesananInfoAdapter = new PesananInfoAdapter(PesananInfo.this, keranjangs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PesananInfo.this);
        pesananInfoRv.setLayoutManager(mLayoutManager);
        pesananInfoRv.setAdapter(pesananInfoAdapter);
        loadData();

        /*Sebelum dimasukin*/
        for(int i=0; i<keranjangs.size(); i++){
            transaksi trn = new transaksi();
            trn.setNis_transaksi(buatNis);
            trn.setNama_customer(tvNamaPemesan.getText().toString());
            trn.setKelas_customer(tvKelasPemesan.getText().toString());
            trn.setPesanan_customer(keranjangs.get(i).getNama());
            trn.setNote_pesanan(keranjangs.get(i).getNotes());
            trn.setJumlah_pesanan(keranjangs.get(i).getJumlah());
            trn.setTotal_harga(keranjangs.get(i).getTotal_harga());
            transaksis.add(i, trn);
        }
        /*/Sebelum dimasukin*/
        btProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(PesananInfo.this, tipe, Toast.LENGTH_SHORT).show();
                String tanggal = btTanggal.getText().toString();
                String nama_customer = tvNamaPemesan.getText().toString();
                String kelas_customer = tvKelasPemesan.getText().toString();
                int total_pembayaran = Integer.parseInt(tvTotalPembayaran.getText().toString());
                int limit_user = Integer.parseInt(tvLimitPembelian.getText().toString());
                if(tipe.equals("siswa")){
                    if(saldo_customer<total_pembayaran){
                        Toast.makeText(PesananInfo.this, "Saldo anda kurang, harap top up terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }else if(limit_user<total_pembayaran){
                        Toast.makeText(PesananInfo.this, "Limit anda kurang dari total pembayaran", Toast.LENGTH_SHORT).show();
                    }else{
                        int limit_kurangin = limit_user-total_pembayaran;
                        int saldo_dikurangin = saldo_customer-total_pembayaran;
                        for(int i=0; i<transaksis.size(); i++){
                            String nis_transaksi = transaksis.get(i).getNis_transaksi();
                            String pesanan_customer = transaksis.get(i).getPesanan_customer();
                            String note_pesanan = transaksis.get(i).getNote_pesanan();
                            int jumlah_pesanan = transaksis.get(i).getJumlah_pesanan();
                            int total_harga = transaksis.get(i).getTotal_harga();
                            savepesanan(nis_transaksi,nama_customer,kelas_customer,pesanan_customer,note_pesanan,jumlah_pesanan,total_harga,saldo_dikurangin,tanggal,limit_kurangin);
                        }
                        deleteData();
                        Intent intent = new Intent(PesananInfo.this, Ending.class);
                        startActivity(intent);
                        finish();

                    }
                }else{
                    if(saldo_customer<total_pembayaran){
                        Toast.makeText(PesananInfo.this, "Saldo anda kurang, harap top up terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }else{
                        int limit_kurangin = limit_user-total_pembayaran;
                        int saldo_dikurangin = saldo_customer-total_pembayaran;
                        for(int i=0; i<transaksis.size(); i++){
                            String nis_transaksi = transaksis.get(i).getNis_transaksi();
                            String pesanan_customer = transaksis.get(i).getPesanan_customer();
                            String note_pesanan = transaksis.get(i).getNote_pesanan();
                            int jumlah_pesanan = transaksis.get(i).getJumlah_pesanan();
                            int total_harga = transaksis.get(i).getTotal_harga();
                            savepesanan(nis_transaksi,nama_customer,kelas_customer,pesanan_customer,note_pesanan,jumlah_pesanan,total_harga,saldo_dikurangin,tanggal,limit_kurangin);
                        }
                        deleteData();
                        Intent intent = new Intent(PesananInfo.this, Ending.class);
                        startActivity(intent);
                        finish();

                    }
                }



            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PesananInfo.this, MenuPager.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    private void deleteData(){
        mydb = new DataHelper(this);
        Boolean res = mydb.deleteData();
        if(res = true){
//            Toast.makeText(PesananInfo.this, "Pesanan akan segera diantar terima kasih!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(PesananInfo.this, "Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData(){
        progressDialog.show();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Value> call = apiInterface.view();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                progressDialog.dismiss();
                String value = response.body().getValue();
                if(value.equals("1")){
                    users = response.body().getUsers();
                    String [] id = new String[users.size()];
                    for(int i = 0; i<users.size(); i++){
                        id[i] = users.get(i).getNis();
                        if(id[i].equals(buatNis)){
                            tvNamaPemesan.setText(users.get(i).getNama());
                            tvKelasPemesan.setText(users.get(i).getKelas());
                            tvNis.setText(users.get(i).getNis());
                            saldo_customer = Integer.parseInt(users.get(i).getSaldo());
                            tvSaldo.setText(String.valueOf(users.get(i).getSaldo()));
                            tipe = users.get(i).getTipe();
                            if(tipe.equals("siswa")){
                                tvLimitPembelian.setText(users.get(i).getLimit_pembelian());
                            }
                        }

                    }

                }else{

                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                t.getMessage();
                Toast.makeText(PesananInfo.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savepesanan(final String nis_transaksi, final String nama_customer, final String kelas_customer, final String pesanan_customer, final String note_pesanan, final int jumlah_pesanan,final int total_harga, final int total_pembayaran, final String tanggal_pemesanan, final int limit_kurangin){
        progressDialog.show();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<transaksi> call = apiInterface.savePesanan(nis_transaksi,nama_customer,kelas_customer,pesanan_customer,note_pesanan,jumlah_pesanan,total_harga, total_pembayaran, tanggal_pemesanan, limit_kurangin);
        call.enqueue(new Callback<transaksi>() {
            @Override
            public void onResponse(Call<transaksi> call, Response<transaksi> response) {
                Boolean success = response.body().getSuccess();
                if(success){
//                    Toast.makeText(PesananInfo.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Throwable t;
                    Toast.makeText(PesananInfo.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<transaksi> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PesananInfo.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}