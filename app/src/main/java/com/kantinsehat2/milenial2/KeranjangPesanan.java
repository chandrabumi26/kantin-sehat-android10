package com.kantinsehat2.milenial2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KeranjangPesanan extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    private KeranjangAdapter keranjangAdapter;
    private RecyclerView rvKeranjang;
    private TextView tvTotal;
    private String abcd,peler;
    private int totalHargaBlmDikali;
    private DataHelper mydb=null;
    private Button prosesBt;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;

    private ArrayList idList;
    private ArrayList namaList;
    private ArrayList jumlahList;
    private ArrayList notesList;
    private ArrayList total_hargaList;
    private ArrayList gambarList;
    private ArrayList totalsList;
    private ArrayList forSize;
    private ArrayList idKeranjangList;
    private List<keranjang> katrin;

    @Override
    /*Airtable revisi *6*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        setContentView(R.layout.activity_keranjang_pesanan);
        rvKeranjang = findViewById(R.id.keranjangRv);
        tvTotal = findViewById(R.id.total);
        prosesBt = findViewById(R.id.btProses);
        idList = new ArrayList<>();
        namaList = new ArrayList<>();
        jumlahList = new ArrayList<>();
        notesList = new ArrayList<>();
        total_hargaList = new ArrayList<>();
        gambarList = new ArrayList<>();
        totalsList = new ArrayList<>();
        forSize = new ArrayList<>();
        idKeranjangList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu...");
        getData();
        if(width>1500){
            layoutManager = new GridLayoutManager(this,2);
        }else{
            layoutManager = new LinearLayoutManager(this);
        }
        rvKeranjang.setLayoutManager(layoutManager);
        keranjangAdapter = new KeranjangAdapter(idList,namaList,jumlahList,notesList,total_hargaList,gambarList,totalsList,forSize,idKeranjangList,this);
        rvKeranjang.setAdapter(keranjangAdapter);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        katrin = new ArrayList<>();

        prosesBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keranjangAdapter.gg(katrin);
                Intent intent = new Intent(KeranjangPesanan.this, ScanPesanan.class);
                intent.putExtra("arraylist", (Serializable) katrin);
                startActivity(intent);
            }
        });

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            peler = intent.getStringExtra("total");
            tvTotal.setText(peler);
        }
    };

    private void getData(){
        mydb = new DataHelper(this);
        Cursor res = mydb.getAllData();
        if(res.getCount() == 0){
            showMessage("Warning","Cart Empty");
            return;
        }
        ArrayList<Integer> dd = new ArrayList<Integer>();
        ArrayList<keranjang> abc = new ArrayList<>();
        res.moveToFirst();
        for (int i=0; i<res.getCount(); i++){
            res.moveToPosition(i);
            idList.add(res.getString(7));
            namaList.add(res.getString(1));
            jumlahList.add(res.getString(2));
            notesList.add(res.getString(3));
            total_hargaList.add(res.getString(4));
            gambarList.add(res.getString(5));
            totalsList.add(res.getString(6));
            forSize.add(res.getInt(6));
            dd.add(res.getInt(6));
            idKeranjangList.add(res.getString(0));
        }
        /*Jumlahin*/
        int [] cat = new int[dd.size()];
        int d = 0;
        for(int i =0; i<cat.length; i++){
            cat[i] = dd.get(i);
            d +=cat[i];
        }
        /*/Jumlahin */
        totalHargaBlmDikali = d;
        abcd =String.valueOf(totalHargaBlmDikali);
        tvTotal.setText(abcd);
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(KeranjangPesanan.this, MenuPager.class);
        startActivity(intent);
        finish();
    }
}