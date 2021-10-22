package com.kantinsehat2.milenial2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaldoInfo extends AppCompatActivity {
    private TextView tvDisini;
    private Button pesanBt;
    private String buatNis;
    private List<users> users = new ArrayList<>();
    private SaldoInfoAdapter saldoInfoAdapter;
    private RecyclerView saldoRv;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo_info);
        Intent coba = getIntent();
        Bundle bb = coba.getExtras();
        buatNis = (String) bb.get("nis");
        pesanBt = findViewById(R.id.pesanBUtton);
        progressDialog = new ProgressDialog(SaldoInfo.this);
        progressDialog.setMessage("Tunggu...");
        saldoRv = findViewById(R.id.rvSaldo);
        saldoInfoAdapter = new SaldoInfoAdapter(SaldoInfo.this,users);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SaldoInfo.this);
        saldoRv.setLayoutManager(mLayoutManager);
        saldoRv.setAdapter(saldoInfoAdapter);
        loadData();

        pesanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SaldoInfo.this, MenuPager.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
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
                    List<users> p = new ArrayList<>();

                    for(int i = 0; i<users.size(); i++){
                        id[i] = users.get(i).getNis();

                        if(id[i].equals(buatNis)){
                            p.add(users.get(i));
                            saldoInfoAdapter = new SaldoInfoAdapter(SaldoInfo.this,p);
                            saldoRv.setAdapter(saldoInfoAdapter);
                        }

                    }

                }else{

                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                t.getMessage();
                Toast.makeText(SaldoInfo.this, "disini", Toast.LENGTH_SHORT).show();
            }
        });
    }
}