package com.project.nvl.aplikasidonasimakanan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AkunFragment extends Fragment {

    private TextView textNama;
    private TextView textTelepon;
    private TextView textAlamat;
    private TextView textEmail;
    private Button btnLogout; // Tambahkan ini

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akun, container, false);

        // Inisialisasi view
        textNama = view.findViewById(R.id.textNama);
        textTelepon = view.findViewById(R.id.textTelepon);
        textAlamat = view.findViewById(R.id.textAlamat);
        textEmail = view.findViewById(R.id.textEmail);
        btnLogout = view.findViewById(R.id.btnLogout); // Tambahkan ini

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Log.d("AkunFragment", "DataSnapshot: " + snapshot.getValue());

                        String nama = snapshot.child("nama").getValue(String.class);
                        String email = currentUser.getEmail();

                        textNama.setText("Nama: " + nama);
                        textEmail.setText("Email: " + email);

                        // Mengambil data telepon dan alamat dari database
                        String telepon = snapshot.child("telepon").getValue(String.class);
                        String alamat = snapshot.child("alamat").getValue(String.class);

                        Log.d("AkunFragment", "Telepon: " + telepon);
                        Log.d("AkunFragment", "Alamat: " + alamat);

                    }
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle kegagalan baca data
                }
            });
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
