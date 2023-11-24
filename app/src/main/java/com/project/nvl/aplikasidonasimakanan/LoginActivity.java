package com.project.nvl.aplikasidonasimakanan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button loginButton, daftarButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.tombolLogin);
        daftarButton = findViewById(R.id.daftarButton);

        mAuth = FirebaseAuth.getInstance();

        daftarButton.setOnClickListener(view -> navigateToRegister());
        loginButton.setOnClickListener(view -> login(email.getText().toString(), password.getText().toString()));
    }

    private boolean isEmpty(String value) {
        return value.isEmpty();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    private void login(String userEmail, String userPassword) {
        if (isEmpty(userEmail) || isEmpty(userPassword)) {
            showToast("Harap isi semua data");
            return;
        }

        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                reload();
                            } else {
                                showToast("Login gagal. Pengguna tidak ditemukan.");
                            }
                        } else {
                            showToast("Login gagal. Periksa kembali email dan password Anda.");
                        }
                    }
                });
    }

    private void reload() {
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Periksa apakah getParent() tidak null sebelum memanggil isAppClosed()
            if (getParent() != null && getParent() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getParent();
                if (!mainActivity.isAppClosed()) {
                    showToast("Anda sudah login sebagai " + currentUser.getEmail());
                    // Berikan opsi untuk langsung ke MainActivity atau logout
                    // Misalnya, tampilkan dialog atau navigasi langsung ke MainActivity
                } else {
                    // Jika aplikasi ditutup sebelumnya, lakukan sign out
                    FirebaseAuth.getInstance().signOut();
                }
            }
        }
    }

}
