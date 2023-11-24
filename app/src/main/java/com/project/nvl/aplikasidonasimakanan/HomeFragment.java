package com.project.nvl.aplikasidonasimakanan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (getChildFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            displayFragment(new DaftarMakananFragment());
        }

        AppCompatImageButton btnPopupMenu = view.findViewById(R.id.btnPopupMenu);

        btnPopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        return view;
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_daftar_makanan) {
                    displayFragment(new DaftarMakananFragment());
                    return true;
                } else if (item.getItemId() == R.id.action_daftar_penyumbang) {
                    displayFragment(new DaftarPenyumbangFragment());
                    return true;
                } else if (item.getItemId() == R.id.action_dokumentasi) {
                    displayFragment(new DokumentasiFragment());
                    return true;
                } else if (item.getItemId() == R.id.action_peta) {
                    displayFragment(new MapsFragment());
                    return true;
                } else {
                    return false;
                }
            }

        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });

        popupMenu.show();
    }


    private void displayFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
