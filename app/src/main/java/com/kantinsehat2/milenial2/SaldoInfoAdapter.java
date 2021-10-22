package com.kantinsehat2.milenial2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SaldoInfoAdapter extends RecyclerView.Adapter<SaldoInfoAdapter.ViewHolder> {
    private Context context;
    private List<users> usersList;
    public SaldoInfoAdapter(Context context, List<users> usersList){
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saldo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*loadDataDulu*/
        users userss = usersList.get(position);
        holder.tvBuatNis.setText(userss.getNis());
        holder.tvNama.setText(userss.getNama());
        holder.tvSaldo.setText(userss.getSaldo());
        holder.tvKelas.setText(userss.getKelas());
        if(userss.getTipe().equals("siswa")){
            holder.tvLimit.setText(userss.getLimit_pembelian());
        }

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvSaldo, tvNama,tvKelas,tvBuatNis,tvLimit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSaldo = itemView.findViewById(R.id.buatSaldo);
            tvNama = itemView.findViewById(R.id.buatNama);
            tvKelas = itemView.findViewById(R.id.buatKelas);
            tvLimit = itemView.findViewById(R.id.buatLimit);
            tvBuatNis = itemView.findViewById(R.id.buatNis);
        }
    }
}
