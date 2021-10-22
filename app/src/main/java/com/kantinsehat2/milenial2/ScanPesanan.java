package com.kantinsehat2.milenial2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScanPesanan extends AppCompatActivity {

    CodeScanner cdScannerPesanan;
    CodeScannerView cdScannerViewPesanan;
    private List<keranjang> keranjangs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_pesanan);
        cdScannerViewPesanan = findViewById(R.id.scannerPesanan);
        cdScannerPesanan = new CodeScanner(this, cdScannerViewPesanan);
        keranjangs = (ArrayList<keranjang>) getIntent().getSerializableExtra("arraylist");
        cdScannerPesanan.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent nis = new Intent(ScanPesanan.this, PesananInfo.class);
                        nis.putExtra("nis", result.getText().toString());
                        nis.putExtra("arraylist", (Serializable) keranjangs);
                        startActivity(nis);
                    }
                });
            }
        });

        cdScannerViewPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cdScannerPesanan.startPreview();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cdScannerPesanan.startPreview();
    }

    @Override
    protected void onPause() {
        cdScannerPesanan.releaseResources();
        super.onPause();
    }
}