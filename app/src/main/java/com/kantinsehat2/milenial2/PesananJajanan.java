package com.kantinsehat2.milenial2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananJajanan extends AppCompatActivity {
    private CircularProgressDrawable circularProgressDrawable;
    private TextView tvNamaPesananJajanan, tvHargaPesananJajanan, tvDeskripsiJajanan;
    private EditText etNotesJajanan;
    private Button btTambahJajanan;
    private ImageView img;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    String namaJajanan, hargaJajanan, gambarJajanan, abc, deskripsi;
    private List<jajanan> jajanan = new ArrayList<>();
    private DataHelper dt = null;
    ElegantNumberButton enbJajanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        if(width > 1500){
            setContentView(R.layout.activity_pesanan_jajanan);
        }else{
            setContentView(R.layout.activity_pesanan_jajanan_phone);
        }
        tvNamaPesananJajanan = findViewById(R.id.namaPesananJajan);
        tvHargaPesananJajanan = findViewById(R.id.hargaPesananJajanan);
        tvDeskripsiJajanan = findViewById(R.id.jajananDeskripsi);
        img = findViewById(R.id.gambarPesananJajanan);
        etNotesJajanan = findViewById(R.id.notesJajanan);
        btTambahJajanan = findViewById(R.id.tambahBtJajanan);
        enbJajanan = findViewById(R.id.btEleganJajanan);
        abc = getIntent().getStringExtra("id_jajanan");

        progressDialog = new ProgressDialog(PesananJajanan.this);
        progressDialog.setMessage("Tunggu...");
        circularProgressDrawable = new CircularProgressDrawable(PesananJajanan.this);
        circularProgressDrawable.setCenterRadius(10f);
        circularProgressDrawable.start();
        loadData(abc);
        enbJajanan.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                String count = enbJajanan.getNumber();
                int hharga = Integer.parseInt(hargaJajanan);
                int qty = Integer.parseInt(count);
                String fixHarga = String.valueOf(hharga*qty);
                tvHargaPesananJajanan.setText(fixHarga);
            }
        });
        btTambahJajanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });

    }

    private void addData(){
        dt = new DataHelper(PesananJajanan.this);
        int idDb = Integer.parseInt(abc);
        String ccount = enbJajanan.getNumber();
        int qty = Integer.parseInt(ccount);
        int hharga = Integer.parseInt(hargaJajanan);
        String notes = etNotesJajanan.getText().toString();
        String tots = tvHargaPesananJajanan.getText().toString();
        int totals = Integer.parseInt(tots);
        boolean isInserted = dt.insertData(idDb,namaJajanan,qty,notes,hharga,gambarJajanan,totals);
        if(isInserted = true){
            Toast.makeText(PesananJajanan.this, "Terima kasih sudah memilih menus pesanan "+namaJajanan, Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this,MenuPager.class);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(PesananJajanan.this, "Gagal ditambahkan", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData(String a){
        progressDialog.show();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Value> call = apiInterface.viewJajanan();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                progressDialog.dismiss();
                String value = response.body().getValue();
                if(value.equals("1")){
                    jajanan = response.body().getJajanan();
                    String [] id = new String[jajanan.size()];
                    for(int i=0; i<jajanan.size(); i++){
                        id[i] = String.valueOf(jajanan.get(i).getId_jajan());
                        if(id[i].equals(a)){
                            namaJajanan = jajanan.get(i).getNama_jajan();
                            hargaJajanan = jajanan.get(i).getHarga_jajan();
                            gambarJajanan = jajanan.get(i).getGambar_jajan();
                            deskripsi = jajanan.get(i).getDeskripsi_jajan();
                        }
                    }
                    tvNamaPesananJajanan.setText(namaJajanan);
                    tvHargaPesananJajanan.setText(hargaJajanan);
                    tvDeskripsiJajanan.setText(deskripsi);
                    Glide.with(PesananJajanan.this).load("https://bikinlanding.com/kantinsehat/upload/"+gambarJajanan).apply(new RequestOptions().placeholder(circularProgressDrawable)).into(img);

                }else{
                    Toast.makeText(PesananJajanan.this, "Not equals 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PesananJajanan.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}