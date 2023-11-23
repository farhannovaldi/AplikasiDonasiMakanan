package com.project.nvl.aplikasidonasimakanan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private TextView namaTextView;
    private TextView teleponTextView;
    private TextView usernameTextView;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_akun, container, false);

        namaTextView = view.findViewById(R.id.namaTextView);
        teleponTextView = view.findViewById(R.id.teleponTextView);
        usernameTextView = view.findViewById(R.id.usernameTextView);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String nama = dataSnapshot.child("nama").getValue(String.class);
                        String telepon = dataSnapshot.child("telepon").getValue(String.class);
                        String username = dataSnapshot.child("username").getValue(String.class);

                        // Set nilai TextView sesuai dengan data pengguna
                        if (nama != null) {
                            namaTextView.setText("Nama: " + nama);
                        }
                        if (telepon != null) {
                            teleponTextView.setText("Telepon: " + telepon);
                        }
                        if (username != null) {
                            usernameTextView.setText("Username: " + username);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }

        return view;
    }
}
