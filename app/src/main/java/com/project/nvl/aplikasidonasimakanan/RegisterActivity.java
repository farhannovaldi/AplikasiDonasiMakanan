package com.project.nvl.aplikasidonasimakanan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText userNama, userName, userTelepon, userPassword;
    private Button btnDaftar;
    private DatabaseReference database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi view
        userNama = findViewById(R.id.userNama);
        userName = findViewById(R.id.userName);
        userTelepon = findViewById(R.id.userTelepon);
        userPassword = findViewById(R.id.userPassword);
        btnDaftar = findViewById(R.id.btnDaftar);

        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://aplikasi-donasi-makanan-default-rtdb.firebaseio.com/");

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        // Dapatkan nilai dari EditText
        String nama = userNama.getText().toString().trim();
        String username = userName.getText().toString().trim();
        String telepon = userTelepon.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if (nama.isEmpty() || username.isEmpty() || telepon.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
        } else {
            database = FirebaseDatabase.getInstance().getReference("users");

            DatabaseReference userRef = database.child(username);

            userRef.child("username").setValue(username);
            userRef.child("nama").setValue(nama);
            userRef.child("telepon").setValue(telepon);
            userRef.child("password").setValue(password);


            Toast.makeText(this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();
            Intent register = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(register);

            finish();
        }
    }
}
