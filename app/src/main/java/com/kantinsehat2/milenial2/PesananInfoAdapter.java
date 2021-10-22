package com.kantinsehat2.milenial2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PesananInfoAdapter extends RecyclerView.Adapter<PesananInfoAdapter.ViewHolder> {
    private Context context;
    private List<keranjang> keranjangList;

    public PesananInfoAdapter(Context context, List<keranjang> keranjangList){
        this.context = context;
        this.keranjangList = keranjangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pesanan_info_item,parent, false);
        return new PesananInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        keranjang keranjangs = keranjangList.get(position);
        holder.tvNama.setText(keranjangs.getNama());
        holder.tvJumlah.setText(String.valueOf(keranjangs.getJumlah()));
        holder.tvHarga.setText(String.valueOf(keranjangs.getTotal_harga()));
        holder.tvNotes.setText(keranjangs.getNotes());
    }

    @Override
    public int getItemCount() {
        return keranjangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvJumlah, tvHarga,tvNotes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.pesananInfoNama);
            tvJumlah = itemView.findViewById(R.id.pesananInfoJumlah);
            tvHarga = itemView.findViewById(R.id.pesananInfoTotalHarga);
            tvNotes = itemView.findViewById(R.id.pesananInfoNotes);
        }
    }
}
