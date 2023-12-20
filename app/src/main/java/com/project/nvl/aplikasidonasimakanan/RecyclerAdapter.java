package com.project.nvl.aplikasidonasimakanan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    // Image List
    private int[] images = {
            R.drawable.pasfoto1,
            R.drawable.pasfoto2,
            R.drawable.pasfoto3,
            R.drawable.pasfoto4,
            R.drawable.pasfoto5,
            R.drawable.pasfoto6
    };

    private String[] titles = {"Captain Amerika", "Iron Man", "Spiderman", "Scarlet Witch", "Black Widow", "Thor"};
    private String[] details = {
            "Alamat : Kuranji, Padang",
            "Alamat : Ampang, Padang",
            "Alamat : Parupuk Tabing, Padang",
            "Alamat : Bekasi",
            "Alamat : Nganjuk",
            "Alamat : Jonggol"
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemTitle;
        TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.cardImage);
            itemTitle = itemView.findViewById(R.id.cardTittle);
            itemDetail = itemView.findViewById(R.id.cardDetail);

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemImage.setImageResource(images[position]);
        holder.itemTitle.setText(titles[position]);
        holder.itemDetail.setText(details[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
}
