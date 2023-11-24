package com.project.nvl.aplikasidonasimakanan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.ValueEventListener;

import static android.app.Activity.RESULT_OK;

public class AkunFragment extends Fragment {

    private TextView textNama;
    private TextView textTelepon;
    private TextView textAlamat;
    private TextView textEmail;
    private Button btnLogout;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference storageReference;
    private ImageView imageProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akun, container, false);

        textNama = view.findViewById(R.id.textNama);
        textTelepon = view.findViewById(R.id.textTelepon);
        textAlamat = view.findViewById(R.id.textAlamat);
        textEmail = view.findViewById(R.id.textEmail);
        btnLogout = view.findViewById(R.id.btnLogout);
        imageProfile = view.findViewById(R.id.imageProfile);

        storageReference = FirebaseStorage.getInstance().getReference();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Log.d("AkunFragment", "DataSnapshot: " + snapshot.getValue());

                        String nama = snapshot.child("nama").getValue(String.class);
                        String telepon = snapshot.child("telepon").getValue(String.class);
                        String alamat = snapshot.child("alamat").getValue(String.class);
                        String email = currentUser.getEmail();
                        String imageURL = snapshot.child("imageURL").getValue(String.class);

                        textNama.setText("Nama : " + nama);
                        textEmail.setText("Email : " + email);
                        textAlamat.setText("Alamat : " + alamat);
                        textTelepon.setText("Nomor Hp : " + telepon);

                        // Menggunakan Glide atau Picasso untuk memuat gambar dari URL
                        if (imageURL != null) {
                            Glide.with(requireActivity())
                                    .load(imageURL)
                                    .into(imageProfile);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // Tambahkan OnClickListener untuk tombol upload foto di sini
        Button btnUploadFoto = view.findViewById(R.id.btnUploadFoto);
        btnUploadFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker(v);
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

    public void openImagePicker(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            // Mendapatkan referensi ke file di Firebase Storage
            StorageReference imageRef = storageReference.child("images").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            // Mengunggah gambar ke Firebase Storage
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Mendapatkan URL gambar setelah berhasil diunggah
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Simpan URL gambar ke dalam database
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    userRef.child("imageURL").setValue(uri.toString());

                                    showToast("Foto berhasil diunggah");
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showToast("Gagal mengunggah foto");
                        }
                    });
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
