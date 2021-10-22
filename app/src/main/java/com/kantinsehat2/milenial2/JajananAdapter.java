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

public class JajananAdapter extends RecyclerView.Adapter<JajananAdapter.ViewHolder> {
    private Context context;
    private List<jajanan> jajananList;
    private List<jajanan> filteredListJajanan;
    DisplayMetrics displayMetrics = new DisplayMetrics();

    public JajananAdapter(Context context, List<jajanan> jajananList){
        this.context = context;
        this.jajananList = jajananList;
        this.filteredListJajanan = jajananList;
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
        return new JajananAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        jajanan jajanans = filteredListJajanan.get(position);
        holder.tvNamaJajanan.setText(jajanans.getNama_jajan());
        holder.tvHargaJajanan.setText(jajanans.getHarga_jajan());
        holder.idJajanan = String.valueOf(jajanans.getId_jajan());
        Glide.with(holder.ivImage.getContext()).load("https://bikinlanding.com/kantinsehat/upload/"+jajanans.getGambar_jajan()).apply(new RequestOptions().placeholder(holder.circularProgressDrawable)).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return filteredListJajanan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircularProgressDrawable circularProgressDrawable;
        private TextView tvNamaJajanan, tvHargaJajanan,tvIdJajanan;
        private ImageView ivImage;
        private String idJajanan;
        private Button btBeli;
        private CardView cardViewJajanan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdJajanan = itemView.findViewById(R.id.butIdMakanan);
            tvNamaJajanan = itemView.findViewById(R.id.buatNamaMakanan);
            tvHargaJajanan = itemView.findViewById(R.id.buatHargaMakanan);
            ivImage = itemView.findViewById(R.id.buatGambarMakanan);
            btBeli = itemView.findViewById(R.id.beliButton);

            circularProgressDrawable = new CircularProgressDrawable(itemView.getContext());
            circularProgressDrawable.setCenterRadius(10f);
            circularProgressDrawable.start();

            cardViewJajanan = itemView.findViewById(R.id.MakananCv);
            cardViewJajanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentPesanan =new Intent(itemView.getContext(), PesananJajanan.class);
                    intentPesanan.putExtra("id_jajanan", idJajanan);
                    itemView.getContext().startActivity(intentPesanan);
                }
            });

            btBeli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentPesanan =new Intent(itemView.getContext(), PesananJajanan.class);
                    intentPesanan.putExtra("id_jajanan", idJajanan);
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
                    filteredListJajanan = jajananList;
                }else{
                    List<jajanan> lstFiltered = new ArrayList<>();
                    for(int i=0; i<jajananList.size(); i++){
                        if(jajananList.get(i).getNama_jajan().toLowerCase().contains(key.toLowerCase())){
                            lstFiltered.add(jajananList.get(i));
                        }
                    }
                    filteredListJajanan = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredListJajanan;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredListJajanan = (List<jajanan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
