package com.project.nvl.aplikasidonasimakanan;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {
    private boolean isAppClosed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        BottomNavigationView navigationView = findViewById(R.id.navigationBot);
        navigationView.setOnItemSelectedListener(this);

        ImageButton btnPopupMenu = findViewById(R.id.btnPopupMenu);
        btnPopupMenu.setOnClickListener(this::showPopupMenu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAppClosed = true;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        int itemId = item.getItemId();

        if (itemId == R.id.frg_home) {
            fragment = new HomeFragment();
        } else if (itemId == R.id.frg_book) {
            fragment = new BantuanFragment();
        } else if (itemId == R.id.frg_help) {
            fragment = new HelpFragment();
        } else if (itemId == R.id.frg_account) {
            fragment = new AkunFragment();
        }

        return loadFragment(fragment);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            Log.d("MenuClick", "Item clicked: " + item.getItemId());
            if (item.getItemId() == R.id.action_daftar_penyumbang) {
                loadFragment(new DaftarPenyumbangFragment());
                return true;
            } else if (item.getItemId() == R.id.action_dokumentasi) {
                loadFragment(new DokumentasiFragment());
                return true;
            } else if (item.getItemId() == R.id.action_daftar) {
                loadFragment(new DaftarDonasiFragment());
                return true;
            } else {
                return false;
            }
        });

        popupMenu.setOnDismissListener(menu -> Log.d("MenuClick", "Menu dismissed"));

        popupMenu.show();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public boolean isAppClosed() {
        return isAppClosed;
    }
}
