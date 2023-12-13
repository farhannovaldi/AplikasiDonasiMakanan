package com.project.nvl.aplikasidonasimakanan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DaftarDonasiFragment extends Fragment {

    private EditText jenisMakananEditText, jumlahMakananEditText, hpDonasiEditText, jemputDonasiEditText, catatanDonasiEditText;
    private Button btnDonasi;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daftar_donasi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jenisMakananEditText = view.findViewById(R.id.jenisMakanan);
        jumlahMakananEditText = view.findViewById(R.id.jumlahMakanan);
        hpDonasiEditText = view.findViewById(R.id.hpDonasi);
        jemputDonasiEditText = view.findViewById(R.id.jemputDonasi);
        catatanDonasiEditText = view.findViewById(R.id.catatanDonasi);
        btnDonasi = view.findViewById(R.id.btnDonasi);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("donasi");

        btnDonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donasi();
            }
        });
    }

    private void donasi() {
        String jenisMakanan = Objects.requireNonNull(jenisMakananEditText.getText()).toString().trim();
        String jumlahMakanan = Objects.requireNonNull(jumlahMakananEditText.getText()).toString().trim();
        String hpDonasi = Objects.requireNonNull(hpDonasiEditText.getText()).toString().trim();
        String jemputDonasi = Objects.requireNonNull(jemputDonasiEditText.getText()).toString().trim();
        String catatanDonasi = Objects.requireNonNull(catatanDonasiEditText.getText()).toString().trim();

        if (isEmpty(jenisMakanan) || isEmpty(jumlahMakanan) || isEmpty(hpDonasi) || isEmpty(jemputDonasi)) {
            showToast("Harap isi semua kolom");
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            String timestamp = getCurrentDate();

            Donasi donasi = new Donasi(jenisMakanan, jumlahMakanan, hpDonasi, jemputDonasi, catatanDonasi, uid);

            String donasiId = databaseReference.push().getKey();
            if (donasiId != null) {
                databaseReference.child(donasiId).setValue(donasi.toMap());

                jenisMakananEditText.setText("");
                jumlahMakananEditText.setText("");
                hpDonasiEditText.setText("");
                jemputDonasiEditText.setText("");
                catatanDonasiEditText.setText("");

                showToast("Donasi berhasil disimpan");
            } else {
                showToast("Gagal menyimpan donasi");
            }
        } else {
            showToast("Silakan login terlebih dahulu");
        }
    }

    private boolean isEmpty(String value) {
        return value.isEmpty();
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
    private String getCurrentDate() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(currentDate);
    }
}
