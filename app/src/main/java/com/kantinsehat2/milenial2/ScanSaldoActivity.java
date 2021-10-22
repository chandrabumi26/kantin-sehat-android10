package com.kantinsehat2.milenial2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ScanSaldoActivity extends AppCompatActivity {
    TextView tvTextQr;
    CodeScanner cdCodeScanner;
    CodeScannerView csScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_saldo);
//        tvTextQr = findViewById(R.id.textQr);
        csScannerView = findViewById(R.id.scannerView);
        cdCodeScanner = new CodeScanner(this, csScannerView);
        cdCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent nis = new Intent(ScanSaldoActivity.this, SaldoInfo.class);
                        nis.putExtra("nis", result.getText().toString());
                        startActivity(nis);
                    }
                });
            }
        });
        csScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cdCodeScanner.startPreview();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cdCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        cdCodeScanner.releaseResources();
        super.onPause();
    }

}