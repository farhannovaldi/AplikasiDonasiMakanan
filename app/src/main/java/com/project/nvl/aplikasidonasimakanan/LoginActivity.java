package com.project.nvl.aplikasidonasimakanan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText userName, userPassword;
    private Button loginButton, daftarButton;

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inisialisasi view
        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.tombolLogin);
        daftarButton = findViewById(R.id.daftarButton);

        loginButton.setOnClickListener(view -> login());

        daftarButton.setOnClickListener(view -> navigateToRegister());
    }

    private void login() {
        String username = userName.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        database = FirebaseDatabase.getInstance().getReference("users");

        if(username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
        }else{
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(username).exists()) {
                        DataSnapshot userSnapshot = snapshot.child(username);
                        String storedPassword = userSnapshot.child("password").getValue(String.class);

                        if (storedPassword != null && storedPassword.equals(password)){
                            Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                            Intent masuk = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(masuk);
                        } else {
                            Toast.makeText(LoginActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Data Belum Terdaftar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });

        }
    }

    private void navigateToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
