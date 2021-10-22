package com.kantinsehat2.milenial2;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MinumanAdapter extends RecyclerView.Adapter<MinumanAdapter.ViewHolder> {
    private Context context;
    private List<minuman> minumanList;
    private List<minuman> filteredMinumanList;
    DisplayMetrics displayMetrics = new DisplayMetrics();

    public MinumanAdapter(Context context, List<minuman> minumanList){
        this.context = context;
        this.minumanList = minumanList;
        this.filteredMinumanList = minumanList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        View view = null;
        if(width > 1500){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pesan_makanan_item, parent, false);

        }else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pesan_makanan_item_phone, parent, false);
        }
        return new MinumanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        minuman minumans = filteredMinumanList.get(position);
        holder.tvNamaMinuman.setText(minumans.getNama_minuman());
        holder.tvHargaMinuman.setText(minumans.getHarga_minuman());
        holder.idMinuman = String.valueOf(minumans.getIdMinuman());
        Glide.with(holder.ivImage.getContext()).load("https://bikinlanding.com/kantinsehat/upload/"+minumans.getGambar_minuman()).apply(new RequestOptions().placeholder(holder.circularProgressDrawable)).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return filteredMinumanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircularProgressDrawable circularProgressDrawable;
        private TextView tvNamaMinuman, tvHargaMinuman,tvIdMinuman;
        private String idMinuman;
        private ImageView ivImage;
        private Button btBeli;
        private CardView cardViewMinuman;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdMinuman = itemView.findViewById(R.id.butIdMakanan);
            tvNamaMinuman = itemView.findViewById(R.id.buatNamaMakanan);
            tvHargaMinuman = itemView.findViewById(R.id.buatHargaMakanan);
            ivImage = itemView.findViewById(R.id.buatGambarMakanan);
            btBeli = itemView.findViewById(R.id.beliButton);

            circularProgressDrawable = new CircularProgressDrawable(itemView.getContext());
            circularProgressDrawable.setCenterRadius(10f);
            circularProgressDrawable.start();

            cardViewMinuman = itemView.findViewById(R.id.MakananCv);
            cardViewMinuman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentPesanan =new Intent(itemView.getContext(), PesananMinuman.class);
                    intentPesanan.putExtra("id_minuman", idMinuman);
                    itemView.getContext().startActivity(intentPesanan);
                }
            });

            btBeli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentPesanan =new Intent(itemView.getContext(), PesananMinuman.class);
                    intentPesanan.putExtra("id_minuman", idMinuman);
                    itemView.getContext().startActivity(intentPesanan);
                }
            });
        }
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String key = charSequence.toString();
                if(key.isEmpty()){
                    filteredMinumanList = minumanList;
                }else{
                    List<minuman> lstFiltered = new ArrayList<>();
                    for(int i=0; i<minumanList.size(); i++){
                        if(minumanList.get(i).getNama_minuman().toLowerCase().contains(key.toLowerCase())){
                            lstFiltered.add(minumanList.get(i));
                        }
                    }
                    filteredMinumanList = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMinumanList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredMinumanList = (List<minuman>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
