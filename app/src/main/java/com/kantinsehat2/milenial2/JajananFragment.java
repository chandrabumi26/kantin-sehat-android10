package com.kantinsehat2.milenial2;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JajananFragment extends Fragment {
    private RecyclerView.LayoutManager mLayoutManager;
    private List<jajanan> jajanan = new ArrayList<>();
    private RecyclerView jajananRv;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private JajananAdapter jajananAdapter;
    EditText searchViewJajanan;
    CharSequence searchJajanan="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jajanan, container, false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Tunggu...");
        searchViewJajanan = v.findViewById(R.id.searchbarJajanan);
        jajananRv = v.findViewById(R.id.rvJajanan);
        jajananAdapter = new JajananAdapter(this.getContext(), jajanan);
        if(width > 1500){
            mLayoutManager = new GridLayoutManager(this.getContext(), 3);
        }else{
            mLayoutManager = new LinearLayoutManager(this.getContext());
        }
        jajananRv.setLayoutManager(mLayoutManager);
        jajananRv.setAdapter(jajananAdapter);
        loadData();
        searchViewJajanan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                jajananAdapter.getFilter().filter(charSequence);
                searchJajanan = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return v;
    }

    private void loadData(){
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
                    String [] status = new String[jajanan.size()];
                    List<jajanan> p = new ArrayList<>();
                    for(int i=0; i<jajanan.size(); i++){
                        status[i] = jajanan.get(i).getStatus_jajan();
                        if(status[i].equals("on")){
                            p.add(jajanan.get(i));
                            jajananAdapter = new JajananAdapter(JajananFragment.this.getContext(), p);
                            jajananRv.setAdapter(jajananAdapter);
                        }
                    }
                }else{
                    Toast.makeText(JajananFragment.this.getContext(), "Not Equal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(JajananFragment.this.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}