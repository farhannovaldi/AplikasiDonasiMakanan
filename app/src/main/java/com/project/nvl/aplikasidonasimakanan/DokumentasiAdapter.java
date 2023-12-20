package com.project.nvl.aplikasidonasimakanan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class DokumentasiAdapter extends RecyclerView.Adapter<DokumentasiAdapter.DokumenHolder> {

    // Image List
    private int[] images = {
            R.drawable.donasi1,
            R.drawable.donasi2,
            R.drawable.donasi3,
            R.drawable.donasi4,
            R.drawable.donasi5
    };

    private String[] titles = {"Panti Asuhan Ceria", "Warga di Jalan", "Rumah Warga", "Pesantren Al Mukmin", "Komplek Cina"};
    private String[] details = {
            "Alamat : Ampang, Padang",
            "Alamat : Jalan Cendrawasih, Padang",
            "Alamat : Parupuk Tabing, Padang",
            "Alamat : Lubuk Alung, Padang Pariaman",
            "Alamat : Jalan Niaga, Padang"
    };

    public class DokumenHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemDetail;

        public DokumenHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.dokumentasiImage);
            itemTitle = itemView.findViewById(R.id.dokumentasiTittle);
            itemDetail = itemView.findViewById(R.id.dokumentasiDetail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Snackbar.make(v, "Ini adalah " + titles[position], Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show();
                }
            });
        }
    }

    @NonNull
    @Override
    public DokumenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dokumentasi, parent, false);
        return new DokumenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DokumenHolder holder, int position) {
        holder.itemImage.setImageResource(images[position]);
        holder.itemTitle.setText(titles[position]);
        holder.itemDetail.setText(details[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
}
