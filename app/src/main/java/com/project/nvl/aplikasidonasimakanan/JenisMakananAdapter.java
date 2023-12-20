package com.project.nvl.aplikasidonasimakanan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JenisMakananAdapter extends RecyclerView.Adapter<JenisMakananAdapter.ViewHolder> {

    private int[] images = {
            R.drawable.makanan1,
            R.drawable.makanan2,
            R.drawable.makanan3,
            R.drawable.makanan4,
            R.drawable.makanan5,
            R.drawable.makanan6,
            R.drawable.makanan7,
            R.drawable.makanan8,
            R.drawable.makanan9,
            R.drawable.makanan10
    };

    private String[] jenisMakanan = {
            "Nasi Goreng",
            "Mie Goreng",
            "Sate Ayam",
            "Bakso",
            "Soto Ayam",
            "Nasi Bungkus",
            "Pecel Lele",
            "Rendang",
            "Ayam Goreng",
            "Nasi Uduk"
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView jenisMakananImage;
        TextView jenisMakananText;

        public ViewHolder(View itemView) {
            super(itemView);
            jenisMakananImage = itemView.findViewById(R.id.homeImage);
            jenisMakananText = itemView.findViewById(R.id.homeTittle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    // Implement your click logic here
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.jenisMakananImage.setImageResource(images[position]);
        holder.jenisMakananText.setText(jenisMakanan[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
}
