package com.project.nvl.aplikasidonasimakanan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText userNama, userPasswordConf, userTelepon, userPassword, userAlamat, userEmail;
    private Button btnDaftar;
    private ProgressDialog progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNama = findViewById(R.id.userNama);
        userTelepon = findViewById(R.id.userTelepon);
        userAlamat = findViewById(R.id.userAlamat);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        userPasswordConf = findViewById(R.id.userPasswordConf);
        btnDaftar = findViewById(R.id.btnDaftar);
        mAuth = FirebaseAuth.getInstance();

        progressBar = new ProgressDialog(RegisterActivity.this);
        progressBar.setTitle("Loading");
        progressBar.setCancelable(false);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        String nama = userNama.getText().toString().trim();
        String telepon = userTelepon.getText().toString().trim();
        String alamat = userAlamat.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String passwordConf = userPasswordConf.getText().toString().trim();

        if (isEmpty(nama) || isEmpty(telepon) || isEmpty(password) || isEmpty(passwordConf) || isEmpty(alamat) || isEmpty(email)) {
            showToast("Harap isi semua kolom");
            return;
        }

        if (!password.equals(passwordConf)) {
            showToast("Masukkan password yang sama");
            return;
        }

        progressBar.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.dismiss();
                if (task.isSuccessful() && task.getResult() != null) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser != null) {
                        // Hanya menyimpan email ke dalam database, informasi lainnya akan disimpan di database
                        saveUserToDatabase(firebaseUser.getUid(), email, nama, telepon, alamat);

                        showToast("Registrasi berhasil");
                        reload();
                    } else {
                        showToast("Register Gagal");
                    }
                } else {
                    showToast(task.getException().getLocalizedMessage());
                }
            }
        });
    }

    private void saveUserToDatabase(String userId, String email, String nama, String telepon, String alamat) {
        // Mendapatkan referensi ke database
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        // Mendapatkan referensi ke "users"
        DatabaseReference usersRef = database.child("users");

        // Menyimpan informasi pengguna ke dalam database
        usersRef.child(userId).child("email").setValue(email);
        usersRef.child(userId).child("nama").setValue(nama);
        usersRef.child(userId).child("telepon").setValue(telepon);
        usersRef.child(userId).child("alamat").setValue(alamat);
    }

    private boolean isEmpty(String value) {
        return value.isEmpty();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // ...
    private void reload() {
        FirebaseAuth.getInstance().signOut();
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
        Log.d("Navigation", "Navigating to LoginActivity after registration");
    }
// ...



    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }
}
