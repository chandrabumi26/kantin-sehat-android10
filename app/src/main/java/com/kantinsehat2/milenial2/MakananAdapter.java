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

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder>{
    private Context context;
    private List<makanan> makananList;
    private List<makanan> filteredMakananList;
    DisplayMetrics displayMetrics = new DisplayMetrics();


    public MakananAdapter(Context context, List<makanan> makananList){
        this.context = context;
        this.makananList = makananList;
        this.filteredMakananList = makananList;
    }

    @NonNull
    @Override
    public MakananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        return new MakananAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MakananAdapter.ViewHolder holder, int position) {
        makanan makanans = filteredMakananList.get(position);
        holder.tvNamaMakanan.setText(makanans.getNama_makanan());
        holder.tvHargaMakanan.setText(makanans.getHarga_makanan());
        holder.idMakanan = String.valueOf(makanans.getIdMakanan());
//        Glide.with(holder.ivImage.getContext()).load().apply(new RequestOptions().placeholder(holder.circularProgressDrawable)).into(holder.ivImage);

        Glide.with(context).load("https://bikinlanding.com/kantinsehat/upload/"+makanans.getGambar()).apply(new RequestOptions().placeholder(holder.circularProgressDrawable)).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return filteredMakananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircularProgressDrawable circularProgressDrawable;
        private TextView tvNamaMakanan, tvHargaMakanan;
        private String idMakanan;
        private ImageView ivImage;
        private Button btBeli;
        private CardView cardViewBeli;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaMakanan = itemView.findViewById(R.id.buatNamaMakanan);
            tvHargaMakanan = itemView.findViewById(R.id.buatHargaMakanan);
            ivImage = itemView.findViewById(R.id.buatGambarMakanan);

            circularProgressDrawable = new CircularProgressDrawable(itemView.getContext());
            circularProgressDrawable.setCenterRadius(10f);
            circularProgressDrawable.start();

            cardViewBeli = itemView.findViewById(R.id.MakananCv);
            cardViewBeli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentPesanan =new Intent(itemView.getContext(), PesananMakanan.class);
                    intentPesanan.putExtra("id", idMakanan);
                    itemView.getContext().startActivity(intentPesanan);
                }
            });
            btBeli = itemView.findViewById(R.id.beliButton);
            btBeli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentPesanan =new Intent(itemView.getContext(), PesananMakanan.class);
                    intentPesanan.putExtra("id", idMakanan);
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
                    filteredMakananList = makananList;
                }else{
                    List<makanan> lstFiltered = new ArrayList<>();
                    for(int i=0; i<makananList.size(); i++){
                        if(makananList.get(i).getNama_makanan().toLowerCase().contains(key.toLowerCase())){
                            lstFiltered.add(makananList.get(i));
                        }
                    }
                    filteredMakananList = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMakananList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredMakananList = (List<makanan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
