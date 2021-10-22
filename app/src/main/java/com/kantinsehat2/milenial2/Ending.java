package com.kantinsehat2.milenial2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Ending extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Ending.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}