package com.kantinsehat2.milenial2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class MenuPager extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MenuAdapter menuAdapter;
    ExtendedFloatingActionButton fabPesananSaya;
    MakananAdapter adapterMakanan;
    private DataHelper mydb=null;
    private ArrayList idList;
    private ArrayList totalsList;
    private int totalHargaBlmDikali=0;
    MakananFragment mkn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pager);
        mkn = new MakananFragment();
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager2);
        FragmentManager fm = getSupportFragmentManager();
        menuAdapter = new MenuAdapter(fm, getLifecycle());
        viewPager2.setAdapter(menuAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("Makanan"));
        tabLayout.addTab(tabLayout.newTab().setText("Minuman"));
        tabLayout.addTab(tabLayout.newTab().setText("Jajanan"));
        fabPesananSaya = findViewById(R.id.pesananSaya);
        idList= new ArrayList<>();
        totalsList = new ArrayList<>();
        getData();
        String countKeranjang = String.valueOf(totalsList.size());
        String countHarga = String.valueOf(totalHargaBlmDikali);
        String buttonText = "Keranjang " + countKeranjang + " Pesanan " +"Rp. "+countHarga;
        fabPesananSaya.setText(buttonText);
        fabPesananSaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPager.this, KeranjangPesanan.class);
                startActivity(intent);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteData();
        Intent intent = new Intent(MenuPager.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void deleteData(){
        mydb = new DataHelper(this);
        Boolean res = mydb.deleteData();
        if(res = true){
            Toast.makeText(MenuPager.this, "Berhasil delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MenuPager.this, "Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    private void getData(){
        mydb = new DataHelper(this);
        Cursor res = mydb.getAllData();
        res.moveToFirst();
        ArrayList<Integer> dd = new ArrayList<>();
        for(int i=0; i<res.getCount(); i++){
            res.moveToPosition(i);
            idList.add(res.getString(0));
            totalsList.add(res.getString(6));
            dd.add(res.getInt(6));
        }

        int [] cat = new int[totalsList.size()];
        int d=0;
        for(int i=0; i<cat.length; i++){
            cat[i] = dd.get(i);
            d+=cat[i];
        }
        totalHargaBlmDikali = d;
    }

}