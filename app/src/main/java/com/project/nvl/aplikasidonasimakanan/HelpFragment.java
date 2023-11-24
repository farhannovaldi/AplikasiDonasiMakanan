package com.project.nvl.aplikasidonasimakanan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HelpFragment extends Fragment {

    private EditText laporanMasalahInput;
    private Button kirimLaporanButton;
    private ProgressDialog progressBar;
    private FirebaseAuth mAuth;

    public HelpFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        // Inisialisasi view
        laporanMasalahInput = view.findViewById(R.id.laporanMasalahInput);
        kirimLaporanButton = view.findViewById(R.id.kirimLaporanButton);

        mAuth = FirebaseAuth.getInstance();

        progressBar = new ProgressDialog(getActivity());
        progressBar.setTitle("Loading");
        progressBar.setCancelable(false);

        kirimLaporanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String laporanMasalah = laporanMasalahInput.getText().toString();

                sendReportToDatabase(laporanMasalah);
            }
        });

        return view;
    }

    private void sendReportToDatabase(String report) {

        // Check id uid
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            String userId = currentUser.getUid();

            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference laporanRef = database.child("laporan_masalah").child(userId).push();
            laporanRef.setValue(report);

            showToast("Laporan berhasil dikirim");
        } else {
            showToast("Silakan login terlebih dahulu");
        }
    }


    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
