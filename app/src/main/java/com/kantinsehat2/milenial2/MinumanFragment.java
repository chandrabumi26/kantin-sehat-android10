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

public class MinumanFragment extends Fragment {
    private RecyclerView.LayoutManager mLayoutManager;
    private List<minuman> minuman = new ArrayList<>();
    private RecyclerView minumanRv;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private MinumanAdapter minumanAdapter;
    EditText searchViewMinuman;
    CharSequence searchMinuman="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_minuman,container,false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Tunggu...");
        searchViewMinuman = v.findViewById(R.id.searchbarMinuman);
        minumanRv = v.findViewById(R.id.rvMinuman);
        minumanAdapter = new MinumanAdapter(this.getContext(), minuman);
        if(width > 1500){
            mLayoutManager = new GridLayoutManager(this.getContext(), 3);
        }else{
            mLayoutManager = new LinearLayoutManager(this.getContext());
        }

        minumanRv.setLayoutManager(mLayoutManager);
        minumanRv.setAdapter(minumanAdapter);
        loadData();
        searchViewMinuman.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                minumanAdapter.getFilter().filter(charSequence);
                searchMinuman = charSequence;
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
        Call<Value> call = apiInterface.viewMinuman();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                progressDialog.dismiss();
                String value = response.body().getValue();
                if(value.equals("1")){
                    minuman = response.body().getMinuman();
                    String [] status = new String[minuman.size()];
                    List<minuman> p = new ArrayList<>();
                    for(int i=0; i<minuman.size(); i++){
                        status[i] = minuman.get(i).getStatus_minuman();
                        if(status[i].equals("on")){
                            p.add(minuman.get(i));
                            minumanAdapter = new MinumanAdapter(MinumanFragment.this.getContext(),p);
                            minumanRv.setAdapter(minumanAdapter);
                        }
                    }
                }else{
                    Toast.makeText(MinumanFragment.this.getContext(), "Not Equal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MinumanFragment.this.getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}