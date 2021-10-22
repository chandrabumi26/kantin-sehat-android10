package com.kantinsehat2.milenial2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {
    private Context context;
    private ArrayList idList;
    private ArrayList nameList;
    private ArrayList jumlahList;
    private ArrayList notesList;
    private ArrayList total_hargaList;
    private ArrayList gambarList;
    private ArrayList totalsList;
    private ArrayList forSize;
    private ArrayList idKeranjangList;
    private ArrayList<String> quantity,totaal_harga;
    private List<keranjang> keranjangList;
    List<keranjang> krn = new ArrayList<>();
    DisplayMetrics displayMetrics = new DisplayMetrics();


    public KeranjangAdapter(ArrayList idList,ArrayList nameList, ArrayList jumlahList,ArrayList notesList, ArrayList total_hargaList, ArrayList gambarList, ArrayList totalsList, ArrayList forSize, ArrayList idKeranjangList, Context context){
        this.idList = idList;
        this.nameList = nameList;
        this.jumlahList = jumlahList;
        this.notesList = notesList;
        this.total_hargaList = total_hargaList;
        this.gambarList = gambarList;
        this.totalsList = totalsList;
        this.forSize = forSize;
        this.idKeranjangList = idKeranjangList;
        quantity = new ArrayList<>();
        totaal_harga = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.keranjang_items,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String idFix = (String) idList.get(position);
        final String nama = (String) nameList.get(position);
        final String jumlah = (String) jumlahList.get(position);
        final String notes = (String) notesList.get(position);
        final String harga = (String) total_hargaList.get(position);
        final String gambar = (String) gambarList.get(position);
        final int qty = (Integer) forSize.get(position);
        final String idPrimary = (String) idKeranjangList.get(position);
        holder.idKeranjang = idPrimary;
        holder.tvKeranjangName.setText(nama);
        /*set harga*/
        int jumlahHarga = Integer.parseInt(jumlah) * Integer.parseInt(harga);
        holder.tvKeranjangHarga.setText(String.valueOf(jumlahHarga));
        holder.enb.setNumber(jumlah);

        holder.tvKeranjangNotes.setText(notes);
        holder.tvKeranjangId.setText(idFix);
        Glide.with(holder.ivKeranjangImg.getContext()).load("https://bikinlanding.com/kantinsehat/upload/"+gambar).apply(new RequestOptions().placeholder(holder.circularProgressDrawable)).into(holder.ivKeranjangImg);

        /*Begin*/
        String [] cut =  new String[idList.size()];
        String [] cit = new String[idList.size()];
        int [] cat = new int[forSize.size()];
        if(position < quantity.size()){
            quantity.set(position, jumlah);
            totaal_harga.set(position, String.valueOf(jumlahHarga));
        }else if(position >= quantity.size()){
            quantity.add(position, jumlah);
            totaal_harga.add(position, String.valueOf(jumlahHarga));
        }
        holder.enb.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                int bb =holder.getAdapterPosition();
                    if(position == bb){
                        cut[position] = holder.enb.getNumber();
                        int hargaKali = Integer.parseInt(harga);
                        int qty = Integer.parseInt(cut[position]);
                        String totalHarga = String.valueOf(hargaKali*qty);
                        holder.tvKeranjangHarga.setText(totalHarga);
                        quantity.set(position, cut[position]);
                        String abcde = holder.tvKeranjangHarga.getText().toString();
                        totaal_harga.set(position, abcde);
                        cit[position] = holder.tvKeranjangHarga.getText().toString();
                        cat[position] =Integer.parseInt(cit[position]);
                        int df = 0;
                        for(int i =0; i<cat.length; i++){
                            df +=cat[i];
                        }
                        holder.totalBeli = df;
                        forSize.set(position,df);
                        /*delete kalo 0*/
                        if(qty==0){
                            holder.mydb = new DataHelper(holder.ivKeranjangImg.getContext());
                            Boolean res = holder.mydb.deleteById(holder.idKeranjang);
                            if(res = true){
                                Toast.makeText(holder.ivKeranjangImg.getContext(), holder.tvKeranjangName.getText().toString()+" berhasil dihapus", Toast.LENGTH_SHORT).show();
                                Intent intent = ((Activity) holder.ivKeranjangImg.getContext()).getIntent();
                                ((Activity) holder.ivKeranjangImg.getContext()).finish();
                                ((Activity) holder.ivKeranjangImg.getContext()).startActivity(intent);

                            }
                            else{
                                Toast.makeText(holder.ivKeranjangImg.getContext(), "gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                        /*/delete kalo 0*/
                    }
                    int[] duts =new int[forSize.size()];
                    int qq = 0;
                    for(int i=0; i<forSize.size(); i++){
                        duts[i] =(int)forSize.get(i);
                        qq += duts[i];
                    }
                    /*Tinggal di broadcast*/
                    String kontol = String.valueOf(qq);
                    Intent intent = new Intent("custom-message");
                    intent.putExtra("total",kontol);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

//                    holder.tvKeranjangName.setText(kontol);
            }
        });

    }

    public List<keranjang> gg(List<keranjang> katrin){
        for(int i=0; i<idList.size(); i++){
            keranjang katrins = new keranjang();
            String untukId = (String) idList.get(i);
            katrins.setId_item(Integer.parseInt(untukId));
            katrins.setNama((String) nameList.get(i));
            String hargaa = (String) total_hargaList.get(i);
            katrins.setHarga(Integer.parseInt(hargaa));

            katrins.setJumlah(Integer.parseInt(quantity.get(i)));
            katrins.setNotes((String) notesList.get(i));
            katrins.setTotal_harga(Integer.parseInt(totaal_harga.get(i)));
            katrin.add(i, katrins);
        }
        return katrin;
    }

    @Override
    public int getItemCount() {
        return idList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircularProgressDrawable circularProgressDrawable;
        private TextView tvKeranjangName, tvKeranjangHarga, tvKeranjangJumlah, tvKeranjangNotes, tvKeranjangId;
        private ImageView ivKeranjangImg;
        private ElegantNumberButton enb;
        private Button btDelete;
        private DataHelper mydb=null;
        private int totalBeli;
        String img,qtyYeah,idKeranjang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circularProgressDrawable = new CircularProgressDrawable(itemView.getContext());
            circularProgressDrawable.setCenterRadius(10f);
            circularProgressDrawable.start();
            tvKeranjangName = itemView.findViewById(R.id.keranjangName);
            tvKeranjangHarga = itemView.findViewById(R.id.keranjangHarga);
            ivKeranjangImg = itemView.findViewById(R.id.keranjangImg);
            enb = itemView.findViewById(R.id.keranjangJumlah);
//            tvKeranjangJumlah = itemView.findViewById(R.id.keranjangJumlah);
            tvKeranjangNotes = itemView.findViewById(R.id.keranjangNotes);
            tvKeranjangId = itemView.findViewById(R.id.keranjangId);
            btDelete = itemView.findViewById(R.id.deleteById);
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = tvKeranjangId.getText().toString();
                    mydb = new DataHelper(itemView.getContext());
                    Boolean res = mydb.deleteById(idKeranjang);
                    if(res = true){
                        Toast.makeText(itemView.getContext(), tvKeranjangName.getText().toString()+" berhasil dihapus", Toast.LENGTH_SHORT).show();
                        Intent intent = ((Activity) itemView.getContext()).getIntent();
                        ((Activity) itemView.getContext()).finish();
                        ((Activity) itemView.getContext()).startActivity(intent);

                    }
                    else{
                        Toast.makeText(itemView.getContext(), "gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }



}
